package com.stussy.stussyclone20220930syw.aop;

import com.stussy.stussyclone20220930syw.exception.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class ValidationAop {
    //get* 으로 시작하는 메소드명 전체한테 적용해라.
    // com 앞에 * 자리 반환자료형.
    @Pointcut("execution(* com.stussy.stussyclone20220930syw..*Api.*(..))")
    private  void executionPointCut(){}

    @Around("executionPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        BeanPropertyBindingResult bindingResult = null;
        for(Object arg : args) {
            if(arg.getClass() == BeanPropertyBindingResult.class){
                bindingResult = (BeanPropertyBindingResult) arg;
                break;
            }
        }

//      에러가 있으면 if 실행
        if(bindingResult.hasErrors()){

            //에러맵을 만들어냄
            Map<String, String> errorMap = new HashMap<String,String>();

            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError fieldError : fieldErrors) {
                errorMap.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
            //에러가있으니 예외 처리를 할 것이다.
            //생성자 , 예외를 생성하면서 강제로 예외를 발생시킴.
            throw  new CustomValidationException("Validation Error", errorMap);
        }


        Object result = null;
        result = joinPoint.proceed();

        return result;
    }
}
