package com.samayu.sca.businessobjects;



import javax.persistence.*;
import javax.print.attribute.standard.DateTimeAtCompleted;
import java.time.LocalDateTime;
import java.util.Date;
@Entity
@Table(name="sa_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sa_user_id")
    private long userId;
    @Column(name ="role")
    private int role;
    @Column(name = "fb_user")
    private String fbUser;
    @Column(name="fb_email")
    private String fbEmail;
    @Column(name = "profile_pic")
    private String profilePic;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "country_code")
    private String countryCode;
    @Column(name = "whatsapp_numbe")
   private String whatsappNumber;
    @Column(name = "create_dt")
    private LocalDateTime createDt;
    @Column(name ="update_dt" )
    private LocalDateTime updateDt;
    @Column(name = "verified")
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
