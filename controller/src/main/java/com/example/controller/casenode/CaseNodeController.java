package com.example.controller.casenode;

import com.example.base.pojo.CaseNode;
import com.example.base.pojo.response.ResultBody;
import com.example.base.pojo.response.ResultCode;
import com.example.base.utils.ObjectUtil;
import com.example.service.tpl.def.CaseNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CaseNodeController {

    @Autowired
    private CaseNodeService caseNodeService;

    @RequestMapping(value = "/caseNode/createCaseNode",method = RequestMethod.POST)
    public ResultBody createCaseNode(CaseNode caseNode){
        if (ObjectUtil.isExist(caseNode.getCreatetime(),caseNode.getCasename(),caseNode.getIsfile())==false)
            return new ResultBody.Builder(ResultCode.LACK_PARAM).build();
        if(caseNodeService.createCaseNode(caseNode)){
            return new ResultBody.Builder(ResultCode.SUCCESS).build();
        }
        return new ResultBody.Builder(ResultCode.ERROR).build();
    }
}
