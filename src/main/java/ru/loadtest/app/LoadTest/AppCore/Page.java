package ru.loadtest.app.LoadTest.AppCore;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

public class Page {
    private String URL;
    private List<Link> links;
    private Queue<Link> linksQueue;
    private long requestsTimeSum;
    private long requestCount;
    private long maxRequest;
    private long minRequest;
    private long avgRequest;

    private static Random random = new Random();

    Page(String URL) {
        this.URL = URL;
    }

    Page(String URL, List<Link> links) {
        this.URL = URL;
        this.links = links;
        if (links.size() == 0) {
            links.add(new Link(""));
        }
        this.linksQueue = new PriorityBlockingQueue<>(links.size(), requestCountComparator);
    }

    public String getURL() {
        return URL;
    }

    public List<Link> getLinks() {
        return links;
    }

    public String getLinkByIndex(int index) {
        return links.get(index).getURL();
    }

    /**
     * method generates 0 or 1 to select link generating way. If value 0 then random link from all list will be returned, else the random link from smallest request count list will be returned. Using PriorityQueue to sort links by request count value.
     * @return
     */
    String getRandomLink() {
        int direction = random.nextInt(3);
        return getLink(direction);
    }

    private String getLink(int direction) {
        switch (direction) {
            case 0: return getRandomLinkFromList(links);
            case 1:
            case 2: return getQueuedRandomLink();
            default: return getRandomLinkFromList(links);
        }
    }

    private String getQueuedRandomLink() {
        List<Link> linksWithSmallestReqCount = new ArrayList<>();
        fillQueue();
        linksWithSmallestReqCount = getLinksWithSmallestReqCount(linksWithSmallestReqCount);
        return linksWithSmallestReqCount.isEmpty()? getRandomLinkFromList(links) : getRandomLinkFromList(linksWithSmallestReqCount);
    }

    private void fillQueue() {
        for(Link link : links) {
            linksQueue.add(link);
        }
    }

    private List<Link> getLinksWithSmallestReqCount(List<Link> list) {
        Link link = linksQueue.peek();
        int smallestRequestCount = link.getReqCount();
        boolean isRelevant = true;
        while (isRelevant) {
            if (link != null && link.getReqCount() == smallestRequestCount) {
                list.add(link);
            } else {
                isRelevant = false;
            }
            link = linksQueue.poll();
        }
        linksQueue.clear();
        return list;
    }

    private String getRandomLinkFromList(List<Link> links) {
        if(links.size() == 0) {
            return "";
        }
        int index = (links.size() == 1)? 0 : random.nextInt(links.size()-1);
        links.get(index).incrementReqCount();
        return links.get(index).getURL();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Page page = (Page) o;

        return new EqualsBuilder()
                .append(URL, page.URL)
                .append(links, page.links)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(URL)
                .append(links)
                .toHashCode();
    }

    /**
     *
     * @return get requests count
     */
    public long getRequestCount() {
        return requestCount;
    }

    /**
     * get max request value in ms
     * @return
     */
    public long getMaxRequest() {
        return maxRequest;
    }

    /**
     * get min request value in ms
     * @return
     */
    public long getMinRequest() {
        return minRequest;
    }

    /**
     * get average request value in ms
     * @return
     */
    public long getAvgRequest() {
        return avgRequest;
    }

    public void addRequest(long requestTime) {
        this.requestsTimeSum += requestTime;
        requestCount++;
        calculateStatistic(requestTime);
    }


    private void calculateStatistic(long requestTime) {
        changeMinAndMax(requestTime);
        changeAverage();
    }

    private void changeMinAndMax(long requestTime) {
        if (requestCount == 1) {
            maxRequest = requestTime;
            minRequest = requestTime;
            return;
        }
        maxRequest = (requestTime > maxRequest)? requestTime : maxRequest;
        minRequest = (requestTime < minRequest)? requestTime : minRequest;
    }

    private void changeAverage() {
        avgRequest = requestsTimeSum / requestCount;
    }

    public static Comparator<Link> requestCountComparator = (o1, o2) -> (o1.getReqCount() - o2.getReqCount());

}
