package ru.loadtest.app.LoadTest.AppCore.Statistic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.loadtest.app.LoadTest.AppCore.Page;

import java.util.Map;
import java.util.Set;

public class RequestsStatistic {
    public static final Logger logger = LogManager.getLogger(RequestsStatistic.class.getName());
    private Map<String, Page> pageMap;

    public RequestsStatistic(Map<String, Page> pageMap) {
        this.pageMap = pageMap;
    }

    public void printPagesStatistic() {
        if (!pageMap.isEmpty()) {
            Set<Map.Entry<String, Page>> pageSet = pageMap.entrySet();
            logger.info("RESULT STATISTIC");
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
            logger.error("Something went wrong. \n Results didn't recieved and can't be showed");
        }
    }

    public void printStatisticTable() {
        if (!pageMap.isEmpty()) {
            Set<Map.Entry<String, Page>> pageSet = pageMap.entrySet();
            logger.info("RESULT STATISTIC");
            for (Map.Entry<String, Page> element : pageSet) {
                StringBuilder result = new StringBuilder();
                result.append(element.getValue().getURL() + "\t")
                        .append(element.getValue().getRequestCount() + "\n");
                System.out.println(result.toString());
            }
        } else {
            logger.error("Something went wrong. \n Results didn't recieved and can't be showed");
        }
    }
}
