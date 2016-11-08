package ru.loadtest.app.LoadTest.AppCore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;

import static org.jsoup.Jsoup.*;

public class Parser {
    public static final Logger logger = LogManager.getLogger(Parser.class.getName());

    public static List<String> getLinksFromHTML(String HTMLString) {
        List<String> listOfLinks = new LinkedList<>();
        Document html = parse(HTMLString);
        Elements links = html.select("a[href]");
        for (Element link : links) {
            String currentLink = link.attr("href");
            if(!currentLink.isEmpty() && isLink(currentLink)){
                if(!hasSameLink(listOfLinks, currentLink)){
                    StringBuilder linkBuilder = new StringBuilder();
                    linkBuilder
                            .append("\"")
                            .append(link.text())
                            .append("\": \"")
                            .append(link.attr("href"))
                            .append("\", ");
                    listOfLinks.add(linkBuilder.toString());
                }
            }
        }
        return listOfLinks;
    }

    private static boolean isLink(String URL){
        return URL.charAt(0) != '#' && !URL.contains("mailto:");
    }

    private static boolean hasSameLink(List<String> list, String currentLink){
        boolean hasLink = false;
        for(String link : list){
            hasLink = link.contains(currentLink);
        }
        return hasLink;
    }
}
