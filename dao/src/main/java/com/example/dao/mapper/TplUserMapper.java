package com.example.dao.mapper;

import com.example.base.pojo.TplUser;

public interface TplUserMapper {
    int deleteByPrimaryKey(String uid);

    int insert(TplUser record);

    int insertSelective(TplUser record);

    TplUser selectByPrimaryKey(String uid);

    int updateByPrimaryKeySelective(TplUser record);

    int updateByPrimaryKey(TplUser record);

    TplUser findUserByName(String username);
}