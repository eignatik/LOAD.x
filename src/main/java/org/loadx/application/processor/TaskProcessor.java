package org.loadx.application.processor;

import org.loadx.application.processor.tasks.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TaskProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(TaskProcessor.class);

    public boolean process(Task task) {
        boolean success = true;
        try {
            task.execute();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            success = false;
        }

        return success;
    }

}
