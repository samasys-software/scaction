package com.samayu.scaction.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by NandhiniGovindasamy on 8/22/18.
 */

public class User {



    private long userId;

    private String fbUser,screenName,fbEmail,countryCode,phoneNumber,whatsappNumber;
    private int gender;

    private Date dateOfBirth;

    private boolean searchable;

    private String profilePic;

    private int role;

  //  private Timestamp createDt;

  // private Timestamp updateDt;

    private boolean verified;


    private int cityId;


    private List<UserRole> userRoles;


    private String fbName;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFbUser() {
        return fbUser;
    }

    public void setFbUser(String fbUser) {
        this.fbUser = fbUser;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getFbEmail() {
        return fbEmail;
    }

    public void setFbEmail(String fbEmail) {
        this.fbEmail = fbEmail;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWhatsappNumber() {
        return whatsappNumber;
    }

    public void setWhatsappNumber(String whatsappNumber) {
        this.whatsappNumber = whatsappNumber;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isSearchable() {
        return searchable;
    }

    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

//    public Timestamp getCreateDt() {
//        return createDt;
//    }
//
//    public void setCreateDt(Timestamp createDt) {
//        this.createDt = createDt;
//    }
//
//    public Timestamp getUpdateDt() {
//        return updateDt;
//    }
//
//    public void setUpdateDt(Timestamp updateDt) {
//        this.updateDt = updateDt;
//    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public String getFbName() {
        return fbName;
    }

    public void setFbName(String fbName) {
        this.fbName = fbName;
    }
}

