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

/**
 * Creator for tasks to have tasks creation details encapsulated.
 */
public class TaskCreator {

    private LoadxDataHelper dataHelper;
    private VertxProperties vertxProperties;

    @Autowired
    public TaskCreator(LoadxDataHelper loadxDataHelper, VertxProperties vertxProperties) {
        this.dataHelper = loadxDataHelper;
        this.vertxProperties = vertxProperties;
    }

    /**
     * Creates the mapping task based on input json.
     * <p>
     * Task is for mapping loading configurations from JSON to domain objects.
     *
     * @param json JSON to be used for mapping to real objects.
     * @return configured task for mapping.
     */
    public Task createMappingTask(String json) {
        return MappingAndPersistingTask.create()
                .withJson(json)
                .withDataHelper(dataHelper)
                .build();
    }

    /**
     * Creates a task for loading based on given load task id.
     *
     * @param taskId LoadTask id to be used for configuring the execution.
     * @return task configured for the loading.
     */
    public Task createLoadingTask(int taskId) {
        LoadTask loadTask = dataHelper.getLoadTaskDao().getById(taskId, LoadTask.class);

        Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(100));
        WebClientOptions options = new WebClientOptions()
                .setConnectTimeout(vertxProperties.getConnectTimeout())
                .setIdleTimeout(2000)
                .setMaxPoolSize(loadTask.getParallelThreshold())
                .setMaxWaitQueueSize(1000);
        WebClient client = WebClient.create(vertx, options);

        return LoadingTask.create()
                .withDataHelper(dataHelper)
                .withLoadRequests(dataHelper.getLoadRequestsByTaskId(loadTask.getId()))
                .withLoadTask(loadTask)
                .withWebClient(client)
                .build();
    }

}
