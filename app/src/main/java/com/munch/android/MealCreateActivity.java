package com.munch.android;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.munch.android.data.Callback;
import com.munch.android.data.MunchDAO;
import com.munch.android.data.Yelp;
import com.munch.android.model.Coordinates;
import com.munch.android.model.Meal;
import com.munch.android.model.Restaurant;
import com.munch.android.widget.RestaurantAutocompleteAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MealCreateActivity extends AppCompatActivity {
    private static final String DATE_PATTERN = "EEE, MMM dd";
    private static final String TIME_PATTERN = "HH:mm a";
    private static final SimpleDateFormat DATE_FORMATTER =
            new SimpleDateFormat(DATE_PATTERN, Locale.US);
    private static final SimpleDateFormat TIME_FORMATTER =
            new SimpleDateFormat(TIME_PATTERN, Locale.US);

    private Session session;

    private Meal meal;

    private EditText titleField;
    private AutoCompleteTextView restaurantField;
    private TextView restaurantAddressText;
    private TextView dateBeginText;
    private TextView timeBeginText;
    private TextView dateEndText;
    private TextView timeEndText;
    private EditText partyMaxField;
    private EditText descriptionField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = Session.getInstance();

        // Initialize meal
        meal = new Meal();
        meal.setHostID(session.getCurrentUser().getId());
        // TODO: Default to next meal period
        Calendar currentCal = Calendar.getInstance();
        currentCal.add(Calendar.HOUR_OF_DAY, 1);
        meal.setTimeBegin(currentCal.getTime());
        currentCal.add(Calendar.HOUR_OF_DAY, 1);
        meal.setTimeEnd(currentCal.getTime());

        setContentView(R.layout.activity_meal_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Get views
        titleField = (EditText) findViewById(R.id.meal_title_field);
        restaurantField = (AutoCompleteTextView) findViewById(R.id.field_restaurant);
        restaurantAddressText = (TextView) findViewById(R.id.restaurant_address);
        dateBeginText = (TextView) findViewById(R.id.date_begin);
        timeBeginText = (TextView) findViewById(R.id.time_begin);
        dateEndText = (TextView) findViewById(R.id.date_end);
        timeEndText = (TextView) findViewById(R.id.time_end);
        partyMaxField = (EditText) findViewById(R.id.meal_party_max_field);
        descriptionField = (EditText) findViewById(R.id.meal_description_field);
        updateDisplay();

        // Initialize title
        // TODO: Generate default title

        // Initialize restaurant field
        final RestaurantAutocompleteAdapter restaurantAdapter =
                new RestaurantAutocompleteAdapter(MealCreateActivity.this);
        restaurantField.setAdapter(restaurantAdapter);
        restaurantField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                meal.setRestaurant(null);
                updateDisplay();
                Yelp.searchRestaurants(s.toString(), new Coordinates(34.0522, -118.2437), 3,
                        new Callback<List<Restaurant>>() {
                            @Override
                            public void onResponse(List<Restaurant> restaurants) {
                                restaurantAdapter.clear();
                                restaurantAdapter.addAll(restaurants);
                                restaurantAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailed(Exception e) {
                                e.printStackTrace();
                            }
                        });
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateDisplay();
            }
        });
        restaurantField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                meal.setRestaurant(restaurantAdapter.getItem(position));
                updateDisplay();
            }
        });

        // Initialize time fields
        dateBeginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                c.setTime(meal.getTimeBegin());
                DatePickerDialog datePickerDialog = new DatePickerDialog(MealCreateActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                c.set(year, monthOfYear, dayOfMonth);
                                meal.setTimeBegin(c.getTime());
                                updateDisplay();
                            }
                        },
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        timeBeginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                c.setTime(meal.getTimeBegin());
                TimePickerDialog timePickerDialog = new TimePickerDialog(MealCreateActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                c.set(Calendar.MINUTE, minute);
                                meal.setTimeBegin(c.getTime());
                                updateDisplay();
                            }
                        },
                        c.get(Calendar.HOUR_OF_DAY),
                        c.get(Calendar.MINUTE),
                        false);
                timePickerDialog.show();
            }
        });
        dateEndText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                c.setTime(meal.getTimeEnd());
                DatePickerDialog datePickerDialog = new DatePickerDialog(MealCreateActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                c.set(year, monthOfYear, dayOfMonth);
                                meal.setTimeEnd(c.getTime());
                                updateDisplay();
                            }
                        },
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        timeEndText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                c.setTime(meal.getTimeEnd());
                TimePickerDialog timePickerDialog = new TimePickerDialog(MealCreateActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                c.set(Calendar.MINUTE, minute);
                                meal.setTimeEnd(c.getTime());
                                updateDisplay();
                            }
                        },
                        c.get(Calendar.HOUR_OF_DAY),
                        c.get(Calendar.MINUTE),
                        false);
                timePickerDialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meal_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create:
                // TODO: Validate meal
                if (meal.isValidTime() && meal.getRestaurant() != null) {
                    meal.setTitle(titleField.getText().toString());
                    meal.setPartyMax(Integer.valueOf(partyMaxField.getText().toString()));
                    meal.setDescription(descriptionField.getText().toString());
                    MunchDAO.pushMeal(meal, new Callback<String>() {
                        @Override
                        public void onResponse(String mealID) {
                            if (getParent() != null) {
                                getParent().setResult(Activity.RESULT_OK);
                            }
                            setResult(Activity.RESULT_OK);
                            finish();
                        }

                        @Override
                        public void onFailed(Exception e) {
                            e.printStackTrace(); // TODO
                        }
                    });
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateDisplay() {
        dateBeginText.setText(DATE_FORMATTER.format(meal.getTimeBegin()));
        timeBeginText.setText(TIME_FORMATTER.format(meal.getTimeBegin()));
        dateEndText.setText(DATE_FORMATTER.format(meal.getTimeEnd()));
        timeEndText.setText(TIME_FORMATTER.format(meal.getTimeEnd()));
        if (meal.isValidTime()) {
            setTimeTextColor(Color.BLACK);
        } else {
            setTimeTextColor(Color.RED);
        }

        if (meal.getRestaurant() != null) {
            Restaurant restaurant = meal.getRestaurant();
            restaurantAddressText.setText(restaurant.getLocation().getDisplayAddress());
            restaurantAddressText.setTextColor(Color.BLACK);
            restaurantAddressText.setVisibility(View.VISIBLE);
            // TODO: Apply restaurant image
        } else if (restaurantField.getText().length() > 0) {
            restaurantAddressText.setText(getString(R.string.hint_select_restaurant));
            restaurantAddressText.setTextColor(Color.RED);
        } else {
            restaurantAddressText.setVisibility(View.GONE);
        }
    }

    private void setTimeTextColor(int color) {
        dateBeginText.setTextColor(color);
        timeBeginText.setTextColor(color);
        dateEndText.setTextColor(color);
        timeEndText.setTextColor(color);
    }
}
