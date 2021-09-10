package com.example.service.tpl.impl;

import com.example.base.pojo.TplNode;
import com.example.base.utils.FileUtil;
import com.example.base.utils.SnowflakeIdWorker;
import com.example.dao.mapper.TplNodeMapper;
import com.example.service.tpl.def.TplNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
public class TplNodeServiceImpl implements TplNodeService {

    @Autowired
    private TplNodeMapper tplNodeMapper;

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
            //转化为pdf文件
            FileUtil.toPdfOfMultipartFile(file);

            //上传文件
            file.transferTo(dest); //文件写入

            //数据存入数据库
            tplNode.setTplid(String.valueOf(idWorker.nextId()));
            tplNode.setTpltype(file.getContentType());
            tplNode.setFilepath(path);
            tplNode.setFilename(originalFilename);
            tplNodeMapper.insertSelective(tplNode);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public void removeTplFile(String filepath,TplNode tplNode) {
        TplNode tpl=tplNodeMapper.selectByPrimaryKey(tplNode.getTplid());
        if (tplNodeMapper.deleteByPrimaryKey(tplNode.getTplid())==1){   //删除数据库成功
            File file=new File(tpl.getFilepath());
            file.delete();  //删除原文件

            String pdfFilePath=tpl.getFilepath().substring(0,tpl.getFilepath().lastIndexOf("."))+".pdf";
            File pdfFile=new File(pdfFilePath);
            pdfFile.delete();   //删除pdf文件
        }
    }

    @Override
    public Boolean updateTplFile(String filepath, TplNode tplNode, MultipartFile file) {
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
                //删除旧文件
                File oldfile=new File(tplNode.getFilepath());
                oldfile.delete();  //删除原文件
                String pdfOldFilePath=tplNode.getFilepath().substring(0,tplNode.getFilepath().lastIndexOf("."))+".pdf";
                File pdfFile=new File(pdfOldFilePath);
                pdfFile.delete();   //删除pdf文件

                //转化为pdf文件
                FileUtil.toPdfOfMultipartFile(file);

                //上传文件
                file.transferTo(dest);

                //修改信息
                tplNode.setTpltype(file.getContentType());
                tplNode.setFilepath(path);
                tplNode.setFilename(originalFilename);
            } catch (IOException e) {
                return false;
            }
        }
        //修改数据库
        tplNodeMapper.updateByPrimaryKeySelective(tplNode);
        return true;
    }
}
