package com.github.arielcarrera.hibernate.agroal.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import io.agroal.api.AgroalDataSourceMetrics;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.github.arielcarrera.hibernate.agroal.entities.TestEntity;
import com.github.arielcarrera.hibernate.agroal.services.TestService;
import com.github.arielcarrera.hibernate.agroal.test.config.AgroalConnectionProvider;
import com.github.arielcarrera.hibernate.agroal.test.config.JtaEnvironment;

public class HibernateAgroalTest {

	@ClassRule public static JtaEnvironment jtaEnvironment = new JtaEnvironment();

	@Rule public WeldInitiator weld = WeldInitiator.from(new Weld())
			.activate(RequestScoped.class).inject(this).build();

	@Rule public TestRule watcher = new TestWatcher() {
		protected void starting(Description description) {
			System.out.println("Starting TEST: " + description.getMethodName());
			currentMethod = description.getMethodName();
		}
	};

	private static String currentMethod = "-";

	@Inject protected EntityManager entityManager;

	@Inject protected TestService service;

	@Before
	public void statusPoolBegin() {
		AgroalDataSourceMetrics metrics = AgroalConnectionProvider.getMetrics();
		if (metrics != null) {
			System.out.println("Metrics BEGIN method:" + currentMethod + ":" + metrics);
		}
	}

	@After
	public void statusPoolEnd() {
		AgroalDataSourceMetrics metrics = AgroalConnectionProvider.getMetrics();
		if (metrics != null) {
			System.out.println("Metrics END method:" + currentMethod + ":" + metrics);
		}
	}

	@Before
	public void load() {
		entityManager.getTransaction().begin();
		for (int i = 1; i < 5; i++) {
			entityManager.persist(new TestEntity(i, i + 100));
		}
		entityManager.getTransaction().commit();
	}

	@Test
	public void findByIdTest() {
		AgroalDataSourceMetrics m = AgroalConnectionProvider.getMetrics();
		long activeCount = m.activeCount();

		Optional<TestEntity> op = service.findById(1);
		assertNotNull(op);
		assertTrue(op.isPresent());
		assertTrue(op.get().getValue().equals(101));
		assertTrue("activeCount <> 0", activeCount - m.activeCount() == 0);
	}

	@Test
	public void getReferenceTest() {
		AgroalDataSourceMetrics m = AgroalConnectionProvider.getMetrics();
		long activeCount = m.activeCount();
//		entityManager.getTransaction().begin();
		TestEntity p = service.getReference(1);
		assertNotNull(p);
		assertTrue(p.getValue().equals(101));
//		entityManager.getTransaction().commit();
		assertTrue("activeCount <> 0", activeCount - m.activeCount() == 0);
	}

}
