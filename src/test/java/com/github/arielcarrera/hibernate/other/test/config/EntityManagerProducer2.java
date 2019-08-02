package com.github.arielcarrera.hibernate.other.test.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.TransactionScoped;

import com.github.arielcarrera.hibernate.other.AlternativeEM;

/**
 * EntityManager producer
 * @author Ariel Carrera <carreraariel@gmail.com>
 *
 */
@ApplicationScoped
public class EntityManagerProducer2 {

    
    @Inject @AlternativeEM
    private EntityManagerFactory emf2;

    
    @Produces @AlternativeEM
    @TransactionScoped
    public EntityManager produceEntityManager2() {
        return emf2.createEntityManager();
    }

    public void close2(@Disposes @AlternativeEM EntityManager em) {
        em.close();
    }
}
