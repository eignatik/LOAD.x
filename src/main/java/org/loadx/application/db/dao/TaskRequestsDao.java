package org.loadx.application.db.dao;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.loadx.application.db.entity.TaskRequests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskRequestsDao extends LoadxDao<TaskRequests> {

    private static final Logger LOG = LoggerFactory.getLogger(TaskRequestsDao.class);

    public TaskRequestsDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void removeRequestsByTaskId(int taskId) {
        Transaction transaction = getSession().beginTransaction();
        Query query = getSession().createQuery("delete TaskRequests as tr where tr.loadTaskId = :taskId");
        query.setParameter("taskId", taskId);
        int deletedNumber = query.executeUpdate();
        transaction.commit();
        LOG.info("Deleted TaskRequests records: deletedNumber={}, taskId={}", deletedNumber, taskId);
    }
}
