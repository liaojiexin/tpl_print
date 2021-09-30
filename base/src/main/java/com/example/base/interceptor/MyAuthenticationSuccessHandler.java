package com.example.base.interceptor;

import com.example.base.pojo.response.ResultBody;
import com.example.base.pojo.response.ResultCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Galen
 * @Date: 2019/3/28-9:17
 * @Description: 认证成功的处理
 */
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        Map map =new HashMap();
//        map.put("msg","登录成功");
//        map.put("User",SecurityUserUtil.getCurrentUser());
        ResultBody resultBody= new ResultBody.Builder(ResultCode.SUCCESS).message("登录成功!").build();
        ObjectMapper om = new ObjectMapper();
        PrintWriter out = response.getWriter();
        out.write(om.writeValueAsString(resultBody));
        out.flush();
        out.close();
    }
}