package com.munch.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.munch.android.data.Callback;
import com.munch.android.data.MunchDAO;
import com.munch.android.intent.MealDetailsIntent;
import com.munch.android.util.MealList;
import com.munch.android.widget.MealListAdapter;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int REQUEST_MEAL_CREATE = 1;
    private static final int REQUEST_MEAL_DETAILS = 2;
    private static final int REQUEST_MEAL_SEARCH = 3;

    private Session session;

    private MealListAdapter upcomingMealListAdapter;

    private ListView upcomingMealsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = Session.getInstance();

        setContentView(R.layout.activity_dashboard);

        // Get views
        upcomingMealsList = (ListView) findViewById(R.id.upcoming_meals_list);

        // Initialize upcoming meals list
        upcomingMealListAdapter = new MealListAdapter(getBaseContext());
        upcomingMealsList.setAdapter(upcomingMealListAdapter);
        upcomingMealsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivityForResult(new MealDetailsIntent(getApplicationContext(),
                        upcomingMealListAdapter.getItem(position)), REQUEST_MEAL_DETAILS);
            }
        });

        updateDisplay();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton mealCreateFab =
                (FloatingActionButton) findViewById(R.id.meal_create_fab);
        mealCreateFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        new Intent(getApplicationContext(), MealCreateActivity.class),
                        REQUEST_MEAL_CREATE);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                startActivityForResult(
                        new Intent(getApplicationContext(), MealSearchActivity.class),
                        REQUEST_MEAL_CREATE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updateDisplay() {
        // Display upcoming meals
        MunchDAO.getUserUpcomingMeals(session.getCurrentUser().getId(), new Callback<MealList>() {
            @Override
            public void onResponse(MealList meals) {
                upcomingMealListAdapter.clear();
                upcomingMealListAdapter.addAll(meals);
                upcomingMealListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_MEAL_CREATE:
            case REQUEST_MEAL_DETAILS:
                if (resultCode == Activity.RESULT_OK) {
                    updateDisplay();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
