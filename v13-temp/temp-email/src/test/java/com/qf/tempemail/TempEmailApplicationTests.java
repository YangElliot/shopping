package com.qf.tempemail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TempEmailApplicationTests {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${mail.fromAddr}")
    private String fromAddr;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void contextLoads() {

        SimpleMailMessage mailMessage= new SimpleMailMessage();
        mailMessage.setFrom(fromAddr);
        mailMessage.setTo(fromAddr);
        mailMessage.setSubject("my first e-mail");
        mailMessage.setText("hello e-mail");

        javaMailSender.send(mailMessage);
        System.out.println("发送邮件成功");

    }

    @Test
    public void templateTest() {

        MimeMessage mailMessage=javaMailSender.createMimeMessage();


        try {
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage,true);

            helper.setFrom(fromAddr);
            helper.setTo(fromAddr);
            helper.setSubject("i dn know how many e-mail");
            //用模板的方式

            Context context = new Context();
            context.setVariable("username","杨睿广");
            String text = templateEngine.process("jihuo.html", context);

            helper.setText(text,true);
            javaMailSender.send(mailMessage);
            System.out.println("发送邮件成功");

        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }

}
