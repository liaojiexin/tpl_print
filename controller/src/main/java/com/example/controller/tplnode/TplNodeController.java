package com.example.controller.tplnode;

import com.example.base.pojo.TplNode;
import com.example.base.pojo.response.ResultBody;
import com.example.base.pojo.response.ResultCode;
import com.example.base.utils.ObjectUtil;
import com.example.base.utils.SnowflakeIdWorker;
import com.example.service.tpl.def.TplNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class TplNodeController {

    @Value("${filepath}")
    public String filepath;

    @Autowired
    private TplNodeService tplNodeService;

    /**
     * 上传模板文件
     */
    @PostMapping(value = "/tplNode/uploadTplFile")
    public ResultBody uploadTplFile(HttpServletRequest request, HttpServletResponse response, TplNode tplNode, MultipartFile file){
        //判断参数
        if (ObjectUtil.isExist(tplNode.getTplname(),tplNode.getIsfile())==false ||file==null  || file.isEmpty()){   //参数为空
            return new ResultBody.Builder(ResultCode.LACK_PARAM).build();
        }else {
            //上传文件
            Boolean b=tplNodeService.uploadTplFile(filepath,file,tplNode);
            if (b)
                return new ResultBody.Builder(ResultCode.SUCCESS).build();
            else
                return new ResultBody.Builder(ResultCode.ERROR).build();
        }
    }

}
