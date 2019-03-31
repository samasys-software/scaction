package com.samayu.sca.businessobjects;

import javax.persistence.*;

@Entity
@Table(name="scaction_portfolio_details")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="scaction_portfolio_details_id")
    private long id;

    @Column(name="short_desc")
    private String shortDescription;

    @Column(name="area_of_expertise")
    private String areaOfExpertise;


    @Column(name="scaction_user_id")
    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    @Column(name="project_worked")
    private String projectsWorked;

    @Column(name="message")
    private String message;

    public long getId() {
        return id;
    }

}
