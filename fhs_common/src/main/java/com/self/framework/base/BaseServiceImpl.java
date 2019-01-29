package com.self.framework.base;

import com.alibaba.fastjson.JSON;
import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.exception.BusinessException;
import com.self.framework.spring.extend.jpa.SpecificationQueryExtend;
import com.self.framework.utils.ConvertDataUtil;
import com.self.framework.utils.ObjectCheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @des 基础service实现
 * @author qiuhang
 * @version v1.0
 * @param <T>
 */
public class BaseServiceImpl<T extends BaseBean> implements BaseService<T> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected BaseDao<T> baseDao;

    @Autowired
    private SpecificationQueryExtend<T> querySqlBuild;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addOrUpdata(T v) {
        T one = this.findOne(v);
        if (!ObjectCheckUtil.checkIsNullOrEmpty(one)){
            v = one;
            v.setUpdateTime(DEFAULT_NOW_DATE);
            v.setUpdateUser("qiuhang");
        }else{
            v.setCreateTime(DEFAULT_NOW_DATE);
            v.setCreateUser("qiuhang");
        }
        T addWho = baseDao.saveAndFlush(v);
        return ObjectCheckUtil.checkIsNullOrEmpty(addWho) ? 0 : 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<String> ids) {
        if (!ObjectCheckUtil.checkIsNullOrEmpty(ids)) {
            ids.forEach(id -> {
                try {
                    baseDao.deleteById(id);
                }catch (Exception e){
                    throw new BusinessException(e.getMessage());
                }
            });
        }
    }

    @Override
    public Page<T> queryListHasPagingAndSort(T v, Integer pageStart, Integer pageEnd, Integer sortType, String ... sortFiled) {
        Sort sort = new Sort(sortType.equals(BusinessCommonConstamt.ZERO_CODE) ? Sort.Direction.DESC : Sort.Direction.ASC, sortFiled);
        PageRequest of = PageRequest.of(pageStart, pageEnd, sort);
        Page<T> all = baseDao.findAll(querySqlBuild.getWhereClause(v), of);
        return all;
    }

    @Override
    public Page<T> queryListHasPaging(T v, Integer pageStart, Integer pageEnd) {
        return this.queryListHasPagingAndSort(v, pageStart, pageEnd, DEFAULT_SORT_TYPE, DEFAULT_SORT_FILE);
    }

    @Override
    public List<T> queryList(T v) {
        if (ObjectCheckUtil.checkIsNullOrEmpty(v)){
            return new ArrayList<>();
        }
        return baseDao.findAll(querySqlBuild.getWhereClause(v));
    }

    @Override
    public T findOne(T v) {
        Example<T> of = Example.of(v);
        Optional<T> one = baseDao.findOne(of);
        try {
            return one.get();
        }catch (Exception e){
            logger.error("数据查询错误,查询参数为{}",JSON.toJSONString(v));
            return null;
        }
    }

    @Override
    public T findOneById(String id) {
        return baseDao.findById(id).get();
    }
}
