package com.self.framework.base;

import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.spring.extend.jpa.SpecificationQueryExtend;
import com.self.framework.utils.ConvertDataUtil;
import com.self.framework.utils.ObjectCheckUtil;
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

    @Autowired
    protected BaseDao<T> baseDao;

    @Autowired
    private SpecificationQueryExtend<T> querySqlBuild;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer add(T v) {
        v.setCreateTime(DEFAULT_NOW_DATE);
        v.setCreateUser("qiuhang");
        T addWho = baseDao.save(v);
        return ObjectCheckUtil.checkIsNullOrEmpty(addWho) ? 0 : 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer update(T v) {
        v.setUpdateTime(DEFAULT_NOW_DATE);
        v.setCreateUser("qiuhang");
        T updateWho = baseDao.saveAndFlush(v);
        return ObjectCheckUtil.checkIsNullOrEmpty(updateWho) ? 0 : 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Object id) {
        if (!ObjectCheckUtil.checkIsNullOrEmpty(id)) {
            baseDao.deleteById(ConvertDataUtil.convertStr(id));
        }
    }

    @Override
    public Page<T> queryListHasPagingAndSort(T v, Integer pageStart, Integer pageEnd, Integer sortType, String... sortFiled) {
//        if (ObjectCheckUtil.checkIsNullOrEmpty(v)){
//            return new PageImpl<>(new ArrayList<>());
//        }
        Sort sort = new Sort(sortType.equals(BusinessCommonConstamt.ZERO_CODE ) ? Sort.Direction.DESC : Sort.Direction.ASC, sortFiled);
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
        return one.get();
    }

}
