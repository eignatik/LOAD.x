package ru.loadtest.app.LoadTest.AppCore;

public class Link {
    private String URL;
    private int reqCount;

    Link(String URL) {
        this.URL = URL;
    }

    public String getURL() {
        return this.URL;
    }

    int getReqCount() {
        return this.reqCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if (reqCount != link.reqCount) return false;
        return URL != null ? URL.equals(link.URL) : link.URL == null;

    }

    @Override
    public int hashCode() {
        int result = URL != null ? URL.hashCode() : 0;
        result = 31 * result + reqCount;
        return result;
    }

    @Override
    public String toString() {
        return "\"Link\": {" +
                "\"URL\": \"" + URL + "\"" +
                ", \"reqCount\": \"" + reqCount + "\"" +
                '}';
    }
}
