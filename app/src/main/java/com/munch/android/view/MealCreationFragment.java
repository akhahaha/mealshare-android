package com.munch.android.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.munch.android.R;

/**
 * A simple {@link ViewFragment} subclass.
 * Use the {@link MealCreationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MealCreationFragment extends ViewFragment {
    private View rootView;

    public MealCreationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MealCreationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MealCreationFragment newInstance() {
        MealCreationFragment fragment = new MealCreationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_meal_creation, container, false);

        return rootView;
    }
}
