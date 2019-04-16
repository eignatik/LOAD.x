package org.loadx.application.processor.tasks;

import org.loadx.application.db.dao.LoadxDataHelper;
import org.loadx.application.db.entity.LoadTask;
import org.loadx.application.http.WebsitesHttpConnector;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

public class TaskCreator {

    private LoadxDataHelper dataHelper;
    private WebsitesHttpConnector httpConnector;

    @Autowired
    public TaskCreator(LoadxDataHelper loadxDataHelper, WebsitesHttpConnector httpConnector) {
        this.dataHelper = loadxDataHelper;
        this.httpConnector = httpConnector;
    }

    public Task createMappingTask(String json) {
        return MappingAndPersistingTask.create()
                .withJson(json)
                .withDataHelper(dataHelper)
                .build();
    }

    public Task createLoadingTask(int taskId) {
        return LoadingTask.create()
                .withDataHelper(dataHelper)
                .withLoadRequests(Collections.emptyList())
                .withLoadTask(dataHelper.getLoadTaskDao().getById(taskId, LoadTask.class))
                .withConnector(httpConnector)
                .build();
    }

}
