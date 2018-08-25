package com.samayu.sca.businessobjects;

import javax.persistence.*;

@Entity
@Table(name="scaction_countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idscaction_countries")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="code")
    private String code;

    @Column(name="isd_code")
    private String isdCode;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIsdCode() {
        return isdCode;
    }

    public void setIsdCode(String isdCode) {
        this.isdCode = isdCode;
    }
}
