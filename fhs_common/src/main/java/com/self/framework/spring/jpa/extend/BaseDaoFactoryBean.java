package com.self.framework.spring.jpa.extend;

import com.self.framework.base.BaseDaoImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class BaseDaoFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable>
            extends JpaRepositoryFactoryBean<R, T, I> {

    public BaseDaoFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new MyBaseDaoFactory(entityManager);
    }

    private static class MyBaseDaoFactory<T> extends JpaRepositoryFactory {

        public MyBaseDaoFactory(EntityManager em) {
            super(em);
        }

        @Override
        protected SimpleJpaRepository<?, ?> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
            return new BaseDaoImpl<T>((Class<T>) information.getDomainType(),entityManager);
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return BaseDaoImpl.class;
        }
    }
}
