package org.loadx.application.IT.websiteValidation;

import org.loadx.application.util.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestValidationController {

    private static final String VALIDATION_PAGE_FILE = "./src/test/resources/websiteValidation/pageForValidation.html";

    @GetMapping("/")
    public String getPageForValidation() {
        return FileUtils.extractResourceToString(VALIDATION_PAGE_FILE);
    }

}
