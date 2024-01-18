package com.meta.metaway.global;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@EnableAspectJAutoProxy
@Component
@Aspect
public class PerformanceMonitoringAspect {
	
    private static final long THRESHOLD = 1000; // 1초
    
    @Autowired
    private JavaMailSender javaMailSender;
    
	Logger logger = LoggerFactory.getLogger(this.getClass());


    @Around("execution(* com.meta.metaway..service.*.*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        logger.info("{} executed in {}ms", joinPoint.getSignature(), executionTime);

        if (executionTime > THRESHOLD) {
            // 임계치를 초과하는 경우 이메일로 경고 보내기
            sendEmailAlert("임계치를 초과했습니다!", joinPoint.getSignature().toString(), executionTime);
        
            logger.warn("Execution time exceeded the threshold! Sent an email alert.");

        }

        return result;
    }
    
    private void sendEmailAlert(String subject, String methodSignature, long executionTime) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("lala9663@naver.com");
        message.setSubject(subject);
        message.setText("시간 초과: " + methodSignature + "\n초과 시간: " + executionTime + "ms" + "\n 이 부분을 보완하세요");

        javaMailSender.send(message);
    }
    
    @After("execution(* com.meta.metaway.global.GlobalExceptionHandler.*(..))")
    public void logExceptionHandling(JoinPoint joinPoint) {
    	String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        Object[] args = joinPoint.getArgs();

        logger.info("예외처리 메소드 '{}' 클래스 '{}' 실행 - Arguments: {}", methodName, className, args);
    }
}
