package com.example.service.tpl.impl;

import com.example.base.pojo.JwtUser;
import com.example.base.pojo.TplUser;
import com.example.dao.mapper.TplUserMapper;
import com.example.service.tpl.def.TplUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TplUserServiceImpl implements TplUserService {

}
