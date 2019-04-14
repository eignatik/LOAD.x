package org.loadx.application.db.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.loadx.application.db.entity.LoadxEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Generic GenericDao class for all entities.
 * <p>
 * The class provides with the CRUD operations in a generic way.
 *
 * @param <T> class implementing LoadxEntity
 * @see LoadxEntity
 * @see Dao for detailed JavaDoc per method.
 */
@SuppressWarnings("unchecked")
public class GenericDao<T extends LoadxEntity> implements Dao<T> {

    private static final Logger LOG = LoggerFactory.getLogger(GenericDao.class);

    public T getById(int id, Class<T> type) {
        Transaction transaction = getSession().beginTransaction();
        T item = getSession().get(type, id);
        transaction.commit();
        return item;
    }

    public List<T> getAll(Class<T> type) {
        Transaction transaction = getSession().beginTransaction();
        Query<T> query = getSession().createQuery(createCriteriaQuery(type));
        List<T> resultList = query.getResultList();
        transaction.commit();
        return resultList;
    }

    @Override
    public int save(T item) {
        Transaction transaction = getSession().beginTransaction();
        int id = (Integer) getSession().save(item);
        transaction.commit();
        return id;
    }

    @Override
    public List<Integer> save(List<T> items) {
        Transaction transaction = getSession().beginTransaction();
        List<Integer> savedIds = items.stream()
                .map(item -> (Integer) getSession().save(item))
                .collect(Collectors.toList());
        transaction.commit();
        return savedIds;
    }

    @Override
    public void update(T item) {
        Transaction transaction = getSession().beginTransaction();
        getSession().update(item);
        transaction.commit();
    }

    @Override
    public void remove(T item) {
        Transaction transaction = getSession().beginTransaction();
        getSession().delete(item);
        transaction.commit();
        LOG.info("Item from DB: " + item + " has been removed.");
    }

    private Session getSession() {
        return SessionFactoryUtil.getCurrentSession();
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
