package com.example.mqprovider.push;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PushInQueue {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    public boolean sendMessages(String mobile){
        //TODO 生成验证码
        String checkcode = RandomStringUtils.randomNumeric(4);
        //输出
        System.out.println("验证码为:"+checkcode);
        //使用阿里云发送短信
        Map<String ,String> map = new HashMap<>();
        map.put("mobile",mobile);
        map.put("templateCode","SMS_23423423");
        //根据短信模板中的参数进行
        Map<String,String> 	mapParams = new HashMap<>();
        mapParams.put("code",checkcode);
        //根据短信的动态参数,进行解析
        String templateJsonParse = JSONObject.toJSONString(mapParams);
        map.put("templateJsonParse",templateJsonParse);
        rabbitTemplate.convertAndSend("","itcast-sms",map);
        //将redis存储5分钟
        redisTemplate.opsForValue().set(mobile,checkcode,5, TimeUnit.MINUTES);
        return true;
    }
}
