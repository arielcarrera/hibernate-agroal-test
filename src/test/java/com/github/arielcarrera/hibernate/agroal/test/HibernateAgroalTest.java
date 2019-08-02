package com.github.arielcarrera.hibernate.agroal.test;

import lombok.Getter;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.github.arielcarrera.hibernate.agroal.services.TestService;

public class HibernateAgroalTest extends BaseTest {

    @Getter
    @Inject
    protected EntityManager entityManager;

    @Getter
    @Inject
    protected TestService service;
}
