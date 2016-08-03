package com.munch.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.munch.android.data.Callback;
import com.munch.android.data.Facebook;
import com.munch.android.data.MunchDAO;
import com.munch.android.model.User;

public class LoginActivity extends AppCompatActivity {
    private Session session;
    private CallbackManager callbackManager = CallbackManager.Factory.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = Session.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_login);

        // Configure Facebook login button
        LoginButton loginButton = (LoginButton) findViewById(R.id.fb_login_button);
        if (loginButton != null) {
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Facebook.getUser(loginResult.getAccessToken(), new Callback<User>() {
                        @Override
                        public void onResponse(final User fbUser) {
                            MunchDAO.getUser(fbUser.getId(), new Callback<User>() {
                                @Override
                                public void onResponse(final User munchUser) {
                                    if (munchUser != null) {
                                        // Pre-existing user
                                        session.setCurrentUser(munchUser);
                                        startActivity(new Intent(getApplicationContext(),
                                                DashboardActivity.class));
                                        finish();
                                    } else {
                                        // New user
                                        MunchDAO.createUser(fbUser, new Callback<Boolean>() {
                                            @Override
                                            public void onResponse(Boolean response) {
                                                session.setCurrentUser(fbUser);
                                                // TODO: Perform onboarding
                                                startActivity(new Intent(getApplicationContext(),
                                                        DashboardActivity.class));
                                                finish();
                                            }

                                            @Override
                                            public void onFailed(Exception e) {

                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onFailed(Exception e) {

                                }
                            });
                        }

                        @Override
                        public void onFailed(Exception e) {

                        }
                    });
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {

                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data); // Notify Facebook callback
    }
}
