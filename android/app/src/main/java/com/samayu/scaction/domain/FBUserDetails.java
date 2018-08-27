package com.samayu.scaction.domain;

import org.json.JSONObject;

/**
 * Created by NandhiniGovindasamy on 8/27/18.
 */

public class FBUserDetails {
    private String name,emailAddress,id,gender;
    private JSONObject profile_pic_data,profile_pic_url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JSONObject getProfile_pic_data() {
        return profile_pic_data;
    }

    public void setProfile_pic_data(JSONObject profile_pic_data) {
        this.profile_pic_data = profile_pic_data;
    }

    public JSONObject getProfile_pic_url() {
        return profile_pic_url;
    }

    public void setProfile_pic_url(JSONObject profile_pic_url) {
        this.profile_pic_url = profile_pic_url;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
