package testUtils;

import static spark.Spark.get;
import static spark.Spark.port;
import static testUtils.HTMLGetter.*;

public class TestServer {
    private static boolean isRunning = false;

    public static void runServer() {
        if(!isRunning) {
            port(8082);
            setBasicTestEndpoints();
            setTestWebSiteEndpoints();
            isRunning = true;
        }
    }

    private static void setBasicTestEndpoints() {
        get("/test", (req, res) -> getHTML("html/test.html"));
    }

    private static void setTestWebSiteEndpoints() {
        String predefinedPath = "html/testSite/";
        get("/", (req, res) -> getHTML(predefinedPath + "index.html"));
        get("contacts.html", (req, res) -> getHTML(predefinedPath + "contacts.html"));
        get("info.html", (req, res) -> getHTML(predefinedPath + "info.html"));
        get("dogs.html", (req, res) -> getHTML(predefinedPath + "dogs.html"));
        get("cats.html", (req, res) -> getHTML(predefinedPath + "cats.html"));
        get("kindsOfCats.html", (req, res) -> getHTML(predefinedPath + "kindsOfCats.html"));
        get("kindsOfDogs.html", (req, res) -> getHTML(predefinedPath + "kindsOfDogs.html"));
        get("cat-kind1.html", (req, res) -> getHTML(predefinedPath + "cat-kind1.html"));
        get("cat-kind2.html", (req, res) -> getHTML(predefinedPath + "cat-kind2.html"));
        get("dog-kind1.html", (req, res) -> getHTML(predefinedPath + "dog-kind1.html"));
        get("dog-kind2.html", (req, res) -> getHTML(predefinedPath + "dog-kind2.html"));
    }
}