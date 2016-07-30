package com.munch.android.model;

import java.util.Date;

/**
 * Meal event model
 * Created by Alan on 7/30/2016.
 */
public class MealEvent {
    private Integer id;
    private String hostID;
    private String restaurantID; // Yelp ID
    private Date start;
    private Date end;
    private Integer priceTier;
    private Integer partyMin;
    private Integer partyMax;

    public MealEvent() {
    }

    public MealEvent(Integer id, String hostID, String restaurantID, Date start, Date end,
                     Integer priceTier, Integer partyMin, Integer partyMax) {
        this.id = id;
        this.hostID = hostID;
        this.restaurantID = restaurantID;
        this.start = start;
        this.end = end;
        this.priceTier = priceTier;
        this.partyMin = partyMin;
        this.partyMax = partyMax;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHostID() {
        return hostID;
    }

    public void setHostID(String hostID) {
        this.hostID = hostID;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Integer getPriceTier() {
        return priceTier;
    }

    public void setPriceTier(Integer priceTier) {
        this.priceTier = priceTier;
    }

    public Integer getPartyMin() {
        return partyMin;
    }

    public void setPartyMin(Integer partyMin) {
        this.partyMin = partyMin;
    }

    public Integer getPartyMax() {
        return partyMax;
    }

    public void setPartyMax(Integer partyMax) {
        this.partyMax = partyMax;
    }
}
