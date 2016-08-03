package com.munch.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.munch.android.data.APIManager;
import com.munch.android.data.Callback;

public class SplashActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        APIManager.initialize(getApplicationContext());
        Session.initialize(getApplicationContext());
        final Session session = Session.getInstance();

        // Verify login status
        session.restoreSession(new Callback<Boolean>() {
            @Override
            public void onResponse(Boolean success) {
                if (success && session.isActive()) {
                    // Logged in
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                    finish();
                } else {
                    // Not logged in
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }
}
