package com.stussy.stussyclone20220930syw.api.advice;

import com.stussy.stussyclone20220930syw.dto.CMRespDto;
import com.stussy.stussyclone20220930syw.exception.CustomValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;


//레스트컨트롤러를 달아줘야함. 이유 :  Json 형태로 객체 데이터를 반환 해야해서.
@RestController
//응답으로 객체를 리턴해야한다면 레스트컨트롤러어드바이스.
@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<?> validationErrorException(CustomValidationException e) {

        //new CMRespDto 항상 유지 / 그안에는 메세지와 데이터가 들어와야한다.
        //이형식은 계속 유지하돼 badRequest부분만 바뀔것이다.
        return ResponseEntity.badRequest().body(new CMRespDto<>(e.getMessage(),e.getErrorMap()));

    }

}
