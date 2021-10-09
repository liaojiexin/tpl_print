package com.example.auth.interceptor;

import com.example.base.pojo.response.ResultBody;
import com.example.base.pojo.response.ResultCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: Galen
 * @Date: 2019/3/27-17:36
 * @Description: Denied是拒签的意思
 * 此处我们可以自定义403响应的内容,让他返回我们的错误逻辑提示
 **/


public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse resp,
                       AccessDeniedException e) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        ResultBody resultBody= new ResultBody.Builder(ResultCode.ERROR).message("权限不足，请联系管理员!").build();
        out.write(new ObjectMapper().writeValueAsString(resultBody));
        out.flush();
        out.close();
    }
}
