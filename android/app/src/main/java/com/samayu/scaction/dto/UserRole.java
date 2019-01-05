package com.samayu.scaction.dto;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by NandhiniGovindasamy on 9/12/18.
 */

public class UserRole {

        private long id;

        private long userId;

        private ProfileType roleType;

        public ProfileType getRoleType() {
            return roleType;
        }

        public void setRoleType(ProfileType roleType) {
            this.roleType = roleType;
        }

      //  private Timestamp createDate;

        //private Timestamp updatedDate;

       private Date expirationDate;

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

//        public Timestamp getCreateDate() {
//            return createDate;
//        }
//
//        public void setCreateDate(Timestamp createDate) {
//            this.createDate = createDate;
//        }
//
//        public Timestamp getUpdatedDate() {
//            return updatedDate;
//        }
//
//        public void setUpdatedDate(Timestamp updatedDate) {
//            this.updatedDate = updatedDate;
//        }

        public Date getExpirationDate() {
            return expirationDate;
        }

        public void setExpirationDate(Date expirationDate) {
            this.expirationDate = expirationDate;
        }


}
