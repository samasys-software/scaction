package com.samayu.sca.businessobjects;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="scaction_user_roles")
public class UserRole {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    private long id;

    @Column(name = "scaction_user_id")
    private long userId;

    @ManyToOne
    @JoinColumn(name="scaction_user_role_id" , referencedColumnName = "idscaction_profile_types")
    private ProfileType roleType;

    public ProfileType getRoleType() {
        return roleType;
    }

    public void setRoleType(ProfileType roleType) {
        this.roleType = roleType;
    }

    @Column(name = "create_dt")
    private Timestamp createDate;

    @Column(name = "update_dt")
    private Timestamp updatedDate;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "active")
    private boolean active;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
