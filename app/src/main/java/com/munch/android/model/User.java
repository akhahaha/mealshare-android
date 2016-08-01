package com.munch.android.model;

/**
 * User model
 * Created by Alan on 7/30/2016.
 */
public class User {
    private String id; // FBID
    private String firstName;
    private String aboutMe;
    private String picture;

    public User() {
    }

    public User(String id, String firstName, String aboutMe, String picture) {
        this.id = id;
        this.firstName = firstName;
        this.aboutMe = aboutMe;
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", aboutMe='" + aboutMe + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
