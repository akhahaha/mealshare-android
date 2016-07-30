package com.mealshare.android.event;

/**
 * Event base class
 * Created by Alan on 5/31/2016.
 */
public class Event {
    private String eventTag;

    public Event(String eventTag) {
        this.eventTag = eventTag;
    }

    public String getEventTag() {
        return eventTag;
    }

    public void setEventTag(String eventTag) {
        this.eventTag = eventTag;
    }
}
