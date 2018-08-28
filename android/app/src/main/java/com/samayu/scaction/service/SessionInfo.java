package com.samayu.scaction.service;

import com.samayu.scaction.domain.FBUserDetails;
import com.samayu.scaction.dto.City;
import com.samayu.scaction.dto.Country;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by NandhiniGovindasamy on 8/22/18.
 */

public class SessionInfo {
    public static final SessionInfo ourInstance = new SessionInfo();


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

    public static SessionInfo getInstance() {
        return ourInstance;
    }

    private SessionInfo() {
    }


}
