package ru.loadtest.app.LoadTest.AppCore;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

public class Page implements Comparable<Page> {
    private String URL;
    private List<Link> links;
    private boolean enabled = true;
    private long requestCount;
    private long requestsTimeSum;
    private long maxRequest;
    private long minRequest;
    private long avgRequest;

    private static Random random = new Random();

    public Page(String URL) {
        this.URL = URL;
    }

    public Page(String URL, List<Link> links) {
        this.URL = URL;
        this.links = links;
        if (links.size() == 0) {
            links.add(new Link(""));
        }
    }

    public String getURL() {
        return URL;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Link> getLinks() {
        return links;
    }

    public String getLinkByIndex(int index) {
        return links.get(index).getURL();
    }


    /**
     *
      * @return random link from page links list
     */
    public String getRandomLink() {
        return getRandomLinkFromList(links);
    }

    public String getRandomLinkFromList(List<Link> links) {
        if(links.size() == 0) {
            return "";
        }
        int index = (links.size() == 1)? 0 : random.nextInt(links.size()-1);
        return links.get(index).getURL();
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

    @Override
    public int compareTo(Page page) {
        return (int)(this.requestCount - page.requestCount);
    }
}
