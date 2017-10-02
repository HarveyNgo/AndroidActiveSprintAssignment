package com.androidactivesprint.components;

import java.util.Date;

/**
 * Created by Hung on 10/2/2017.
 */

public class Task {


    private String content;

    private Date createDate;
    private Date updateDate;
    private Status status;
    private Priority priority;
    private String assignee;

    public Task(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}

