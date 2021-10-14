package com.example.service.tpl.impl;

import cn.afterturn.easypoi.entity.ImageEntity;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.alibaba.fastjson.JSONObject;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.data.Includes;
import com.deepoove.poi.data.Numberings;
import com.deepoove.poi.plugin.table.LoopColumnTableRenderPolicy;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;

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
        byte[] pdfBytes = null;   //pdf文件byte
        caseNode = caseNodeMapper.selectByPrimaryKey(caseNode.getCaseid());
        TplNode tplNode = tplNodeMapper.selectByPrimaryKey(caseNode.getTplid());
        if (tplNode != null && StringUtils.isNotBlank(caseNode.getFilecontent())) {
            //先把实例的内容导入到模板中
            byte[] bytes = caseOnTpl(caseNode, tplNode);
            //文件转化
            pdfBytes = fileOperateService.toPdfOfBytes(bytes, tplNode.getTpltype());
        }
        return pdfBytes;
    }

    public byte[] caseOnTpl(CaseNode caseNode, TplNode tplNode) {
        byte[] bytes = new byte[0];
        String filecontent = caseNode.getFilecontent();   //实例内容
        String tpltype = tplNode.getTpltype();
        switch (tpltype) {
            case "application/msword":
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
            case "application/vnd.ms-works":
                bytes = caseOnTplWord(filecontent, tplNode);
                break;
            case "application/vnd.ms-excel":
            case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
                bytes = caseOnTplExcel(filecontent, tplNode);
                break;
            default:
                break;
        }
        return bytes;
    }

    public byte[] caseOnTplExcel(String filecontent, TplNode tplNode) {
        String filepath = tplNode.getFilepath();      //模板文件路径
        byte[] bytes = new byte[0];
        Map<String, Object> map = new HashMap();
        JSONObject jsonObject = JSONObject.parseObject(filecontent);
        for (Map.Entry<String, Object> key : jsonObject.entrySet()) {
/*            if (key.getKey().startsWith("image")) {      //图片
                ImageEntity image = new ImageEntity(StringUtils.strip(String.valueOf(key.getValue()), "\u202a"),3000,3000);
                map.put(key.getKey(), image);
            } else {*/
            map.put(key.getKey(), key.getValue());
            /*}*/
        }

        TemplateExportParams params = new TemplateExportParams(filepath);
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        try (ByteArrayOutputStream fos = new ByteArrayOutputStream()) {
            workbook.write(fos);
            bytes = fos.toByteArray();
            FileOutputStream outputStream = new FileOutputStream(new File("C:\\Users\\admin\\Desktop\\test.xls"));
            outputStream.write(bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public byte[] caseOnTplWord(String filecontent, TplNode tplNode) {
        String filepath = tplNode.getFilepath();      //模板文件路径
        byte[] bytes = new byte[0];

        /*JSONObject jsonObject = JSONObject.parseObject(filecontent);
        for (Map.Entry<String, Object> key : jsonObject.entrySet()) {
            map.put(key.getKey(), key.getValue());
        }
        //json数据处理
        Map<String, Object> map = new HashMap();
        Map<String, Object> results = manageMap(map);
        Configure config=(Configure)results.get("config");
        map = (Map<String, Object>) results.get("map");*/

        ConfigureBuilder configureBuilder = Configure.builder();
        Map<String, Object> map = manageMap(filecontent, configureBuilder);
        ConfigureBuilder configureBuilder1 = (ConfigureBuilder) map.get("configureBuilder");
        Configure config = configureBuilder1.build();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            XWPFTemplate xwpfTemplate;
            if (config != null) {
                xwpfTemplate = XWPFTemplate.compile(filepath, config).render(map);
            } else {
                xwpfTemplate = XWPFTemplate.compile(filepath).render(map);
            }
            xwpfTemplate.write(outputStream);
            bytes = outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    //word模板中实例json数据处理 http://deepoove.com/poi-tl
    private Map<String, Object> manageMap(String filecontent, ConfigureBuilder configureBuilder) {
        Map<String, Object> results = new HashMap<>();
        LoopRowTableRenderPolicy policyRow = new LoopRowTableRenderPolicy();   //行循环
        LoopColumnTableRenderPolicy policyColumn = new LoopColumnTableRenderPolicy(); //列循环
        JSONObject jsonObject = JSONObject.parseObject(filecontent);
        for (Map.Entry<String, Object> key : jsonObject.entrySet()) {
            if (key.getValue().toString().startsWith("[")        //数据格式表示表单行循环
                    && key.getValue().toString().endsWith("]")
                    && key.getKey().startsWith("row_")) {    //row_开头为行循环表
                configureBuilder = configureBuilder.bind(key.getKey(), policyRow);
                String s = String.valueOf(key.getValue());
                String ss[] = s.substring(2, s.length() - 2).trim().split("},\\{");
                List<String> lists = Arrays.asList(ss);
                List<Map> listMap = new ArrayList<>();
                for (String list : lists) {
                    listMap.add(manageMap("{" + list + "}", configureBuilder));
                }
                results.put("configureBuilder", configureBuilder);
                results.put(key.getKey(), listMap);
                continue;
            } else if (key.getValue().toString().startsWith("[")             //数据格式表示表单列循环
                    && key.getValue().toString().endsWith("]")
                    && key.getKey().startsWith("column_")) {     //column_开头为列循环表
                configureBuilder = configureBuilder.bind(key.getKey(), policyColumn);
                String s = String.valueOf(key.getValue());
                String ss[] = s.substring(2, s.length() - 2).trim().split("},\\{");
                List<String> lists = Arrays.asList(ss);
                List<Map> listMap = new ArrayList<>();
                for (String list : lists) {
                    listMap.add(manageMap("{" + list + "}", configureBuilder));
                }
                results.put("configureBuilder", configureBuilder);
                results.put(key.getKey(), listMap);
                continue;
            } else if (key.getKey().startsWith("*")) {    //列表 key开头*   格式为数据格式"xxx":["aaa","bbb"]
                String string = String.valueOf(key.getValue());
                String[] strings = string.substring(2, string.length() - 2).split("\",\"");
                results.put(key.getKey().substring(1), Numberings.create(strings));
                continue;
            } else if (key.getKey().startsWith("+")) {    //嵌套打印 key开头+ oflocal默认为嵌套模板的id，data为嵌套模板的数据
                JSONObject json = JSONObject.parseObject(key.getValue().toString());
                HashMap mapInclude = new HashMap();
                for (Map.Entry<String, Object> keyMap : json.entrySet()) {
                    mapInclude.put(keyMap.getKey(), keyMap.getValue());
                }
                String ofLocal = String.valueOf(mapInclude.get("oflocal"));
                String filePath = tplNodeMapper.selectByPrimaryKey(ofLocal).getFilepath();
                results.put(key.getKey().substring(1), Includes.ofLocal(filePath).setRenderModel(mapInclude.get("data")).create());
                continue;
            } else {             //普通文本和图片
                //注意去掉控制字符\u202a https://blog.csdn.net/qq_27508477/article/details/100571942
                results.put(key.getKey().replace("@",""), StringUtils.strip(key.getValue().toString(), "\u202a"));
                continue;
            }
        }
        return results;
    }

    //word模板中实例json数据处理 http://deepoove.com/poi-tl
    public Map<String, Object> manageMap(Map<String, Object> map) {
        Map<String, Object> results = new HashMap<>();
        Map<String, Object> resultmap = new HashMap<>();
        LoopRowTableRenderPolicy policyRow = new LoopRowTableRenderPolicy();   //行循环
        LoopColumnTableRenderPolicy policyColumn = new LoopColumnTableRenderPolicy(); //列循环
        Configure config = null;
        List<String> tablesRow = new ArrayList();    //行循环表格
        List<String> tablesColumn = new ArrayList();    //列循环表格

        Set set = map.keySet(); //拿到所有的key
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            String key = String.valueOf(iterator.next());
            //数据格式表示表单行循环
            if (map.get(key).toString().startsWith("[")
                    && map.get(key).toString().endsWith("]")
                    && key.startsWith("row_")) {    //row_开头为行循环表
                tablesRow.add(key);
                resultmap.put(key, map.get(key));
            } else if (map.get(key).toString().startsWith("[")             //数据格式表示表单列循环
                    && map.get(key).toString().endsWith("]")
                    && key.startsWith("column_")) {     //column_开头为列循环表
                tablesColumn.add(key);
                resultmap.put(key, map.get(key));
            } else /*if (key.startsWith("@")) {   //图片 key开头@
                //注意去掉控制字符\u202a https://blog.csdn.net/qq_27508477/article/details/100571942
                resultmap.put(key.substring(1), StringUtils.strip(map.get(key).toString(), "\u202a"));
            } else*/ if (key.startsWith("*")) {    //列表 key开头*   格式为数据格式“xxx”:["aaa","bbb"]
                String string = String.valueOf(map.get(key));
                String[] strings = string.substring(2, string.length() - 2).split("\",\"");
                resultmap.put(key.substring(1), Numberings.create(strings));
            } else if (key.startsWith("+")) {    //嵌套打印 key开头+ oflocal默认为嵌套模板的id，data为嵌套模板的数据
                JSONObject jsonObject = JSONObject.parseObject(map.get(key).toString());
                HashMap mapInclude = new HashMap();
                for (Map.Entry<String, Object> keyMap : jsonObject.entrySet()) {
                    mapInclude.put(keyMap.getKey(), keyMap.getValue());
                }
                String ofLocal = String.valueOf(mapInclude.get("oflocal"));
                String filePath = tplNodeMapper.selectByPrimaryKey(ofLocal).getFilepath();
                resultmap.put(key.substring(1), Includes.ofLocal(filePath).setRenderModel(mapInclude.get("data")).create());
            } else {             //普通文本和图片
//                resultmap.put(key, map.get(key));
                //注意去掉控制字符\u202a https://blog.csdn.net/qq_27508477/article/details/100571942
                resultmap.put(key, StringUtils.strip(map.get(key).toString(), "\u202a"));
            }
        }
        if (tablesRow.size() > 0 || tablesColumn.size() > 0) {
            ConfigureBuilder configureBuilder = Configure.builder();
            //存在表格行循环
            for (String table : tablesRow) {
                configureBuilder = configureBuilder.bind(table, policyRow);
            }
            //存在表格列循环
            for (String table : tablesColumn) {
                configureBuilder = configureBuilder.bind(table, policyColumn);
            }
            config = configureBuilder.build();
        }
        results.put("config", config);
        results.put("map", resultmap);
        return results;
    }

    @Override
    public void mergeCasePdf(List<byte[]> list, HttpServletResponse response) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             OutputStream out = response.getOutputStream()) {
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
            byte[] bytes = outputStream.toByteArray();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", String.format("inline; filename=%s", ""));
            response.setContentLength(bytes.length);
            out.write(bytes);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
