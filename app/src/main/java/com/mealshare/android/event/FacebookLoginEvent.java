package com.mealshare.android.event;

import com.facebook.AccessToken;

/**
 * Facebook login event
 * Created by Alan on 7/30/2016.
 */
public class FacebookLoginEvent extends Event {
    AccessToken accessToken;

    public FacebookLoginEvent(String eventTag, AccessToken accessToken) {
        super(eventTag);
        this.accessToken = accessToken;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }
}
