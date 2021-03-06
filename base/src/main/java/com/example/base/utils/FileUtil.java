package com.example.base.utils;

import com.example.base.pojo.TplNode;
import org.apache.tika.metadata.HttpHeaders;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.helpers.DefaultHandler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 文件工具类
 */
public class FileUtil {

    //通过流获取文件的格式
    public static String returnFileTypeByStream(InputStream inputStream) throws Exception{
        Metadata metadata = new Metadata();
        try (InputStream is=inputStream){
            AutoDetectParser parser = new AutoDetectParser();
            parser.parse(is, new DefaultHandler(), metadata, new ParseContext());
        }
        return metadata.get(HttpHeaders.CONTENT_TYPE);
    }

    //通过byte获取文件的格式
    public static String returnFileTypeByByte(byte[] bytes) throws Exception{
        Metadata metadata = new Metadata();
        try (InputStream is=new ByteArrayInputStream(bytes)){
            AutoDetectParser parser = new AutoDetectParser();
            parser.parse(is, new DefaultHandler(), metadata, new ParseContext());
        }
        return metadata.get(HttpHeaders.CONTENT_TYPE);
    }
}
