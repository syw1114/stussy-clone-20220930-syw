package com.stussy.stussyclone20220930syw.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

//AllArgsConstructor : 해당 객체 내에 있는 모든 변수들을 인수로 받는 생성자를 만들어내는 어노테이션
@AllArgsConstructor
@Data
public class CMRespDto<T> {
    private String msg;
    private T data;
}
