package com.example.mqconsumer.send;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Date;
import java.util.Map;

public class PushToQueue {

    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    //发送邮件
    public void SendMessage(Map<String,String> map){
        //1.构建一个邮件对象
        SimpleMailMessage message = new SimpleMailMessage();
        //2.设置邮件主题
        message.setSubject("验证码");
        //3.设置邮件发送者
        message.setFrom(map.get("from"));
        //4.设置邮件接收者，可以有多个接收者
        message.setTo(map.get("to"));
        //5.设置邮件抄送人，可以有多个抄送人
        message.setCc(map.get("cc"));
        //6.设置隐秘抄送人，可以有多个
        message.setBcc(map.get("bcc"));
        //7.设置邮件发送日期
        message.setSentDate(new Date());
        //8.设置邮件的正文
        message.setText(map.get("text"));
        //9.发送邮件
        javaMailSender.send(message);
    }
}
