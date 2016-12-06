package ru.loadtest.app.LoadTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.loadtest.app.LoadTest.AppCore.ConnectionAPI;
import ru.loadtest.app.LoadTest.AppCore.Page;
import ru.loadtest.app.LoadTest.AppCore.Progress;
import ru.loadtest.app.LoadTest.AppCore.Statistic.RequestsStatistic;
import ru.loadtest.app.LoadTest.AppCore.exceptions.ValueException;

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
     * Execute test with random visits of pages, one user and default timeout
     * Timeout for exploring is standart value that referenced in Core
     * @param startURL This is the first point of exploring. Explore is started from address.zone if @param startURL is empty string.
     */
    public void executeRandomTest(String startURL) {
        showDebugInfo(startURL, getTimeout());
        ConnectionAPI connection = new ConnectionAPI(this.URL, startURL);
        connection.start();
        progressInfo(getTimeout());
    }
    /**
     * Overloaded method with manual timeout, one user only
     * @param startURL This is th first point of exploring. Explore is started from address.zone if @param startUrl is empty string
     * @param timeout values in seconds that set timeout for testing time
     */
    public void executeRandomTest(String startURL, long timeout) {
        showDebugInfo(startURL, timeout);
        setTimeout(timeout);
        ConnectionAPI connection = new ConnectionAPI(this.URL, startURL);
        connection.start();
        progressInfo(timeout);
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
        logger.info("All threads are created");
        for (ConnectionAPI thread : threads) {
            thread.start();
        }
        logger.info("All threads are started");
        progressInfo(timeout);
    }

    public void printStatistic() {
        RequestsStatistic.printStatistic();
    }

    /**
     * Set request limit interval in ms
     * @param limit value in mileseconds
     */
    public void setMaxIntervalVal(int limit) {
        setRequestIntervals(limit);
    }


    private void showDebugInfo(String startURL, long period) {
        logger.info("Current work URL is " + this.URL + " Exploring starts from " + startURL + "/");
        logger.info("Timeout in " + (period/60)/60 + " hours and " + (period/60) + " min. (" + period + " sec.)");
    }
     private void progressInfo(long timeout) {
         Runnable runnable = new Progress(timeout*1000);
         runnable.run();
         System.out.println("\nProcessing with data...\n");
     }
}
