package com.example.controller;

import com.example.base.utils.PdfUtil;

import java.io.*;

public class PdfTests {

    public static void main(String[] args) throws Exception {
        execlToPdfByte();
    }

    public static void execlToPdfByte() throws Exception{
        File file=new File("C:\\Users\\admin\\Desktop\\专项支出用款申请书_map.xls");
        InputStream inputStream=new FileInputStream(file);
        byte[] byt = new byte[inputStream.available()];
        inputStream.read(byt);
        byte[] bytes=PdfUtil.excelToPdfBytes(byt);
        File file1 = new File("C:\\Users\\admin\\Desktop\\专项支出用款申请书_map.pdf");
        OutputStream output = new FileOutputStream(file1);
        BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);
        bufferedOutput.write(bytes);
    }
}
