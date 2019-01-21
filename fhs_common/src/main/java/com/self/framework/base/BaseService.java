package com.self.framework.base;

import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.utils.DateTool;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @des 基础service接口
 * @author qiuhang
 * @version v1.0
 * @param <T>
 */
public interface BaseService<T> {
    String DEFAULT_SORT_FILE = "updateTime";//默认排序字段
    Integer DEFAULT_SORT_TYPE = BusinessCommonConstamt.ZERO_CODE;//默认排序字段
    String DEFAULT_NOW_DATE = DateTool.getDataStrByLocalDateTime(LocalDateTime.now(), DateTool.FORMAT_L6);

    /**
     * @des 添加方法
     * @return
     */
    Integer add(T v);

    /**
     * @des 更新方法
     * @return
     */
    Integer update(T v);

    /**
     * @des 删除方法
     * @return
     */
    void delete(Object id);

    /**
     * @des 列表查询 支持分页和排序
     * @return
     */
    Page<T> queryListHasPagingAndSort(T v, Integer pageStart, Integer pageEnd, Integer sortType, String ... sortFiled);

    /**
     * @des 列表查询 支持分页
     * @return
     */
    Page<T> queryListHasPaging(T v, Integer pageStart, Integer pageEnd);

    /**
     * @des 列表查询
     * @return
     */
    List<T> queryList(T v);

    /**
     * @des 详情数据查询
     * @param v
     * @return
     */
    T findOne(T v);
}
