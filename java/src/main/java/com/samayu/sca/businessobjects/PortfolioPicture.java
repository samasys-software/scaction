package com.samayu.sca.businessobjects;

import javax.persistence.*;

@Entity
@Table(name="scaction_portfolio")
public class PortfolioPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="portfolio_id")
    private long id;

    @Column(name="user_id")
    private long userId;

    @Column(name="extension")
    private String extension;

    @Column(name="active")
    private boolean active;

    @Column(name="filename")
    private String fileName;

    @Column(name="type")
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

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

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


}
