package com.example.auth.service;

import com.example.base.pojo.JwtUser;
import com.example.base.pojo.TplUser;
import com.example.dao.mapper.TplUserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Resource
    private TplUserMapper tplUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TplUser tplUser = tplUserMapper.findUserByName(username);
        if (tplUser == null) {
            throw new RuntimeException("用户" + username + "不存在");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(tplUser.getPassword());
        JwtUser jwtUser=new JwtUser(tplUser);
        return jwtUser;
    }
}
