package ewhacodic.demo.controller;

import ewhacodic.demo.util.MailUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@RequiredArgsConstructor
@Controller
public class MailController {

    @RequestMapping(value = "/checkMail", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> SendMail(String mail, HttpSession session) {

        Map<String, Object> map = new HashMap<>();
        Random random = new Random();
        JavaMailSender javaMailSender = new JavaMailSenderImpl();
        //MailUtils mailUtils = new MailUtils(javaMailSender); //내가 추가한 코드
        String key = "";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail); // 스크립트에서 보낸 메일을 받을 사용자 이메일 주소
        // 입력 키를 위한 코드
        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(25) + 65; // A~Z까지 랜덤 알파벳 생성
            key += (char) index;
        }
        int numIndex = random.nextInt(8999) + 1000; // 4자리 정수를 생성
        key += numIndex;
        message.setSubject("이화코딕 회원가입을 위한 인증번호 전송");
        message.setText("인증 번호 : " + key);
        message.setFrom("EWHA CODIC");
        javaMailSender.send(message);
        map.put("key", key);
        return map;
    }
}
