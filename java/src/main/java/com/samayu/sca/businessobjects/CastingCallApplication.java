package com.samayu.sca.businessobjects;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name="scaction_casting_call_application")
public class CastingCallApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idscaction_casting_call_application")
    private long id;

    @Column(name="casting_call_id")
    private long castingCallId;

    @Column(name="user_id")
    private long userId;

    @Column(name="create_dt")
    private Timestamp createDate;

    public long getId() {
        return id;
    }

    public long getCastingCallId() {
        return castingCallId;
    }

    public void setCastingCallId(long castingCallId) {
        this.castingCallId = castingCallId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Column(name="role_id")

    private int roleId;


}
