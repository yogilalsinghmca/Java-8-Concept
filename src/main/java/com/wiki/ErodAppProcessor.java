package com.wiki;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import com.google.maps.model.LatLng;

/**
 * @author yogi.lalsingh *
 */
public class ErodAppProcessor {
	public final static String SYSTEM_USER_LOCATION = System.getProperty("user.dir");
	public static final String DATE_FORMAT ="yyyy-MM-dd HH:mm:ss";
	public static String DELIMINTAOR = ",";
		
	/**
	 * write data to output path
	 * @param filePath
	 * @throws IOException
	 */
	public void processOutput(Path outputFilePath , List<String> output) throws IOException {
		Files.write(outputFilePath, output);		
		//display data on screen
		for(String s:output){
			System.out.println(s);
		}
	}
	/**
	 * read all input data
	 * @param inputData
	 * @param latLngToTimezone
	 * @returnConvertlatLonToTimezone
	 * @throws IOException
	 */
	public List<String> processInput(Path inputFilePath ) throws IOException{
		List<String> inputData = Files.readAllLines(inputFilePath);
		List<String> outputList = new ArrayList<String>();
		Iterator<String> ite= inputData.iterator();
		LocalDateTime localDateTime = null;
		TimeZone timeZone = null;
		String input = null, output = null;
		while(ite.hasNext()){
			input = ite.next();
			String[] split = input.split(DELIMINTAOR);
			String dateTime = split[0];
			Double lat = Double.valueOf(split[1]);
			Double lon = Double.valueOf(split[2]);            
			LatLng latLng = new LatLng(lat,lon);
			timeZone =  LatLonToTimezoneConvertor.ConvertlatLonToTimezone(latLng);
			if(timeZone == null){
				System.out.println("Invalid input details, processing next item");
				continue;
			}
			localDateTime = getLocalDateFromUtc(dateTime, timeZone);
			output = input.concat(",").concat(timeZone.getID()).concat(",").concat(localDateTime.toString());
			outputList.add(output);
		}
		return outputList;
	}
	/**
	 * @return dateTimeFormatter with zone
	 */
	private DateTimeFormatter getUTCDateTimeFormat(){
		return DateTimeFormatter.ofPattern(DATE_FORMAT).withZone(ZoneOffset.UTC);
	}
	/**
	 * @param dateTime
	 * @return LocalDateTime
	 */
	private LocalDateTime getLocalDateFromUtc(String dateTime, TimeZone timeZone){
		 DateTimeFormatter UTCTimeFormat = getUTCDateTimeFormat();
		 ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTime, UTCTimeFormat);
		
		return LocalDateTime.ofInstant(zonedDateTime.toInstant(), timeZone.toZoneId());
	}
}
