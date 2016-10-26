package ru.loadtest.app.LoadTest;

import ru.loadtest.app.LoadTest.HTTPConnection.ConnectionAPI;

/**
 * The class provide you several public methods to operate with LoadTest Application. Use this class in your applications.
 * Default constructor uses localhost with port 8802. You should call constructor with URL and port or only with port if you want
 * to use localhost with other port.
 *
 * Use other constructors if you want to get access to remote web-sites.
 */
public class LoadTestAPI {
    private String URL;
    private int port = 8802;

    public LoadTestAPI(){
        URL = "localhost:" + port;
    }

    public LoadTestAPI(String URL, int port){
        this.URL = URL + port;
    }

    public LoadTestAPI(String URL){
        this.URL = URL;
    }

    /**
     * Execute test with random visits of pages.
     * @param startURL This is the first point of exploring. Explore is started from address.zone if @param startURL is empty string.
     */
    public void executeRandomTest(String startURL){
        ConnectionAPI connection = new ConnectionAPI();
        connection.setBaseURL(this.URL);
        connection.exploreLinks(startURL);
    }
}
