package org.loadx.application.config;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.loadx.application.db.dao.*;
import org.loadx.application.db.entity.*;
import org.loadx.application.http.HttpClientManager;
import org.loadx.application.processor.TaskProcessor;
import org.loadx.application.processor.tasks.TaskCreator;
import org.springframework.beans.factory.annotation.Autowired;
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
@PropertySource("environment.properties")
public class ApplicationConfig {

    private static final String PACKAGE_TO_SCAN = "org.loadx.application.db";

    @Autowired
    private Environment env;

    @Bean
    public Dao<LoadTask> loadTaskDao(SessionFactory sessionFactory) {
        return new LoadxDao<>(sessionFactory);
    }

    @Bean
    public Dao<LoadRequest> loadRequestDao(SessionFactory sessionFactory) {
        return new LoadxDao<>(sessionFactory);
    }

    @Bean
    public Dao<ExecutionDetails> executionDetailsDao(SessionFactory sessionFactory) {
        return new ExecutionDetailsDao(sessionFactory);
    }

    @Bean
    public Dao<LoadingExecution> loadingExecutionDao(SessionFactory sessionFactory) {
        return new LoadxDao<>(sessionFactory);
    }

    @Bean
    public Dao<TaskRequests> taskRequestsDao(SessionFactory sessionFactory) {
        return new TaskRequestsDao(sessionFactory);
    }

    @Bean
    public LoadxDataHelper loadxDataHelper(Dao<LoadTask> loadTaskDao, Dao<LoadRequest> loadRequestDao,
                                           Dao<ExecutionDetails> executionDetailsDao,
                                           Dao<LoadingExecution> loadingExecutionDao, Dao<TaskRequests> taskRequestsDao,
                                           SessionFactory sessionFactory) {
        return new LoadxDataHelper(
                loadTaskDao, loadRequestDao, executionDetailsDao, loadingExecutionDao, taskRequestsDao, sessionFactory);
    }

    @Bean
    public TaskCreator taskCreator(LoadxDataHelper loadxDataHelper, HttpClientManager httpClientManager) {
        return new TaskCreator(loadxDataHelper, httpClientManager);
    }

    @Bean
    public TaskProcessor processor() {
        return new TaskProcessor();
    }

    @Bean
    public HttpClientManager httpClientManager(VertxProperties vertxProperties) {
        return new HttpClientManager(vertxProperties);
    }

    @Bean
    public VertxProperties vertxProperties() {
        return new VertxProperties();
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource restDataSource, Properties dataBaseProperties) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(restDataSource);
        sessionFactory.setPackagesToScan(PACKAGE_TO_SCAN);
        sessionFactory.setHibernateProperties(dataBaseProperties);
        return sessionFactory;
    }

    @Bean
    public DataSource restDataSource(Properties dataBaseProperties) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(dataBaseProperties.getProperty("hibernate.connection.driver_class"));
        dataSource.setUrl(dataBaseProperties.getProperty("hibernate.connection.url"));
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

    @Bean
    Properties dataBaseProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.driver_class", env.getProperty("hibernate.driver.class"));
        properties.setProperty("hibernate.connection.pool_size", env.getProperty("hibernate.connection.pool"));
        properties.setProperty("hibernate.connection.url", env.getProperty("hibernate.db.path"));
        properties.setProperty(
                "hibernate.current_session_context_class", env.getProperty("hibernate.current.session.context.class"));
        properties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show.sql"));
        properties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));

        properties.setProperty(
                "hibernate.cache.use_second_level_cache", env.getProperty("hibernate.cache.use_second_level_cache"));
        properties.setProperty(
                "hibernate.cache.region.factory_class", env.getProperty("hibernate.cache.region.factory_class"));
        properties.setProperty(
                "hibernate.javax.cache.provider", env.getProperty("hibernate.javax.cache.provider"));
        return properties;
    }

}
