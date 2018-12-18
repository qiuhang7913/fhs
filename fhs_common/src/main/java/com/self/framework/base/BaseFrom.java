package com.self.framework.base;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @des 基础前段from交互
 * @author qiuhang
 * @version v1.0
 */
public class BaseFrom implements Serializable {

    public String asJson(){
        return JSON.toJSONString(this);
    };
}
