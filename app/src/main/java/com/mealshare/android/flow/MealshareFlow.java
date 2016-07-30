package com.mealshare.android.flow;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.Firebase;
import com.mealshare.android.MainActivity;
import com.mealshare.android.R;
import com.mealshare.android.model.User;

import org.json.JSONObject;
/**
 * Mealshare flow controller
 * Created by Alan on 7/30/2016.
 */
public class MealshareFlow extends FlowController {
    private String currUID;
    private AccessToken accessToken;
    private boolean success = false;
    private String username;
    private String fbID;
    private Firebase ref;
    public LoginButton loginButton;

    public MealshareFlow(FragmentActivity activity, int resourceID) {
        super(activity, resourceID);
        ref = new Firebase("https://luminous-fire-3016.firebaseio.com");


        // FB LOGIN LISTENER
        MainActivity.callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) getActivity().findViewById(R.id.login_button);
        loginButton.registerCallback(MainActivity.callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                final AccessToken accessToken = loginResult.getAccessToken();
                Log.i("ID/Auth Token", "User ID: " +
                        loginResult.getAccessToken().getUserId() +
                        "\n " +
                        "Auth Token: " +
                        loginResult.getAccessToken().getToken());
                success = true;
                GraphRequestAsyncTask request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject user, GraphResponse graphResponse) {
                        User newUser = new User((user.optString("name")), user.optString("id")) ;
                        ref.setValue(newUser);

                    }
                }).executeAsync();


            }

            @Override
            public void onCancel() {
//                info.setText("Login Attempted Cancelled");
            }

            @Override
            public void onError(FacebookException error) {
//                info.setText("Login Attempt failed.");
            }
        });


        // Initialize user session and preferences
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        currUID = settings.getString(getActivity().getString(R.string.pref_curr_uid), null);
        if (currUID == null) {
            currUID = "abc"; // TODO: Implement Facebook login
            settings.edit().putString(getActivity().getString(R.string.pref_curr_uid), currUID)
                    .apply();
            // TODO: Start onboarding
        } else {
//            loginButton.setVisibility(View.INVISIBLE);
        }

        // Set default presenter
        setPresenter(new DashboardPresenter(currUID));
    }

}
