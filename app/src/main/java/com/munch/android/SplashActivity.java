package com.munch.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.firebase.client.Firebase;
import com.munch.android.data.Callback;
import com.munch.android.data.Facebook;
import com.munch.android.model.User;

public class SplashActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Perform initialization
        FacebookSdk.sdkInitialize(getApplicationContext());
        Firebase.setAndroidContext(getApplicationContext());

        // Verify login status
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null) {
            // Not logged in
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        } else {
            // Logged in
            Facebook.getUser(accessToken, new Callback<User>() {
                @Override
                public void onResponse(User user) {
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                    finish();
                }

                @Override
                public void onFailed(Exception e) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            });
        }
    }
}
