package com.self.framework.utils;

/**
 * @des: 数据转换
 * @author qiuhang
 * @version v1
 */
public class ConvertDataUtil {

    private static String DEFAULT_STR_ = "";

    private static Integer DEFUALT_INT = 0;

    private static Double DEFUALT_DOUBLE = 0.00;
    /**
     *
     * @param obj
     * @return
     */
    public static String convertStr(Object obj){
        return null == obj ? DEFAULT_STR_ : String.valueOf(obj);
    }

    /**
     *
     * @param obj
     * @return
     */
    public static Integer convertInt(Object obj){
        if (!ObjectCheckUtil.checkIsNullOrEmpty(obj)){
            return Integer.parseInt(String.valueOf(obj));

        }
        return DEFUALT_INT;
    }

    /**
     *
     * @param obj
     * @return
     */
    public static Double convertDouble(Object obj){
        if (!ObjectCheckUtil.checkIsNullOrEmpty(obj)){
            return Double.parseDouble(String.valueOf(obj));

        }
        return DEFUALT_DOUBLE;
    }

    public static void main(String[] args) {
        System.out.println(ConvertDataUtil.convertStr(null));
    }
}
