package com.munch.android.data;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.munch.android.model.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Facebook data access object
 * Created by Alan on 7/31/2016.
 */
public class FacebookDAO {
    private static FacebookDAO ourInstance = new FacebookDAO();

    public static FacebookDAO getInstance() {
        return ourInstance;
    }

    private FacebookDAO() {
    }

    /**
     * Gets a Munch User from a Facebook access token
     *
     * @param accessToken User's Facebook access token
     */
    public void generateMunchUser(AccessToken accessToken, final UserQueryCallback callback) {
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject data, GraphResponse graphResponse) {
                        try {
                            User user = new User();
                            user.setId(data.getString("id"));
                            user.setFirstName(data.getString("first_name"));
                            user.setPicture(data.getJSONObject("picture").getJSONObject("data")
                                    .getString("url"));
                            callback.onCompleted(user);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id , first_name, picture");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
