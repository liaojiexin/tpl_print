package com.example.service.tpl.def;

import com.example.base.pojo.TplNode;
import org.springframework.web.multipart.MultipartFile;

public interface TplNodeService {

    Boolean uploadTplFile(String filepath, MultipartFile file, TplNode tplNode);

    void removeTplFile(String filepath,TplNode tplNode);

    Boolean updateTplFile(String filepath, TplNode tplNode, MultipartFile file);
}
