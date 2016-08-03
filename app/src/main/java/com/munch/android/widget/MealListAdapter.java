package com.munch.android.widget;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.munch.android.R;
import com.munch.android.Session;
import com.munch.android.model.Meal;
import com.munch.android.util.MealList;
import com.squareup.picasso.Picasso;

/**
 * Meal list array adapter
 * Created by Alan on 7/30/2016.
 */
public class MealListAdapter extends ArrayAdapter<Meal> {
    private Session session;

    public MealListAdapter(Context context) {
        this(context, new MealList());
    }

    public MealListAdapter(Context context, MealList meals) {
        super(context, R.layout.listitem_meal, meals);
        this.session = Session.getInstance();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            // Inflate the layout for the list item if necessary
            convertView = inflater.inflate(R.layout.listitem_meal, parent, false);
        }

        Meal meal = getItem(position);

        TextView titleText = (TextView) convertView.findViewById(R.id.meal_title);
        titleText.setText(meal.getTitle());

        TextView nameText = (TextView) convertView.findViewById(R.id.restaurant_name);
        nameText.setText(meal.getRestaurant().getName());

        ImageView thumbnailImage = (ImageView) convertView.findViewById(R.id.restaurant_thumbnail);
        Picasso.with(getContext())
                .load(meal.getRestaurant().getImageUrl())
                .into(thumbnailImage);

        TextView locationText = (TextView) convertView.findViewById(R.id.restaurant_location);
        locationText.setText(meal.getRestaurant().getLocation().getNeighborhood());

        TextView relativeTimeText = (TextView) convertView.findViewById(R.id.meal_relative_time);
        long timeBegin = meal.getTimeBegin().getTime();
        long timeEnd = meal.getTimeEnd().getTime();
        long currentTime = System.currentTimeMillis();
        if (timeBegin >= currentTime) {
            relativeTimeText.setText(DateUtils.getRelativeTimeSpanString(timeBegin, currentTime,
                    DateUtils.MINUTE_IN_MILLIS));
        } else if (timeEnd >= currentTime) {
            relativeTimeText.setText(R.string.label_relative_time_now);
        }

        TextView hostText = (TextView) convertView.findViewById(R.id.host_indicator);
        hostText.setVisibility(meal.getHostID().equals(session.getCurrentUser().getId()) ?
                View.VISIBLE : View.GONE);

        return convertView;
    }
}
