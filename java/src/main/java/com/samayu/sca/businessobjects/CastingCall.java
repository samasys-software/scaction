package com.samayu.sca.businessobjects;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="scaction_casting_call")
public class CastingCall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="casting_call_id")
    private long id;

    @Column(name="project_name")
    private String projectName;

    @Column(name="project_details")
    private String projectDetails;

    @Column(name="production_cmpy")
    private String productionCompany;

    @Column(name="role_details")
    private String roleDetails;

    @Column(name="start_age")
    private int startAge;

    @Column(name="end_age")
    private int endAge;

    @Column(name="gender")
    private int gender;

    @Column(name="city_id")
    private int cityId;

    @Column(name="country_id")
    private int countryId;

    @Column(name="venue")
    private String address;

    @Column(name="start_date")
    private Date startDate;

    @Column(name="end_date")
    private Date endDate;

    @Column(name="hours")
    private String hours;

    @Column(name="scaction_user_id")
    private long userId;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDetails() {
        return projectDetails;
    }

    public void setProjectDetails(String projectDetails) {
        this.projectDetails = projectDetails;
    }

    public String getProductionCompany() {
        return productionCompany;
    }

    public void setProductionCompany(String productionCompany) {
        this.productionCompany = productionCompany;
    }

    public String getRoleDetails() {
        return roleDetails;
    }

    public void setRoleDetails(String roleDetails) {
        this.roleDetails = roleDetails;
    }

    public int getStartAge() {
        return startAge;
    }

    public void setStartAge(int startAge) {
        this.startAge = startAge;
    }

    public int getEndAge() {
        return endAge;
    }

    public void setEndAge(int endAge) {
        this.endAge = endAge;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

}
