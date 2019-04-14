package org.loadx.application.db;

import org.apache.commons.collections4.CollectionUtils;
import org.loadx.application.db.dao.Dao;
import org.loadx.application.db.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LoadPersistent {

    private static final Logger LOG = LoggerFactory.getLogger(LoadPersistent.class);

    private Dao<LoadTask> loadTaskDao;
    private Dao<LoadRequest> loadRequestDao;
    private Dao<ExecutionDetails> executionDetailsDao;
    private Dao<LoadingExecution> loadingExecutionDao;
    private Dao<TaskRequests> taskRequestsDao;

    @Autowired
    public LoadPersistent(
            Dao<LoadTask> loadTaskDao, Dao<LoadRequest> loadRequestDao, Dao<ExecutionDetails> executionDetailsDao,
            Dao<LoadingExecution> loadingExecutionDao, Dao<TaskRequests> taskRequestsDao) {
        this.loadTaskDao = loadTaskDao;
        this.loadRequestDao = loadRequestDao;
        this.executionDetailsDao = executionDetailsDao;
        this.loadingExecutionDao = loadingExecutionDao;
        this.taskRequestsDao = taskRequestsDao;
    }

    public int save(LoadxEntity item) {
        return selectDao(item).save(item);
    }

    public List<Integer> save(List<LoadxEntity> items) {
        if (CollectionUtils.isEmpty(items)) {
            LOG.info("Empty list of items passed to persistent module.");
            return Collections.emptyList();
        }
        return selectDao(items.get(0)).save(items);
    }

    public void update(LoadxEntity item) {
        selectDao(item).update(item);
    }

    public void remove(LoadxEntity item) {
        selectDao(item).remove(item);
    }

    public void persistLoadTaskRequests(int loadTaskId, List<Integer> requestsIds) {
        List<TaskRequests> linkedRequests = requestsIds.stream()
                .map(req -> {
                    TaskRequests taskRequests = new TaskRequests();
                    taskRequests.setLoadRequestId(req);
                    taskRequests.setLoadTaskId(loadTaskId);
                    return taskRequests;
                })
                .collect(Collectors.toList());
        taskRequestsDao.save(linkedRequests);
    }

    private Dao selectDao(LoadxEntity entity) {
        if (entity instanceof LoadTask) {
            return loadTaskDao;
        } else if (entity instanceof LoadRequest) {
            return loadRequestDao;
        } else if (entity instanceof ExecutionDetails) {
            return executionDetailsDao;
        } else if (entity instanceof LoadingExecution) {
            return loadingExecutionDao;
        } else if (entity instanceof TaskRequests) {
            return taskRequestsDao;
        }
        throw new IllegalArgumentException("Given type of entity isn't compatible with current implementation");
    }
}
