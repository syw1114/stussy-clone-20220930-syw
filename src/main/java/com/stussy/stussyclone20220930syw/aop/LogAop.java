package com.stussy.stussyclone20220930syw.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

@Slf4j //log4j 라이브러리가 내장됨.
@Aspect
@Component
public class LogAop {

    @Pointcut("execution(* com.stussy.stussyclone20220930syw.api.*Api.*(..))")

    private void pointCut(){}

    @Pointcut("@annotation(com.stussy.stussyclone20220930syw.aop.annotation.LogAspect)")
    private void annotionPotinCut(){}

    @Around("annotionPotinCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{


        //다운캐스팅을함.
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();

        String className = codeSignature.getDeclaringTypeName();
        String methodName = codeSignature.getName();

        //파라미터들의 이름을 가져올수있음.
        //다운캐스팅을 안하면 파러미터네임을 가져오지못함.
        String[] parameterNames = codeSignature.getParameterNames();
        //매개변수 값을들고옴
        Object[] args = joinPoint.getArgs();

        for(int i =0; i< parameterNames.length; i++){
            log.info("<<<< Parameter info >>>> {}.{} >>>> [{}: {}]",className, methodName, parameterNames[i], args[i]);
        }


        Object result = joinPoint.proceed();

        log.info("<<<< Return >>>> {}.{} >>> [{}: {}]",className, methodName, result);

        return result;
    }
}
