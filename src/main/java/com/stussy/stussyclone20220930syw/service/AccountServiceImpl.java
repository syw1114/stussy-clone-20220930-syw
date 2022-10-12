package com.stussy.stussyclone20220930syw.service;

import com.stussy.stussyclone20220930syw.domain.User;
import com.stussy.stussyclone20220930syw.dto.RegisterReqDto;
import com.stussy.stussyclone20220930syw.exception.CustomValidationException;
import com.stussy.stussyclone20220930syw.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public void register(RegisterReqDto registerReqDto) throws Exception {

        User user = accountRepository.findUserByEmail(registerReqDto.getEmail());
        if(user != null){
            Map<String,String> errorMap = new HashMap<String,String>();
            errorMap.put("email", "이미 사용중인 이메일 주소 입니다.");

            throw new CustomValidationException("Duplicate email", errorMap);
        }

    }
}
