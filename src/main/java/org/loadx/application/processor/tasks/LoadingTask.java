package org.loadx.application.processor.tasks;

import org.loadx.application.db.LoadPersistent;
import org.loadx.application.db.entity.LoadRequest;
import org.loadx.application.db.entity.LoadTask;

import java.util.List;
import java.util.concurrent.CompletableFuture;

class LoadingTask implements Task<Integer> {

    private LoadPersistent persistent;
    private LoadTask loadTask;
    private List<LoadRequest> loadRequests;

    public static LoadingTaskBuilder create() {
        return new LoadingTaskBuilder();
    }

    /**
     *
     * @return submitted execution id
     */
    @Override
    public CompletableFuture<Integer> execute() {
//        LoadingExecution execution = new LoadingExecution();
//        execution.setLoadingTaskId(10);
        Integer id = loadTask.getId();

        // submit loading requests in thread pool etc
//        CompletableFuture



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

        public LoadingTask build() {
            return task;
        }
    }

}
