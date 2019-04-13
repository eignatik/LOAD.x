package org.loadx.application.db;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.loadx.application.exceptions.DataBaseConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionFactoryUtil {

    private static final Logger LOG = LoggerFactory.getLogger(SessionFactoryUtil.class);
    private static final SessionFactory SESSION_FACTORY;

    static {
        try {
            Configuration configuration = new Configuration();
            SESSION_FACTORY = configuration
                    .configure()
                    .buildSessionFactory();
        } catch (HibernateException e) {
            String message = "Hibernate session factory couldn't be built.";
            LOG.error(message, e);
            throw new DataBaseConfigurationException(message, e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }
}
