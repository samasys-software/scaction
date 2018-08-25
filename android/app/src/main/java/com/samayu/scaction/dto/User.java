package com.samayu.scaction.dto;

import java.time.LocalDateTime;

/**
 * Created by NandhiniGovindasamy on 8/22/18.
 */

public class User {


    private long userId;

    private int role;

    private String fbUser;

    private String fbEmail;

    private String profilePic;

    private String phoneNumber;

    private String countryCode;

    private String whatsappNumber;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;

    private boolean verified;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getFbUser() {
        return fbUser;
    }

    public void setFbUser(String fbUser) {
        this.fbUser = fbUser;
    }

    public String getFbEmail() {
        return fbEmail;
    }

    public void setFbEmail(String fbEmail) {
        this.fbEmail = fbEmail;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getWhatsappNumber() {
        return whatsappNumber;
    }

    public void setWhatsappNumber(String whatsappNumber) {
        this.whatsappNumber = whatsappNumber;
    }

    public LocalDateTime getCreateDt() {
        return createDt;
    }

    public void setCreateDt(LocalDateTime createDt) {
        this.createDt = createDt;
    }

    public LocalDateTime getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(LocalDateTime updateDt) {
        this.updateDt = updateDt;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}

