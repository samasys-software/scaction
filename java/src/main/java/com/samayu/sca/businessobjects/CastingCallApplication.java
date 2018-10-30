package com.samayu.sca.businessobjects;

import javax.persistence.*;

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

    @ManyToOne
    @JoinColumn(name="user_id" )
    private User user;

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