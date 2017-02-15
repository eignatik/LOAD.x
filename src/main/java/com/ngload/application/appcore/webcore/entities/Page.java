package com.ngload.application.appcore.webcore.entities;

import java.util.ArrayList;
import java.util.List;

public class Page {
    private Link pageLink;
    private List<Link> links;
    private boolean isWork;
    private RequestStatistics requestStatistics = new RequestStatistics();;

    public Page(Link pageLink, List<Link> links) {
        this.pageLink = pageLink;
        this.links = setList(links);
        this.isWork = true;
    }

    public Page(Link pageLink, List<Link> links, boolean isWork) {
        this.pageLink = pageLink;
        this.links = setList(links);
        this.isWork = isWork;
    }

    private List<Link> setList(List<Link> links) {
        return (links.isEmpty())? addHomePageLink() : links;
    }

    private List<Link> addHomePageLink() {
        List<Link> links = new ArrayList<>();
        links.add(new Link(""));
        return links;
    }

    public boolean isWork() {
        return isWork;
    }

    public void setWork(boolean work) {
        isWork = work;
    }
}
