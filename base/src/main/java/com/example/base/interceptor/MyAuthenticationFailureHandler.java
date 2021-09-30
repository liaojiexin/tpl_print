package com.example.base.interceptor;

import com.example.base.pojo.response.ResultBody;
import com.example.base.pojo.response.ResultCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * @Author: Galen
 * @Date: 2019/3/28-9:17
 * @Description: 认证失败的处理
 **/

public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        ResultBody resultBody;
        if (exception instanceof BadCredentialsException ||
                exception instanceof UsernameNotFoundException) {
            resultBody = new ResultBody.Builder(ResultCode.ERROR).message("账户名或者密码输入错误").build();
        } else if (exception instanceof LockedException) {
            resultBody = new ResultBody.Builder(ResultCode.ERROR).message("账户被锁定，请联系管理员!").build();
        } else if (exception instanceof CredentialsExpiredException) {
            resultBody = new ResultBody.Builder(ResultCode.ERROR).message("密码过期，请联系管理员!").build();
        } else if (exception instanceof AccountExpiredException) {
            resultBody = new ResultBody.Builder(ResultCode.ERROR).message("账户过期，请联系管理员!").build();
        } else if (exception instanceof DisabledException) {
            resultBody = new ResultBody.Builder(ResultCode.ERROR).message("账户被禁用，请联系管理员!").build();
        } else {
            resultBody = new ResultBody.Builder(ResultCode.ERROR).message("登录失败!").build();
        }
        response.setStatus(200);
        ObjectMapper om = new ObjectMapper();
        PrintWriter out = response.getWriter();
        out.write(om.writeValueAsString(resultBody));
        out.flush();
        out.close();
    }
}
