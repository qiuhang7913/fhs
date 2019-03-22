package com.self.framework.base;

import com.alibaba.fastjson.JSON;
import com.self.framework.constant.BusinessCommonConstamt;
import com.self.framework.exception.BusinessException;
import com.self.framework.spring.extend.jpa.SpecificationQueryExtend;
import com.self.framework.utils.ConvertDataUtil;
import com.self.framework.utils.DateTool;
import com.self.framework.utils.ObjectCheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Autowired
    private TransService<T> transService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addOrUpdata(T v) {
        return ObjectCheckUtil.checkIsNullOrEmpty(saveCurrData(v)) ? 0 : 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public T addOrUpdataReturn(T v) {
        return saveCurrData(v);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<String> ids) {
        List<T> beDeleteObj = new ArrayList<>();
        ids.forEach(id -> {
            Optional<T> byId = baseDao.findById(id);
            if (byId.isPresent()){
                beDeleteObj.add(byId.get());
            }
        });
        baseDao.deleteInBatch(beDeleteObj);
    }

    @Override
    public Page<T> queryListHasPagingAndSort(T v, Integer pageStart, Integer pageEnd, Integer sortType, String ... sortFiled) {
        Sort sort = new Sort(sortType.equals(BusinessCommonConstamt.ZERO_CODE) ? Sort.Direction.DESC : Sort.Direction.ASC, sortFiled);
        PageRequest of = PageRequest.of(pageStart, pageEnd, sort);
        Page<T> all = baseDao.findAll(querySqlBuild.getWhereClause(v), of);
        transService.transMore(all.getContent());
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
        List<T> rvDataList = baseDao.findAll(querySqlBuild.getWhereClause(v));
        return rvDataList;
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

    /**
     *
     * @return T
     */
    private T saveCurrData(T v){
        T one = findOne(v);
        String userNmae = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!ObjectCheckUtil.checkIsNullOrEmpty(authentication)){
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            userNmae = userDetails.getUsername();
        }

        if (ObjectCheckUtil.checkIsNullOrEmpty(one)){
            v.setCreateTime(DateTool.getDataStrByLocalDateTime(LocalDateTime.now(), DateTool.FORMAT_L3));
            v.setCreateUser(userNmae);
        }else{
            v.setCreateTime(one.getCreateTime());
            v.setCreateUser(one.getCreateUser());
            v.setUpdateTime(DateTool.getDataStrByLocalDateTime(LocalDateTime.now(), DateTool.FORMAT_L3));
            v.setUpdateUser(userNmae);
        }
        return baseDao.saveAndFlush(v);
    }
}
