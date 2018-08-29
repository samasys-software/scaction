package com.samayu.sca.dto;

import com.samayu.sca.businessobjects.Country;
import com.samayu.sca.businessobjects.ProfileType;

import java.util.List;

public class ProfileDefaultsDTO {

    private Iterable<Country> countries;

    private Iterable<ProfileType> profileTypes;

    public Iterable<Country> getCountries() {
        return countries;
    }

    public void setCountries(Iterable<Country> countries) {
        this.countries = countries;
    }

    public Iterable<ProfileType> getProfileTypes() {
        return profileTypes;
    }

    public void setProfileTypes(Iterable<ProfileType> profileTypes) {
        this.profileTypes = profileTypes;
    }
}
