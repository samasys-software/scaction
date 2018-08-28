package com.samayu.sca.businessobjects;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="scaction_users")
public class UserRole {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idscaction_user_roles")
    private long id;

    @Column(name = "scaction_user_id")
    private long userId;

    @Column(name = "scaction_user_role_id")
    private int roleId;

    @Column(name = "create_dt")
    private LocalDateTime createDate;

    @Column(name = "update_dt")
    private LocalDateTime updatedDate;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
