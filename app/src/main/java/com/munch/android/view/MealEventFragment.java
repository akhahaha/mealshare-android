package com.munch.android.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.munch.android.R;
import com.munch.android.model.MealEvent;

/**
 * A simple {@link ViewFragment} subclass.
 * Use the {@link MealEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MealEventFragment extends ViewFragment {
    private static final String ARG_CURR_UID = "ARG_CURR_UID";
    private static final String ARG_EVENT_ID = "ARG_EVENT_ID";

    private String currUID;
    private Integer eventID;
    private MealEvent event;

    private View rootView;

    public MealEventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MealEventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MealEventFragment newInstance(String currUID, Integer eventID) {
        MealEventFragment fragment = new MealEventFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_EVENT_ID, eventID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currUID = getArguments().getString(ARG_CURR_UID);
            eventID = getArguments().getInt(ARG_EVENT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_meal_event, container, false);

        // TODO: Populate event information

        return rootView;
    }
}
