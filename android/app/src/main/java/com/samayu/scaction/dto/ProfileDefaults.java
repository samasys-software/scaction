package com.samayu.scaction.dto;

import java.util.List;

/**
 * Created by NandhiniGovindasamy on 9/6/18.
 */

public class ProfileDefaults {
    private List<Country> countries;

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }



    private List<ProfileType> profileTypes;

    public List<ProfileType> getProfileTypes() {
        return profileTypes;
    }

    public void setProfileTypes(List<ProfileType> profileTypes) {
        this.profileTypes = profileTypes;
    }
}
