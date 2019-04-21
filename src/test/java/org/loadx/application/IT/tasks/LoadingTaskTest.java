package org.loadx.application.IT.tasks;

import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;
import org.loadx.application.IT.testServer.TestServerSupport;
import org.loadx.application.constants.RequestType;
import org.loadx.application.db.dao.LoadxDataHelper;
import org.loadx.application.db.entity.ExecutionDetails;
import org.loadx.application.db.entity.LoadRequest;
import org.loadx.application.db.entity.LoadTask;
import org.loadx.application.db.entity.LoadingExecution;
import org.loadx.application.http.HttpClientManager;
import org.loadx.application.processor.TaskProcessor;
import org.loadx.application.processor.tasks.Task;
import org.loadx.application.processor.tasks.TaskCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class LoadingTaskTest extends TestServerSupport {

    @Autowired
    private TaskCreator taskCreator;

    @Autowired
    private LoadxDataHelper dataHelper;

    @Autowired
    private TaskProcessor processor;

    @Test
    public void testLoadingTask() throws ExecutionException, InterruptedException {
        final int executionTime = 10000;

        LoadTask task = new LoadTask();
        task.setParallelThreshold(5);
        task.setLoadingTime(executionTime);
        task.setBaseUrl("localhost:" + port);

        LoadRequest request = new LoadRequest();
        request.setUrl(TEST_ENDPOINT);
        request.setType(RequestType.GET.getValue());
        request.setTimeout(2000);
        request.setRequestName("Test request");

        int taskId = dataHelper.persistLoadTaskRequests(task, List.of(request));

        Task loadingTask = taskCreator.createLoadingTask(taskId);

        CompletableFuture<Integer> execuitonId = processor.process(loadingTask);

        int executionId = execuitonId.get();

        Thread.sleep(executionTime + 1000);

        LoadingExecution execution = dataHelper.getLoadingExecutionDao().getById(executionId, LoadingExecution.class);

        Assert.assertNotNull(execution.getEndTime(), "End time should be present");
        List<ExecutionDetails> details = dataHelper.getExecutionDetailsDao().getDetailsByExecutionId(executionId);
        int successCount = details.stream()
                .filter(detail -> detail.getLoadingStatus().equals("SUCCESS"))
                .collect(Collectors.toList()).size();
        Assert.assertEquals(successCount, details.size());

        // TODO: remove test task and its requests
    }

}
