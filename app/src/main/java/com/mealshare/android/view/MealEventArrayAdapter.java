package com.mealshare.android.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mealshare.android.R;
import com.mealshare.android.model.MealEvent;

import java.util.List;

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
        final TextView placeholderView = (TextView) convertView.findViewById(R.id.placeholder);
        placeholderView.setText(event.getId().toString());

        return convertView;
    }
}