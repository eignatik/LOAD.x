package org.loadx.application.controllers;

import org.apache.commons.lang3.StringUtils;
import org.loadx.application.constants.JsonBodyConstants;
import org.loadx.application.http.WebsiteValidationUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
