package com.munch.android;

import android.content.Context;
import android.content.SharedPreferences;

import com.munch.android.data.APIManager;
import com.munch.android.data.Callback;
import com.munch.android.data.MunchDAO;
import com.munch.android.model.User;

/**
 * Session singleton
 * Created by Alan on 8/3/2016.
 * <p>
 * Requires initialize() to be performed at least once using an Android context.
 */
public class Session {
    private static Session ourInstance = new Session();

    public static Session getInstance() {
        if (!ourInstance.initialized) {
            new Exception("Session must be initialized with context before being used.")
                    .printStackTrace();
        }

        return ourInstance;
    }

    private Boolean initialized = false;
    private Context context;
    private SharedPreferences preferences;
    private User currentUser;

    private Session() {
    }

    public static void initialize(Context context) {
        APIManager.initialize(context);
        ourInstance.context = context;
        ourInstance.preferences = context.getSharedPreferences(context.getString(
                R.string.pref_file_name),
                Context.MODE_PRIVATE);
        ourInstance.initialized = true;
        ourInstance.restoreSession();
    }

    public void saveSession() {
        SharedPreferences.Editor editor = preferences.edit();
        if (currentUser != null) {
            editor.putString(context.getString(R.string.pref_key_curr_uid),
                    currentUser.getId()).apply();
        } else {
            editor.remove(context.getString(R.string.pref_key_curr_uid));
        }
    }

    public void restoreSession(final Callback<Boolean> callback) {
        String currentUserID = preferences.getString(context.getString(R.string.pref_key_curr_uid),
                null);
        if (currentUserID == null) {
            callback.onResponse(false);
            return;
        }

        MunchDAO.getUser(currentUserID, new Callback<User>() {
            @Override
            public void onResponse(User user) {
                if (user != null) {
                    currentUser = user;
                    if (callback != null) {
                        callback.onResponse(true);
                    }
                } else if (callback != null) {
                    callback.onResponse(false);
                }
            }

            @Override
            public void onFailed(Exception e) {
                if (callback != null) {
                    callback.onFailed(e);
                }
            }
        });
    }

    public void restoreSession() {
        restoreSession(null);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        saveSession();
    }

    public Boolean isActive() {
        return currentUser != null;
    }
}
