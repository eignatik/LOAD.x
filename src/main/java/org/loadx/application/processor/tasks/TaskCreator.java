package org.loadx.application.processor.tasks;

import org.loadx.application.db.dao.LoadxDataHelper;
import org.loadx.application.db.entity.LoadTask;
import org.loadx.application.http.HttpClientManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Creator for tasks to have tasks creation details encapsulated.
 */
public class TaskCreator {

    private LoadxDataHelper dataHelper;
    private HttpClientManager httpClientManager;

    @Autowired
    public TaskCreator(LoadxDataHelper loadxDataHelper, HttpClientManager httpClientManager) {
        this.dataHelper = loadxDataHelper;
        this.httpClientManager = httpClientManager;
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

        return LoadingTask.create()
                .withDataHelper(dataHelper)
                .withLoadRequests(dataHelper.getLoadRequestsByTaskId(loadTask.getId()))
                .withLoadTask(loadTask)
                .withWebClient(httpClientManager.createClient(loadTask.getParallelThreshold()))
                .withMaxQueueSize(httpClientManager.getProperties().getMaxWaitQueue())
                .build();
    }

}
