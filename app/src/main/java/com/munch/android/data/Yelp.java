package com.munch.android.data;

import com.munch.android.model.Coordinates;
import com.munch.android.model.Restaurant;
import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;
import com.yelp.clientlib.entities.options.CoordinateOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Yelp API wrapper
 * Created by Alan on 8/2/2016.
 */
public class Yelp {
    private static YelpAPI yelpAPI = APIManager.getInstance().getYelpAPI();

    public static void getRestaurant(String businessID, final Callback<Restaurant> callback) {
        yelpAPI.getBusiness(businessID).enqueue(new retrofit2.Callback<Business>() {
            @Override
            public void onResponse(Call<Business> call, Response<Business> response) {
                callback.onResponse(new Restaurant(response.body()));
            }

            @Override
            public void onFailure(Call<Business> call, Throwable t) {
                t.printStackTrace();
                callback.onFailed(new Exception());
            }
        });
    }

    public static void searchRestaurants(String term, final Coordinates coordinates,
                                         int numResults,
                                         final Callback<List<Restaurant>> callback) {
        CoordinateOptions coordinateOptions = CoordinateOptions.builder()
                .latitude(coordinates.getLatitude())
                .longitude(coordinates.getLongitude()).build();

        Map<String, String> params = new HashMap<>();
        params.put("term", term);
        //params.put("category_filter", "Restaurants"); // TODO: Filter for restaurants only
        params.put("limit", String.valueOf(numResults));

        yelpAPI.search(coordinateOptions, params).enqueue(new retrofit2.Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse searchResponse = response.body();
                List<Restaurant> restaurants = new ArrayList<>();
                for (Business business : searchResponse.businesses()) {
                    restaurants.add(new Restaurant(business));
                }
                callback.onResponse(restaurants);
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                t.printStackTrace();
                callback.onFailed(new Exception());
            }
        });
    }
}
