package com.mealshare.android.model;

/**
 * User model
 * Created by Alan on 7/30/2016.
 */
public class User {
    private String id; // FBID
    private String aboutMe;

    public User(String id, String aboutMe) {
        this.id = id;
        this.aboutMe = aboutMe;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }
}
