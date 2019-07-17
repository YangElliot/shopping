package com.qf.v13commonservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.v13.api.IEmailService;
import com.qf.v13.common.pojo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @Author: YangRuiGuang
 * @Date: 2019/6/25 20:29
 */
@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${mail.fromAddr}")
    private String fromAddr;


    @Override
    public ResultBean send(String to, String subject, String text) {

        MimeMessage mailMessage=javaMailSender.createMimeMessage();


        try {
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage,true);

            helper.setFrom(fromAddr);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text,true);
            javaMailSender.send(mailMessage);
            System.out.println("发送邮件成功");
            return new ResultBean("200","发送成功");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return new ResultBean("500","发送失败");
    }

}
