package org.loadx.application.processor.tasks;

import io.vertx.core.AsyncResult;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.ConnectionPoolTooBusyException;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import org.loadx.application.db.dao.LoadxDataHelper;
import org.loadx.application.db.entity.ExecutionDetails;
import org.loadx.application.db.entity.LoadRequest;
import org.loadx.application.db.entity.LoadTask;
import org.loadx.application.db.entity.LoadingExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

class LoadingTask implements Task {

    private static final Logger LOG = LoggerFactory.getLogger(LoadingTask.class);

    private LoadxDataHelper loadxDataHelper;
    private LoadTask loadTask;
    private List<LoadRequest> loadRequests;
    private WebClient webClient;

    static LoadingTaskBuilder create() {
        return new LoadingTaskBuilder();
    }

    /**
     * @return submitted execution id
     */
    @Override
    public CompletableFuture<Integer> execute() {
        LoadingExecution execution = new LoadingExecution();
        execution.setLoadingTaskId(loadTask.getId());
        int executionId = loadxDataHelper.getLoadingExecutionDao().save(execution);

        // TODO: validate ownership of the web-server to be tested.

        Map<Integer, HttpRequest<Buffer>> vertxRequests = loadRequests.stream()
                .collect(Collectors.toMap(LoadRequest::getId, req -> webClient.get(loadTask.getBaseUrl(), req.getUrl())));

        CompletableFuture.runAsync(() -> launchLoading(vertxRequests, executionId));

        CompletableFuture<Integer> future = new CompletableFuture<>();
        future.complete(executionId);

        return future;
    }

    private void launchLoading(Map<Integer, HttpRequest<Buffer>> requests, int executionId) {
        int executionTime = loadTask.getLoadingTime();
        boolean finished = false;
        int reqIndex = 0;
        List<Integer> requestIds = new ArrayList<>(requests.keySet());
        long startTime = System.currentTimeMillis();
        while (!finished) {
            Integer loadRequestId = requestIds.get(reqIndex++);
            requests.get(loadRequestId)
                    .send(res -> requestCallback(res, executionId, loadRequestId, System.currentTimeMillis()));

            if (reqIndex >= requests.size()) {
                reqIndex = 0;
            }

            if (System.currentTimeMillis() - startTime >= executionTime) {
                finished = true;
            }
        }
    }

    private void requestCallback(AsyncResult<HttpResponse<Buffer>> response,
                                 int executionId, int loadRequestId, long reqStartTimestamp) {
        long reqEndTimestamp = System.currentTimeMillis();
        ExecutionDetails details = new ExecutionDetails();
        details.setExecutionId(executionId);
        details.setRequestId(loadRequestId);
        details.setTimeElapsed((int) (reqEndTimestamp - reqStartTimestamp)); //TODO AMAZING! Rework to have LONG instead
        if (response.failed()) {
            details.setLoadingStatus("FAILED");
            //TODO: add cause or error message field to database
            Throwable cause = response.cause();
            if (cause instanceof ConnectionPoolTooBusyException) {
                LOG.warn("The connection pool exceeded the max queue size. Making a pause");
            }
            LOG.trace("The request failed: executionId={}, loadRequestId={}",
                    executionId, loadRequestId, cause);
        } else {
            HttpResponse<Buffer> result = response.result();
            LOG.info("The request executed: executionId={}, loadRequestId={}, statusCode={}",
                    executionId, loadRequestId, response.result().statusCode());
            details.setResponseCode(result.statusCode());
            details.setLoadingStatus("SUCCESS");
        }
        loadxDataHelper.getExecutionDetailsDao().save(details);
    }

    static class LoadingTaskBuilder {
        private LoadingTask task;

        private LoadingTaskBuilder() {
            task = new LoadingTask();
        }

        LoadingTaskBuilder withDataHelper(LoadxDataHelper loadxDataHelper) {
            task.loadxDataHelper = loadxDataHelper;
            return this;
        }

        LoadingTaskBuilder withLoadTask(LoadTask loadTask) {
            task.loadTask = loadTask;
            return this;
        }

        LoadingTaskBuilder withLoadRequests(List<LoadRequest> loadRequests) {
            task.loadRequests = loadRequests;
            return this;
        }

        /**
         * TODO: create a facade or abstractopn for clients to swap clients if required.
         *
         * @param webClient web client that will be used for loading requests.
         * @return instance of the builder.
         */
        LoadingTaskBuilder withWebClient(WebClient webClient) {
            task.webClient = webClient;
            return this;
        }

        LoadingTask build() {
            return task;
        }
    }

}
