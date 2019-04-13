package org.loadx.application.db.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "LOAD_TASK")
public class LoadTask implements LoadxEntity {
    private int id;
    private String baseUrl;
    private int loadingTime;
    private int parallelThreshold;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public int getLoadingTime() {
        return loadingTime;
    }

    public void setLoadingTime(int loadingTime) {
        this.loadingTime = loadingTime;
    }

    public int getParallelThreshold() {
        return parallelThreshold;
    }

    public void setParallelThreshold(int parallelThreshold) {
        this.parallelThreshold = parallelThreshold;
    }
}
