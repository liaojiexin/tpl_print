package com.example.controller.casenode;

import com.example.base.pojo.CaseNode;
import com.example.base.pojo.PageParam;
import com.example.base.pojo.response.ResultBody;
import com.example.base.pojo.response.ResultCode;
import com.example.base.utils.ObjectUtil;
import com.example.service.tpl.def.CaseNodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "实例类接口")
@RestController
public class CaseNodeController {

    @Autowired
    private CaseNodeService caseNodeService;

    /**
     * 新建实例
     * @param caseNode 实例
     * @return
     */
    @ApiOperation("新建实例")
    @RequestMapping(value = "/caseNode/createCaseNode",method = RequestMethod.POST)
    public ResultBody createCaseNode(@RequestBody CaseNode caseNode){
        if (ObjectUtil.isExist(caseNode.getCreatetime(),caseNode.getCasename(),caseNode.getIsfile())==false)
            return new ResultBody.Builder(ResultCode.ERROR).message("必填参数为空").build();
        if(caseNodeService.createCaseNode(caseNode)){
            return new ResultBody.Builder(ResultCode.SUCCESS).message("请求成功").build();
        }
        return new ResultBody.Builder(ResultCode.ERROR).message("请求失败").build();
    }

    /**
     * 删除实例
     * @param caseNode 实例
     * @return
     */
    @ApiOperation("删除实例")
    @RequestMapping(value = "/caseNode/removeCaseNode",method = RequestMethod.POST)
    public ResultBody removeCaseNode(@RequestBody CaseNode caseNode){
        if (ObjectUtil.isExist(caseNode.getCaseid())){
            if (caseNodeService.removeCaseNode(caseNode)){
                return new ResultBody.Builder(ResultCode.SUCCESS).message("请求成功").build();
            }
        }
        return new ResultBody.Builder(ResultCode.ERROR).message("请求失败").build();
    }

    /**
     * 修改实例
     * @param caseNode
     * @return
     */
    @ApiOperation("修改实例")
    @RequestMapping(value = "/caseNode/updateCaseNode",method = RequestMethod.POST)
    public ResultBody updateCaseNode(@RequestBody CaseNode caseNode){
        if (ObjectUtil.isExist(caseNode.getCaseid(),caseNode.getUpdatetime())){
            if (caseNodeService.updateCaseNode(caseNode))
                return new ResultBody.Builder(ResultCode.SUCCESS).message("请求成功").build();
        }
        return new ResultBody.Builder(ResultCode.ERROR).message("请求失败").build();
    }

    /**
     * 查找实例
     * @return
     */
    @ApiOperation("查找实例")
    @RequestMapping(value = "/caseNode/selectCaseNode",method = RequestMethod.GET)
    public ResultBody selectCaseNode(PageParam pageParam){
        pageParam=caseNodeService.selectCaseNode(pageParam);
        return new ResultBody.Builder(ResultCode.SUCCESS).message("请求成功").body(pageParam).build();
    }
}
