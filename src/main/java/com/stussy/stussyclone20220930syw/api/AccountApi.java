package com.stussy.stussyclone20220930syw.api;

import com.stussy.stussyclone20220930syw.aop.annotation.LogAspect;
import com.stussy.stussyclone20220930syw.dto.CMRespDto;
import com.stussy.stussyclone20220930syw.dto.RegisterReqDto;
import com.stussy.stussyclone20220930syw.dto.validation.ValidationSequence;
import com.stussy.stussyclone20220930syw.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;


@RequestMapping("/api/account")
@RestController
@RequiredArgsConstructor //accountService에 객체 주입이 일어난다. 생성자인데 , 매개변수안에 필수로 들어와야한다.
public class AccountApi {

    //final 상수 초기화를해주어야함 , 파이널 사용하면 기본생성자 안만들어짐. -> 기본생성자를 만들어주면 초기화가안일어남
    private final AccountService accountService;

    //우리가만든 로그를 적용해라.
    @LogAspect

    @PostMapping("/register") // json문자열 dto를 받을때. RequestBody를 쓴다.
    public ResponseEntity<?> register (@Validated(ValidationSequence.class) @RequestBody RegisterReqDto registerReqDto, BindingResult bindingResult) throws Exception{

        //서비스한테 DTO를 던져줌.
        accountService.duplicateEmail(registerReqDto);
        //예외안터지면 밑에 실행 하지만 터지면 위에만 실행.
        accountService.register(registerReqDto);

       //ResponseEntitiy는  사용자의  HttpRequest에 대한 응답 데이터
         return ResponseEntity.created(URI.create("/account/login")).body(new CMRespDto<>("회원가입 성공", registerReqDto.getEmail()));



    }
}
