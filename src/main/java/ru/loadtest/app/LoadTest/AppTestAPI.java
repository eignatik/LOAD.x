package ru.loadtest.app.LoadTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.loadtest.app.LoadTest.AppCore.Progress;
import ru.loadtest.app.LoadTest.AppCore.Statistic.Availability.AvailabilityStatistic;
import ru.loadtest.app.LoadTest.AppCore.Statistic.Load.RequestsStatistic;
import ru.loadtest.app.LoadTest.AppCore.testsModels.Simulation;

import static ru.loadtest.app.LoadTest.AppCore.testsModels.Availability.*;
import static ru.loadtest.app.LoadTest.AppCore.testsModels.Load.*;

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

    private long parsingTimeout = 60000;
    private int timeBetweenRequests = 10000;

    public AppTestAPI() {
        URL = "localhost:" + port;
    }

    public AppTestAPI(String URL, int port) {
        this.URL = URL + port;
    }

    public AppTestAPI(String URL) {
        this.URL = URL;
    }

    /**
     * Execute load test that make equable loading on web-resource and provie statistic
     * @param timeout maximal executable time in seconds
     * @param usersCount
     */
    public void executeLoad(long timeout, int usersCount) {
        showDebugInfo(timeout);
        setPages(URL, parsingTimeout);
        setTimeout(timeout);
        execute(usersCount, this.URL);
        runProgress(timeout);
        RequestsStatistic.printStatistic();
    }

    /**
     * Execute availability test that represents accessable pages
     */
    public void executeAvailability() {
        showDebugInfo(0);
        setURL(this.URL);
        parseLinks(this.parsingTimeout);
        execute();
        AvailabilityStatistic.printStatistic();
     }

    /**
     *
     * @param timeout time in ms that is maximal executable time
     * @param usersCount count of users
     * @param startURL url that will be first point of site exploring
     */
    public void executeSimulation(long timeout, int usersCount, String startURL) {
        Simulation.setBaseURL(this.URL);
        Simulation.setTimeout(timeout);
        Simulation.setTopRange(this.timeBetweenRequests);
        Simulation.execute(usersCount, startURL);
        runProgress(timeout);
        RequestsStatistic.printStatistic();
    }

    public void printStatistic() {
        AvailabilityStatistic.printStatistic();
        RequestsStatistic.printStatistic();
    }


    private void showDebugInfo(long period) {
        logger.info("Current work URL is " + this.URL);
        logger.info("Timeout in " + (period/60)/60 + " hours and " + (period/60) + " min. (" + period + " sec.).\n All tasks will be finished in " + (period + this.parsingTimeout/1000) + "( parsing: " + parsingTimeout/1000 + "sec, testing: " + period + " sec.)");
    }

     private void runProgress(long timeout) {
         Runnable runnable = new Progress(timeout*1000);
         runnable.run();
         System.out.print(".........DONE\nData processing...\n");
     }

    /**
     * set parsing timeout on seconds
     * @param timeout
     */
     public void setParsingTimeout(long timeout) {
         this.parsingTimeout = timeout*1000;
     }

    /**
     * Set time between requests in ms
     * @param
     */
    public void setTimeBetweenRequests(int time) {
        this.timeBetweenRequests = time;
    }
}
