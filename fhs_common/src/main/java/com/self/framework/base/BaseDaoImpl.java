package com.self.framework.base;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;

public class BaseDaoImpl<T>
        extends SimpleJpaRepository<T, String> implements BaseDao<T>  {

    private final Class<T> domainClass;

    public BaseDaoImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.domainClass = domainClass;
    }

}
