package com.ngload.application;

import static spark.Spark.get;
import static spark.Spark.port;

public class FakeServer {
    private static boolean isRunning = false;

    public static void runServer() {
        if(!isRunning) {
            port(8082);
            get("/test", (req, res) -> HTMLGetter.getBasicHTML());
            get("/testLinks", (req, res) -> HTMLGetter.getLinksHTML());
            get("/alotoflinksTest", (req, res) -> HTMLGetter.getLotOfLinksHTML());
            isRunning = true;
        }
    }
}