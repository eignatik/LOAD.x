package ru.loadtest.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.loadtest.app.Pasrser.Parser;

public class Main {
    public static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args){
        HTTPConnection connection = HTTPConnection.getInstance();
        Parser parser = new Parser();
        parser.parseLinks(connection.getHTMLPageByURL("http://google.com/"));
    }
}
