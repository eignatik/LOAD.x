package ru.loadtest.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.loadtest.app.HTTPConnection.ConnectionAPI;
import ru.loadtest.app.HTTPConnection.HTTPConnection;
import ru.loadtest.app.Pasrser.Parser;

import java.util.List;
import java.util.Random;

public class Main {
    public static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args){
        ConnectionAPI connection = new ConnectionAPI();
        connection.setBaseURL("http://yandex.ru");
        connection.exploreLinks("");
    }
}
