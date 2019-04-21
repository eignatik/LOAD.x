package org.loadx.application.IT.testServer;

import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestServerApplication.class)
public abstract class TestServerSupport extends AbstractTestNGSpringContextTests {

    protected static final String TEST_ENDPOINT = "/test";

    @LocalServerPort
    protected int port;

}
