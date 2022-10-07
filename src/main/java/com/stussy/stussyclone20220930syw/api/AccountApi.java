package com.stussy.stussyclone20220930syw.api;

import com.stussy.stussyclone20220930syw.aop.annotation.LogAspect;
import com.stussy.stussyclone20220930syw.dto.CMRespDto;
import com.stussy.stussyclone20220930syw.dto.RegisterReqDto;
import com.stussy.stussyclone20220930syw.dto.validation.ValidationSequence;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/account")

@RestController
public class AccountApi {

    //우리가만든 로그를 적용해라.
    @LogAspect

    @PostMapping("/register") // json문자열 dto를 받을때. RequestBody를 쓴다.
    public ResponseEntity<?> register (@Validated(ValidationSequence.class) @RequestBody RegisterReqDto registerReqDto, BindingResult bindingResult){
        //ResponseEntitiy는  사용자의  HttpRequest에 대한 응답 데이터
         return ResponseEntity.created(null).body(new CMRespDto<>("회원가입 성공", registerReqDto));
    }

}
