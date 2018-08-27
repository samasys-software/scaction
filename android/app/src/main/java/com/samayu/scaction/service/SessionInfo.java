package com.samayu.scaction.service;

import org.json.JSONObject;

/**
 * Created by NandhiniGovindasamy on 8/22/18.
 */

public class SessionInfo {
    public static final SessionInfo ourInstance = new SessionInfo();


    private JSONObject FBUserDetails;


    public JSONObject getFBUserDetails() {
        return FBUserDetails;
    }

    public void setFBUserDetails(JSONObject FBUserDetails) {
        this.FBUserDetails = FBUserDetails;
    }

    public static SessionInfo getInstance() {
        return ourInstance;
    }

    private SessionInfo() {
    }


}
