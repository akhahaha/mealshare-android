package com.munch.android.data;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.munch.android.model.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Facebook API wrapper
 * Created by Alan on 7/31/2016.
 */
public class Facebook {
    /**
     * Gets a Munch User from a Facebook access token
     *
     * @param accessToken User's Facebook access token
     */
    public static void getUser(AccessToken accessToken, final Callback<User> callback) {
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject data, GraphResponse graphResponse) {
                        try {
                            User user = new User();
                            user.setId(data.getString("id"));
                            user.setFirstName(data.getString("first_name"));
                            user.setPictureUrl(data.getJSONObject("picture").getJSONObject("data")
                                    .getString("url"));
                            callback.onResponse(user);
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
