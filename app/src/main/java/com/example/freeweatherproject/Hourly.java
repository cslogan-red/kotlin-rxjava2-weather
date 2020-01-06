package com.example.freeweatherproject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Auto-generated serialization class from: http://www.jsonschema2pojo.org/
 */
public class Hourly {

    @SerializedName("temp")
    @Expose
    private String temp;
    @SerializedName("time")
    @Expose
    private int time;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("precip")
    @Expose
    private int precip;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getPrecip() {
        return precip;
    }

    public void setPrecip(int precip) {
        this.precip = precip;
    }

}