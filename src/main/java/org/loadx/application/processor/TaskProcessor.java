package org.loadx.application.processor;

import org.loadx.application.processor.tasks.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class TaskProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(TaskProcessor.class);

    public CompletableFuture<Integer> process(Task<?> task) {
        return task.execute()
                .thenApply(v -> 10)
                .exceptionally(e -> {
                    LOG.error(e.getMessage(), e);
                    return 0;
                });

    }

}
