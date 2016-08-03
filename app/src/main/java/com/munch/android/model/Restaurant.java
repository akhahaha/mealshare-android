package com.munch.android.model;

import com.yelp.clientlib.entities.Business;

/**
 * Restaurant model
 * Created by Alan on 8/2/2016.
 */
public class Restaurant {
    private String id;
    private String name;
    private String imageUrl;
    private Location location;

    public Restaurant() {
    }

    public Restaurant(Business business) {
        id = business.id();
        name = business.name();
        imageUrl = business.imageUrl();
        location = new Location(business.location());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", location=" + location +
                '}';
    }
}
