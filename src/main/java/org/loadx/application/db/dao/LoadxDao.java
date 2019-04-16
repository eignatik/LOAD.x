package org.loadx.application.db.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.loadx.application.db.entity.LoadRequest;
import org.loadx.application.db.entity.LoadTask;
import org.loadx.application.db.entity.LoadxEntity;
import org.loadx.application.db.entity.TaskRequests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Generic LoadxDao class for all entities.
 * <p>
 * The class provides with the CRUD operations in a generic way.
 *
 * @see LoadxEntity
 * @see Dao for detailed JavaDoc per method.
 */
@Component
@SuppressWarnings("unchecked")
public class LoadxDao<T extends LoadxEntity> implements Dao<T> {

    private SessionFactory sessionFactory;

    @Autowired
    public LoadxDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private static final Logger LOG = LoggerFactory.getLogger(LoadxDao.class);

    public T getById(int id, Class<T> type) {
        Transaction transaction = getSession().beginTransaction();
        T item = (T) getSession().get(type, id);
        transaction.commit();
        LOG.info("Fetched the record: item={}", item);
        return item;
    }

    public List<T> getAll(Class type) {
        Transaction transaction = getSession().beginTransaction();
        Query<T> query = getSession().createQuery(createCriteriaQuery(type));
        List<T> items = query.getResultList();
        transaction.commit();
        LOG.info("Fetched the records: items.size={}", items.size());
        return items;
    }

    @Override
    public int save(T item) {
        Transaction transaction = getSession().beginTransaction();
        int id = (Integer) getSession().save(item);
        transaction.commit();
        LOG.info("The item is saved and id generated: id={}", id);
        return id;
    }

    @Override
    public List<Integer> save(List<T> items) {
        Transaction transaction = getSession().beginTransaction();
        List<Integer> savedIds = items.stream()
                .map(item -> (Integer) getSession().save(item))
                .collect(Collectors.toList());
        transaction.commit();
        LOG.info("The items were saved and ids generated: savedIds.size={}", savedIds.size());
        return savedIds;
    }

    @Override
    public void update(T item) {
        Transaction transaction = getSession().beginTransaction();
        getSession().update(item);
        transaction.commit();
        LOG.info("The item updated: id={}, entityClass={}", item.getId(), item.getClass().getSimpleName());
    }

    @Override
    public void remove(LoadxEntity item) {
        Transaction transaction = getSession().beginTransaction();
        getSession().delete(item);
        transaction.commit();
        LOG.info("Item from DB removed: id={}, entityClass={}", item.getId(), item.getClass().getSimpleName());
    }

    @Override
    public void remove(List<T> items) {
        Transaction transaction = getSession().beginTransaction();
        items.forEach(item -> getSession().delete("", item));
        transaction.commit();
        LOG.info("Removed items: count=", items.size());
    }

    @Override
    public List<Integer> saveAll(List<T> items) {
        return items.stream()
                .map(item -> (Integer) getSession().save(item))
                .collect(Collectors.toList());
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    private CriteriaQuery<T> createCriteriaQuery(Class<T> type) {
        CriteriaQuery<T> criteriaQuery = getSession()
                .getCriteriaBuilder()
                .createQuery(type);
        Root<T> rootItem = criteriaQuery.from(type);
        criteriaQuery.select(rootItem);
        return criteriaQuery;
    }

}
