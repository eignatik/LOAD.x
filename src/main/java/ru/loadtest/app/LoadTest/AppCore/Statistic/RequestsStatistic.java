package ru.loadtest.app.LoadTest.AppCore.Statistic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.loadtest.app.LoadTest.AppCore.Page;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * the class provides statistic about pages requests, page count, coverage, and so on
 */
public class RequestsStatistic {
    public static final Logger logger = LogManager.getLogger(RequestsStatistic.class.getName());
    private static Map<String, Page> collectedPages = new ConcurrentHashMap<>();
    private static List<String> brokenLinks = new ArrayList<>();
    private static int visitedPagesCounter = 0;
    private static int parsedPagesCounter = 0;

    public static void collect(String key, Page pageObject) {
        if (pageObject!=null) {
            collectedPages.put(key, pageObject);
        }
    }

    public static void collect(Map<String, Page> pagesToCollect) {
        if (!pagesToCollect.isEmpty()) {
            collectedPages = pagesToCollect;
        }
    }

    public static void incrementVisitedPagesCounter() {
        visitedPagesCounter++;
    }

    public static void addParsedPagesCount(int pagesCount) {
        parsedPagesCounter += pagesCount;
    }

    public static void writeStatisticToFile() {

    }

    public static void printStatistic() {
        logger.info("Request statistic: { Parsed links:" + parsedPagesCounter + ", Visited links:" + visitedPagesCounter + "  (" + (double)visitedPagesCounter/parsedPagesCounter + ")" + " Broken links: "  + brokenLinks.size());
        printTable();
        logger.info("Extended statistic:");
        printBasicStatistic();
    }

    public static void addBrokenLink(String URL) {
        brokenLinks.add(URL);
    }

    private static void printTable() {
        if (!collectedPages.isEmpty()) {
            Set<Map.Entry<String, Page>> pageSet = collectedPages.entrySet();
            for (Map.Entry<String, Page> element : pageSet) {
                StringBuilder result = new StringBuilder();
                result.append(element.getValue().getURL() + "\t")
                        .append(element.getValue().getRequestCount());
                System.out.println(result.toString());
            }
        } else {
            logger.error("Something went wrong. \n Results didn't recieved and can't be showed");
        }
    }

    private static void printBasicStatistic() {
        if (!collectedPages.isEmpty()) {
            Set<Map.Entry<String, Page>> pageSet = collectedPages.entrySet();
            for (Map.Entry<String, Page> element : pageSet) {
                StringBuilder result = new StringBuilder();
                result.append("\"" + element.getValue().getURL() + "\": \n")
                        .append("\t\"RequestCount\": " + "\"" + element.getValue().getRequestCount() + "\"\n")
                        .append("\t\"AverageRequestTime\": " + "\"" + element.getValue().getAvgRequest() + "\"\n")
                        .append("\t\"MaxRequestTime\": " + "\"" + element.getValue().getMaxRequest() + "\"\n")
                        .append("\t\"MinRequestTime\": " + "\"" +element.getValue().getMinRequest()  + "\"\n\n");
                System.out.println(result.toString());
            }
        } else {
            logger.error("Something went wrong. \n Results haven't recieved and can't be showed");
        }
    }

//    public RequestsStatistic(Map<String, Page> pageMap) {
//        this.pageMap = pageMap;
//    }
//
//    public void printPagesStatistic() {
//        if (!pageMap.isEmpty()) {
//            Set<Map.Entry<String, Page>> pageSet = pageMap.entrySet();
//            logger.info("RESULT STATISTIC");
//            for (Map.Entry<String, Page> element : pageSet) {
//                StringBuilder result = new StringBuilder();
//                result.append("\"" + element.getValue().getURL() + "\": \n")
//                        .append("\t\"RequestCount\": " + "\"" + element.getValue().getRequestCount() + "\"\n")
//                        .append("\t\"AverageRequestTime\": " + "\"" + element.getValue().getAvgRequest() + "\"\n")
//                        .append("\t\"MaxRequestTime\": " + "\"" + element.getValue().getMaxRequest() + "\"\n")
//                        .append("\t\"MinRequestTime\": " + "\"" +element.getValue().getMinRequest()  + "\"\n\n");
//                System.out.println(result.toString());
//            }
//        } else {
//            logger.error("Something went wrong. \n Results didn't recieved and can't be showed");
//        }
//    }
//
//    public void printStatisticTable() {
//        if (!pageMap.isEmpty()) {
//            Set<Map.Entry<String, Page>> pageSet = pageMap.entrySet();
//            logger.info("RESULT STATISTIC");
//            for (Map.Entry<String, Page> element : pageSet) {
//                StringBuilder result = new StringBuilder();
//                result.append(element.getValue().getURL() + "\t")
//                        .append(element.getValue().getRequestCount() + "\n");
//                System.out.println(result.toString());
//            }
//        } else {
//            logger.error("Something went wrong. \n Results didn't recieved and can't be showed");
//        }
//    }
}
