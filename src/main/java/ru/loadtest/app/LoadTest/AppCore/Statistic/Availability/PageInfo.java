package ru.loadtest.app.LoadTest.AppCore.Statistic.Availability;

public class PageInfo {
    private String URL;
    private boolean isAvailable;
    private String status;

    public PageInfo(String URL, boolean isAvailable, String status) {
        this.URL = URL;
        this.isAvailable = isAvailable;
        this.status = status;
    }

    public String getURL() {
        return URL;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getStatus() {
        return status;
    }
}
