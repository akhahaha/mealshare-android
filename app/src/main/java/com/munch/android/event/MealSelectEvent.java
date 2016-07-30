package com.munch.android.event;

/**
 * Meal select event
 * Created by Alan on 7/30/2016.
 */
public class MealSelectEvent extends Event {
    private Integer eventID;

    public MealSelectEvent(String eventTag, Integer eventID) {
        super(eventTag);
        this.eventID = eventID;
    }

    public Integer getEventID() {
        return eventID;
    }

    public void setEventID(Integer eventID) {
        this.eventID = eventID;
    }
}
