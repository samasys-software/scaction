package com.samayu.scaction.service;

import com.samayu.scaction.domain.FBUserDetails;
import com.samayu.scaction.dto.CastingCall;
import com.samayu.scaction.dto.City;
import com.samayu.scaction.dto.Country;
import com.samayu.scaction.dto.ProfileType;
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


    public static SessionInfo getInstance() {
        return ourInstance;
    }

    private SessionInfo() {
    }

    public static void destroy(){
        ourInstance= new SessionInfo();
    }


}
