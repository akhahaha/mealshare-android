package com.munch.android;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.munch.android.data.Callback;
import com.munch.android.data.MunchDAO;
import com.munch.android.model.Meal;
import com.munch.android.model.RSVP;
import com.munch.android.model.User;
import com.munch.android.util.UserList;
import com.munch.android.widget.AttendeeGridViewAdapter;
import com.munch.android.widget.CircleTransformation;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MealDetailsActivity extends AppCompatActivity {
    public static final String ARG_MEAL_ID = "ARG_MEAL_ID";

    public static final String ARG_MEAL_TITLE = "ARG_MEAL_TITLE";
    private static final String DATE_PATTERN = "EEE, MMM dd";
    private static final String TIME_PATTERN = "HH:mm a";
    private static final SimpleDateFormat DATE_FORMATTER =
            new SimpleDateFormat(DATE_PATTERN, Locale.US);
    private static final SimpleDateFormat TIME_FORMATTER =
            new SimpleDateFormat(TIME_PATTERN, Locale.US);

    private Session session;

    private Meal meal;
    private User host;
    private UserList rsvps;

    private AttendeeGridViewAdapter attendeeAdapter;

    private AppBarLayout appbarLayout;
    private MenuItem rsvpMenuItem;
    private MenuItem cancelRSVPMenuItem;
    private MenuItem editMenuItem;

    private TextView mealTitleText;
    private TextView hostNameText;
    private ImageView hostImage;
    private TextView restaurantNameText;
    private TextView restaurantAddressText;
    private TextView dateBeginText;
    private TextView timeBeginText;
    private TextView dateEndText;
    private TextView timeEndText;
    private TextView partyMaxText;
    private GridView attendeeGrid;
    private TextView mealDescriptionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            Session.initialize(getApplicationContext());
        }
        session = Session.getInstance();

        setContentView(R.layout.activity_meal_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(" ");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        appbarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mealTitleText = (TextView) findViewById(R.id.meal_title);
        hostNameText = (TextView) findViewById(R.id.host_name);
        hostImage = (ImageView) findViewById(R.id.host_picture);
        restaurantNameText = (TextView) findViewById(R.id.restaurant_name);
        restaurantAddressText = (TextView) findViewById(R.id.restaurant_address);
        dateBeginText = (TextView) findViewById(R.id.date_begin);
        timeBeginText = (TextView) findViewById(R.id.time_begin);
        dateEndText = (TextView) findViewById(R.id.date_end);
        timeEndText = (TextView) findViewById(R.id.time_end);
        partyMaxText = (TextView) findViewById(R.id.meal_party_max);
        attendeeGrid = (GridView) findViewById(R.id.attendee_grid);
        mealDescriptionText = (TextView) findViewById(R.id.meal_description);

        meal = new Meal();
        meal.setId(getIntent().getStringExtra(ARG_MEAL_ID));
        meal.setTitle(getIntent().getStringExtra(ARG_MEAL_TITLE));
        mealTitleText.setText(meal.getTitle());

        refreshMeal();

        appbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                // Fade title text
                float percentage = ((float)
                        (appBarLayout.getTotalScrollRange() - Math.abs(verticalOffset)) /
                        appBarLayout.getTotalScrollRange());
                mealTitleText.setAlpha(percentage);

                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0 && getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(meal.getTitle());
                    isShow = true;
                } else if (isShow && getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(" ");
                    isShow = false;
                }
            }
        });

        attendeeAdapter = new AttendeeGridViewAdapter(getBaseContext());
        attendeeGrid.setAdapter(attendeeAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meal_details, menu);
        rsvpMenuItem = menu.findItem(R.id.action_rsvp);
        cancelRSVPMenuItem = menu.findItem(R.id.action_rsvp_cancel);
        editMenuItem = menu.findItem(R.id.action_edit);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_rsvp:
                MunchDAO.addRSVP(new RSVP(meal.getId(), session.getCurrentUser().getId()),
                        new Callback<Boolean>() {
                            @Override
                            public void onResponse(Boolean response) {
                                refreshRSVPs();
                            }

                            @Override
                            public void onFailed(Exception e) {

                            }
                        });
                return true;
            case R.id.action_rsvp_cancel:
                MunchDAO.removeRSVP(new RSVP(meal.getId(), session.getCurrentUser().getId()));
                refreshRSVPs();
                return true;
            case R.id.action_edit:
                // TODO:
                refreshMeal();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void refreshMeal() {
        MunchDAO.getMeal(meal.getId(), new Callback<Meal>() {
            @Override
            public void onResponse(Meal meal) {
                MealDetailsActivity.this.meal = meal;
                MunchDAO.getUser(meal.getHostID(), new Callback<User>() {
                    @Override
                    public void onResponse(User user) {
                        host = user;
                        updateDisplay();
                        refreshRSVPs();
                    }

                    @Override
                    public void onFailed(Exception e) {

                    }
                });
            }

            @Override
            public void onFailed(Exception e) {
                e.printStackTrace(); // TODO
            }
        });
    }

    private void refreshRSVPs() {
        MunchDAO.getMealRSVPs(meal.getId(), new Callback<UserList>() {
            @Override
            public void onResponse(UserList users) {
                rsvps = users;
                attendeeAdapter.clear();
                attendeeAdapter.addAll(rsvps);
                attendeeAdapter.notifyDataSetChanged();
                updateDisplay();
            }

            @Override
            public void onFailed(Exception e) {
                e.printStackTrace(); // TODO
            }
        });
    }

    private void updateDisplay() {
        mealTitleText.setText(meal.getTitle());

        // Set menu items
        if (session.getCurrentUser().getId().equals(meal.getHostID())) {
            // Host
            rsvpMenuItem.setVisible(false);
            cancelRSVPMenuItem.setVisible(false);
            editMenuItem.setVisible(true);
        } else if (rsvps != null) {
            System.out.println(rsvps);
            if (rsvps.contains(session.getCurrentUser())) {
                // RSVPed
                rsvpMenuItem.setVisible(false);
                cancelRSVPMenuItem.setVisible(true);
                editMenuItem.setVisible(false);
            } else {
                // Not RSVP
                rsvpMenuItem.setVisible(true);
                cancelRSVPMenuItem.setVisible(false);
                editMenuItem.setVisible(false);
            }
        } else {
            rsvpMenuItem.setVisible(false);
            cancelRSVPMenuItem.setVisible(false);
            editMenuItem.setVisible(false);
        }

        if (session.getCurrentUser().getId().equals(meal.getHostID())) {
            // Host
            hostNameText.setText(getString(R.string.label_hosted_by_you));
        } else {
            // Attendee
            hostNameText.setText(String.format(getString(R.string.label_hosted_by),
                    host.getFirstName()));
        }
        Picasso.with(getBaseContext())
                .load(host.getPictureUrl())
                .transform(new CircleTransformation())
                .into(hostImage);

        restaurantNameText.setText(meal.getRestaurant().getName());
        restaurantAddressText.setText(meal.getRestaurant().getLocation().getDisplayAddress());
        dateBeginText.setText(DATE_FORMATTER.format(meal.getTimeBegin()));
        timeBeginText.setText(TIME_FORMATTER.format(meal.getTimeBegin()));
        dateEndText.setText(DATE_FORMATTER.format(meal.getTimeEnd()));
        timeEndText.setText(TIME_FORMATTER.format(meal.getTimeEnd()));
        partyMaxText.setText(String.valueOf(meal.getPartyMax()));
        mealDescriptionText.setText(meal.getDescription());
    }
}
