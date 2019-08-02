package com.github.arielcarrera.hibernate.other.test.config;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.github.arielcarrera.hibernate.other.AlternativeEM;

/**
 * EntityManagerFactory producer
 * 
 * @author Ariel Carrera <carreraariel@gmail.com>
 *
 */
@ApplicationScoped
public class EntityManagerFactoryProducer2 {

	@Inject
	private BeanManager beanManager;

	@Produces @AlternativeEM
	@ApplicationScoped
	public EntityManagerFactory produceEntityManagerFactory2() {
		Map<String, Object> props = new HashMap<>();
		props.put("javax.persistence.bean.manager", beanManager);
		return Persistence.createEntityManagerFactory("testPersistenceUnit2", props);
	}

	public void close2(@Disposes @AlternativeEM EntityManagerFactory emf) {
		emf.close();
	}
	
	
}
