package com.self.framework.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @des 基础result
 * @author qiuhang
 * @version v1.0
 */
public class BaseReult implements Serializable {

    /**
     * 返回操作code 码
     */
    private Integer code;

    /**
     * 返回操作描述
     */
    private String describe;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
