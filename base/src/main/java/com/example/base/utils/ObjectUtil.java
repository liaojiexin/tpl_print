package com.example.base.utils;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

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
}
