package com.ibm.timetracker.model.v2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MacAndIpV2  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String computerName;
	
	private String macAddress;
	
	private String ipAddress;
	
	private String startTime;
	
	private String endTime;
	
	public String getComputerName() {
		return computerName;
	}

	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

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

}
