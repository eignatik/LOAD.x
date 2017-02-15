package com.ngload.application.appcore.webcore.entities;

public class Link {
    private String URL;
    private boolean isCorrect;

    public Link(String URL) {
        this.URL = URL;
    }

    public String getURL() {
        return this.URL;
    }

    public boolean isCorrect() {
        return this.isCorrect;
    }

    public void setCorrect(boolean isLinkCorrect) {
        this.isCorrect = isLinkCorrect;
    }
}
