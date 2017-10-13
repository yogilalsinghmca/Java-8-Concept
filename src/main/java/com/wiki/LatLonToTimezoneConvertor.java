package com.wiki;
import java.util.TimeZone;

import com.google.maps.GeoApiContext;
import com.google.maps.TimeZoneApi;
import com.google.maps.model.LatLng;


/**
 * utility class to convert LatLng to TimeZone using google API
 * @author yogi.lalsingh
 *
 */
public class LatLonToTimezoneConvertor {
	
	private static final String API_KEY = "AIzaSyDFpR3PlyIidDyzUYAh0pKEpQZ7PBGWRcU";
	private static GeoApiContext CONTEXT = new GeoApiContext().setApiKey(API_KEY);
	
    public static TimeZone ConvertlatLonToTimezone(LatLng latLng) {
        TimeZone timeZone = null;
        try {
        	timeZone =  (TimeZone)TimeZoneApi.getTimeZone(CONTEXT,latLng).await();
    		if(timeZone == null){
    			System.out.println("Invalid input");
    		}
        } catch (Exception e) {
           System.out.println("Exception occure while converting.." + e);
        }
        return timeZone;
    }
}