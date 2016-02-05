package com.ibm.timetracker.model;

import java.io.Serializable;

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
	
	

}
