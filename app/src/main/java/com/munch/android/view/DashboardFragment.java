package com.munch.android.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.munch.android.R;
import com.munch.android.event.Event;
import com.munch.android.event.MealSelectEvent;
import com.munch.android.model.MealEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Dashboard fragment
 * A simple {@link ViewFragment} subclass.
 */
public class DashboardFragment extends ViewFragment {
    public static final String EVENT_CREATE_MEAL = "EVENT_CREATE_MEAL";
    public static final String EVENT_MEAL_SELECTED = "EVENT_MEAL_SELECTED";

    private static final String ARG_CURR_UID = "ARG_CURR_UID";

    private String currUID;

    private View rootView;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment DashboardFragment.
     */
    public static DashboardFragment newInstance(String currUID) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CURR_UID, currUID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currUID = getArguments().getString(ARG_CURR_UID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        final ListView myMealsList =
                (ListView) rootView.findViewById(R.id.dashboard_my_meals_list);

        // TODO: Populate MyMeals
        List<MealEvent> myMeals = new ArrayList<>();
        MealEvent event = new MealEvent();
        event.setId(1234);
        event.setRestaurantID("yelp-san-francisco");
        myMeals.add(event);
        event = new MealEvent();
        event.setId(1235);
        event.setRestaurantID("urban-curry-san-francisco");
        myMeals.add(event);

        final MealEventArrayAdapter myMealsAdapter = new MealEventArrayAdapter(getContext(), myMeals);
        myMealsList.setAdapter(myMealsAdapter);
        myMealsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                notifyListener(new MealSelectEvent(EVENT_MEAL_SELECTED,
                        myMealsAdapter.getItem(position).getId()));
            }
        });

        FloatingActionButton fab =
                (FloatingActionButton) rootView.findViewById(R.id.dashboard_fab_create_meal);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyListener(new Event(EVENT_CREATE_MEAL));
            }
        });


        return rootView;
    }
}
