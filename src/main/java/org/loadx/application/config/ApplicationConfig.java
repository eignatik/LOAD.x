package org.loadx.application.config;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.loadx.application.db.LoadPersistent;
import org.loadx.application.db.dao.Dao;
import org.loadx.application.db.dao.GenericDao;
import org.loadx.application.http.WebsitesHttpConnector;
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
    public Dao dao(SessionFactory sessionFactory) {
        return new GenericDao(sessionFactory);
    }

    @Bean
    public TaskCreator taskCreator(LoadPersistent loadPersistent, WebsitesHttpConnector httpConnector) {
        return new TaskCreator(loadPersistent, httpConnector);
    }

    @Bean
    public LoadPersistent loadPersister(Dao dao) {
        return new LoadPersistent(dao);
    }

    @Bean
    public WebsitesHttpConnector httpConnector(HttpProperties httpClientProperties) {
        return WebsitesHttpConnector.createWithProperties(httpClientProperties);
    }

    @Bean
    public HttpProperties httpClientProperties() {
        return new HttpProperties();
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
        return properties;
    }

}
