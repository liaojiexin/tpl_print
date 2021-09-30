package com.example.base.utils;

import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.Workbook;
import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import com.example.base.pojo.TplNode;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * pdf文件工具类
 */
public class PdfUtil {

    //word转为pdf
    public static void wordToPdf(MultipartFile file, String filepath, TplNode tplNode){
        String filename=file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."))+".pdf";
        String path = filepath+ File.separator+tplNode.getTplid() + "-" + filename;
        try(FileOutputStream fileOutputStream=new FileOutputStream(path);
            InputStream inputStream=file.getInputStream()){
            Document document=new Document(inputStream);
            document.save(fileOutputStream, SaveFormat.PDF);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //excel转为pdf
    public static void excelToPdf(MultipartFile file, String filepath, TplNode tplNode){
        String filename=file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."))+".pdf";
        String path = filepath+ File.separator+tplNode.getTplid() + "-" + filename;
        try(FileOutputStream fileOutputStream=new FileOutputStream(path);
            InputStream inputStream=file.getInputStream()){
            Workbook workbook = new Workbook(inputStream);
            PdfSaveOptions saveOptions = new PdfSaveOptions();
            saveOptions.setAllColumnsInOnePagePerSheet(true);
            saveOptions.setCalculateFormula(true);
            workbook.save(fileOutputStream, saveOptions);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //word byte转pdf byte
    public static byte[] wordToPdfBytes(byte[] bytes) {
        byte[] pdfBytes=new byte[0];
        try(ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        ByteArrayInputStream inputStream=new ByteArrayInputStream(bytes)){
            Document doc = new Document(inputStream);
            doc.save(outputStream, SaveFormat.PDF);
            pdfBytes = outputStream.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }
        return pdfBytes;
    }

    //excel byte转pdf byte
    public static byte[] excelToPdfBytes(byte[] bytes) {
        byte[] pdfBytes=new byte[0];
        try(ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
            ByteArrayInputStream inputStream=new ByteArrayInputStream(bytes)){
            Workbook workbook = new Workbook(inputStream);
            PdfSaveOptions saveOptions = new PdfSaveOptions();
            saveOptions.setAllColumnsInOnePagePerSheet(true);
            workbook.save(outputStream, saveOptions);
            pdfBytes = outputStream.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }
        return pdfBytes;
    }
}
