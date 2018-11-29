package com.samayu.scaction.dto;

import java.sql.Timestamp;

/**
 * Created by NandhiniGovindasamy on 10/30/18.
 */

public class CastingCallApplication {

    private long id;

    private long castingCallId;

    private User user;


    private int roleId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCastingCallId() {
        return castingCallId;
    }

    public void setCastingCallId(long castingCallId) {
        this.castingCallId = castingCallId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
