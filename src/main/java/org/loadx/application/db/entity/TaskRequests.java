package org.loadx.application.db.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TASK_REQUESTS")
public class TaskRequests implements LoadxEntity {
    private int id;
    private int loadTaskId;
    private int loadRequestId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoadTaskId() {
        return loadTaskId;
    }

    public void setLoadTaskId(int loadTaskId) {
        this.loadTaskId = loadTaskId;
    }

    public int getLoadRequestId() {
        return loadRequestId;
    }

    public void setLoadRequestId(int loadRequestId) {
        this.loadRequestId = loadRequestId;
    }
}
