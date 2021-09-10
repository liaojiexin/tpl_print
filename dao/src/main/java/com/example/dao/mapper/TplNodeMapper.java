package com.example.dao.mapper;

import com.example.base.pojo.TplNode;
import org.apache.ibatis.annotations.Mapper;

public interface TplNodeMapper {
    int deleteByPrimaryKey(String tplid);

    int insert(TplNode record);

    int insertSelective(TplNode record);

    TplNode selectByPrimaryKey(String tplid);

    int updateByPrimaryKeySelective(TplNode record);

    int updateByPrimaryKey(TplNode record);
}