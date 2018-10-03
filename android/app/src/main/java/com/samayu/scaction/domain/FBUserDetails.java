package com.samayu.scaction.domain;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by NandhiniGovindasamy on 8/27/18.
 */

public class FBUserDetails implements Serializable
{
    private String name,emailAddress,id,gender,url;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
