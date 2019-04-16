package org.loadx.application.processor.tasks;

import org.loadx.application.db.dao.Dao;
import org.loadx.application.db.entity.LoadTask;
import org.loadx.application.http.WebsitesHttpConnector;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

public class TaskCreator {

    private Dao dao;
    private WebsitesHttpConnector httpConnector;

    @Autowired
    public TaskCreator(Dao dao, WebsitesHttpConnector httpConnector) {
        this.dao = dao;
        this.httpConnector = httpConnector;
    }

    public Task createMappingTask(String json) {
        return MappingAndPersistingTask.create()
                .withJson(json)
                .withDao(dao)
                .build();
    }

    public Task createLoadingTask(int taskId) {
        return LoadingTask.create()
                .withDao(dao)
                .withLoadRequests(Collections.emptyList())
                .withLoadTask((LoadTask) dao.getById(taskId, LoadTask.class))
                .withConnector(httpConnector)
                .build();
    }

}
