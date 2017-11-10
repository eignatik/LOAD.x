package com.ngload.application.appcore.entities;

/**
 * Entity represents web site link. Should be a part of tree or smth. like that
 * TODO: analyze trees possibilities in this case
 */
public class Link {
    private String URL;
    private boolean isLinkCorrect;

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
}
