package com.example.service.tpl.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.alibaba.fastjson.JSONObject;
import com.example.base.pojo.CaseNode;
import com.example.base.pojo.TplNode;
import com.example.dao.mapper.CaseNodeMapper;
import com.example.dao.mapper.TplNodeMapper;
import com.example.service.tpl.def.CaseTplService;
import com.example.service.tpl.def.FileOperateService;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CaseTplServiceImpl implements CaseTplService {

    @Autowired
    private TplNodeMapper tplNodeMapper;

    @Autowired
    private CaseNodeMapper caseNodeMapper;

    @Autowired
    private FileOperateService fileOperateService;

    @Override
    public byte[] caseToPdfByTpl(CaseNode caseNode) {
        byte[] pdfBytes=null;   //pdf文件byte
        caseNode = caseNodeMapper.selectByPrimaryKey(caseNode.getCaseid());
        TplNode tplNode=tplNodeMapper.selectByPrimaryKey(caseNode.getTplid());
        if (tplNode!=null && StringUtils.isNotBlank(caseNode.getFilecontent())){
            //先把实例的内容导入到模板中
            byte[] bytes=caseOnTpl(caseNode,tplNode);
            //文件转化
            pdfBytes=fileOperateService.toPdfOfBytes(bytes,tplNode.getTpltype());
        }
        return pdfBytes;
    }

    private byte[] caseOnTpl(CaseNode caseNode, TplNode tplNode) {
        byte[] bytes = new byte[0];
        String filecontent=caseNode.getFilecontent();
        String filepath=tplNode.getFilepath();
        String tpltype=tplNode.getTpltype();
        switch (tpltype){
            case "application/msword":
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
            case "application/vnd.ms-works":
                bytes=caseOnTplWord(filecontent,filepath);
                break;
            case "application/vnd.ms-excel":
            case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
                bytes=caseOnTplExcel(filecontent,filepath);
                break;
            default:
                break;
        }
        return bytes;
    }

    private byte[] caseOnTplExcel(String filecontent, String filepath) {
        byte[] bytes = new byte[0];
        TemplateExportParams params = new TemplateExportParams(filepath);
        Map<String, Object> map = new HashMap();
        JSONObject jsonObject = JSONObject.parseObject(filecontent);
        for (Map.Entry<String, Object> key:jsonObject.entrySet()) {
            map.put(key.getKey(),key.getValue());
        }

        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        try(ByteArrayOutputStream fos = new ByteArrayOutputStream()) {
            workbook.write(fos);
            bytes=fos.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }
        return bytes;
    }

    private byte[] caseOnTplWord(String filecontent, String filepath) {
        byte[] bytes = new byte[0];
        return bytes;
    }

    @Override
    public void mergeCasePdf(List<byte[]> list , HttpServletResponse response) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            OutputStream out =response.getOutputStream()) {
            //用itext合并多个pdf的byte为一个pdf文件
            Document document = new Document();
            try {
                PdfCopy copy = new PdfCopy(document, outputStream);
                document.open();
                for (int i = 0; i < list.size(); i++) {
                    PdfReader pdfReader = new PdfReader(list.get(i));
                    int pageSize = pdfReader.getNumberOfPages();
                    for (int j = 1; j <= pageSize; j++) {
                        document.newPage();
                        PdfImportedPage pdfImportedPage = copy.getImportedPage(pdfReader, j);
                        copy.addPage(pdfImportedPage);
                    }
                }
            } finally { //要在使用outputStream之前关闭document，不然流会获取出错
                document.close();
            }
            byte[] bytes=outputStream.toByteArray();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition",String.format("inline; filename=%s",""));
            response.setContentLength(bytes.length);
            out.write(bytes);
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
