package com.github.arielcarrera.hibernate.agroal.test.config;

import org.jnp.server.NamingBeanImpl;
import org.junit.rules.ExternalResource;

import com.arjuna.ats.arjuna.common.ObjectStoreEnvironmentBean;
import com.arjuna.ats.jta.utils.JNDIManager;
import com.arjuna.common.internal.util.propertyservice.BeanPopulator;
import com.github.arielcarrera.hibernate.other.test.config.TransactionalConnectionProvider;

public class JtaEnvironment extends ExternalResource {

    private NamingBeanImpl NAMING_BEAN;

    @Override
    protected void before() throws Throwable {
        NAMING_BEAN = new NamingBeanImpl();
        NAMING_BEAN.start();

        JNDIManager.bindJTAImplementation();
        
        // Bind datasource
        TransactionalConnectionProvider.bindDataSource();
        // Set transaction log location
        setObjectStoreDir();
    }

    @Override
    protected void after() {
        NAMING_BEAN.stop();
        //Close Datasource
        //AgroalConnectionProvider.stop(); //Comentado caso contrario no soportaria el Test en la Test Suite
    }
    
    public static void setObjectStoreDir() {
        BeanPopulator.getDefaultInstance(ObjectStoreEnvironmentBean.class).setObjectStoreDir("target/tx-object-store");
        BeanPopulator.getNamedInstance(ObjectStoreEnvironmentBean.class, "communicationStore")
                .setObjectStoreDir("target/tx-object-store");
        BeanPopulator.getNamedInstance(ObjectStoreEnvironmentBean.class, "stateStore")
                .setObjectStoreDir("target/tx-object-store");
    }
}
