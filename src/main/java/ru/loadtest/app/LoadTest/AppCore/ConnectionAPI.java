package ru.loadtest.app.LoadTest.AppCore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class ConnectionAPI extends Thread {
    public static final Logger logger = LogManager.getLogger(ConnectionAPI.class.getName());
    private Random random = new Random();
    private HTTPConnection connection;
    private List<Page> visitedPages = new LinkedList<>();
    private String baseURL;
    private String startURL;
    private long period = 300000;

    public ConnectionAPI(String baseURL, String startURL) {
        this.baseURL = baseURL;
        this.startURL = startURL;
    }
    
    @Override
    public void run() {
        startExplore();
    }

    private void startExplore() {
        connection = new HTTPConnection(this.baseURL);
        Util.setWorkURL(this.baseURL);
        long startTime = System.currentTimeMillis();
        long currentTime;
        while(true) {
            currentTime = System.currentTimeMillis() - startTime;
            if(currentTime > this.period){
                logger.info("Timeout. (" + currentTime + ")");
                break;
            }
            explore();

        }
    }

    private void explore(){
        String htmlPage = connection.getHTMLPageByURL(this.startURL);
        visitedPages.add(new Page(this.startURL));
        List<String> links = Parser.getLinksFromHTML(htmlPage);
        if(links.isEmpty()) {
            this.startURL = "";
        } else {
            int index = getRandomValue(links.size());
            this.startURL = links.get(index);
        }
    }

    private int getRandomValue(int size) {
        return random.nextInt(size);
    }

    public List<Page> getVisitedPages() {
        return visitedPages;
    }

    public void setPeriod(long period) {
        this.period = period;
    }
}
