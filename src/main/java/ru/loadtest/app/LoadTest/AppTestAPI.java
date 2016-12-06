package ru.loadtest.app.LoadTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.loadtest.app.LoadTest.AppCore.Progress;
import ru.loadtest.app.LoadTest.AppCore.Statistic.RequestsStatistic;
import ru.loadtest.app.LoadTest.AppCore.testsModels.Load;

import java.util.ArrayList;
import java.util.List;

/**
 * The class provide you several public methods to operate with LoadTest Application. Use this class in your applications.
 * Default constructor uses localhost with port 8802. You should call constructor with URL and port or only with port if you want
 * to use localhost with other port.
 * <p>
 * Use other constructors if you want to get access to remote web-sites.
 */
public class AppTestAPI {
    public static final Logger logger = LogManager.getLogger(AppTestAPI.class.getName());
    private String URL;
    private int port = 8802;
    public enum TestType {
        LOAD, AVAILABILITY, SIMULATION
    }
    private long parsingTimeout = 60000;

    public AppTestAPI() {
        URL = "localhost:" + port;
    }

    public AppTestAPI(String URL, int port) {
        this.URL = URL + port;
    }

    public AppTestAPI(String URL) {
        this.URL = URL;
    }

    public void execute(String startURL, long timeout, int usersCount, TestType type) {
        switch (type) {
            case LOAD:
                executeLoad(timeout, usersCount);
                break;
            case AVAILABILITY:
                executeAvailability(timeout, usersCount);
                break;
            case SIMULATION:
                executeSimulation(timeout, usersCount);
                break;
            default: logger.error("Test type have not selected");
        }
    }

    private void executeLoad(long timeout, int usersCount) {
        showDebugInfo(timeout);
        Load.setPages(URL, parsingTimeout);
        Load.setTimeout(timeout);
        List<Load> threads = new ArrayList<>();
        for (int i = 0; i < usersCount; i++) {
            threads.add(new Load(this.URL));
        }
        logger.info(usersCount + " threads are created");
        for (Load thread : threads) {
            thread.start();
        }
        logger.info("All threads are started");
        progressInfo(timeout);
        System.out.print(".........DONE\nProcessing with data...\n");
    }

    private void executeAvailability(long timeout, int usersCount) {

    }

    private void executeSimulation(long timeout, int usersCount) {

    }

    public void printStatistic() {
        RequestsStatistic.printStatistic();
    }


    private void showDebugInfo(long period) {
        logger.info("Current work URL is " + this.URL);
        logger.info("Timeout in " + (period/60)/60 + " hours and " + (period/60) + " min. (" + period + " sec.).\n All tasks will be finished in " + (period + this.parsingTimeout/1000) + "( parsing: " + parsingTimeout/1000 + "sec, testing: " + period + " sec.)");
    }

     private void progressInfo(long timeout) {
         Runnable runnable = new Progress(timeout*1000);
         runnable.run();
     }

    /**
     * set parsing timeout on seconds
     * @param timeout
     */
    public void setParsingTimeout(long timeout) {
         this.parsingTimeout = timeout*1000;
     }
}
