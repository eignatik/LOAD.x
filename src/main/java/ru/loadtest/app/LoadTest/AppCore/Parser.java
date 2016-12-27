package ru.loadtest.app.LoadTest.AppCore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.jsoup.Jsoup.*;

public class Parser extends Thread {
    public static final Logger logger = LogManager.getLogger(Parser.class.getName());

    private static HTTPConnection connection;
    private static String currentURL;
    private static Map<String, Page> pageList = new ConcurrentHashMap<>();
    private static Random random = new Random();
    private static final long DEFAULT_TIMEOUT = 60000;



    @Override
    public void run() {

    }

    public static String getNextLinkFromHTML(String html) {
        List<Link> links = getLinksFromHTML(html);
        return links.size() == 0? "":links.get(random.nextInt(links.size()-1)).getURL();
    }

    /**
     * Parse links from the site during timeout
     * @param timeout in seconds
     * @return
     */
    public static Map<String, Page> getCollectedLinks(String url, long timeout) {
        return collect(url, timeout);
    }

    /**
     * Parse links from the site during default timeout in 60 seconds
     * @return
     */
    public static Map<String, Page> getCollectedLinks(String url) {
        return collect(url, DEFAULT_TIMEOUT);
    }

    private static Map<String, Page> collect(String url, long time) {
        System.out.print("\nParsing... (it keeps " + time/1000 + "sec.)");
        currentURL = "";
        connection = new HTTPConnection(url);
        long startTime = System.currentTimeMillis();
        long currentTime = 0;
        while (currentTime < time) {
            currentTime = System.currentTimeMillis() - startTime;
            getLinks();
        }
        System.out.print(".............DONE  (" + pageList.size() + " links parsed, speed is " + pageList.size()/(time/1000.0) + " links per second)\n");
        return pageList;
    }

    private static void getLinks() {
        String htmlPage = connection.getHTMLPageByURL(currentURL);
        currentURL = setNextURL(htmlPage, currentURL);
    }

    private static String setNextURL(String htmlPage, String address) {
        if (pageList.containsKey(address)) {
            address = pageList.get(address).getRandomLink();
        } else {
            address = getNextParsedLink(htmlPage);
        }
        return address;
    }

    private static String getNextParsedLink(String html) {
        List<Link> links = getLinksFromHTML(html);
        String url;
        pageList.put(currentURL, new Page(currentURL, links));
        if (links.isEmpty()) {
            url = getRandomLinkToRedirect();
        } else {
            url = pageList.get(currentURL).getRandomLink();
        }
        return url;
    }

    private static String getRandomLinkToRedirect() {
        int index = random.nextInt(pageList.size());
        int i = 0;
        Page page = null;
        Set<Map.Entry<String, Page>> sitePagesSet = pageList.entrySet();
        for (Map.Entry<String, Page> element : sitePagesSet) {
            if (i == index) {
                page = element.getValue();
                break;
            }
        }
        return page!=null? page.getURL() : "";
    }

    public static List<Link> getLinksFromHTML(String HTMLString) {
        List<Link> listOfLinks = new ArrayList<>();
        Document html = parse(HTMLString);
        Elements links = html.select("a[href]");
        for (Element link : links) {
            String currentLink = link.attr("href");
            if (!currentLink.isEmpty() && isLink(currentLink)) {
                if (!hasSameLink(listOfLinks, currentLink)) {
                    listOfLinks.add(new Link(link.attr("href")));
                }
            }
        }
        return listOfLinks;
    }

    private static boolean isLink(String URL) {
        boolean isURLLink = true;
        if (URL.length() >= 5) {
            isURLLink = !URL.substring(0, 5).equals("//www");
        }
        return URL.charAt(0) != '#' && !URL.contains("mailto:") && isURLLink;
    }

    private static boolean hasSameLink(List<Link> list, String currentLink) {
        return  list.contains(new Link(currentLink));
    }
}
