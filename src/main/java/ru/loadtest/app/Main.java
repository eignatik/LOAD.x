package ru.loadtest.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.loadtest.app.LoadTest.HTTPConnection.ConnectionAPI;

public class Main {
    public static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {
        ConnectionAPI connection = new ConnectionAPI();
        connection.setBaseURL("http://mail.ru");
        connection.exploreLinks("");
    }
}
