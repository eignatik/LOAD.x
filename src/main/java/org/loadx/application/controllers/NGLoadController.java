package org.loadx.application.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.loadx.application.constants.JsonBodyConstants;
import org.loadx.application.http.WebsiteValidationUtil;
import org.loadx.application.processor.TaskProcessor;
import org.loadx.application.processor.tasks.MappingAndPersistingTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

/**
 * Basic controller that provides the possibility to trigger execution jobs.
 * <p>
 * There are two way to trigger the job.
 * 1. Trigger /load endpoint to run job with default configuration
 */

@RestController
public class NGLoadController {

    private TaskProcessor processor;

    @Autowired
    public NGLoadController(TaskProcessor processor) {
        this.processor = processor;
    }

    @GetMapping("/health")
    public @ResponseBody ResponseEntity health() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/addTask")
    public @ResponseBody ResponseEntity<String> addTask(@RequestBody String json) {
        MappingAndPersistingTask task = MappingAndPersistingTask.createWithJson(json).build();
        boolean succeeded = processor.process(task);
        if (succeeded) {
            return new ResponseEntity("Given task is successfully added", HttpStatus.OK);
        } else {
            return new ResponseEntity("Given task is failed to save", HttpStatus.BAD_REQUEST);
        }
    }

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
