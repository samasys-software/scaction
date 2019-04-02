package com.samayu.scaction.dto;

/**
 * Created by NandhiniGovindasamy on 4/1/19.
 */

public class Portfolio {

    private long id;

    private String shortDescription, areaOfExpertise, projectsWorked, message;

    private long userId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getAreaOfExpertise() {
        return areaOfExpertise;
    }

    public void setAreaOfExpertise(String areaOfExpertise) {
        this.areaOfExpertise = areaOfExpertise;
    }

    public String getProjectsWorked() {
        return projectsWorked;
    }

    public void setProjectsWorked(String projectsWorked) {
        this.projectsWorked = projectsWorked;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
