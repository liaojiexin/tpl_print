package com.example.controller.tplnode;

import com.example.base.pojo.PageParam;
import com.example.base.pojo.TplNode;
import com.example.base.pojo.response.ResultBody;
import com.example.base.pojo.response.ResultCode;
import com.example.base.utils.ObjectUtil;
import com.example.service.tpl.def.TplNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

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
    @RequestMapping(value = "/tplNode/uploadTplFile", method = RequestMethod.POST)
    public ResultBody uploadTplFile(TplNode tplNode, MultipartFile file){
        //判断参数
        if (ObjectUtil.isExist(tplNode.getTplname(),tplNode.getIsfile())==false || file.isEmpty()){   //参数为空
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
    @RequestMapping(value = "/tplNode/removeTplFile", method = RequestMethod.POST)
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
     * @param tplNode   模板信息
     * @param file  新文件
     * @return
     */
    @RequestMapping(value = "/tplNode/updateTplFile", method = RequestMethod.POST)
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

     /**
     *  查询模板信息
     * @param pageParam 分页信息
     * @return
     */
    @RequestMapping(value = "/tplNode/selectTplAll", method = RequestMethod.GET)
    public ResultBody selectTplAll(PageParam pageParam){
        pageParam=tplNodeService.selectTplAll(pageParam);
        return new ResultBody.Builder(ResultCode.SUCCESS).body(pageParam).build();
    }

    /**
     *   预览模板的pdf文件
     * @param response  响应参数
     * @param tplid 模板id
     * @return
     */
    @RequestMapping(value = "/tplNode/previewPdf",method = RequestMethod.POST)
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

}
