package com.self.framework.generator;

import com.alibaba.fastjson.JSON;
import com.self.framework.utils.ConvertDataUtil;

import java.util.HashMap;
import java.util.List;

public class StaticConfg {

    public static final String driver = ConvertDataUtil.convertStr(PropertiesInit.obtainValue("jdbc.driver"));
    public static final String url =  ConvertDataUtil.convertStr(PropertiesInit.obtainValue("jdbc.url"));
    public static final String user = ConvertDataUtil.convertStr(PropertiesInit.obtainValue("jdbc.user"));
    public static final String password = ConvertDataUtil.convertStr(PropertiesInit.obtainValue("jdbc.password"));


    /**
     * 忽略数据库字段头
     * 忽略的字段头(表名: t_user, 可以设置 hand="t_", 这样javabean就会忽略t_)
     * 如果不全是以"t_"开头, 不是的则会被忽略
     * 如果出现这种 user, t_user, 后者会覆盖前者
     */
    public static final String hand = "";

    public static final String path = ConvertDataUtil.convertStr(PropertiesInit.obtainValue("generator_code_root_path"));

    public static final String packageName = ConvertDataUtil.convertStr(PropertiesInit.obtainValue("generator_code_root_packageName"));
    /**
     * 指定生成表
     * (列: 生成user表, appoint = ["user"])
     * 注: 名字与数据库名字一致
     */
    public static final List<String> appoint = JSON.parseArray(ConvertDataUtil.convertStr(PropertiesInit.obtainValue("generator_table_names")),String.class);

    // 时间转化格式(可自定义)
    public static final String pattern = "yyyy-MM-dd HH:mm:ss";

    public static final String domainDir = "bean";

    // 如果你不想生成的字段使用驼峰转化, 可以使ishump = false
    public static final boolean ishump = true;

    // 如果你不想生成JavaBean, 仅仅想生成service/dao, 将isdomain = false;
    public static final boolean isdomain = true;


    /**
     * 生成(dao/mapper)
     * 这里会在当前目录下创建一个dao文件夹, 生成mapper
     * 你也可以自定义这个文件夹的名字,这样复制会方便许多
     * 例(daopath = "com.czxy.bos.dao")
     */
    public static final String daomkdir = "dao";
    // 如果你不想生成dao, 将isdao = false;
    public static final boolean isdao = false;

    /**
     * 生成(service)
     * 这里会在当前目录下创建一个servicemkdir文件夹, 生成service
     * 包含基本的增删改查
     * 你也可以自定义这个文件夹的名字,这样复制会方便许多
     * 例(daopath = "com.czxy.bos.dao")
     */
    public static final String servicemkdir = "service";
    // 如果你不想生成service, 将isservice = false;
    public static final boolean isservice = false;

    /**
     *Types数据类型转化
     * 可能的所有数据类型, 目前仅支持这几种
     * 如果有未知的数据类型, 默认Object
     * 你可以手动在此配置
     */
    public static final HashMap<String, String> map = new HashMap<String, String>() {
        {
            put("INT", "Integer");
            put("INT UNSIGNED", "Integer");
            put("VARCHAR", "String");
            put("CHAR", "String");
            put("DOUBLE", "Double");
            put("DATETIME", "Date");
            put("DATE", "Date");
            put("FLOAT", "Float");
            put("TIMESTAMP", "Timestamp");
            put("BIGINT", "Long");
            put("TEXT", "String");
            put("DECIMAL", "Double");
            put("LONGTEXT", "String");
            put("TINYINT UNSIGNED", "Integer");
        }
    };
}
