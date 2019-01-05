package com.samayu.scaction.dto;

/**
 * Created by NandhiniGovindasamy on 9/6/18.
 */

public class ProfileType {


    private int id;

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
    @Override
    public String toString() {
        return name;            // What to display in the Spinner list.
    }
}
