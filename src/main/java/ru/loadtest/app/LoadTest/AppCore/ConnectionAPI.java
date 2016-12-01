package ru.loadtest.app.LoadTest.AppCore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

public class ConnectionAPI extends Thread {
    public static final Logger logger = LogManager.getLogger(ConnectionAPI.class.getName());

    private Random random = new Random();
    private HTTPConnection connection;
    private static Map<String, Page> sitePages = new ConcurrentHashMap<>();
    private Queue<Page> linksQueue;
    private String baseURL;
    private String URL;
    private long requestTime;

    private static long timeout = 300000;
    private static int topRange = 10000;

    public ConnectionAPI(String baseURL, String startURL) {
        this.baseURL = baseURL;
        this.URL = startURL;
        Util.setWorkURL(this.baseURL);
    }

    @Override
    public void run() {
        startExplore();
    }

    private void startExplore() {
        connection = new HTTPConnection(this.baseURL);
        long startTime = System.currentTimeMillis();
        long currentTime = 0;
        while (currentTime <= timeout) {
            currentTime = System.currentTimeMillis() - startTime;
            sleepByCondition();
            explore();
        }
        logger.info("Timeout. (" + currentTime + ")");
    }

    private void sleepByCondition() {
        int interval = random.nextInt(topRange);
        try {
            Thread.sleep(interval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void explore() {
        requestTime = System.currentTimeMillis();
        String htmlPage = connection.getHTMLPageByURL(this.URL);
        requestTime = System.currentTimeMillis() - requestTime;
        setNextLink(htmlPage);
    }

    /**
     * method generates 0, 1, or 2 to select link generating way. If value 0 then random link from all list will be returned, else the random link from smallest request count list will be returned. Using PriorityQueue to sort links by request count value.
     */
    private void setNextLink(String htmlPage) {
        int direction = random.nextInt(3);
        if (direction == 0 || sitePages.isEmpty()) {
            getRandomParsedOrExistedLink(htmlPage);
        } else {
            getRandomQueuedLink(htmlPage);
        }
    }

    private void getRandomParsedOrExistedLink(String htmlPage) {
        if (sitePages.containsKey(this.URL)) {
            sitePages.get(this.URL).addRequest(requestTime);
            this.URL = sitePages.get(this.URL).getRandomLink();
        } else {
            this.URL = getNextParsedLink(htmlPage);
        }
    }

    private void getRandomQueuedLink(String htmlPage) {
        List<Page> littleRequestCountLinks;
        fillQueue();
        littleRequestCountLinks = getLittleReqLinks();
        if (littleRequestCountLinks.isEmpty()) {
            getRandomParsedOrExistedLink(htmlPage);
        } else {
            this.URL = getRandomLinkFromList(littleRequestCountLinks);
            sitePages.get(this.URL).addRequest(requestTime);
        }
    }

    private void fillQueue() {
        linksQueue = new PriorityBlockingQueue<>();
        Set<Map.Entry<String, Page>> sitePagesSet = sitePages.entrySet();
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

    private String getRandomLinkFromList(List<Page> links) {
        if(links.size() == 0) {
            return getRandomLinkToRedirect();
        }
        int index = (links.size() == 1)? 0 : random.nextInt(links.size()-1);
        return links.get(index).getURL();
    }

    private String getRandomLinkToRedirect() {
        int index = random.nextInt(sitePages.size());
        int i = 0;
        Set<Map.Entry<String, Page>> sitePagesSet = sitePages.entrySet();
        Page page = null;
        for (Map.Entry<String, Page> element : sitePagesSet) {
            if (i == index) {
                page = element.getValue();
                break;
            }
        }
        return page!=null? page.getURL() : "";
    }

    private String getNextParsedLink(String htmlPage) {
        List<Link> links = Parser.getLinksFromHTML(htmlPage);
        String url;
        sitePages.put(this.URL, new Page(this.URL, links));
        sitePages.get(this.URL).addRequest(requestTime);
        if (links.isEmpty()) {
            url = getRandomLinkToRedirect();
        } else {
            url =  sitePages.get(this.URL).getRandomLink();
        }
        return url;
    }

    public static Map<String, Page> getSitePages() {
        return sitePages;
    }

    /**
     * Set timeout in seconds
     * @param timeout values in seconds
     */
    public static void setTimeout(long timeout) {
        ConnectionAPI.timeout = timeout*1000;
    }

    public static long getTimeout() {
        return timeout;
    }

    public static void setRequestIntervals(int topLimit) {
        topRange = topLimit;
    }
}
