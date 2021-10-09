package com.example.controller.system;

import com.example.base.pojo.response.ResultBody;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户")
@RestController
public class TplUserController {

    @RequestMapping(value = "/system/login")
    public ResultBody login(){
        return null;
    }
}
