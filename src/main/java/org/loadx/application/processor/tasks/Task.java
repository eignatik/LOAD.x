package org.loadx.application.processor.tasks;

import java.util.concurrent.CompletableFuture;

public interface Task<T> {
    CompletableFuture<T> execute();
}
