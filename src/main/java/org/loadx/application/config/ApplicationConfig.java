package org.loadx.application.config;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.loadx.application.db.LoadPersistent;
import org.loadx.application.db.dao.Dao;
import org.loadx.application.db.dao.GenericDao;
import org.loadx.application.db.entity.*;
import org.loadx.application.processor.tasks.TaskCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties
@PropertySource("environment.properties")
public class ApplicationConfig {

    @Autowired
    private Environment env;

    @Bean(name = "loadTaskDao")
    public Dao<LoadTask> loadTaskDao(SessionFactory sessionFactory) {
        return new GenericDao<>(sessionFactory);
    }

    @Bean
    public TaskCreator taskCreator(LoadPersistent loadPersistent) {
        return new TaskCreator(loadPersistent);
    }

    @Bean
    public LoadPersistent loadPersister(
            Dao<LoadTask> loadTaskDao,
            Dao<LoadRequest> loadRequestDao,
            Dao<ExecutionDetails> executionDetailsDao,
            Dao<LoadingExecution> loadingExecutionDao,
            Dao<TaskRequests> taskRequestsDao) {
        return new LoadPersistent(loadTaskDao, loadRequestDao, executionDetailsDao, loadingExecutionDao, taskRequestsDao);
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(restDataSource());
        sessionFactory.setPackagesToScan("org.loadx.application.db");
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    public DataSource restDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("hibernate.driver.class"));
        dataSource.setUrl(env.getProperty("hibernate.db.path"));
//        dataSource.setUsername(env.getProperty("jdbc.user"));
//        dataSource.setPassword(env.getProperty("jdbc.pass"));

        return dataSource;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(
            SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    /**
     * TODO: configure via ConfigurationProperties
     *
     * @return
     */
    Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.connection.driver_class", env.getProperty("hibernate.driver.class"));
                setProperty("hibernate.connection.url", env.getProperty("hibernate.db.path"));
                setProperty("hibernate.connection.pool_size", env.getProperty("hibernate.connection.pool"));
                setProperty("hibernate.current_session_context_class", env.getProperty("hibernate.current.session.context.class"));
                setProperty("hibernate.show_sql", env.getProperty("hibernate.show.sql"));
                setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
            }
        };
    }

}
