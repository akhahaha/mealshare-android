package com.munch.android.util;

import com.munch.android.model.Meal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * MealList utility class
 * Created by Alan on 8/11/2016.
 */
public class MealList extends ArrayList<Meal> {
    private static final int LESS_THAN = -1;
    private static final int EQUAL_TO = 0;
    private static final int GREATER_THAN = 1;

    /**
     * Sorts meals by time from soonest to latest.
     */
    public void sortByTime() {
        Collections.sort(this, MealTimeComparator);
    }

    /**
     * Filters meals by time range.
     *
     * @param earliest Time range start
     * @param latest   Optional range time end
     */
    public void filterTime(Date earliest, Date latest) {
        MealList filteredMeals = new MealList();
        for (Meal meal : this) {
            if (meal.getTimeEnd().getTime() > earliest.getTime() &&
                    (latest == null || meal.getTimeEnd().getTime() < latest.getTime())) {
                filteredMeals.add(meal);
            }
        }

        clear();
        addAll(filteredMeals);
    }

    public void filterTimeBegin(Date earliest) {
        filterTime(earliest, null);
    }

    public static Comparator<Meal> MealTimeComparator = new Comparator<Meal>() {
        @Override
        public int compare(Meal lhs, Meal rhs) {
            if (lhs.getTimeBegin().getTime() < rhs.getTimeBegin().getTime()) {
                return LESS_THAN;
            } else if (lhs.getTimeBegin().getTime() == rhs.getTimeBegin().getTime()) {
                if (lhs.getTimeEnd().getTime() < rhs.getTimeEnd().getTime()) {
                    return LESS_THAN;
                } else if (lhs.getTimeEnd().getTime() == rhs.getTimeEnd().getTime()) {
                    return EQUAL_TO;
                } else {
                    return GREATER_THAN;
                }
            } else {
                return GREATER_THAN;
            }
        }
    };
}
