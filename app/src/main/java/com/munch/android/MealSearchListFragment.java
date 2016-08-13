package com.munch.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.munch.android.data.Callback;
import com.munch.android.data.MunchDAO;
import com.munch.android.intent.MealDetailsIntent;
import com.munch.android.util.MealList;
import com.munch.android.widget.MealListAdapter;

public class MealSearchListFragment extends Fragment {
    private static final int REQUEST_MEAL_DETAILS = 0;

    private final Session session = Session.getInstance();

    private MealList userMeals;

    private MealListAdapter searchResultsAdapter;

    private View rootView;
    private ListView searchResultsList;

    public MealSearchListFragment() {
        // Required empty public constructor
    }

    public static MealSearchListFragment newInstance() {
        MealSearchListFragment fragment = new MealSearchListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_meal_search_list, container, false);
        searchResultsList = (ListView) rootView.findViewById(R.id.search_results_list);

        searchResultsAdapter = new MealListAdapter(getContext());
        searchResultsList.setAdapter(searchResultsAdapter);
        searchResultsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivityForResult(new MealDetailsIntent(getContext(),
                        searchResultsAdapter.getItem(position)), REQUEST_MEAL_DETAILS);
            }
        });

        MunchDAO.getUserMeals(session.getCurrentUser().getId(), new Callback<MealList>() {
            @Override
            public void onResponse(MealList meals) {
                userMeals = meals;
                MunchDAO.searchUpcomingMeals(new Callback<MealList>() {
                    @Override
                    public void onResponse(MealList meals) {
                        meals.removeAll(userMeals); // Hide meals the user is already part of
                        searchResultsAdapter.clear();
                        searchResultsAdapter.addAll(meals);
                        searchResultsAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailed(Exception e) {
                        e.printStackTrace(); // TODO
                    }
                });
            }

            @Override
            public void onFailed(Exception e) {
                e.printStackTrace(); // TODO
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
