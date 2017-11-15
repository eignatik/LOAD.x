package testUtils;

import static spark.Spark.get;
import static spark.Spark.port;

public class FakeServer {
    private static boolean isRunning = false;

    public static void runServer() {
        String predefinedPath = "html/testSite/";
        if(!isRunning) {
            port(8082);
            get("/test", (req, res) -> HTMLGetter.getHTML("html/test.html"));
            get("/", (req, res) -> HTMLGetter.getHTML(predefinedPath + "index.html"));
            get("contacts.html", (req, res) -> HTMLGetter.getHTML(predefinedPath + "contacts.html"));
            get("dogs.html", (req, res) -> HTMLGetter.getHTML(predefinedPath + "dogs.html"));
            isRunning = true;
        }
    }
}