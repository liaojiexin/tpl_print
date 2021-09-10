package com.example.service.tpl.impl;

import com.example.base.pojo.TplNode;
import com.example.base.utils.SnowflakeIdWorker;
import com.example.dao.mapper.TplNodeMapper;
import com.example.service.tpl.def.TplNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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
        //判断文件是否已经存在
        if (dest.exists())
            return false;
        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        try {
            //上传文件
            file.transferTo(dest); //文件写入

            //数据存入数据库
            tplNode.setTplid(String.valueOf(idWorker.nextId()));
            tplNode.setTpltype(file.getContentType());
            tplNode.setFilepath(fileName);
            tplNodeMapper.insertSelective(tplNode);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
