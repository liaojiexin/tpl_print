package com.example.base.utils;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObjectUtil {

    //判断多个属性是否为空
    public static boolean isExist(Object ...objects){
        for (Object object:objects) {
            if(StringUtils.isBlank(ObjectUtils.toString(object))){
                return false;
            }
        }
        return true;
    }

    //解析字符串json数据
    public static List<String> parseJson(String s){
        List<String> result=new ArrayList();
        ArrayDeque stack = new ArrayDeque();
        String[] prefix={"[","{","(","<"};
        String[] suffix={"]","}",")",">"};
        String pre_flag=s.substring(0,1);   //获取解析字符串的第一个字符，例如"{"
        if (Arrays.asList(prefix).contains(pre_flag)){      //包含要解析的标识符
            int i;
            for (i=0;i<prefix.length;i++){
                if (prefix[i].equals(pre_flag))
                    break;
            }
            String suf_flag=suffix[i];  //对应第一个字符的标志,例如”}“
            char[] chars=s.toCharArray();
            int flag=0; //标志位
            for (int z=0;z<chars.length;z++){
                if (chars[z]==pre_flag.charAt(0))     //如果byte值为前缀字符（如”{“），入栈
                    stack.push(pre_flag);
                if (chars[z]==suf_flag.charAt(0))     //如果byte值为后缀字符（如”}“），出栈
                    stack.pop();
                if (stack.isEmpty()){
                    result.add(s.substring(flag,z+1));
                    z=z+1;
                    if (z<chars.length && chars[z]==',')
                        flag=z+1;
                }
            }
        }
        return result;
    }
}
