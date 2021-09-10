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
import org.springframework.web.bind.annotation.RequestBody;
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
     *  上传模板文件和模板信息
     * @param tplNode   模板信心
     * @param file  文件
     * @return
     */
    @PostMapping(value = "/tplNode/uploadTplFile")
    public ResultBody uploadTplFile(TplNode tplNode, MultipartFile file){
        //判断参数
        if (ObjectUtil.isExist(tplNode.getTplname(),tplNode.getIsfile())==false ||file==null  || file.isEmpty()){   //参数为空
            return new ResultBody.Builder(ResultCode.LACK_PARAM).build();
        }else {
            //上传文件并存数据
            Boolean b=tplNodeService.uploadTplFile(filepath,file,tplNode);
            if (b){
                return new ResultBody.Builder(ResultCode.SUCCESS).build();
            } else
                return new ResultBody.Builder(ResultCode.ERROR).build();
        }
    }

    /**
     * 删除模板文件和信息
     * @param tplNode
     * @return
     */
    @PostMapping(value = "/tplNode/removeTplFile")
    public ResultBody removeTplFile(@RequestBody TplNode tplNode){
        if (ObjectUtil.isExist(tplNode.getTplid())==false){
            return new ResultBody.Builder(ResultCode.LACK_PARAM).build();
        }else {
            tplNodeService.removeTplFile(filepath,tplNode);
            return new ResultBody.Builder(ResultCode.SUCCESS).build();
        }
    }

    /**
     * 修改模板文件和信息
     *
     */
    @PostMapping(value = "/tplNode/updateTplFile")
    public ResultBody updateTplFile(TplNode tplNode, MultipartFile file){
        if (ObjectUtil.isExist(tplNode.getTplid())==false){
            return new ResultBody.Builder(ResultCode.LACK_PARAM).build();
        }else {
            Boolean b = tplNodeService.updateTplFile(filepath,tplNode,file);
            if (b){
                return new ResultBody.Builder(ResultCode.SUCCESS).build();
            }else {
                return new ResultBody.Builder(ResultCode.ERROR).build();
            }
        }
    }

}
