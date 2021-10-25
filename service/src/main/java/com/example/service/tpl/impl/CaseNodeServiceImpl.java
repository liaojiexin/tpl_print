package com.example.service.tpl.impl;

import com.example.base.pojo.CaseNode;
import com.example.base.pojo.PageParam;
import com.example.base.pojo.TplNode;
import com.example.base.utils.SnowflakeIdWorker;
import com.example.dao.mapper.CaseNodeMapper;
import com.example.service.tpl.def.CaseNodeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javafx.concurrent.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaseNodeServiceImpl implements CaseNodeService {

    @Autowired
    private CaseNodeMapper caseNodeMapper;

    //创建新的实例节点
    @Override
    public boolean createCaseNode(CaseNode caseNode) {
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        caseNode.setCaseid(String.valueOf(idWorker.nextId()));
        if (caseNodeMapper.insertSelective(caseNode)==1)
            return true;
        return false;
    }

    @Override
    public boolean removeCaseNode(CaseNode caseNode) {
        if (caseNodeMapper.deleteByPrimaryKey(caseNode.getCaseid())==1)
            return true;
        return false;
    }

    @Override
    public boolean updateCaseNode(CaseNode caseNode) {
        if (caseNodeMapper.updateByPrimaryKeySelective(caseNode)==1)
            return true;
        return false;
    }

    @Override
    public PageParam selectCaseNode(PageParam pageParam) {
        try{
            PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize(),true);
            List<TplNode> list=caseNodeMapper.selectCaseAll();
            PageInfo pageInfo=new PageInfo(list);
            pageParam.setContent(pageInfo.getList());
            pageParam.setTotal(pageInfo.getTotal());
            return pageParam;
        }finally {
            PageHelper.clearPage();
        }
    }
}
