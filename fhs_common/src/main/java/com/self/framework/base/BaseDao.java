package com.self.framework.base;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @des 当前所有库表主键类型都为string
 * @param <T>
 */
@NoRepositoryBean
public interface BaseDao<T>
        extends JpaRepository<T, String>, JpaSpecificationExecutor<T> {
    List<Map<String, Object>> findOther(String sql, Map<String,Object> param);

    <Z> List<Z> findOther(String sql, Map<String,Object> param, Class<Z> zClass);

    boolean insertOther(BaseBean baseBean);
}
