package com.self.framework.otherBean;

import com.self.framework.base.BaseVo;

/**
 * @des 公共combox vo
 * @author qiuhang
 * @version v1.0
 */
public class ComboxVo extends BaseVo {
    private String value;

    private String text;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
