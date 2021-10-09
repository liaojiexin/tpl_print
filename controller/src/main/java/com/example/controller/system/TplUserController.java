package com.example.controller.system;

import com.example.auth.service.JwtAuthService;
import com.example.base.pojo.TplUser;
import com.example.base.pojo.response.ResultBody;
import com.example.base.pojo.response.ResultCode;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户")
@RestController
public class TplUserController {

    @Autowired
    private JwtAuthService jwtAuthService;

    @RequestMapping(value = "/system/login")
    public ResultBody login(@RequestBody TplUser tplUser){
        String token = jwtAuthService.login(tplUser.getUsername(), tplUser.getPassword());
        return new ResultBody.Builder(ResultCode.SUCCESS).message("登录成功").body(token).build();
    }
}
