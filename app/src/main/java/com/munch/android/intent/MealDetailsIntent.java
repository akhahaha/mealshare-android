package com.munch.android.intent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.munch.android.MealDetailsActivity;
import com.munch.android.model.Meal;

/**
 * MealDetailsActivity launch intent
 * Created by Alan on 8/7/2016.
 */
@SuppressLint("ParcelCreator")
public class MealDetailsIntent extends Intent {
    public MealDetailsIntent(Context context) {
        super(context, MealDetailsActivity.class);
    }

    public MealDetailsIntent(Context context, Meal meal) {
        this(context);
        setMealID(meal.getId());
        setMealTitle(meal.getTitle());
    }

    public MealDetailsIntent setMealID(String mealID) {
        putExtra(MealDetailsActivity.ARG_MEAL_ID, mealID);
        return this;
    }

    public MealDetailsIntent setMealTitle(String mealTitle) {
        putExtra(MealDetailsActivity.ARG_MEAL_TITLE, mealTitle);
        return this;
    }
}
