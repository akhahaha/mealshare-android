package com.munch.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.firebase.client.Firebase;
import com.munch.android.data.FacebookDAO;
import com.munch.android.data.UserQueryCallback;
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
            FacebookDAO.getInstance().generateMunchUser(accessToken, new UserQueryCallback() {
                @Override
                public void onCompleted(User user) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }

                @Override
                public void onFailed() {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            });
        }
    }
}
