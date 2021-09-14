package com.example.service.tpl.impl;

import com.example.base.pojo.CaseNode;
import com.example.base.utils.SnowflakeIdWorker;
import com.example.dao.mapper.CaseNodeMapper;
import com.example.service.tpl.def.CaseNodeService;
import javafx.concurrent.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
