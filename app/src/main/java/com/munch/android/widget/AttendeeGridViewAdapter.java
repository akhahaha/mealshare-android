package com.munch.android.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.munch.android.R;
import com.munch.android.model.User;
import com.munch.android.util.UserList;
import com.squareup.picasso.Picasso;

/**
 * Attendee grid view adapter
 * Created by Alan on 8/12/2016.
 */
public class AttendeeGridViewAdapter extends ArrayAdapter<User> {
    public AttendeeGridViewAdapter(Context context) {
        this(context, new UserList());
    }

    public AttendeeGridViewAdapter(Context context, UserList users) {
        super(context, R.layout.griditem_attendee, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            // Inflate the layout for the list item if necessary
            convertView = inflater.inflate(R.layout.griditem_attendee, parent, false);
        }

        User user = getItem(position);

        ImageView thumbnail = (ImageView) convertView.findViewById(R.id.attendee_thumbnail);
        Picasso.with(getContext())
                .load(user.getPictureUrl())
                .transform(new CircleTransformation())
                .into(thumbnail);

        return convertView;
    }
}
