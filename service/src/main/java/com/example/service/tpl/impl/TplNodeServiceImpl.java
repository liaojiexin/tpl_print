package com.example.service.tpl.impl;

import com.example.base.pojo.PageParam;
import com.example.base.pojo.TplNode;
import com.example.base.utils.FileUtil;
import com.example.base.utils.SnowflakeIdWorker;
import com.example.dao.mapper.TplNodeMapper;
import com.example.service.tpl.def.FileOperateService;
import com.example.service.tpl.def.TplNodeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
@Service
public class TplNodeServiceImpl implements TplNodeService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private TplNodeMapper tplNodeMapper;

    @Autowired
    private FileOperateService fileOperateService;

    //上传模板
    @Override
    public Boolean uploadTplFile(String filepath, MultipartFile file, TplNode tplNode) {
        String originalFilename = file.getOriginalFilename();
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        String tplid=String.valueOf(idWorker.nextId());
        String fileName =tplid + "-" + originalFilename;
        //文件存储路径
        String path = filepath+File.separator+fileName;
        //创建文件路径
        File dest = new File(path);
        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        try {
            //数据存入数据库
            tplNode.setTplid(tplid);
            tplNode.setTpltype(file.getContentType());
            tplNode.setFilepath(path);
            tplNode.setFilename(originalFilename);
            tplNodeMapper.insertSelective(tplNode);

            //转化为pdf文件
            fileOperateService.toPdfOfMultipartFile(file,filepath,tplNode);

            //上传文件
            file.transferTo(dest); //文件写入
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    //删除模板
    @Override
    public void removeTplFile(String filepath,TplNode tplNode) {
        TplNode tpl=tplNodeMapper.selectByPrimaryKey(tplNode.getTplid());
        if (tplNodeMapper.deleteByPrimaryKey(tplNode.getTplid())==1){   //删除数据库成功
            fileOperateService.removeFile(tpl);
        }
    }

    //修改模板
    @Override
    public Boolean updateTplFile(String filepath, TplNode tplNode, MultipartFile file) {
        tplNode=tplNodeMapper.selectByPrimaryKey(tplNode.getTplid());
        if (file!=null && !file.isEmpty()){     //文件不为空，修改文件
            String originalFilename = file.getOriginalFilename();
            String fileName = tplNode.getTplid() + "-" + originalFilename;
            //文件存储路径
            String path = filepath+File.separator+fileName;
            //创建文件路径
            File dest = new File(path);
            //判断文件父目录是否存在
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdir();
            }
            try {
                //修改信息
                tplNode.setTpltype(file.getContentType());
                tplNode.setFilepath(path);
                tplNode.setFilename(originalFilename);

                //删除旧文件
                fileOperateService.removeFile(tplNode);

                //转化为pdf文件
                fileOperateService.toPdfOfMultipartFile(file,filepath,tplNode);

                //上传文件
                file.transferTo(dest);
            } catch (IOException e) {
                return false;
            }
        }
        //修改数据库
        if (tplNodeMapper.updateByPrimaryKeySelective(tplNode)!=1){
            return false;
        }
        return true;
    }

    //查询模板
    @Override
    public PageParam selectTplAll(PageParam pageParam) {
        try{
            PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize(),true);
            List<TplNode> list=tplNodeMapper.selectTplAll();
            PageInfo pageInfo=new PageInfo(list);
            pageParam.setContent(pageInfo.getList());
            pageParam.setAllNum(pageInfo.getTotal());
            return pageParam;
        }finally {
            PageHelper.clearPage();
        }
    }

    //预览模板
    @Override
    public Map<String,Object> previewPdf(HttpServletResponse response,String tplid ,String filepath) {
        Map<String,Object> result=new HashMap();
        TplNode tplNode=tplNodeMapper.selectByPrimaryKey(tplid);
        String filePath=tplNode.getFilepath().substring(0,tplNode.getFilepath().lastIndexOf("."))+".pdf";
        File file =new File(filePath);
        if (file.exists()){     //pdf文件存在
            try(FileInputStream inputStream=new FileInputStream(file);
                    ByteArrayOutputStream outputStream=new ByteArrayOutputStream()){
                int len=0;
                byte[] buffer = new byte[1024];
                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
                outputStream.flush();
                byte[] bytes=outputStream.toByteArray();
                //请求头处理
                response.setContentType("application/pdf;charset=UTF-8");
                String filename= tplNode.getFilename().substring(0,tplNode.getFilename().lastIndexOf("."))+".pdf";
                filename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
                response.setHeader("Content-Disposition", String.format("inline; filename=%s",filename));
                response.setContentLength(bytes.length);
                result.put("response",response);
                result.put("bytes",bytes);
                result.put("result","success");
            }catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }else { //pdf不存在,转化为pdf文件
            File mfile=new File(tplNode.getFilepath());
            try (FileInputStream input = new FileInputStream(mfile)){
                MultipartFile multipartFile =new MockMultipartFile(tplNode.getFilename(), tplNode.getFilename(), tplNode.getTpltype(), input);
                fileOperateService.toPdfOfMultipartFile(multipartFile,filepath,tplNode);
            } catch (Exception e) {
                e.printStackTrace();
            }
            result.put("result","convert");
            return result;
        }
    }

    //下载文件
    @Override
    public void downloadTpl(HttpServletResponse response, String tplid) {
        TplNode tplNode=tplNodeMapper.selectByPrimaryKey(tplid);
        File file=new File(tplNode.getFilepath());
        if (tplNode!=null && file.exists()){
            try(InputStream inputStream=new FileInputStream(file);
                    ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
                    OutputStream out=response.getOutputStream()){
                byte[] buffer = new byte[1024];
                int len=0;
                while ((len=inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
                outputStream.flush();
                byte[] bytes=outputStream.toByteArray();

                String filename = URLEncoder.encode(tplNode.getFilename(), "UTF-8").replaceAll("\\+", "%20");

                response.setHeader("Content-Disposition", String.format("attachment; filename=%s", filename));
                response.setContentType("application/octet-stream;charset=UTF-8");
                response.setContentLength(bytes.length);

                out.write(bytes);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
