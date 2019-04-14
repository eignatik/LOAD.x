package org.loadx.application.db.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.loadx.application.exceptions.DataBaseConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Util class for getting session factory for hibernate.
 */
class SessionFactoryUtil {

    private static final Logger LOG = LoggerFactory.getLogger(SessionFactoryUtil.class);
    private static final SessionFactory SESSION_FACTORY;

    private SessionFactoryUtil() {
        // hidden constructor for util class purposes
    }

    static {
        try {
            Configuration configuration = new Configuration();
            SESSION_FACTORY = configuration
                    .configure()
                    .buildSessionFactory();
            LOG.info("Session factory is configured and built");
        } catch (HibernateException e) {
            throw new DataBaseConfigurationException("Hibernate session factory couldn't be built.", e);
        }
    }

    static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    static Session getCurrentSession() {
        return SESSION_FACTORY.getCurrentSession();
    }
}
