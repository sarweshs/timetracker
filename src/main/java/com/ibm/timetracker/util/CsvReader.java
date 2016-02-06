package com.ibm.timetracker.util;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CsvReader {
	
	private static final Logger LOG = Logger.getLogger("CsvReader");
	public static void main(String[] args) {
		//System.out.println(getLastLineFromCsv("c:/cpo776.csv"));
		String getEndTime = "23:50";
		String getStartTime = "09:30";
		
		Calendar cal = Calendar.getInstance();
		String[] endTime = getEndTime.split(":");
		int endHour = Integer.parseInt(endTime[0]);
		cal.set(Calendar.HOUR_OF_DAY, endHour);
		cal.set(Calendar.MINUTE, Integer.parseInt(endTime[1]));
		
		long end = cal.getTimeInMillis();
		
		String[] startTime = getStartTime.split(":");
		String startHour = startTime[0];
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startHour));
		cal.set(Calendar.MINUTE, Integer.parseInt(startTime[1]));
		long start = cal.getTimeInMillis();
		
		long diff = end - start;
		long hours = TimeUnit.MILLISECONDS.toHours(diff);
		System.out.println(hours);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(diff - (hours * 60 * 60 * 1000));
		System.out.println(minutes);
		double minuteD = (double)minutes/60;
		System.out.println("Hours:" + DecimalFormatUtil.getFormattedData(hours + minuteD));
	}
	
	public static String getLastLineFromCsv(String fileName)
	{
		LOG.entering("getLastLineFromCsv", "====");
		String retVal = null;
		try(LineNumberReader lnr = new LineNumberReader(new FileReader(fileName))) {
			String str = lnr.readLine();
			
			while(str != null && !str.trim().equals(""))
			{
				retVal = str;
				str = lnr.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOG.log(Level.SEVERE, "Could not read:" + fileName + "\n error:" + e.getMessage());
		}
		LOG.exiting("getLastLineFromCsv", "====");
		return retVal;
	}
	
	public static List<String> getAllLines(String fileName)
	{
		LOG.entering("getAllLines", "====");
		List<String> list = new ArrayList<>();
		try(LineNumberReader lnr = new LineNumberReader(new FileReader(fileName))) {
			String str = lnr.readLine();
			while(str != null && !str.trim().equals(""))
			{
				list.add(str);
				str = lnr.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOG.log(Level.SEVERE, "Could not read:" + fileName + "\n error:" + e.getMessage());
		}
		LOG.exiting("getAllLines", "====");
		return list;
	}

}
