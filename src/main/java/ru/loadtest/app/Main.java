package ru.loadtest.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.loadtest.app.LoadTest.AppTestAPI;

import static ru.loadtest.app.LoadTest.AppCore.Parser.getCollectedLinks;
import static ru.loadtest.app.LoadTest.AppTestAPI.TestType.LOAD;

public class Main {
    public static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {
        AppTestAPI test = new AppTestAPI("http://yandex.ru");
        test.setParsingTimeout(300);
        test.execute(600,100, LOAD);
        test.printStatistic();
    }
}
