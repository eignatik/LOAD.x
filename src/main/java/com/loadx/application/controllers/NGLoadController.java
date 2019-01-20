package com.loadx.application.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Basic controller that provides the possibility to trigger execution jobs.
 *
 * There are two way to trigger the job.
 * 1. Trigger /start endpoint to run job with default configuration
 */

@RestController
public class NGLoadController {

    @GetMapping("/start")
    @ResponseBody
    public ResponseEntity triggerExecution() {
        return new ResponseEntity(HttpStatus.OK);
    }
}
