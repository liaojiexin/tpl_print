package com.example.controller.casenode;

import com.example.base.pojo.CaseNode;
import com.example.base.pojo.PageParam;
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

    /**
     * 新建实例
     * @param caseNode 实例
     * @return
     */
    @RequestMapping(value = "/caseNode/createCaseNode",method = RequestMethod.POST)
    public ResultBody createCaseNode(CaseNode caseNode){
        if (ObjectUtil.isExist(caseNode.getCreatetime(),caseNode.getCasename(),caseNode.getIsfile())==false)
            return new ResultBody.Builder(ResultCode.LACK_PARAM).build();
        if(caseNodeService.createCaseNode(caseNode)){
            return new ResultBody.Builder(ResultCode.SUCCESS).build();
        }
        return new ResultBody.Builder(ResultCode.ERROR).build();
    }

    /**
     * 删除实例
     * @param caseNode 实例
     * @return
     */
    @RequestMapping(value = "/caseNode/removeCaseNode",method = RequestMethod.POST)
    public ResultBody removeCaseNode(CaseNode caseNode){
        if (ObjectUtil.isExist(caseNode.getCaseid())){
            if (caseNodeService.removeCaseNode(caseNode)){
                return new ResultBody.Builder(ResultCode.SUCCESS).build();
            }
        }
        return new ResultBody.Builder(ResultCode.ERROR).build();
    }

    /**
     * 修改实例
     * @param caseNode
     * @return
     */
    @RequestMapping(value = "/caseNode/updateCaseNode",method = RequestMethod.POST)
    public ResultBody updateCaseNode(CaseNode caseNode){
        if (ObjectUtil.isExist(caseNode.getCaseid(),caseNode.getUpdatetime())){
            if (caseNodeService.updateCaseNode(caseNode))
                return new ResultBody.Builder(ResultCode.SUCCESS).build();
        }
        return new ResultBody.Builder(ResultCode.ERROR).build();
    }

    /**
     * 查找实例
     * @return
     */
    @RequestMapping(value = "/caseNode/selectCaseNode",method = RequestMethod.GET)
    public ResultBody selectCaseNode(PageParam pageParam){
        pageParam=caseNodeService.selectCaseNode(pageParam);
        return new ResultBody.Builder(ResultCode.SUCCESS).body(pageParam).build();
    }
}
