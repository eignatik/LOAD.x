package com.ngload.application.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Basic controller that provides the possibility to trigger execution jobs.
 *
 * There are two way to trigger the job.
 * 1. Trigger /start endpoint to run job with default configuration
 * 2. TODO: mapping for configurable job execution
 */

@RestController
public class NGLoadController {

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity triggerExecution() {
        return new ResponseEntity(HttpStatus.OK);
    }
}
