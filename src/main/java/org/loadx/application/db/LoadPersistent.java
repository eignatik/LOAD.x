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

    private Dao dao;

    @Autowired
    public LoadPersistent(Dao dao) {
        this.dao = dao;
    }

    public int save(LoadxEntity item) {
        return dao.save(item);
    }

    public List<Integer> save(List<LoadxEntity> items) {
        if (CollectionUtils.isEmpty(items)) {
            LOG.info("Empty list of items passed to persistent module.");
            return Collections.emptyList();
        }
        return dao.save(items);
    }

    public void update(LoadxEntity item) {
        dao.update(item);
    }

    public void remove(LoadxEntity item) {
        dao.remove(item);
    }

    public void persistLoadTaskRequests(int loadTaskId, List<Integer> requestsIds) {
        List<LoadxEntity> linkedRequests = requestsIds.stream()
                .map(req -> {
                    TaskRequests taskRequests = new TaskRequests();
                    taskRequests.setLoadRequestId(req);
                    taskRequests.setLoadTaskId(loadTaskId);
                    return taskRequests;
                })
                .collect(Collectors.toList());
        dao.save(linkedRequests);
    }
}
