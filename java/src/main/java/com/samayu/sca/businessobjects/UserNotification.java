package com.samayu.sca.businessobjects;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="scaction_user_notifications")
public class UserNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idscaction_user_notifications")
    private long userNotificationId;
    @Column(name = "scaction_user_id")
    private long userId;
    @Column(name = "notification_type")
    private int notificationType;
    @Column(name = "notification_message")
    private String message;
    @Column(name = "redirect_info")
    private String redirectLink;
    @Column(name = "notification_reference_key")
    private long referenceKey;
    @Column(name = "create_dt")
    private Timestamp createDt;

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

    public long getReferenceKey() {
        return referenceKey;
    }

    public void setReferenceKey(long referenceKey) {
        this.referenceKey = referenceKey;
    }

    public Timestamp getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Timestamp createDt) {
        this.createDt = createDt;
    }
}
