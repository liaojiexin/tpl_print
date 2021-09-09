package com.example.dao.mapper;

import com.example.base.pojo.TplNode;

public interface TplNodeMapper {
    int deleteByPrimaryKey(String tplid);

    int insert(TplNode record);

    int insertSelective(TplNode record);

    TplNode selectByPrimaryKey(String tplid);

    int updateByPrimaryKeySelective(TplNode record);

    int updateByPrimaryKey(TplNode record);
}