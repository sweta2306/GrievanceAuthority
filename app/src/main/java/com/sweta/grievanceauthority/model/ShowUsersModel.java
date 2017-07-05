package com.sweta.grievanceauthority.model;

/**
 * Created by 1406074 on 02-06-2017.
 */

public class ShowUsersModel {

    public String createdAt;
    public String displayName;
    public String email;
    public String uid;

    public ShowUsersModel() {
    }

    public ShowUsersModel(String createdAt, String displayName, String email, String uid) {
        this.createdAt = createdAt;
        this.displayName = displayName;
        this.email = email;
        this.uid = uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}