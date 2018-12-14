package com.self.framework.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BaseDao<T extends BaseBean> extends JpaRepository<T, Object>, JpaSpecificationExecutor<T> {

}
