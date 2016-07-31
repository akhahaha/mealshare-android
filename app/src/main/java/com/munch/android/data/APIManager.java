package com.munch.android.data;

import com.firebase.client.Firebase;
import com.munch.android.BuildConfig;
import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;

/**
 * API Manager singleton
 * Created by Alan on 7/31/2016.
 */
public class APIManager {
    private static APIManager ourInstance = new APIManager();

    public static APIManager getInstance() {
        return ourInstance;
    }

    private Firebase firebase;
    private YelpAPI yelpAPI;

    private APIManager() {
        firebase = new Firebase(BuildConfig.MUNCH_FIREBASE_ADDRESS);
        YelpAPIFactory apiFactory = new YelpAPIFactory(
                BuildConfig.YELP_CONSUMER_KEY,
                BuildConfig.YELP_CONSUMER_SECRET,
                BuildConfig.YELP_TOKEN,
                BuildConfig.YELP_TOKEN_SECRET);
        yelpAPI = apiFactory.createAPI();
    }

    public Firebase getFirebase() {
        return firebase;
    }

    public YelpAPI getYelpAPI() {
        return yelpAPI;
    }
}
