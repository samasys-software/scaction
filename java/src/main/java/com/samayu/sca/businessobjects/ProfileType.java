package com.samayu.sca.businessobjects;

import javax.persistence.*;

@Entity
@Table(name="scaction_profile_types")
public class ProfileType {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idscaction_profile_types")
    private int id;

    @Column(name="name")
    private String name;

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




}
