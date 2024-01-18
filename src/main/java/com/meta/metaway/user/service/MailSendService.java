package com.meta.metaway.user.service;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class MailSendService {
    @Autowired
    private JavaMailSender mailSender;

    private static final String SESSION_AUTH_NUMBER_KEY = "authNumber";

    // 임의의 6자리 양수를 반환합니다.
    public int makeRandomNumber() {
        Random r = new Random();
        String randomNumber = "";
        for (int i = 0; i < 6; i++) {
            randomNumber += Integer.toString(r.nextInt(10));
        }
        return Integer.parseInt(randomNumber);
    }

    public String joinEmail(String email, HttpSession session) {
        // 인증번호 생성
        int authNumber = makeRandomNumber();

        // 세션에 인증번호 저장
        session.setAttribute(SESSION_AUTH_NUMBER_KEY, authNumber);

        System.out.println("세션에 저장된 인증번호: " + session.getAttribute(SESSION_AUTH_NUMBER_KEY));

        String setFrom = "lala96632040@gmail.com";
        String toMail = email;
        String title = "인증 메일 발송 완료 ❕";
        String content = "메타웨이를 방문해주셔서 감사합니다." + "<br><br>" +
                         "인증번호는 " + authNumber + "입니다." + "<br>" +
                         "인증번호를 입력해주세요.";
        mailSend(setFrom, toMail, title, content);

        return Integer.toString(authNumber);
    }

    // 이메일을 전송합니다.
    public void mailSend(String setFrom, String toMail, String title, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public boolean verifyCode(String usercode, HttpSession session) {
        Integer authNumber = (Integer) session.getAttribute(SESSION_AUTH_NUMBER_KEY);

        return usercode.equals(String.valueOf(authNumber));
    }
}