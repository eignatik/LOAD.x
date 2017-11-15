package com.ngload.application.appcore.entities;

import java.util.List;

/**
 * Entity represents web site link for graph based algo
 */
public class Link {
    private String URL;
    private boolean isLinkCorrect;
    private List<Link> prevNodes;
    private List<Link> nextNodes;

    public Link(String URL) {
        this.URL = URL;
    }

    public String getURL() {
        return this.URL;
    }

    public boolean isLinkCorrect() {
        return this.isLinkCorrect;
    }

    public void setLinkCorrect(boolean isLinkCorrect) {
        this.isLinkCorrect = isLinkCorrect;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public List<Link> getPrevNodes() {
        return prevNodes;
    }

    public void setPrevNodes(List<Link> prevNodes) {
        this.prevNodes = prevNodes;
    }

    public List<Link> getNextNodes() {
        return nextNodes;
    }

    public void setNextNodes(List<Link> nextNodes) {
        this.nextNodes = nextNodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        return URL.equals(link.URL);
    }

    @Override
    public int hashCode() {
        return URL.hashCode();
    }

    @Override
    public String toString() {
        return "Link{" +
                "URL='" + URL + '\'' +
                ", isLinkCorrect=" + isLinkCorrect +
                ", prevNodes=" + prevNodes +
                ", nextNodes=" + nextNodes +
                '}';
    }
}
