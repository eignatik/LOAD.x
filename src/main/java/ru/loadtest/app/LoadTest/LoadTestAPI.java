package ru.loadtest.app.LoadTest;

import ru.loadtest.app.LoadTest.AppCore.ConnectionAPI;

import java.util.ArrayList;
import java.util.List;

/**
 * The class provide you several public methods to operate with LoadTest Application. Use this class in your applications.
 * Default constructor uses localhost with port 8802. You should call constructor with URL and port or only with port if you want
 * to use localhost with other port.
 * <p>
 * Use other constructors if you want to get access to remote web-sites.
 */
public class LoadTestAPI {
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
     * Execute test with random visits of pages.
     *
     * @param startURL This is the first point of exploring. Explore is started from address.zone if @param startURL is empty string.
     */
    public void executeRandomTest(String startURL) {
        ConnectionAPI connection = new ConnectionAPI(this.URL, startURL);
        connection.start();
    }

    /**
     * Overloaded method with manual timeout value
     * @param startURL This is the first point of exploring. Explore is started from address.zone if @param startURL is empty string.
     * @param timeout value in seconds that set timeout for testing time
     */
    public void executeRandomTest(String startURL, long timeout, int usersCount) {

        List<ConnectionAPI> threads = new ArrayList<>();
        ConnectionAPI.setTimeout(timeout);
//        ConnectionAPI connection = new ConnectionAPI(this.URL, startURL);
        for (int i = 0; i < usersCount; i++) {
            threads.add(new ConnectionAPI(this.URL, startURL));
        }
        for (ConnectionAPI thread : threads) {
            thread.start();
        }
//        connection.start();
    }

    /**
     * Set request limit interval in ms
     * @param limit value in mileseconds
     */
    public void setMaxIntervalVal(int limit) {
        ConnectionAPI.setRequestIntervals(limit);
    }
}
