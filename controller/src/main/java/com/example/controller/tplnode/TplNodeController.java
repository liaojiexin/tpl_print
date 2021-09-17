package com.example.controller.tplnode;

import com.example.base.pojo.PageParam;
import com.example.base.pojo.TplNode;
import com.example.base.pojo.response.ResultBody;
import com.example.base.pojo.response.ResultCode;
import com.example.base.utils.ObjectUtil;
import com.example.service.tpl.def.TplNodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@Api(tags = "模板类接口")
@RestController
@Transactional
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
    @ApiOperation("上传模板文件和模板信息")
    @RequestMapping(value = "/tplNode/uploadTplFile", method = RequestMethod.POST)
    public ResultBody uploadTplFile(TplNode tplNode, MultipartFile file){
        //判断参数
        if (ObjectUtil.isExist(tplNode.getTplname(),tplNode.getIsfile(),tplNode.getCreatetime())==false || file.isEmpty()){   //参数为空
            return new ResultBody.Builder(ResultCode.ERROR).message("必填参数为空").build();
        }else if(file.getContentType().equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document") ||
                file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")||
                file.getContentType().equals("application/vnd.ms-excel")){
            //上传文件并存数据
            Boolean b=tplNodeService.uploadTplFile(filepath,file,tplNode);
            if (b){
                return new ResultBody.Builder(ResultCode.SUCCESS).message("请求成功").build();
            }else {
                return new ResultBody.Builder(ResultCode.SUCCESS).message("请求错误").build();
            }
        }else {
            return new ResultBody.Builder(ResultCode.ERROR).message("文件格式错误").build();
        }
    }

    /**
     * 删除模板文件和信息
     * @param tplNode
     * @return
     */
    @ApiOperation("删除模板文件和信息")
    @RequestMapping(value = "/tplNode/removeTplFile", method = RequestMethod.POST)
    public ResultBody removeTplFile(@RequestBody TplNode tplNode){
        if (ObjectUtil.isExist(tplNode.getTplid())==false){
            return new ResultBody.Builder(ResultCode.ERROR).message("必填参数为空").build();
        }else {
            tplNodeService.removeTplFile(filepath,tplNode);
            return new ResultBody.Builder(ResultCode.SUCCESS).message("请求成功").build();
        }
    }

    /**
     * 修改模板文件和信息
     * @param tplNode   模板信息
     * @param file  新文件
     * @return
     */
    @ApiOperation("修改模板文件和信息")
    @RequestMapping(value = "/tplNode/updateTplFile", method = RequestMethod.POST)
    public ResultBody updateTplFile(TplNode tplNode, MultipartFile file){
        if (ObjectUtil.isExist(tplNode.getTplid(),tplNode.getUpdatetime())==false){
            return new ResultBody.Builder(ResultCode.ERROR).message("必填参数为空").build();
        }else {
            Boolean b = tplNodeService.updateTplFile(filepath,tplNode,file);
            if (b){
                return new ResultBody.Builder(ResultCode.SUCCESS).message("请求成功").build();
            }else {
                return new ResultBody.Builder(ResultCode.ERROR).message("请求失败").build();
            }
        }
    }

     /**
     *  查询模板信息
     * @param pageParam 分页信息
     * @return
     */
    @ApiOperation("查询模板信息")
    @RequestMapping(value = "/tplNode/selectTplAll", method = RequestMethod.GET)
    public ResultBody selectTplAll(PageParam pageParam){
        pageParam=tplNodeService.selectTplAll(pageParam);
        return new ResultBody.Builder(ResultCode.SUCCESS).message("请求成功").body(pageParam).build();
    }

    /**
     *   预览模板的pdf文件
     * @param response  响应参数
     * @param tplid 模板id
     * @return
     */
    @ApiOperation("预览模板的pdf文件")
    @RequestMapping(value = "/tplNode/previewPdf",method = RequestMethod.GET)
    public void previewPdf(HttpServletResponse response,String tplid) throws Exception {
        Map<String,Object> result=tplNodeService.previewPdf(response,tplid,filepath);
        if (result.get("result").toString().equals("success")){     //成功
            response=(HttpServletResponse)result.get("response");
            try (OutputStream outputStream=response.getOutputStream()){
                byte[] bytes= (byte[]) result.get("bytes");
                outputStream.write(bytes,0,bytes.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (result.get("result").toString().equals("convert")){   //文件转换中
//            throw new Exception("文件转化中.....");
        }

    }


    /**
     * 下载模板文件
     * @param response
     * @param tplid
     */
    @ApiOperation("下载模板文件")
    @RequestMapping(value = "/tplNode/downloadTpl",method = RequestMethod.POST)
    public void downloadTpl(HttpServletResponse response,String tplid){
        if (ObjectUtil.isExist(tplid)==true){
            tplNodeService.downloadTpl(response,tplid);
        }
    }
}
