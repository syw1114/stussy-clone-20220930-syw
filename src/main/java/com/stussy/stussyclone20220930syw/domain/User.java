package com.stussy.stussyclone20220930syw.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class User {
    private String id;
    private String email;
    private String password;
    private String name;
    private String provider;
    private int role_id;
    private LocalDateTime create_date;
    private LocalDateTime update_date;

    //롤은 우리가만든 타입이다.그래서 Mybatis가 그냥 줄수없음 그래서 account.xml에 role 타입을 만든것.
    private Role role;
}
