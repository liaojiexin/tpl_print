package com.example.mqconsumer.listener;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.example.mqconsumer.utils.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "itcast-sms")
public class SmsListener {
    @Autowired
    private SmsUtil smsUtil;
    /**
     * 发送短信
     */
    @RabbitHandler
    public void sendSms(Map<String,String> message){
        try {
            //System.out.println("手机号:"+message.get("mobile"));
            //System.out.println("验证码:"+message.get("code"));
            String mobile = message.get("mobile");
            String templateJsonParse = message.get("templateJsonParse");
            String templateCode = message.get("templateCode");
            System.out.println(mobile+" "+templateJsonParse+" "+ templateCode);
            SendSmsResponse sendSmsResponse = smsUtil.sendSms(mobile, templateCode, templateJsonParse);
            System.out.println(sendSmsResponse.getMessage());
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
 
}