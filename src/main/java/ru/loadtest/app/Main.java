package ru.loadtest.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.loadtest.app.LoadTest.LoadTestAPI;

public class Main {
    public static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {
        LoadTestAPI test = new LoadTestAPI("http://m-universal.ru");
//        test.executeRandomTest("");
        test.setMaxIntervalVal(100);
        test.executeRandomTest("", 120, 10);
        test.printStatistic();
    }
}
