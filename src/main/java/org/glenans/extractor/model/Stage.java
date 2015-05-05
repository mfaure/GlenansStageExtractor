/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glenans.extractor.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/**
 *
 * @author mfaure
 */
public class Stage implements Serializable {

    public static int DURATION_INDEX = 0;
    public static int FROM_DATE_INDEX = 1;
    public static int TO_DATE_INDEX = 2;
    public static int HOSTING_INDEX = 3;
    public static int FLEET_INDEX = 4;
    public static int LANGUAGE_INDEX = 5;
    public static int AVAILABILITY_INDEX = 6;
    public static int FOOD_INDEX = 7;
    public static int PRICE_INDEX = 8;
    
    private final String name;
    public String getName() {
        return name;
    }
    
    private String city;
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    
    private float duration;
    public float getDuration() {
        return duration;
    }
    
    private String fromDate;
    public String getFromDate() {
        return fromDate;
    }
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    private String toDate;
    public String getToDate() {
        return toDate;
    }
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
    
    private String hosting;
    public String getHosting() {
        return hosting;
    }

    private String fleet;
    public String getFleet() {
        return fleet;
    }
 
    private String language;
    public String getLanguage() {
        return language;
    }
   
    private String availibility; // create an ENUM with the 3 known states
    public String getAvailibility() {
        return availibility;
    }

    private String food; // create an ENUM with the 3 known states
    public String getFood() {
        return food;
    }
    
    private String price; 
    public String getPrice() {
        return price;
    }
    
    @JsonCreator
    public Stage(
            @JsonProperty("name") String name,
            @JsonProperty("city") String city,
            @JsonProperty("duration") String duration, 
            @JsonProperty("fromDate") String fromDate, 
            @JsonProperty("toDate") String toDate,
            @JsonProperty("hosting") String hosting, 
            @JsonProperty("fleet") String fleet, 
            @JsonProperty("language") String language, 
            @JsonProperty("availibility") String availibility, 
            @JsonProperty("food") String food, 
            @JsonProperty("price") String price) {
        this.name = normalizeString(name);
        this.duration = extractDuration(duration);
        this.hosting = normalizeString(hosting);
        this.fleet = normalizeString(fleet);
        this.language = normalizeString(language);
        this.availibility = normalizeString(availibility);
        this.food = normalizeString(food);
        this.price = normalizeString(price);
        this.fromDate = normalizeString(fromDate);
        this.toDate = normalizeString(toDate);
        this.city = normalizeString(city);
    }
    
    private String normalizeString(String data) {
        return data
                .replace("Stage", "")
                .replace("stage", "")
                .replaceAll("“", "")
                .replaceAll("/", "-")
                .trim();
    }
    
    private float extractDuration(String duration) {
        if (!duration.contains(" ")) {
            return Integer.valueOf(duration);
        }  
        return Float.valueOf(duration.substring(0,duration.indexOf(" ")).trim());
               
    }
    
}