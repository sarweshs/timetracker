package com.ibm.timetracker.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class IpAddress  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ipAddress;
	
	private String startTime;
	
	private String endTime;
	
	private String parentMac;
	
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getParentMac() {
		return parentMac;
	}

	public void setParentMac(String parentMac) {
		this.parentMac = parentMac;
	}
	
	protected double getHours()
	{
		if(getEndTime() == null || getStartTime() == null)
		{
			return 0;
		}
		Calendar cal = Calendar.getInstance();
		String[] endTime = getEndTime().split(":");
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endTime[0]));
		cal.set(Calendar.MINUTE, Integer.parseInt(endTime[1]));
		long end = cal.getTimeInMillis();
		
		String[] startTime = getStartTime().split(":");
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startTime[0]));
		cal.set(Calendar.MINUTE, Integer.parseInt(startTime[1]));
		long start = cal.getTimeInMillis();
		
		long diff = end - start;
		long hours = TimeUnit.MILLISECONDS.toHours(diff);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(diff - (hours * 60 * 60 * 1000));
		double minuteD = (double)minutes/60;
		return hours + minuteD;
	}

}
