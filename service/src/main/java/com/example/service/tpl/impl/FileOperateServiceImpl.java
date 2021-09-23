package com.example.service.tpl.impl;

import com.example.base.pojo.TplNode;
import com.example.base.utils.PdfUtil;
import com.example.service.tpl.def.FileOperateService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class FileOperateServiceImpl implements FileOperateService {

    //判断文件格式来确定转化pdf的格式
    @Async("fileTaskExecutor")  //异步
    @Override
    public void toPdfOfMultipartFile(MultipartFile file, String filepath, TplNode tplNode) {
        String fileType=file.getContentType();
        switch (fileType) {
            case "application/msword":
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
            case "application/vnd.ms-works":
                PdfUtil.wordToPdf(file,filepath,tplNode);
                break;
            case "application/vnd.ms-excel":
            case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
                PdfUtil.excelToPdf(file,filepath,tplNode);
                break;
            default:
                break;
        }
    }

    @Override
    public byte[] toPdfOfBytes(byte[] bytes, String fileType) {
        byte[] pdfBytes=new byte[0];
        switch (fileType) {
            case "application/msword":
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
            case "application/vnd.ms-works":
                pdfBytes=PdfUtil.wordToPdfBytes(bytes);
                break;
            case "application/vnd.ms-excel":
            case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
                pdfBytes=PdfUtil.excelToPdfBytes(bytes);
                break;
            default:
                break;
        }
        return pdfBytes;
    }

    //删除文件
    @Override
    public void removeFile(TplNode tplNode){
        File file=new File(tplNode.getFilepath());
        if (file.exists())
            file.delete();  //删除原文件

        String pdfFilePath=tplNode.getFilepath().substring(0,tplNode.getFilepath().lastIndexOf("."))+".pdf";
        File pdfFile=new File(pdfFilePath);
        if (pdfFile.exists())
            pdfFile.delete();   //删除pdf文件
    }
}
