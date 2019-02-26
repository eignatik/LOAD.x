package org.loadx.application.http.IT.testServer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public ResponseEntity testResponse() {
        return ResponseEntity.ok().build();
    }

}
