package org.loadx.application.processor.tasks;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.loadx.application.db.LoadPersistent;
import org.loadx.application.db.entity.ExecutionDetails;
import org.loadx.application.db.entity.LoadRequest;
import org.loadx.application.db.entity.LoadTask;
import org.loadx.application.db.entity.LoadingExecution;
import org.loadx.application.http.WebsitesHttpConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

class LoadingTask implements Task<Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(LoadingTask.class);

    private LoadPersistent persistent;
    private LoadTask loadTask;
    private List<LoadRequest> loadRequests;
    private WebsitesHttpConnector connector;

    public static LoadingTaskBuilder create() {
        return new LoadingTaskBuilder();
    }

    /**
     * @return submitted execution id
     */
    @Override
    public CompletableFuture<Integer> execute() {
//        LoadingExecution execution = new LoadingExecution();
//        execution.setLoadingTaskId(10);
        Integer id = loadTask.getId();

        // submit loading requests in thread pool etc
//        CompletableFuture

        // get pool capacity in runtime (if no available threads, wait don't create new requests
        int i = 0;
        while (true) {
            LoadRequest loadRequest = loadRequests.get(i);
            // check pool and wait before submitting
            // put something to the queue and check  this queue for threshold
            HttpUriRequest request = new HttpGet(loadTask.getBaseUrl() + loadRequest.getUrl());
            CompletableFuture
                    .supplyAsync(() -> {
                        try {
                            return connector.getHttpClient().execute(request);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                            // todo: handle exception properly, with adding info to statistics/db, exceptionally
                        }
                    })
                    .thenApply(res -> {
                        ExecutionDetails details = new ExecutionDetails();
                        // todo: fill that shit
                        return details;
                    })
                    .thenRun(() -> {
                        // todo: write to buffer
                    })
                    .exceptionally(v -> {
                        LOG.error("You are fucked up");
                        // todo: some shit happened, write it somewhere
                        return null;
                    });
            // TEMP BREAK
            break;
            //check time
        }


        CompletableFuture<Integer> future = new CompletableFuture<>();
        future.complete(id);

        return future;
    }

    public static class LoadingTaskBuilder {
        private LoadingTask task;

        private LoadingTaskBuilder() {
            task = new LoadingTask();
        }

        public LoadingTaskBuilder withLoadPersistent(LoadPersistent persistent) {
            task.persistent = persistent;
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

        public LoadingTaskBuilder withConnector(WebsitesHttpConnector connector) {
            task.connector = connector;
            return this;
        }

        public LoadingTask build() {
            return task;
        }
    }

}
