package com.example.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.example.auth.service.JwtAuthService;
import com.example.base.pojo.PageParam;
import com.example.base.pojo.TplUser;
import com.example.base.pojo.response.ResultBody;
import com.example.base.pojo.response.ResultCode;
import com.example.base.utils.ObjectUtil;
import com.example.base.utils.SnowflakeIdWorker;
import com.example.mqprovider.push.PushInQueue;
import com.example.service.tpl.def.TplUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Api(tags = "用户")
@RestController
public class TplUserController {

    @Autowired
    private JwtAuthService jwtAuthService;

    @Autowired
    private TplUserService tplUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 登录
     * @param tplUser
     * @return
     */
    @ApiOperation("用户登录")
    @RequestMapping(value = "/system/login",method = RequestMethod.POST)
    public ResultBody login(@RequestBody TplUser tplUser){
        String token = jwtAuthService.login(tplUser.getUsername(), tplUser.getPassword());
        return new ResultBody.Builder(ResultCode.SUCCESS).message("登录成功").body(token).build();
    }

    /**
     * 登出
     * @return
     */
    @ApiOperation("用户登出")
    @RequestMapping(value = "/system/logout")
    public ResultBody logout(){
        return new ResultBody.Builder(ResultCode.SUCCESS).message("登出成功").build();
    }

    /**
     * 注册时发送手机短信
     * @param mobile 手机号
     * @return
     */
    @ApiOperation("注册时发送手机短信")
    @RequestMapping(value = "/system/sendMessages")
    public ResultBody sendMessages(String mobile){
        if (StringUtils.isNotBlank(mobile)){
            PushInQueue pushInQueue=new PushInQueue();
            if (pushInQueue.sendMessages(mobile))
                return new ResultBody.Builder(ResultCode.SUCCESS).message("短信发送成功").build();
        }
        return new ResultBody.Builder(ResultCode.ERROR).message("短信发送失败").build();
    }

    /**
     * 注册
     * @param tplUser
     * @return
     */
    @ApiOperation("用户注册")
    @RequestMapping(value = "/system/register")
    public ResultBody register(@RequestBody TplUser tplUser){
        if (!ObjectUtil.isExist(tplUser.getPassword(),tplUser.getUsername(),tplUser.getUname())){
            return new ResultBody.Builder(ResultCode.ERROR).message("注册失败,缺少必传项").build();
        }
        if (tplUserService.isExistUsername(tplUser.getUsername())){
            return new ResultBody.Builder(ResultCode.ERROR).message("注册失败,账户已存在").build();
        }
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        String uid=String.valueOf(idWorker.nextId());
        tplUser.setUid(uid);
        tplUser.setPassword(passwordEncoder.encode(tplUser.getPassword()));   //加密
        if (tplUserService.insertTplUser(tplUser)!=1){
            return new ResultBody.Builder(ResultCode.ERROR).message("注册失败，未知原因").build();
        }
        return new ResultBody.Builder(ResultCode.SUCCESS).message("注册成功").build();
    }

    /**
     * 查询所有用户
     * @param pageParam
     * @return
     */
    @ApiOperation("查询所有用户")
    @RequestMapping(value = "/system/selectAllUser")
    public ResultBody selectAllUser(PageParam pageParam){
        pageParam=tplUserService.selectAllUser(pageParam);
        return new ResultBody.Builder(ResultCode.SUCCESS).message("查询成功").body(pageParam).build();
    }

    /**
     * 删除用户
     * @param uid
     * @return
     */
    @ApiOperation("删除用户")
    @RequestMapping(value = "/system/deleteUser",method = RequestMethod.POST)
    public ResultBody deleteUser(String uid){
        if (tplUserService.deleteUser(uid)==1)
            return new ResultBody.Builder(ResultCode.SUCCESS).message("删除成功").build();
        else
            return new ResultBody.Builder(ResultCode.SUCCESS).message("删除失败").build();
    }

    /**
     * 修改用户信息
     * @param tplUser
     * @return
     */
    @ApiOperation("修改用户")
    @RequestMapping(value = "/system/updateUser",method = RequestMethod.POST)
    public ResultBody updateUser(@RequestBody TplUser tplUser){
        String result=tplUserService.updateUser(tplUser);
        if(result.equals("修改成功"))
            return new ResultBody.Builder(ResultCode.SUCCESS).message("修改成功").build();
        else
            return new ResultBody.Builder(ResultCode.ERROR).message(result).build();
    }
}
