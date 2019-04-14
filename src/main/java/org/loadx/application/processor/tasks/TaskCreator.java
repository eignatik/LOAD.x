package org.loadx.application.processor.tasks;

import org.loadx.application.db.dao.Dao;
import org.loadx.application.db.entity.LoadRequest;
import org.loadx.application.db.entity.LoadTask;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskCreator {

    private Dao<LoadRequest> loadRequestDao;
    private Dao<LoadTask> loadTaskDao;

    @Autowired
    public TaskCreator(Dao<LoadTask> loadTaskDao, Dao<LoadRequest> loadRequestDao) {
        this.loadTaskDao = loadTaskDao;
        this.loadRequestDao = loadRequestDao;
    }

    public Task createMappingTask(String json) {
        return MappingAndPersistingTask.create()
                .withJson(json)
                .withLoadRequestDao(loadRequestDao)
                .withLoadTaskDao(loadTaskDao)
                .build();
    }

}
