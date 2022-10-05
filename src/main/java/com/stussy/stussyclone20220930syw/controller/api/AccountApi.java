package com.stussy.stussyclone20220930syw.controller.api;

import com.stussy.stussyclone20220930syw.controller.dto.RegisterReqDto;
import com.stussy.stussyclone20220930syw.controller.dto.validation.ValidationSequence;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/account")

@RestController
public class AccountApi {

    @PostMapping("/register") // json문자열 dto를 받을때. RequestBody를 쓴다.
    public ResponseEntity<?> register (@Validated(ValidationSequence.class) @RequestBody RegisterReqDto registerReqDto, BindingResult bindingResult){
        //ResponseEntitiy는  사용자의  HttpRequest에 대한 응답 데이터

        if(bindingResult.hasErrors()){
            Map<String, String> errorMap = new HashMap<String,String>();

            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError fieldError : fieldErrors) {
                System.out.println("필드명: " + fieldError.getField());
                System.out.println("에러 메세지: " + fieldError.getDefaultMessage());
                errorMap.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
            return  ResponseEntity.badRequest().body(errorMap);
         }
         return ResponseEntity.created(null).body(null);
    }
}
