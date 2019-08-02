package com.github.arielcarrera.hibernate.agroal.services;

import lombok.Getter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Transactional
@ApplicationScoped
public class TestService extends BaseService {

    @Getter
    @Inject
    EntityManager em;

}
