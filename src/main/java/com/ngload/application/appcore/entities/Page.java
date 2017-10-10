package com.ngload.application.appcore.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * @Extendable should be refactored at all
 */
public class Page {
    private Link pageLink;
    private List<Link> links;
    private boolean isWork;
    private RequestStatistics requestStatistics = new RequestStatistics();;

    @Deprecated
    public Page(Link pageLink, List<Link> links) {
        this.pageLink = pageLink;
        this.links = setList(links);
        this.isWork = true;
    }

    @Deprecated
    public Page(Link pageLink, List<Link> links, boolean isWork) {
        this.pageLink = pageLink;
        this.links = setList(links);
        this.isWork = isWork;
    }

    @Deprecated
    private List<Link> setList(List<Link> links) {
        return (links.isEmpty())? addHomePageLink() : links;
    }

    @Deprecated
    private List<Link> addHomePageLink() {
        List<Link> links = new ArrayList<>();
        links.add(new Link(""));
        return links;
    }

    @Deprecated
    public boolean isWork() {
        return isWork;
    }

    @Deprecated
    public void setWork(boolean work) {
        isWork = work;
    }
}
