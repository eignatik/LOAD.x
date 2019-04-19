package org.loadx.application.IT.websiteValidation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.loadx.application.IT.testServer.TestServerApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestServerApplication.class)
public class WebsiteValidationIT {

    @Test
    public void testThatWebSiteContainsSuitableHashAndPassesValidation() {
        
    }

}
