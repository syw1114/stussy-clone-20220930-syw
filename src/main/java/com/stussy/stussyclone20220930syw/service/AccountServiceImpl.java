package com.stussy.stussyclone20220930syw.service;

import com.stussy.stussyclone20220930syw.domain.User;
import com.stussy.stussyclone20220930syw.dto.RegisterReqDto;
import com.stussy.stussyclone20220930syw.exception.CustomInternalServerErrorException;
import com.stussy.stussyclone20220930syw.exception.CustomValidationException;
import com.stussy.stussyclone20220930syw.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public void duplicateEmail(RegisterReqDto registerReqDto) throws Exception {

        //User이 엔티티객체이다. 이 user 가 not null 이면  (이미 사용중이면) 에러 맵을 만들어서 예외처리를 한다.
        User user = accountRepository.findUserByEmail(registerReqDto.getEmail());
        if(user != null){
            Map<String,String> errorMap = new HashMap<String,String>();
            errorMap.put("email", "이미 사용중인 이메일 주소 입니다.");
            //얘가 예외가 터지면 RestHandler가 잡아서 validatinErrorException가 일어나라. errorMap을 던져줌
            throw new CustomValidationException("Duplicate email", errorMap);
        }
        //예외가 일어나지 않으면 밑에부터 실행. -> if가아니다
    }
    @Override
    // 회원가입 진행
    public void register(RegisterReqDto registerReqDto) throws Exception{
        User user = registerReqDto.toEntity();
        //accountRepository에 saveuser에 user을 던져줌.
        int result = accountRepository.saveUser(user);

        if(result == 0){
            //문제가 있다면 에러생성. validation 오류가아님. -> 데이터베이스로 넘어가다가 오류가 뜬거라 서버문제, 클라이언트문제가아님.
            throw new CustomInternalServerErrorException("회원가입중 문제가 발생");
        }

    }
}
