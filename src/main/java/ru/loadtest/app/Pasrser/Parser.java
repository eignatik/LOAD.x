package ru.loadtest.app.Pasrser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;

public class Parser {
    public static final Logger logger = LogManager.getLogger(Parser.class.getName());
    private List<String> listOfLinks = new LinkedList<>();

    public void parseLinks(String HTMLString){
        Document html = Jsoup.parse(HTMLString);
        Elements links = html.select("a[href]");
        for(Element link : links){
            StringBuilder linkBuilder = new StringBuilder();
            linkBuilder
                    .append("\"")
                    .append(link.text())
                    .append("\": \"")
                    .append(link.attr("href"))
                    .append("\", ");
            listOfLinks.add(linkBuilder.toString());
            logger.info(link.toString() +  "\n");
            logger.info(linkBuilder.toString() +  "\n");
        }
    }

    public List<String> getListOfLinks() throws Exception{
        if(!listOfLinks.isEmpty()){
            return listOfLinks;
        } else {
            logger.error("\nList of links is empty\n");
            throw new Exception();
        }
    }
}
