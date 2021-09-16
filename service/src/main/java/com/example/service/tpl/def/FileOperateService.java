package com.example.service.tpl.def;

import com.example.base.pojo.TplNode;
import org.springframework.web.multipart.MultipartFile;

public interface FileOperateService {

    void toPdfOfMultipartFile(MultipartFile file, String filepath, TplNode tplNode);

    void removeFile(TplNode tplNode);

    byte[] toPdfOfBytes(byte[] bytes, String tpltype);
}
