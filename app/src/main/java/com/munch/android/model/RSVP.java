package com.munch.android.model;

/**
 * RSVP model
 * Created by Alan on 8/2/2016.
 */
public class RSVP {
    private String mealID;
    private String userID;

    public RSVP() {
    }

    public String getMealID() {
        return mealID;
    }

    public void setMealID(String mealID) {
        this.mealID = mealID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public RSVP(String mealID, String userID) {
        this.mealID = mealID;
        this.userID = userID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RSVP rsvp = (RSVP) o;

        return mealID.equals(rsvp.mealID) && userID.equals(rsvp.userID);
    }

    @Override
    public int hashCode() {
        int result = mealID.hashCode();
        result = 31 * result + userID.hashCode();
        return result;
    }
}
