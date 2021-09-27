package com.example.service.tpl.def;

import com.example.base.pojo.PageParam;
import com.example.base.pojo.TplNode;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface TplNodeService {

    Boolean uploadTplFile(String filepath, MultipartFile file, TplNode tplNode);

    void removeTplFile(String filepath,TplNode tplNode);

    Boolean updateTplFile(String filepath, TplNode tplNode, MultipartFile file);

    PageParam selectTplAll(PageParam pageParam);

    Map<String,Object> previewPdf(String tplid ,String filepath);

    void downloadTpl(HttpServletResponse response, String tplid);
}
