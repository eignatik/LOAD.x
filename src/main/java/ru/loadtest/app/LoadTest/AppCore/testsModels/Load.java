package ru.loadtest.app.LoadTest.AppCore.testsModels;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.loadtest.app.LoadTest.AppCore.HTTPConnection;
import ru.loadtest.app.LoadTest.AppCore.Page;
import ru.loadtest.app.LoadTest.AppCore.Parser;
import ru.loadtest.app.LoadTest.AppCore.Statistic.Load.RequestsStatistic;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

import static ru.loadtest.app.LoadTest.AppCore.Statistic.Load.RequestsStatistic.collect;
import static ru.loadtest.app.LoadTest.AppCore.Statistic.Load.RequestsStatistic.incrementVisitedPagesCounter;
import static ru.loadtest.app.LoadTest.AppCore.Util.*;

/**
 * Basic load test that provides check server with big loading by making a lot of requests to all available pages
 * Requests to pages are equable in this case, it means that all pages will have same requests counts.
 */
public class Load extends Thread implements ITest {
    public static final Logger logger = LogManager.getLogger(Load.class.getName());

    private static Random random = new Random();
    private static Map<String, Page> pages;
    private static long TIMEOUT = 30000;
    private static int REQUEST_TIME_TOP_RANGE = 1000;

    private HTTPConnection connection;
    private Queue<Page> linksQueue;
    private String currentURL = "";
    private String baseURL;
    private long requestTime;


    public Load(String baseURL) {
        this.baseURL = baseURL;
    }

    @Override
    public void run() {
        startTest();
    }

    private void startTest() {
        connection = new HTTPConnection(this.baseURL);
        long startTime = System.currentTimeMillis();
        long currentTime = 0;
        while (currentTime < TIMEOUT) {
            currentTime = System.currentTimeMillis() - startTime;
            setSleeping(random.nextInt(REQUEST_TIME_TOP_RANGE));
            explore();
        }
    }

    synchronized private void explore() {
        this.requestTime = getRequestTime();
        String URLToCollect = this.currentURL;
        this.currentURL = setNextLink();
        addToStatistic(URLToCollect);
    }

    private long getRequestTime() {
        long time = System.currentTimeMillis();
        connection.getHTMLPageByURL(this.currentURL);
        time = System.currentTimeMillis() - time;
        return time;
    }

    synchronized private String setNextLink() {
        return getRandomQueuedLink();
    }

    private String getRandomQueuedLink() {
        String url;
        List<Page> pageList = getLinks();
        if (pageList.isEmpty()) {
            url = linksQueue.peek().getURL();
        } else {
            url = getRandomExistedLink(pageList);
            pages.get(url).addRequest(requestTime);
        }
        return url;
    }

    private List<Page> getLinks() {
        fillQueue();
        return getLittleReqLinks();
    }

    private void fillQueue() {
        linksQueue = new PriorityBlockingQueue<>();
        Set<Map.Entry<String, Page>> sitePagesSet = pages.entrySet();
        for (Map.Entry<String, Page> element : sitePagesSet) {
            linksQueue.add(element.getValue());
        }
    }

    private List<Page> getLittleReqLinks() {
        List<Page> list = new ArrayList<>();
        Page page = linksQueue.peek();
        long minRequest = page.getRequestCount();
        boolean isRelevant = true;
        while (isRelevant) {
            if(page != null && page.getRequestCount() == minRequest) {
                list.add(page);
            } else {
                isRelevant = false;
            }
            page = linksQueue.poll();
        }
        linksQueue.clear();
        return list;
    }

    private String getRandomExistedLink(List<Page> links) {
        if(links.size() == 0) {
            return "";
        }
        int index = (links.size() == 1)? 0 : random.nextInt(links.size()-1);
        return links.get(index).getURL();
    }

    private void addToStatistic(String URLToCollect) {
        collect(URLToCollect, pages.get(URLToCollect));
        incrementVisitedPagesCounter();
    }

    public static void setPages(String url, long parsingTimeout) {
        pages = Parser.getCollectedLinks(url, parsingTimeout);
        RequestsStatistic.addParsedPagesCount(pages.size());
    }

    public static void setTimeout(long timeout) {
        TIMEOUT = timeout*1000;
    }

    public static void setRequestTimeTopRange(int range) {
        REQUEST_TIME_TOP_RANGE = range;
    }

    @Override
    public void execute() {

    }
}
