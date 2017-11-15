package com.ngload.application.appcore.webcore.pasrer;

import com.ngload.application.appcore.webcore.parser.LinkParser;
import org.testng.annotations.Test;
import testUtils.FakeServer;

public class LinkParserTest {
    @Test
    public void testIfParsingWorks() {
        FakeServer.runServer();

        LinkParser parser = new LinkParser();
        parser.withStartUrl("");
    }
}
