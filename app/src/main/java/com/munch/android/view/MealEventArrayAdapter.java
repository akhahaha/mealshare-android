package com.munch.android.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.munch.android.R;
import com.munch.android.model.MealEvent;
import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Meal event array adapter
 * Created by Alan on 7/30/2016.
 */
public class MealEventArrayAdapter extends BaseAdapter {
    private Context context;
    private List<MealEvent> events;

    public MealEventArrayAdapter(Context context, List<MealEvent> events) {
        this.context = context;
        this.events = events;
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public MealEvent getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return events.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            // Inflate the layout for the list item if necessary
            convertView = inflater.inflate(R.layout.listitem_meal_event, parent, false);
        }

        MealEvent event = getItem(position);
        final TextView restaurantNameView =
                (TextView) convertView.findViewById(R.id.restaurant_name);
        final ImageView thumbnailView = (ImageView) convertView.findViewById(R.id.thumbnail);

        YelpAPIFactory apiFactory = new YelpAPIFactory(
                consumerKey,
                consumerSecret,
                token,
                tokenSecret);
        YelpAPI yelpAPI = apiFactory.createAPI();
        System.out.println("derp");
        yelpAPI.getBusiness(event.getRestaurantID()).enqueue(new Callback<Business>() {
            @Override
            public void onResponse(Call<Business> call, Response<Business> response) {
                restaurantNameView.setText(response.body().name());
                // TODO: Display image
            }

            @Override
            public void onFailure(Call<Business> call, Throwable t) {

            }
        });

        // TODO: Display pending time

        return convertView;
    }
}
