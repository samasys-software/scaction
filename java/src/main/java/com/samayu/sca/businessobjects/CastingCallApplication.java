package com.samayu.sca.businessobjects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="scaction_casting_call_application")
public class CastingCallApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idscaction_casting_call_application")
    private long id;

    public CastingCall getCastingCall() {
        return castingCall;
    }

    public void setCastingCall(CastingCall castingCall) {
        this.castingCall = castingCall;
    }

    @ManyToOne
    @JoinColumn(name="casting_call_id" , referencedColumnName = "casting_call_id")
    @JsonIgnore
    private CastingCall castingCall;

    @ManyToOne
    @JoinColumn(name="user_id" )
    @JsonIgnore
    private User user;

    @Column(name="create_dt")
    private Timestamp createDate;

    public long getId() {
        return id;
    }

/*
    public long getCastingCallId() {
        return castingCallId;
    }

    public void setCastingCallId(long castingCallId) {
        this.castingCallId = castingCallId;
    }
*/

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
