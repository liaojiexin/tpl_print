package com.example.service.tpl.impl;

import com.example.base.pojo.TplUser;
import com.example.dao.mapper.TplUserMapper;
import com.example.service.tpl.def.TplUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TplUserServiceImpl implements TplUserService {

    @Resource
    private TplUserMapper tplUserMapper;

    @Override
    public int insertTplUser(TplUser tplUser) {
        return tplUserMapper.insertSelective(tplUser);
    }

    public boolean isExistUsername(String username){
        TplUser tplUser=tplUserMapper.findUserByName(username);
        if (tplUser!=null)
            return true;
        else
            return false;
    }
}
