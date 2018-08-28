package com.samayu.sca.businessobjects;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="scaction_casting_call")
public class CastingCall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="casting_call_id")
    private int id;

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

    @Column(name="city_name")
    private String city;

    @Column(name="city_id")
    private int cityId;

    @Column(name="country_id")
    private int countryId;

    @Column(name="country_name")
    private int countryName;

    @Column(name="venue")
    private String address;

    @Column(name="start_date")
    private LocalDate startDate;

    @Column(name="end_date")
    private LocalDate endDate;

    @Column(name="start_time")
    private LocalTime startTime;

    @Column(name="end_time")
    private LocalTime endTime;


    public int getId() {
        return id;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getCountryName() {
        return countryName;
    }

    public void setCountryName(int countryName) {
        this.countryName = countryName;
    }
}
