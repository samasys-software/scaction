package com.samayu.sca.businessobjects;

public class Notification {

   private User user;

   private int notificationType;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(int notificationType) {
        this.notificationType = notificationType;
    }

    public CastingCall getReference() {
        return reference;
    }

    public void setReference(CastingCall reference) {
        this.reference = reference;
    }

    private CastingCall reference;
}
