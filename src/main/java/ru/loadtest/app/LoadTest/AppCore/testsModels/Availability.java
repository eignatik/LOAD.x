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
    private HTTPConnection connection;

    public Availability(String URL, long parsingTimeout) {
        pages = getPageListFromMap(URL, parsingTimeout);
        connection = new HTTPConnection(URL);
    }

    private List<Page> getPageListFromMap(String URL, long parsingTimeout) {
        Set<Map.Entry<String, Page>> pageSet =  Parser.getCollectedLinks(URL, parsingTimeout).entrySet();
        List<Page> list = new LinkedList<>();
        for (Map.Entry<String, Page> element : pageSet) {
            list.add(element.getValue());
        }
        return list;
    }

    @Override
    public void execute() {
        Thread thread = this;
        thread.run();
    }

    @Override
    public void run() {
        exploreIfPagesExist();
    }

    private void exploreIfPagesExist() {
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
            }
        }
    }

    private String getPageStatus(String html) {
        String status = "Available";
        if (isPageFound(html)) {
            status = "Page not found (Error 404)";
        } else {
            if (html == null) {
                status = "Unavailable";
            }
        }
        return status;
    }

    private boolean isPageFound(String html) {
        return html.contains("not found") || html.contains("error 404");
    }
}
