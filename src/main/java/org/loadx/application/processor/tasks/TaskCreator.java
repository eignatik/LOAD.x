package org.loadx.application.processor.tasks;

import org.loadx.application.db.LoadPersistent;
import org.loadx.application.http.WebsitesHttpConnector;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

public class TaskCreator {

    private LoadPersistent loadPersistent;
    private WebsitesHttpConnector connector;

    @Autowired
    public TaskCreator(LoadPersistent loadPersistent, WebsitesHttpConnector connector) {
        this.loadPersistent = loadPersistent;
        this.connector = connector;
    }

    public Task createMappingTask(String json) {
        return MappingAndPersistingTask.create()
                .withJson(json)
                .withLoadPersistent(loadPersistent)
                .build();
    }

    public Task createLoadingTask(String json) {
        return LoadingTask.create()
                .withLoadPersistent(loadPersistent)
                .withLoadRequests(Collections.emptyList())
                .withLoadTask(null)
                .withConnector(connector)
                .build();
    }

}
