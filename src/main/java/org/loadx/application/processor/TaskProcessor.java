package org.loadx.application.processor;

import org.loadx.application.processor.tasks.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * The processor that runs all tasks created via dedicated task creator.
 *
 * @see org.loadx.application.processor.tasks.TaskCreator
 */
public class TaskProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(TaskProcessor.class);

    /**
     * Runs the task passed to the method and returns the identifiers of processes.
     *
     * Handles all errors occurred in the execution.
     * @param task to be submitted for execution.
     * @see Task
     * @return non zero identifier if submitted successfully.
     */
    public CompletableFuture<Integer> process(Task task) {
        return task.execute()
                .exceptionally(e -> {
                    LOG.error(e.getMessage(), e);
                    return 0;
                });
    }

}
