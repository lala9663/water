package com.meta.metaway.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
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


    @Around("execution(* com.meta.metaway..service.*.*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

    	Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("{} executed in {}ms", joinPoint.getSignature(), executionTime);

        if (executionTime > THRESHOLD) {
            // 임계치를 초과하는 경우 이메일로 경고 보내기
            sendEmailAlert("Method execution time exceeded threshold!", joinPoint.getSignature().toString(), executionTime);
        }

        return result;
    }
    
    private void sendEmailAlert(String subject, String methodSignature, long executionTime) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("lala9663@naver.com");
        message.setSubject(subject);
        message.setText("시간 초과: " + methodSignature + "\n초과 시간: " + executionTime + "ms");

        javaMailSender.send(message);
    }
}
