package org.loadx.application.db.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "EXECUTION_DETAILS")
public class ExecutionDetails implements LoadxEntity {
    private int id;
    private int executionId;
    private Date startTime;
    private Date endTime;
    private int requestId;
    private int timeElapsed;
    private int responseCode;
    private String loadingStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExecutionId() {
        return executionId;
    }

    public void setExecutionId(int executionId) {
        this.executionId = executionId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(int timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getLoadingStatus() {
        return loadingStatus;
    }

    public void setLoadingStatus(String loadingStatus) {
        this.loadingStatus = loadingStatus;
    }
}
