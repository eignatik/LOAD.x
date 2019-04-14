package org.loadx.application.processor.tasks;

import org.loadx.application.db.LoadPersistent;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskCreator {

    private LoadPersistent loadPersistent;

    @Autowired
    public TaskCreator(LoadPersistent loadPersistent) {
        this.loadPersistent = loadPersistent;
    }

    public Task createMappingTask(String json) {
        return MappingAndPersistingTask.create()
                .withJson(json)
                .withLoadPersistent(loadPersistent)
                .build();
    }

}
