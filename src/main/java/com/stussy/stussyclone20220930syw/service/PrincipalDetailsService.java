package com.stussy.stussyclone20220930syw.service;

import com.stussy.stussyclone20220930syw.domain.User;
import com.stussy.stussyclone20220930syw.exception.CustomInternalServerErrorException;
import com.stussy.stussyclone20220930syw.repository.AccountRepository;
import com.stussy.stussyclone20220930syw.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;
    @Override
    //UserDetails에 DB정보를 넣어서 리턴해줄것임.
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = null;

        try{
           user = accountRepository.findUserByEmail(email);
           log.info("{}", user);
        }catch (Exception e){
            throw new CustomInternalServerErrorException("회원 정보 조회 오류");
        }
        if(user == null){
            throw new UsernameNotFoundException("잘 못된 사용자 정보");
        }
        return new PrincipalDetails(user);
    }
}
