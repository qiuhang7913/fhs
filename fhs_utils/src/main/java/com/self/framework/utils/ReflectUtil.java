package com.self.framework.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @des: 反射工具类
 * @author qiuhang
 * @version v1
 */
public class ReflectUtil {

    public static Method reflectObjObtainFileMethod(Object cls, String fileName){
        List<Method> ms = Arrays.asList(cls.getClass().getMethods());
        List<Method> methods = ms.stream().filter((Method method) -> ("get" + fileName).toLowerCase().equals(method.getName().toLowerCase())).collect(Collectors.toList());
        return methods.get(0);
    }

    /**
     *
     * @param object
     * @param fileName
     * @return
     */
    public static Class<?> reflectObjObtainFileClassType(Object object, String fileName) {
        return reflectObjObtainFileMethod(object,fileName).getReturnType();
    }

    /**
     *
     * @param object
     * @param fileName
     * @return
     */
    public static Object reflectObjObtainFileValue(Object object, String fileName) {
        Object invoke = null;
        try {
            invoke = reflectObjObtainFileMethod(object, fileName).invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e ) {
            e.printStackTrace();
        }
        return invoke;
    }

    /**
     * 通过反射获取泛型类的泛型
     * @return
     */
    public static Object reflectObtainGenericOfGenericClass(Class cls, String name){
        Type genericType = null;
        try {
            genericType = cls.getDeclaredField(cls.getName()).getGenericType();
            ParameterizedType pt = (ParameterizedType)genericType;
            Type[] args = pt.getActualTypeArguments();
            return args[0];
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

    }
}
