package org.loadx.application.processor;

import org.loadx.application.processor.tasks.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskProcessor {

    public void process(Task task) {
        task.execute();
    }

}
