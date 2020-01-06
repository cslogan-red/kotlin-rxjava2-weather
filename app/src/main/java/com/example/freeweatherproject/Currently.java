package com.example.freeweatherproject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Auto-generated serialization class from: http://www.jsonschema2pojo.org/
 */
public class Currently {

    @SerializedName("temp")
    @Expose
    private String temp;
    @SerializedName("feelsLikeTemp")
    @Expose
    private String feelsLikeTemp;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("humidity")
    @Expose
    private int humidity;
    @SerializedName("windSpeed")
    @Expose
    private int windSpeed;
    @SerializedName("sunriseTime")
    @Expose
    private int sunriseTime;
    @SerializedName("sunsetTime")
    @Expose
    private int sunsetTime;
    @SerializedName("timezone")
    @Expose
    private String timezone;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getFeelsLikeTemp() {
        return feelsLikeTemp;
    }

    public void setFeelsLikeTemp(String feelsLikeTemp) {
        this.feelsLikeTemp = feelsLikeTemp;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getSunriseTime() {
        return sunriseTime;
    }

    public void setSunriseTime(int sunriseTime) {
        this.sunriseTime = sunriseTime;
    }

    public int getSunsetTime() {
        return sunsetTime;
    }

    public void setSunsetTime(int sunsetTime) {
        this.sunsetTime = sunsetTime;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

}