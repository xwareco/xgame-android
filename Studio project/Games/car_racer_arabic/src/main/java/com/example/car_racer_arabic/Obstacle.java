package com.example.car_racer_arabic;

import android.support.annotation.Keep;

@Keep
public class Obstacle {
    private int location;
    private String type;

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
