package com.munch.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.FacebookSdk;
import com.firebase.client.Firebase;

public class SplashActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Perform initialization
        FacebookSdk.sdkInitialize(getApplicationContext());
        Firebase.setAndroidContext(getApplicationContext());

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
