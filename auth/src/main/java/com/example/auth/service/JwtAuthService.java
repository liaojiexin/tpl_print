package com.example.auth.service;

import com.example.auth.rsa.RSAKeyProperties;
import com.example.auth.utils.JwtTokenUtil;
import com.example.base.pojo.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

//Token颁发服务类
@Service
public class JwtAuthService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RSAKeyProperties rsaKeyProp;

    public String login(String username, String password) {
        // 用户验证
        Authentication authentication;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            throw new RuntimeException("用户验证失败");
        }
        JwtUser loginUser = (JwtUser) authentication.getPrincipal();
        // 生成Token
        return jwtTokenUtil.generateToken(loginUser, rsaKeyProp.getPrivateKey());
    }
}
