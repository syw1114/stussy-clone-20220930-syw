package com.stussy.stussyclone20220930syw.service;

import com.stussy.stussyclone20220930syw.dto.RegisterReqDto;

public interface AccountService {
    //회원가입은 회원가입만진행 , 중복은 중복만 진행하게  따로 분리시킴.

    public void duplicateEmail(RegisterReqDto registerReqDto) throws Exception;
    public void register(RegisterReqDto registerReqDto) throws Exception;

}
