package com.github.arielcarrera.hibernate.agroal.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.arjuna.ats.jta.common.jtaPropertyManager;
import com.github.arielcarrera.hibernate.agroal.entities.TestEntity;
import com.github.arielcarrera.hibernate.agroal.services.BaseService;
import com.github.arielcarrera.hibernate.agroal.test.config.JtaEnvironment;

public abstract class BaseTest {

    @ClassRule
    public static JtaEnvironment jtaEnvironment = new JtaEnvironment();
    @Rule
    public WeldInitiator weld = WeldInitiator.from(new Weld()).inject(this).build();
    @Inject
    protected UserTransaction ut;
    
    public abstract EntityManager getEntityManager();
    
    public abstract BaseService getService();

    public BaseTest() {
	super();
    }
    
    public static TestEntity jdbcGetById(Integer id) {
        try {
            TransactionManager tm = jtaPropertyManager.getJTAEnvironmentBean().getTransactionManager();
            Transaction t = tm.suspend();
            tm.begin();
            try {
        	try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;LOCK_MODE=3",
        		null, null)) {
        	    try (PreparedStatement statement = connection
        		    .prepareStatement("SELECT `id`,`value` FROM `TestEntity` WHERE `id` = ?")) {
        		statement.setInt(1, id);
        		try (ResultSet resultSet = statement.executeQuery()) {
        		    while (resultSet.next()) {
        			return new TestEntity(resultSet.getInt("id"), resultSet.getInt("value"));
        		    }
        		}
        	    }
        	}
            } finally {
        	tm.commit();
        	if (t != null) {
        	    tm.resume(t);
        	}
            }
        } catch (Exception e) {
            fail("Invalid Test JDBC Configuration");
        }
        return null;
    }

    @Before
    public void load() throws Exception {
        ut.begin();
        for (int i = 1; i < 5; i++) {
            getEntityManager().persist(new TestEntity(i, i + 100));
        }
        ut.commit();
    }

    @Test
    public void basicTest_OK() {
        Optional<TestEntity> op = getService().findById(1);
        assertNotNull(op);
        assertTrue(op.isPresent());
    }

    @Test
    public void updateTest_OK() throws Exception {
        ut.begin();
        TestEntity e1 = getEntityManager().find(TestEntity.class, 1);
        e1.setValue(9999);
        ut.commit();
        TestEntity op = jdbcGetById(1);
        assertTrue(op.getValue() == 9999);
    }

    @Test
    public void updateFlushTest_failed() throws Exception {
        ut.begin();
        try {
            TestEntity e1 = getEntityManager().find(TestEntity.class, 1);
            assertTrue(jdbcGetById(1).getValue() == 101);
            e1.setValue(9999);
            assertTrue(jdbcGetById(1).getValue() == 101);
            getEntityManager().flush();
            assertTrue(jdbcGetById(1).getValue() == 101);
            ut.commit();
        } catch (Throwable t) {
            ut.rollback();
            throw t;
        }
        assertTrue(jdbcGetById(1).getValue() == 9999);
    }

}