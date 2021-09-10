package com.example.base.utils;

import org.apache.tika.metadata.HttpHeaders;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.xml.sax.helpers.DefaultHandler;
import java.io.InputStream;

/**
 * 文件工具类
 */
public class FileUtil {

    public static String returnFileTypeByStream(InputStream inputStream) throws Exception{
        Metadata metadata = new Metadata();
        try (InputStream is=inputStream){
            AutoDetectParser parser = new AutoDetectParser();
            parser.parse(is, new DefaultHandler(), metadata, new ParseContext());
        }
        return metadata.get(HttpHeaders.CONTENT_TYPE);
    }
}
