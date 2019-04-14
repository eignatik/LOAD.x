package org.loadx.application.db.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "LOADING_EXECUTION")
public class LoadingExecution implements LoadxEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int loadingTaskId;
    private Date startTime;
    private Date endTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoadingTaskId() {
        return loadingTaskId;
    }

    public void setLoadingTaskId(int loadingTaskId) {
        this.loadingTaskId = loadingTaskId;
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
}
