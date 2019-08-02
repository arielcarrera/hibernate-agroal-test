package com.github.arielcarrera.hibernate.other.test;

import lombok.Getter;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.github.arielcarrera.hibernate.agroal.test.BaseTest;
import com.github.arielcarrera.hibernate.other.AlternativeEM;
import com.github.arielcarrera.hibernate.other.services.TestService2;

public class HibernateTransactionalConnectionProviderTest extends BaseTest {

    @Getter
    @Inject @AlternativeEM
    protected EntityManager entityManager;

    @Getter
    @Inject
    protected TestService2 service;

}
