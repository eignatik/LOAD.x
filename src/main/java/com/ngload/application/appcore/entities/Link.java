package com.ngload.application.appcore.entities;

/**
 * Entity represents web site link. Should be a part of tree or smth. like that
 * TODO: analyze trees possibilities in this case
 */
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
