package org.loadx.application.processor.tasks;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import org.loadx.application.config.VertxProperties;
import org.loadx.application.db.dao.LoadxDataHelper;
import org.loadx.application.db.entity.LoadTask;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

public class TaskCreator {

    private LoadxDataHelper dataHelper;
    private VertxProperties vertxProperties;

    @Autowired
    public TaskCreator(LoadxDataHelper loadxDataHelper, VertxProperties vertxProperties) {
        this.dataHelper = loadxDataHelper;
        this.vertxProperties = vertxProperties;
    }

    public Task createMappingTask(String json) {
        return MappingAndPersistingTask.create()
                .withJson(json)
                .withDataHelper(dataHelper)
                .build();
    }

    public Task createLoadingTask(int taskId) {
        LoadTask loadTask = dataHelper.getLoadTaskDao().getById(taskId, LoadTask.class);

        Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(100));
        WebClientOptions options = new WebClientOptions()
                .setConnectTimeout(vertxProperties.getConnectTimeout())
                .setMaxPoolSize(loadTask.getParallelThreshold())
                .setMaxWaitQueueSize(1000);
        WebClient client = WebClient.create(vertx, options);

        return LoadingTask.create()
                .withDataHelper(dataHelper)
                .withLoadRequests(Collections.emptyList())
                .withLoadTask(loadTask)
                .withWebClient(client)
                .build();
    }

}
