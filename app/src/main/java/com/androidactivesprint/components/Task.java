package com.androidactivesprint.components;

import java.util.Date;

/**
 * Created by Hung on 10/2/2017.
 */

public class Task {

    private Date createDate;
    private Date updateDate;
    private Status status;
    private Priority priority;
    private String assignee;
    private String summary;
    private String description;


    public Task() {
    }

    public Task(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

