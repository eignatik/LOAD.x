package org.loadx.application.processor.tasks;

import io.vertx.core.AsyncResult;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.loadx.application.db.dao.LoadxDataHelper;
import org.loadx.application.db.entity.ExecutionDetails;
import org.loadx.application.db.entity.LoadRequest;
import org.loadx.application.db.entity.LoadTask;
import org.loadx.application.db.entity.LoadingExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

        Map<Integer, HttpRequest<Buffer>> vertxRequests = loadRequests.stream()
                .collect(Collectors.toMap(LoadRequest::getId, req -> webClient.get(loadTask.getBaseUrl(), req.getUrl())));

        CompletableFuture.runAsync(() -> launchLoading(vertxRequests, executionId));

        CompletableFuture<Integer> future = new CompletableFuture<>();
        future.complete(executionId);

        return future;
    }

    private void launchLoading(Map<Integer, HttpRequest<Buffer>> requests, int executionId) {
        int executionTime = loadTask.getLoadingTime();
        long startTime = System.currentTimeMillis();
        long currentTime = 0;
        boolean finished = false;
        int reqIndex = 0;
        List<Integer> requestIds = new ArrayList<>(requests.keySet());
        while (!finished) {
            Integer loadRequestId = requestIds.get(reqIndex++);
            HttpRequest<Buffer> req = requests.get(loadRequestId);
            long requestStart = System.currentTimeMillis();
            req.send(res -> {
                long requestEnd = System.currentTimeMillis();
                ExecutionDetails details = new ExecutionDetails();
                details.setExecutionId(executionId);
                details.setRequestId(loadRequestId);
                details.setTimeElapsed((int) (requestEnd - requestStart)); //TODO AMAZING! Rework to have LONG instead
                if (res.failed()) {
                    Throwable cause = res.cause(); // exception of Queue as well might be here
                    details.setLoadingStatus("FAILED");

                    // TODO: check if the root cause is about exceeding the maxWaitQueue size and make a pause for some time
                } else {
                    HttpResponse<Buffer> result = res.result();
                    details.setResponseCode(result.statusCode());
                    details.setLoadingStatus("SUCCESS");
                }

            });

            if (reqIndex >= requests.size()) {
                reqIndex = 0;
            }
            currentTime = System.currentTimeMillis() - startTime;
            if (currentTime >= executionTime) {
                finished = true;
            }
        }
    }

    public static class LoadingTaskBuilder {
        private LoadingTask task;

        private LoadingTaskBuilder() {
            task = new LoadingTask();
        }

        public LoadingTaskBuilder withDataHelper(LoadxDataHelper loadxDataHelper) {
            task.loadxDataHelper = loadxDataHelper;
            return this;
        }

        public LoadingTaskBuilder withLoadTask(LoadTask loadTask) {
            task.loadTask = loadTask;
            return this;
        }

        public LoadingTaskBuilder withLoadRequests(List<LoadRequest> loadRequests) {
            task.loadRequests = loadRequests;
            return this;
        }

        /**
         * TODO: create a facade or abstractopn for clients to swap clients if required.
         *
         * @param webClient
         * @return
         */
        public LoadingTaskBuilder withWebClient(WebClient webClient) {
            task.webClient = webClient;
            return this;
        }

        public LoadingTask build() {
            return task;
        }
    }

}
