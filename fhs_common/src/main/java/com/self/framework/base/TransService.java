package com.self.framework.base;

import java.util.List;

/**
 * @des 翻译service
 */
public interface TransService<T extends BaseBean> {
    /**
     * 列表翻译
     * @return
     */
    void transMore(List<T> listData);

    /**
     * 单个翻译
     * @return
     */
    void transOne(T data);

}
