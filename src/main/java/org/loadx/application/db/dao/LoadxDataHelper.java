package org.loadx.application.db.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.loadx.application.db.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class LoadxDataHelper {

    private static final Logger LOG = LoggerFactory.getLogger(LoadxDataHelper.class);

    private Dao<LoadTask> loadTaskDao;
    private Dao<LoadRequest> loadRequestDao;
    private Dao<ExecutionDetails> executionDetailsDao;
    private Dao<LoadingExecution> loadingExecutionDao;
    private Dao<TaskRequests> taskRequestsDao;
    private SessionFactory sessionFactory;

    @Autowired
    public LoadxDataHelper(Dao<LoadTask> loadTaskDao, Dao<LoadRequest> loadRequestDao, Dao<ExecutionDetails> executionDetailsDao, Dao<LoadingExecution> loadingExecutionDao, Dao<TaskRequests> taskRequestsDao, SessionFactory sessionFactory) {
        this.loadTaskDao = loadTaskDao;
        this.loadRequestDao = loadRequestDao;
        this.executionDetailsDao = executionDetailsDao;
        this.loadingExecutionDao = loadingExecutionDao;
        this.taskRequestsDao = taskRequestsDao;
        this.sessionFactory = sessionFactory;
    }

    public int persistLoadTaskRequests(LoadTask task, List<LoadRequest> requests) {
        Transaction transaction = sessionFactory.getCurrentSession().beginTransaction();

        int loadTaskId = (Integer) sessionFactory.getCurrentSession().save(task);
        List<Integer> savedIds = loadRequestDao.saveAll(requests);

        List<TaskRequests> linkedRequests = savedIds.stream()
                .map(req -> {
                    TaskRequests taskRequests = new TaskRequests();
                    taskRequests.setLoadRequestId(req);
                    taskRequests.setLoadTaskId(loadTaskId);
                    return taskRequests;
                })
                .collect(Collectors.toList());

        taskRequestsDao.saveAll(linkedRequests);
        transaction.commit();
        LOG.info("Task and request are saved successfully: loadTaskId={}, requests={}", loadTaskId, linkedRequests.size());

        return loadTaskId;
    }

    public List<LoadRequest> getLoadRequestsByTaskId(int taskId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Query<LoadRequest> query = session.createQuery("from LoadRequest as lr " +
                "where lr.id in (select tr.loadRequestId from TaskRequests as tr where tr.loadTaskId = :taskId)");
        query.setParameter("taskId", taskId);

        List<LoadRequest> resultList = query.getResultList();

        transaction.commit();
        return resultList;
    }

    public void deleteExecution(int executionId) {
        ((ExecutionDetailsDao) executionDetailsDao).deleteDetailsByExecutionId(executionId);
        loadingExecutionDao.remove(executionId, LoadingExecution.class);
    }

    public void deleteTask(int taskId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Query<LoadRequest> query = session.createQuery("delete from LoadRequest as lr " +
                "where lr.id in (select tr.loadRequestId from TaskRequests as tr where tr.loadTaskId = :taskId)");
        query.setParameter("taskId", taskId);

        int deletedRows = query.executeUpdate();
        LOG.info("Deleted load task: taskId={}, requests={}", taskId, deletedRows);
        transaction.commit();

        loadTaskDao.remove(taskId, LoadTask.class);
        ((TaskRequestsDao) taskRequestsDao).removeRequestsByTaskId(taskId);
    }

    public Dao<LoadTask> getLoadTaskDao() {
        return loadTaskDao;
    }

    public Dao<LoadRequest> getLoadRequestDao() {
        return loadRequestDao;
    }

    public ExecutionDetailsDao getExecutionDetailsDao() {
        return (ExecutionDetailsDao) executionDetailsDao;
    }

    public Dao<LoadingExecution> getLoadingExecutionDao() {
        return loadingExecutionDao;
    }

    public TaskRequestsDao getTaskRequestsDao() {
        return (TaskRequestsDao) taskRequestsDao;
    }
}
