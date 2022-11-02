package com.stussy.stussyclone20220930syw.service;

import com.stussy.stussyclone20220930syw.domain.User;
import com.stussy.stussyclone20220930syw.repository.AccountRepository;
import com.stussy.stussyclone20220930syw.security.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalOauth2Service extends DefaultOAuth2UserService {
    //데이터베이스 연결.
    private final AccountRepository accountRepository;

    //
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("oAuth2User: {}" , oAuth2User.getAttributes());
        log.info("userRequest: {}" , userRequest.getClientRegistration());
        String provider = userRequest.getClientRegistration().getClientName();
        PrincipalDetails principalDetails = null;
        try {
            principalDetails = getPrincipalDetails(provider, oAuth2User.getAttributes());
        } catch (Exception e) {
            throw new OAuth2AuthenticationException("login failed");
        }

        return principalDetails;
    }
    private PrincipalDetails getPrincipalDetails(String provider, Map<String,Object> attributes) throws Exception {
        User user = null;

        Map<String, Object> oauth2Attributes = null;
        String email = null;


        if(provider.equalsIgnoreCase("google")) {
            oauth2Attributes = attributes;
        }else if(provider.equalsIgnoreCase("naver")){
            //맵에서 꺼내서 넣어줘야함. 그럼 공통적인 똑같은 맵을 가짐.
            oauth2Attributes = (Map<String,Object>) attributes.get("response");
        }
        email =(String)oauth2Attributes.get("email");


        user = accountRepository.findUserByEmail(email);

        if(user == null) {
            // 회원가입

           user = User.builder()
                   .email(email)
                   .password(new BCryptPasswordEncoder().encode(UUID.randomUUID().toString()))
                   .name((String)attributes.get("name"))
                   .provider(provider)
                   .role_id(1)
                   .build();

           accountRepository.saveUser(user);

        } else if(user.getProvider() == null){
            // 연동
            /* 업데이트 처리 */
        }
        return new PrincipalDetails(user,attributes);
    }
}
