package ru.loadtest.app.LoadTest.AppCore.testsModels;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.loadtest.app.LoadTest.AppCore.HTTPConnection;
import ru.loadtest.app.LoadTest.AppCore.Page;
import ru.loadtest.app.LoadTest.AppCore.Parser;
import ru.loadtest.app.LoadTest.AppCore.Statistic.Availability.AvailabilityStatistic;
import ru.loadtest.app.LoadTest.AppCore.Statistic.Availability.PageInfo;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ru.loadtest.app.LoadTest.AppCore.Statistic.Availability.AvailabilityStatistic.*;

public class Availability extends Thread implements ITest {
    public static final Logger logger = LogManager.getLogger(Availability.class.getName());

    private static List<Page> pages;
    private static String URL;

    private Availability() {
    }

    private static List<Page> getPageListFromMap(String URL, long parsingTimeout) {
        Set<Map.Entry<String, Page>> pageSet =  Parser.getCollectedLinks(URL, parsingTimeout).entrySet();
        List<Page> list = new LinkedList<>();
        for (Map.Entry<String, Page> element : pageSet) {
            list.add(element.getValue());
        }
        return list;
    }

    public static void execute() {
        exploreIfPagesExist();
    }

    private static void exploreIfPagesExist() {
        HTTPConnection connection = new HTTPConnection(URL);
        int i = 0;
        if (pages.isEmpty()) {
            logger.error("No available links");
        } else {
            for (Page page : pages) {
                String response = connection.getHTMLPageByURL(page.getURL());
                response = response.toLowerCase();
                if (isPageFound(response)) {
                    collect(page.getURL(), new PageInfo(page.getURL(), false, getPageStatus(response)));
                } else {
                    collect(page.getURL(), new PageInfo(page.getURL(), true, getPageStatus(response)));
                }
                System.out.print("\r Pages:" + ++i + "/" + pages.size());
            }
        }
    }

    private static String getPageStatus(String html) {
        String status = "Page is available";
        if (isPageFound(html)) {
            status = "Page not found (Error 404)";
        } else {
            if (html == null) {
                status = "Page is not available";
            }
        }
        return status;
    }

    private static boolean isPageFound(String html) {
        return html.contains("not found") || html.contains("error 404");
    }

    public static void parseLinks(long parsingTimeout) {
        pages = getPageListFromMap(URL, parsingTimeout);
    }

    public static void setURL(String url) {
        URL = url;
    }
}
