package com.samayu.scaction.dto;

import java.sql.Timestamp;

/**
 * Created by NandhiniGovindasamy on 9/27/18.
 */

public class UserNotification {

    private long userNotificationId,userId,referenceKey;

    private int notificationType;

    private String message,redirectLink;


    public long getUserNotificationId() {
        return userNotificationId;
    }

    public void setUserNotificationId(long userNotificationId) {
        this.userNotificationId = userNotificationId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getReferenceKey() {
        return referenceKey;
    }

    public void setReferenceKey(long referenceKey) {
        this.referenceKey = referenceKey;
    }

    public int getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(int notificationType) {
        this.notificationType = notificationType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRedirectLink() {
        return redirectLink;
    }

    public void setRedirectLink(String redirectLink) {
        this.redirectLink = redirectLink;
    }
}
