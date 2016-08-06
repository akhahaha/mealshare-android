package com.munch.android.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.munch.android.R;
import com.munch.android.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Restaurant autocomplete adapter
 * Created by Alan on 8/5/2016.
 */
public class RestaurantAutocompleteAdapter extends ArrayAdapter<Restaurant> {
    public RestaurantAutocompleteAdapter(Context context) {
        super(context, R.layout.listitem_meal);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            // Inflate the layout for the list item if necessary
            convertView = inflater.inflate(R.layout.autocompleteitem_restaurant, parent, false);
        }

        Restaurant restaurant = getItem(position);
        TextView nameText = (TextView) convertView.findViewById(R.id.restaurant_name);
        TextView addressText = (TextView) convertView.findViewById(R.id.restaurant_address);
        TextView neighborhoodText =
                (TextView) convertView.findViewById(R.id.restaurant_neighborhood);

        nameText.setText(restaurant.getName());
        addressText.setText(restaurant.getLocation().getAddress());
        neighborhoodText.setText(restaurant.getLocation().getNeighborhood());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return ((Restaurant) resultValue).getName();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (constraint != null) {
                    clear();
                    FilterResults filterResults = new FilterResults();
                    List<Restaurant> restaurants = new ArrayList<>();
                    for (int i = 0; i < getCount(); i++) {
                        restaurants.add(getItem(i));
                    }
                    filterResults.values = restaurants;
                    filterResults.count = getCount();
                    return filterResults;
                } else {
                    return new FilterResults();
                }
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    clear();
                    for (Restaurant restaurant : (List<Restaurant>) results.values) {
                        add(restaurant);
                        notifyDataSetChanged();
                    }
                }
            }
        };
    }
}
