package org.loadx.application.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Basic controller that provides the possibility to trigger execution jobs.
 * <p>
 * There are two way to trigger the job.
 * 1. Trigger /start endpoint to run job with default configuration
 */

@RestController
public class NGLoadController {

    @GetMapping("/start")
    public @ResponseBody ResponseEntity execute() {
        return new ResponseEntity(HttpStatus.OK);
    }
}
