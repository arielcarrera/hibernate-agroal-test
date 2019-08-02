package com.github.arielcarrera.hibernate.other.services;


import lombok.Getter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.github.arielcarrera.hibernate.agroal.services.BaseService;
import com.github.arielcarrera.hibernate.other.AlternativeEM;

@Transactional
@ApplicationScoped
public class TestService2 extends BaseService {

    	@Getter
	@Inject @AlternativeEM
	private EntityManager em;
	
	
}
