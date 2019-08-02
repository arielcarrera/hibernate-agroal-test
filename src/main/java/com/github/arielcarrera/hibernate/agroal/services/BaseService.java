package com.github.arielcarrera.hibernate.agroal.services;

import java.util.Optional;

import javax.persistence.EntityManager;

import com.github.arielcarrera.hibernate.agroal.entities.TestEntity;

public abstract class BaseService {

    public BaseService() {
	super();
    }
    
    public abstract EntityManager getEm();

    public Optional<TestEntity> findById(Integer id) {
    	return Optional.ofNullable(getEm().find(TestEntity.class, id));
    }

    public TestEntity getReference(Integer id) {
    	return getEm().getReference(TestEntity.class, id);
    }

}