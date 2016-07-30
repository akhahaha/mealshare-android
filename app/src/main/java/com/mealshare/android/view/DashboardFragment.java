package com.mealshare.android.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mealshare.android.R;
import com.mealshare.android.model.MealEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Dashboard fragment
 * A simple {@link ViewFragment} subclass.
 */
public class DashboardFragment extends ViewFragment {
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
        myMeals.add(event);
        event = new MealEvent();
        event.setId(1235);
        myMeals.add(event);

        MealEventArrayAdapter myMealsAdapter = new MealEventArrayAdapter(getContext(), myMeals);
        myMealsList.setAdapter(myMealsAdapter);

        return rootView;
    }
}
