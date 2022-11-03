package com.stussy.stussyclone20220930syw.config;

import com.stussy.stussyclone20220930syw.security.AuthFailureHandler;
import com.stussy.stussyclone20220930syw.service.PrincipalOauth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalOauth2Service principalOauth2Service;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.httpBasic().disable();
        http.authorizeRequests()
                .antMatchers("/account/mypage", "/index" , "/checkout")
                .authenticated()
//              .antMatchers("/admin/**")
//              .hasRole("ADMIN")
                .antMatchers("/admin/**", "/api/admin/**")
                .permitAll()
                .anyRequest()
                .permitAll()
                .and()
                .formLogin()
                .usernameParameter("email")
                .loginPage("/account/login") // login page Get요청
                .loginProcessingUrl("/account/login") // login service Post요청. //매핑주소임.
                .failureHandler(new AuthFailureHandler())
                .defaultSuccessUrl("/collections/all")
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(principalOauth2Service)
                .and()
                .defaultSuccessUrl("/index"); // 돌려줄곳이없으면 여기로
    }
}
