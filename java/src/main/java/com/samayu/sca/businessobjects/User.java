package com.samayu.sca.businessobjects;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="scaction_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;
    @Column(name = "fb_user")
    private String fbUser;
    @Column(name="screen_name")
    private String screenName;
    @Column(name="fb_email")
    private String fbEmail;
    @Column(name = "country_code")
    private String countryCode;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "whatsapp_numbe")
    private String whatsappNumber;
    @Column(name = "gender")
    private int gender;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name = "searchable")
    private boolean searchable;
    @Column(name = "profile_pic")
    private String profilePic;
    @Column(name ="role")
    private int role;
    @Column(name = "create_dt")
    private LocalDateTime createDt;
    @Column(name ="update_dt" )
    private LocalDateTime updateDt;
    @Column(name = "verified")
    private boolean verified;
    @Column(name = "city")
    private String city;

    @Column(name="city_id")
    private int cityId;

    private List<UserRole> userRoles;

    public String getCity() {
        return city;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public int getCityId() {

        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFbName() {
        return fbName;
    }

    public void setFbName(String fbName) {
        this.fbName = fbName;
    }

    @Column(name="fbName")
    private String fbName;

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


    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isSearchable() {
        return searchable;
    }

    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }

}
