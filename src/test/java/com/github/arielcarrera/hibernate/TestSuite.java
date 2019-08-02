package com.github.arielcarrera.hibernate;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.github.arielcarrera.hibernate.agroal.test.HibernateAgroalTest;
import com.github.arielcarrera.hibernate.other.test.HibernateTransactionalConnectionProviderTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ HibernateTransactionalConnectionProviderTest.class, HibernateAgroalTest.class })
public class TestSuite {
    // This class remains empty, it is used only as a holder for the above
    // annotations
}