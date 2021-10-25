package com.example.controller.system;

import com.example.auth.service.JwtAuthService;
import com.example.base.pojo.TplUser;
import com.example.base.pojo.response.ResultBody;
import com.example.base.pojo.response.ResultCode;
import com.example.base.utils.ObjectUtil;
import com.example.base.utils.SnowflakeIdWorker;
import com.example.service.tpl.def.TplUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
