package com.munch.android.data;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.firebase.client.Firebase;
import com.munch.android.model.User;

import org.json.JSONObject;

/**
 * Data access object singleton
 * Created by Alan on 7/30/2016.
 */
public class DAO {
    private static DAO ourInstance = new DAO();

    public static DAO getInstance() {
        return ourInstance;
    }

    private Firebase firebase = APIManager.getInstance().getFirebase();

    private DAO() {
    }

    /**
     * Find User by user ID.
     *
     * @param uid User ID
     */
    public User getUser(String uid) {
        return null; // TODO
    }

    /**
     * Inserts or creates a User from Facebook.
     *
     * @param accessToken Facebook access token
     */
    public void upsertFacebookUser(AccessToken accessToken) {
        GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject fbUser, GraphResponse graphResponse) {
                User user = new User((fbUser.optString("name")), fbUser.optString("id"));
                firebase.setValue(user);
            }
        }).executeAsync();
    }
}
