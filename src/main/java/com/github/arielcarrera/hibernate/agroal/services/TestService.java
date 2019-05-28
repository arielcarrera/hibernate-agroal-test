package com.github.arielcarrera.hibernate.agroal.services;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.github.arielcarrera.hibernate.agroal.entities.TestEntity;

@Transactional
@ApplicationScoped
public class TestService {

	@Inject 
	private EntityManager em;
	
	public Optional<TestEntity> findById(Integer id) {
		return Optional.ofNullable(em.find(TestEntity.class, id));
	}
	
	public TestEntity getReference(Integer id) {
		return em.getReference(TestEntity.class, id);
	}
	
}
