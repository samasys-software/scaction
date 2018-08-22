package com.samayu.scaction.service;

/**
 * Created by NandhiniGovindasamy on 8/22/18.
 */

public class SessionInfo {
    private static final SessionInfo ourInstance = new SessionInfo();

    static SessionInfo getInstance() {
        return ourInstance;
    }

    private SessionInfo() {
    }
}
