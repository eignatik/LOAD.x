package org.loadx.application.controllers;

import org.apache.commons.lang3.StringUtils;
import org.loadx.application.constants.JsonBodyConstants;
import org.loadx.application.http.WebsiteValidationUtil;
import org.loadx.application.processor.TaskProcessor;
import org.loadx.application.processor.tasks.TaskCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Basic controller that provides the possibility to trigger execution jobs.
 * <p>
 */

@RestController
public class NGLoadController {

    private TaskProcessor processor;
    private TaskCreator taskCreator;

    @Autowired
    public NGLoadController(TaskProcessor processor, TaskCreator taskCreator) {
        this.processor = processor;
        this.taskCreator = taskCreator;
    }

    @GetMapping("/health")
    public @ResponseBody
    ResponseEntity health() {
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Gets all available tasks.
     *
     * @return entity with JSON mapped tasks and their requests.
     */
    @GetMapping("/get/tasks")
    public @ResponseBody
    ResponseEntity<String> getTasks() {
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    /**
     * Gets all requests created for the given task.
     *
     * @return JSON mapped requests.
     */
    @GetMapping("/get/taskRequests")
    public @ResponseBody
    ResponseEntity<String> getTaskRequests() {
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    /**
     * Adds new task with its requests to store.
     *
     * @param json with task configuration and requests.
     * @return result of creating new task.
     */
    @PostMapping("/add/task")
    public @ResponseBody
    ResponseEntity<String> addTask(@RequestBody String json) throws ExecutionException, InterruptedException {
        boolean succeeded = processor.process(taskCreator.createMappingTask(json)).get();
        if (succeeded) {
            return new ResponseEntity<>("Given task is successfully added", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Given task is failed to save", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Starts loading for given executions.
     *
     * @param json with executions to apply.
     * @return TODO: decide how to return status etc.
     */
    @PostMapping("/startLoading")
    public @ResponseBody
    ResponseEntity<String> startLoading(@RequestBody String json) {
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    /**
     * Returns generated MD-5 hash for further validations.
     *
     * @param payload the object with URL to be converted to MD-5.
     * @return response with MD-5 hash token generated.
     */
    @GetMapping("/hashgen")
    public ResponseEntity generateHashForOwnership(@RequestBody Map<String, String> payload) {
        if (payload == null || StringUtils.isEmpty(payload.get(JsonBodyConstants.URL))) {
            return ResponseEntity.badRequest()
                    .body("The url should be present, but wasn't passed or passed empty");
        }
        return ResponseEntity.ok()
                .body(WebsiteValidationUtil.generateHash(payload.get(JsonBodyConstants.URL)));
    }

}
