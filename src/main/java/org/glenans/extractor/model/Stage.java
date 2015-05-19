/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glenans.extractor.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author mfaure
 */
public class Stage implements Serializable {

    private static Logger LOGGER = Logger.getLogger(Stage.class);

    public static String DATE_PATTERN_OUTPUT = "dd/MM/yy HH':'mm";

    private static String[] ALL_DATE_PARSERS = {
            "d/MM/yy 'à' HH'h'mm",
            "d/MM/yy 'à' HH'H'mm",
            "d/MM/yy"};
        
    public static int DURATION_INDEX = 0;
    public static int FROM_DATE_INDEX = 1;
    public static int TO_DATE_INDEX = 2;
    public static int HOSTING_INDEX = 3;
    public static int FLEET_INDEX = 4;
    public static int LANGUAGE_INDEX = 5;
    public static int AVAILABILITY_INDEX = 6;
    public static int FOOD_INDEX = 7;
    public static int PRICE_INDEX = 8;

    private final String url;
    private final String name;
    private String city;
    private float duration;
    private Date fromDate;
    private Date toDate;
    private String hosting;
    private String fleet;
    private String language;
    private String availibility; // @@@TODO create an ENUM with the 3 known states
    private String food; // @@@TODO create an ENUM with the 3 known states
    private String price;

    public String getUrl() {
        return url;
    }
    
    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public float getDuration() {
        return duration;
    }

    @JsonIgnore
    public Date getFromDate() {
        return fromDate;
    }

    @JsonProperty("fromDate")
    public String getFromDateAsString() {
        return convertDateToString(fromDate);
    }

    @JsonIgnore
    public Date getToDate() {
        return toDate;
    }

    @JsonProperty("toDate")
    public String getToDateAsString() {
        return convertDateToString(toDate);
    }
    
    public String getHosting() {
        return hosting;
    }

    public String getFleet() {
        return fleet;
    }

    public String getLanguage() {
        return language;
    }

    public String getAvailibility() {
        return availibility;
    }

    public String getFood() {
        return food;
    }

    public String getPrice() {
        return price;
    }

    @JsonCreator
    public Stage(
            @JsonProperty("url") String url,
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
        this.url            = url;
        this.name           = normalizeString(name);
        this.duration       = extractDuration(duration);
        this.hosting        = normalizeString(hosting);
        this.fleet          = normalizeString(fleet);
        this.language       = normalizeString(language);
        this.availibility   = normalizeString(availibility);
        this.food           = normalizeString(food);
        this.price          = normalizeString(price);
        this.fromDate       = convertStringToDate(fromDate);
        this.toDate         = convertStringToDate(toDate);
        this.city           = normalizeString(city);
    }

    private String normalizeString(String data) {
        return data
                .replace("Stage", "")
                .replace("stage", "")
                .replaceAll("“", "")
                .replaceAll("”", "")
                .replaceAll("/", "-")
                .trim();
    }

    private String normalizeDateString(String data) {
        return data
                .trim();
    }

    private float extractDuration(String duration) {
        if (!duration.contains(" ")) {
            return Float.valueOf(duration);
        }
        return Float.valueOf(duration.substring(0, duration.indexOf(" ")).trim());
    }

    public static Date convertStringToDate(String stringDate) {
        /*
         Date can have two formats:
         - "04/10/15" for october, 4th 2015
         - "04/10/15 à 14h00" for october, 4th 2015, 2pm
         */
        DateFormat df = new SimpleDateFormat("dd/MM/yy", Locale.FRANCE);
        DateFormat dfWithTime = new SimpleDateFormat("d/MM/yy 'à' HH'h'mm", Locale.FRANCE);
        
        try {
            LOGGER.info("Date : " + stringDate);
            return DateUtils.parseDate(stringDate, ALL_DATE_PARSERS);
        } catch (ParseException ex) {
            LOGGER.info("ParseException : " + ex);
        }
        return null;
    }
    
    public static String convertDateToString(Date stringDate) {
        if (stringDate == null) {
            return "";
        } else {
            return new SimpleDateFormat(DATE_PATTERN_OUTPUT, Locale.FRANCE).format(stringDate);
        }
    }

}
