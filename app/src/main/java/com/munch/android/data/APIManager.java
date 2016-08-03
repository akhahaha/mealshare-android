package com.munch.android.data;

import android.content.Context;

import com.firebase.client.Firebase;
import com.munch.android.BuildConfig;
import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;

/**
 * API Manager singleton
 * Created by Alan on 7/31/2016.
 * <p/>
 * Requires initialize() to be performed at least once using an Android context.
 */
public class APIManager {
    private static APIManager ourInstance = new APIManager();

    public static APIManager getInstance() {
        if (!ourInstance.initialized) {
            new Exception("APIManager must be initialized with context before being used.")
                    .printStackTrace();
        }

        return ourInstance;
    }

    private Boolean initialized = false;
    private Firebase firebase;
    private YelpAPI yelpAPI;

    public static void initialize(Context context) {
        Firebase.setAndroidContext(context);
        ourInstance.firebase = new Firebase(BuildConfig.MUNCH_FIREBASE_ADDRESS);

        ourInstance.yelpAPI = new YelpAPIFactory(
                BuildConfig.YELP_CONSUMER_KEY,
                BuildConfig.YELP_CONSUMER_SECRET,
                BuildConfig.YELP_TOKEN,
                BuildConfig.YELP_TOKEN_SECRET)
                .createAPI();

        ourInstance.initialized = true;
    }

    private APIManager() {
    }

    public Firebase getFirebase() {
        return firebase;
    }

    public YelpAPI getYelpAPI() {
        return yelpAPI;
    }
}
