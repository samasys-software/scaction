package com.samayu.scaction.service;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.samayu.scaction.domain.FBUserDetails;
import com.samayu.scaction.dto.CastingCall;
import com.samayu.scaction.dto.City;
import com.samayu.scaction.dto.Country;
import com.samayu.scaction.dto.PortfolioPicture;
import com.samayu.scaction.dto.ProfileType;
import com.samayu.scaction.dto.SelectedCastingCallRoles;
import com.samayu.scaction.dto.User;
import com.samayu.scaction.dto.UserNotification;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by NandhiniGovindasamy on 8/22/18.
 */

public class SessionInfo {
    public static  SessionInfo ourInstance = new SessionInfo();


    private FBUserDetails fbUserDetails;


    public FBUserDetails getFbUserDetails() {
        return fbUserDetails;
    }

    public void setFbUserDetails(FBUserDetails fbUserDetails) {
        this.fbUserDetails = fbUserDetails;
    }

//    private GoogleSignInAccount GoogleUserDetails;
//
//    public GoogleSignInAccount getGoogleUserDetails() {
//        return GoogleUserDetails;
//    }
//
//    public void setGoogleUserDetails(GoogleSignInAccount googleUserDetails) {
//        GoogleUserDetails = googleUserDetails;
//    }

    private List<Country> countries;

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }


    private List<City> cities;

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }


    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private List<ProfileType> profileTypes;

    public List<ProfileType> getProfileTypes() {
        return profileTypes;
    }

    public void setProfileTypes(List<ProfileType> profileTypes) {
        this.profileTypes = profileTypes;
    }

    private CastingCall currentCastingCall;

    public CastingCall getCurrentCastingCall() {
        return currentCastingCall;
    }

    public void setCurrentCastingCall(CastingCall currentCastingCall) {
        this.currentCastingCall = currentCastingCall;
    }


    private List<UserNotification> userNotifications;

    public List<UserNotification> getUserNotifications() {
        return userNotifications;
    }

    public void setUserNotifications(List<UserNotification> userNotifications) {
        this.userNotifications = userNotifications;
    }


    private List<SelectedCastingCallRoles> selectedCastingCallRoles;

    public List<SelectedCastingCallRoles> getSelectedCastingCallRoles() {
        return selectedCastingCallRoles;
    }

    public void setSelectedCastingCallRoles(List<SelectedCastingCallRoles> selectedCastingCallRoles) {
        this.selectedCastingCallRoles = selectedCastingCallRoles;
    }


    private List<SelectedCastingCallRoles> rolesList;

    public List<SelectedCastingCallRoles> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<SelectedCastingCallRoles> rolesList) {
        this.rolesList = rolesList;
    }

    private List<PortfolioPicture> portfolioPictureList;

    public List<PortfolioPicture> getPortfolioPictureList() {
        return portfolioPictureList;
    }

    public void setPortfolioPictureList(List<PortfolioPicture> portfolioPictureList) {
        this.portfolioPictureList = portfolioPictureList;
    }

//    public int loginType;
//
//    public int getLoginType() {
//        return loginType;
//    }
//
//    public void setLoginType(int loginType) {
//        this.loginType = loginType;
//    }

    public static SessionInfo getInstance() {
        return ourInstance;
    }

    private SessionInfo() {
    }

    public static void destroy(){
        ourInstance= new SessionInfo();
    }


}
