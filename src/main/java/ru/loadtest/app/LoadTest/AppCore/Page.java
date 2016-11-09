package ru.loadtest.app.LoadTest.AppCore;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.List;

public class Page {
    private String URL;
    private List<String> links;

    public Page(String URL, List<String> links) {
        this.URL = URL;
        this.links = links;
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
}
