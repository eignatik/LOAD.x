package ru.loadtest.app.LoadTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.loadtest.app.LoadTest.AppCore.ConnectionAPI;
import ru.loadtest.app.LoadTest.AppCore.Page;
import ru.loadtest.app.LoadTest.AppCore.Statistic.RequestsStatistic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.loadtest.app.LoadTest.AppCore.ConnectionAPI.*;

/**
 * The class provide you several public methods to operate with LoadTest Application. Use this class in your applications.
 * Default constructor uses localhost with port 8802. You should call constructor with URL and port or only with port if you want
 * to use localhost with other port.
 * <p>
 * Use other constructors if you want to get access to remote web-sites.
 */
public class LoadTestAPI {
    public static final Logger logger = LogManager.getLogger(LoadTestAPI.class.getName());
    private String URL;
    private int port = 8802;
    private Map<String, Page> listOfPages;

    public LoadTestAPI() {
        URL = "localhost:" + port;
    }

    public LoadTestAPI(String URL, int port) {
        this.URL = URL + port;
    }

    public LoadTestAPI(String URL) {
        this.URL = URL;
    }

    /**
     * Execute test with random visits of pages.
     * Timeout for exploring is standart value that referenced in Core
     * @param startURL This is the first point of exploring. Explore is started from address.zone if @param startURL is empty string.
     */
    public void executeRandomTest(String startURL) {
        showDebugInfo(startURL, getTimeout());
        ConnectionAPI connection = new ConnectionAPI(this.URL, startURL);
        connection.start();
    }
    /**
     * Overloaded method with manual timeout
     * @param startURL This is th first point of exploring. Explore is started from address.zone if @param startUrl is empty string
     * @param timeout values in seconds that set timeout for testing time
     */
    public void executeRandomTest(String startURL, long timeout) {
        showDebugInfo(startURL, timeout);
        setTimeout(timeout);
        ConnectionAPI connection = new ConnectionAPI(this.URL, startURL);
        connection.start();
    }

    /**
     * Overloaded method with manual timeout value and users count
     * @param startURL This is the first point of exploring. Explore is started from address.zone if @param startURL is empty string.
     * @param timeout value in seconds that set timeout for testing time
     */
    public void executeRandomTest(String startURL, long timeout, int usersCount) {
        showDebugInfo(startURL, timeout);
        List<ConnectionAPI> threads = new ArrayList<>();
        setTimeout(timeout);
        for (int i = 0; i < usersCount; i++) {
            threads.add(new ConnectionAPI(this.URL, startURL));
        }
        for (ConnectionAPI thread : threads) {
            thread.start();
        }
        listOfPages = getSitePages();
    }

    public void printStatistic() {
        try {
            Thread.sleep(ConnectionAPI.getTimeout() + 3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        RequestsStatistic requestsStatistic = new RequestsStatistic(listOfPages);
        requestsStatistic.printPagesStatistic();
    }

    /**
     * Set request limit interval in ms
     * @param limit value in mileseconds
     */
    public void setMaxIntervalVal(int limit) {
        setRequestIntervals(limit);
    }

    public Map<String, Page> getListOfPages() {
        return listOfPages;
    }

    private void showDebugInfo(String startURL, long period) {
        logger.info("Current work URL is " + this.URL + " Exploring starts from " + startURL + "/");
        logger.info("Timeout in " + (period/1000)/60 + " min. (" + period/1000 + " sec.)");
    }
}
