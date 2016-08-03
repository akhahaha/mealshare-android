package com.munch.android.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Meal model
 * Created by Alan on 7/30/2016.
 */
public class Meal {
    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm zzz";
    private static final SimpleDateFormat SDF = new SimpleDateFormat(DATE_PATTERN, Locale.US);

    private String id;
    private String hostID;
    private Restaurant restaurant;
    private String title;
    private Date timeBegin;
    private Date timeEnd;
    private Integer partyMax;
    private String description;

    public Meal() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHostID() {
        return hostID;
    }

    public void setHostID(String hostID) {
        this.hostID = hostID;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonIgnore
    public Date getTimeBegin() {
        return timeBegin;
    }

    @JsonIgnore
    public void setTimeBegin(Date timeBegin) {
        this.timeBegin = timeBegin;
    }

    @JsonProperty("timeBegin")
    public String getTimeStringBegin() {
        return SDF.format(timeBegin);
    }

    @JsonProperty("timeBegin")
    public void setTimeStringBegin(String timeBegin) {
        try {
            this.timeBegin = SDF.parse(timeBegin);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @JsonIgnore
    public Date getTimeEnd() {
        return timeEnd;
    }

    @JsonIgnore
    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    @JsonProperty("timeEnd")
    public String getTimeStringEnd() {
        return SDF.format(timeEnd);
    }

    @JsonProperty("timeEnd")
    public void setTimeStringEnd(String timeEnd) {
        try {
            this.timeEnd = SDF.parse(timeEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @JsonIgnore
    public Boolean isValidTime() {
        return timeBegin.getTime() < timeEnd.getTime();
    }

    public Integer getPartyMax() {
        return partyMax;
    }

    public void setPartyMax(Integer partyMax) {
        this.partyMax = partyMax;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Meal meal = (Meal) o;

        return id != null ? id.equals(meal.id) : meal.id == null;

    }

    @Override
    public String toString() {
        return "Meal{" +
                "id='" + id + '\'' +
                ", hostID='" + hostID + '\'' +
                ", restaurant=" + restaurant +
                ", title='" + title + '\'' +
                ", timeBegin=" + timeBegin +
                ", timeEnd=" + timeEnd +
                ", partyMax=" + partyMax +
                ", description='" + description + '\'' +
                '}';
    }
}
