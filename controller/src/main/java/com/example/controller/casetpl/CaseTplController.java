package com.example.controller.casetpl;

import com.example.base.pojo.CaseNode;
import com.example.base.utils.ObjectUtil;
import com.example.service.tpl.def.CaseTplService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "实例模板交互接口")
@RestController
public class CaseTplController {

    @Autowired
    private CaseTplService caseTplService;

    /**
     * 批量实例打印
     * @param caseNodes 要打印的实例
     * @param response  响应参数
     */
    @ApiOperation("批量实例打印")
    @RequestMapping(value = "/caseTpl/CaseTplPrintStream",method = RequestMethod.POST)
    public void CaseTplPrintStream(@RequestBody CaseNode[] caseNodes, HttpServletResponse response){
        if (caseNodes.length>0){
            List<byte[]> list=new ArrayList();
            //循环把每个实例转成pdf
            for (CaseNode caseNode:caseNodes) {
                if (ObjectUtil.isExist(caseNode.getCaseid(),caseNode.getTplid())==false){   //缺少参数
                    try {
                        throw new Exception("缺少参数");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {     //批量打印
                    byte[] bytes=caseTplService.caseToPdfByTpl(caseNode);
                    list.add(bytes);
                }
            }
            //合并实例处理
            caseTplService.mergeCasePdf(list,response);
        }
    }
}
