package com.self.framework.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @des: 对象判空
 * @author qiuhang
 * @version v1
 */
public class ObjectCheckUtil {

    /**
     * 判断对象是否为空
     * @param T
     * @return
     */
    public static boolean checkIsNullOrEmpty(Object T){
        if(null == T){
            return true;
        }
        if (T instanceof Iterable){
            Iterable iterable = (Iterable) T;
            if (iterable.iterator().hasNext()){
                return false;
            }
        }
        if (T instanceof String){
           return StrTool.is_empty((String) T);
        }
        return false;
    }

    public static void main(String[] args) {
        List<String> test0 = Arrays.asList();
        List<String> test = Arrays.asList("111","222");
        String str = new String("aaa");
        System.out.println(ObjectCheckUtil.checkIsNullOrEmpty(str));
    }
}
