package com.ngload.application.appcore.webcore.pasrer;

import com.ngload.application.appcore.webcore.WebConnector;
import com.ngload.application.appcore.webcore.parser.LinkParser;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import testUtils.TestServer;

import static org.mockito.MockitoAnnotations.*;

public class LinkParserTest {
    @Mock
    private WebConnector connector;

    @InjectMocks
    private LinkParser parser;

    @BeforeClass
    public void setUpTestServer() {
        TestServer.runServer();
    }

    @BeforeMethod
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testIfParsingWorks() {
        parser.withStartUrl("");
    }
}
