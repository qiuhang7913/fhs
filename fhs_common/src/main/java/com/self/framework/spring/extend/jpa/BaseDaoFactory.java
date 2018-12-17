package com.self.framework.spring.extend.jpa;

import com.self.framework.base.BaseDao;
import com.self.framework.base.BaseDaoImpl;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class BaseDaoFactory<S, ID extends Serializable>
            extends JpaRepositoryFactory  {

    public BaseDaoFactory(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected SimpleJpaRepository<?, ?> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
        return new BaseDaoImpl(information.getDomainType(), entityManager);
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return BaseDao.class;
    }
}
