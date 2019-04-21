package org.loadx.application.db.dao;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.loadx.application.db.entity.ExecutionDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ExecutionDetailsDao extends LoadxDao<ExecutionDetails> {

    private static final Logger LOG = LoggerFactory.getLogger(ExecutionDetailsDao.class);

    public ExecutionDetailsDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<ExecutionDetails> getDetailsByExecutionId(int executionId) {
        Transaction transaction = getSession().beginTransaction();
        Query<ExecutionDetails> query = getSession().createQuery("from ExecutionDetails where executionId = :executionId");
        query.setParameter("executionId", executionId);
        List<ExecutionDetails> details = query.list();
        transaction.commit();
        LOG.info("Fetched records: fetched={}, executionId={}", details.size(), executionId);
        return details;
    }
}
