package ru.loadtest.app.LoadTest.AppCore.Statistic.Availability;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AvailabilityStatistic {
    public static final Logger logger = LogManager.getLogger(AvailabilityStatistic.class.getName());

    private static Map<String, PageInfo> statistic = new HashMap<>();
    private static int availableCount;
    private static int unavailableCount;

    public static void collect(String URL, PageInfo pageInfo) {
        statistic.put(URL, pageInfo);
        incrementAvailabilityCounters(pageInfo);
    }

    private static void incrementAvailabilityCounters(PageInfo pageInfo) {
        if (pageInfo.isAvailable()) {
            availableCount++;
        } else {
            unavailableCount++;
        }
    }

    public static void printStatistic() {
        System.out.println("\n");
        if (!statistic.isEmpty()) {
            Set<Map.Entry<String, PageInfo>> statisticSet = statistic.entrySet();
            for (Map.Entry<String, PageInfo> element : statisticSet) {
                PageInfo pageInfo = element.getValue();
                StringBuilder result = new StringBuilder();
                result
                        .append("\"")
                        .append(pageInfo.getURL())
                        .append("\":{")
                        .append("\n\t\"available\":")
                        .append(pageInfo.isAvailable()? "\"Available\"":"\"Unavailable\"")
                        .append(",\n\t")
                        .append("\"status\":\"")
                        .append(pageInfo.getStatus())
                        .append("\"\n}, ");
                System.out.println(result.toString());
            }
        } else {
            logger.error("Something went wrong. \n Results didn't recieved and can't be showed");
        }
    }

    public static Map<String, PageInfo> getStatistic() {
        return statistic;
    }

}
