package ru.loadtest.app.LoadTest.AppCore.testsModels;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.loadtest.app.LoadTest.AppCore.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import static ru.loadtest.app.LoadTest.AppCore.Statistic.Load.RequestsStatistic.*;

public class Simulation extends Thread implements ITest {
    public static final Logger logger = LogManager.getLogger(Simulation.class.getName());

    private Random random = new Random();
    private HTTPConnection connection;
    private static Map<String, Page> sitePages = new ConcurrentHashMap<>();

    private String URL;
    private long requestTime;

    private static String baseURL;
    private static long TIMEOUT = 300000;
    private static int WAITING_BETWEEN_REQUESTS = 10000;

    private Simulation(String startURL) {
        this.URL = startURL;
    }

    public static void execute(int usersCount, String URL) {
        logger.info("Initializing simulation test...");
        if(!checkPreferences()) {
            return;
        }
        List<Simulation> threads = new ArrayList<>();
        for (int i = 0; i < usersCount; i++) {
            threads.add(new Simulation(URL));
        }
        logger.info(usersCount + " threads are created");
        threads.parallelStream().forEach(Thread::start);
        logger.info("All threads are started");
    }

    @Override
    public void run(){
        explore();
    }

    private void explore() {
        connection = new HTTPConnection(baseURL);
        long startTime = System.currentTimeMillis();
        long currentTime = 0;
        while (currentTime < TIMEOUT) {
            currentTime = System.currentTimeMillis() - startTime;
            Util.setSleeping(random.nextInt(WAITING_BETWEEN_REQUESTS));
            proceedExploring();
        }
    }

    synchronized private void proceedExploring() {
        requestTime = System.currentTimeMillis();
        String htmlPage = connection.getHTMLPageByURL(this.URL);
        requestTime = System.currentTimeMillis() - requestTime;
        String URLToCollect = this.URL;
        this.URL = setNextLink(htmlPage);
        addToStatistic(URLToCollect);
    }

    private String setNextLink(String htmlPage) {
        String link = "";
        int direction = random.nextInt(3);
        if (direction == 0 || sitePages.isEmpty()) {
            link = getRandomParsedOrExistedLink(htmlPage);
        } else {
            //link = getRandomQueuedLink(htmlPage); TODO queued getting links
        }
        return link;
    }

    private String getRandomParsedOrExistedLink(String htmlPage) {
        String link;
        if (sitePages.containsKey(this.URL)) {
            sitePages.get(this.URL).addRequest(requestTime);
            link = sitePages.get(this.URL).getRandomLink();
        } else {
            link = getNextLink(htmlPage);
        }
        return link;
    }

    private String getNextLink(String htmlPage) {
        sitePages.put(this.URL, new Page(this.URL, Parser.getLinksFromHTML(htmlPage)));
        return Parser.getNextLinkFromHTML(htmlPage);
    }

    private void addToStatistic(String URLToCollect) {
        collect(URLToCollect, sitePages.get(URLToCollect));
        incrementVisitedPagesCounter();
    }

    public static void setTimeout(long timeout) {
        TIMEOUT = timeout;
    }

    public static void setTopRange(int requestTime) {
        WAITING_BETWEEN_REQUESTS = requestTime;
    }

    public static void setBaseURL(String url) {
        baseURL = url;
        Util.setWorkURL(url);
    }

    private static boolean checkPreferences() {
        boolean isCorrect = true;
        if (baseURL.equals("")) {
            logger.error("Base URL did't set. Can't execute test.");
            isCorrect = false;
        }
        return isCorrect;
    }
}
