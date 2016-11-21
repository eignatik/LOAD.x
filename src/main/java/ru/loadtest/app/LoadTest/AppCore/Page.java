package ru.loadtest.app.LoadTest.AppCore;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Page {
    private String URL;
    private List<String> links;
    private int requestCount;
    private int maxRequest;
    private int minRequest;
    private int avgRequest;

    private Random random;

    Page(String URL) {
        this.URL = URL;
    }

    Page(String URL, List<String> links) {
        this.URL = URL;
        this.links = links;
        random = new Random();
    }

    public String getURL() {
        return URL;
    }

    public List<String> getLinks() {
        return links;
    }

    public String getLinkByIndex(int index) {
        return links.get(index);
    }

    public String getRandomLink() {
        if(links.size() == 0) {
            return "";
        }
        int index = random.nextInt(links.size()-1);
        return links.get(index);
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
    public int getRequestCount() {
        return requestCount;
    }

    /**
     * get max request value in ms
     * @return
     */
    public int getMaxRequest() {
        return maxRequest;
    }

    /**
     * get min request value in ms
     * @return
     */
    public int getMinRequest() {
        return minRequest;
    }

    /**
     * get average request value in ms
     * @return
     */
    public int getAvgRequest() {
        return avgRequest;
    }
}
