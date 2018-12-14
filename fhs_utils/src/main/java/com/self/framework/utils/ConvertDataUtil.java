package com.self.framework.utils;

/**
 * @des: 数据转换
 * @author qiuhang
 * @version v1
 */
public class ConvertDataUtil {

    private static String DEFAULT_STR_ = "";

    public static String convertStr(Object obj){
        return null == obj ? DEFAULT_STR_ : String.valueOf(obj);
    }

    public static void main(String[] args) {
        System.out.println(ConvertDataUtil.convertStr(null));
    }
}
