package com.github.arielcarrera.hibernate.agroal.test.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.TransactionScoped;

/**
 * EntityManager producer
 * @author Ariel Carrera <carreraariel@gmail.com>
 *
 */
@ApplicationScoped
public class EntityManagerProducer {

    @Inject
    private EntityManagerFactory emf;
    
    @Produces
    @TransactionScoped
    public EntityManager produceEntityManager() {
        return emf.createEntityManager();
    }

    public void close(@Disposes EntityManager em) {
        em.close();
    }
    
}
    
