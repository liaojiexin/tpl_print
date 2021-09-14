package com.example.dao.mapper;

import com.example.base.pojo.CaseNode;
import com.example.base.pojo.TplNode;

import java.util.List;

public interface CaseNodeMapper {
    int deleteByPrimaryKey(String caseid);

    int insert(CaseNode record);

    int insertSelective(CaseNode record);

    CaseNode selectByPrimaryKey(String caseid);

    int updateByPrimaryKeySelective(CaseNode record);

    int updateByPrimaryKey(CaseNode record);

    List<TplNode> selectCaseAll();
}