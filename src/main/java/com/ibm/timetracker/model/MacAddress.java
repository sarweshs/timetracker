package com.ibm.timetracker.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MacAddress  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	private int parentId;
	
	private String computerName;
	
	private String macAddress;
	
	private List<IpAddress> allIpAddress;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

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


	protected List<IpAddress> getAllIpAddress() {
		if(allIpAddress == null)
		{
			allIpAddress = new ArrayList<>();
		}
		return allIpAddress;
	}
	
	public void addIpAddress(IpAddress ipAddress , boolean writeToJson)
	{
		getAllIpAddress().add(ipAddress);
		if(writeToJson)
		{
			System.out.println("Added IP");
		}
	}

	public IpAddress getIpAddressByIp(String ipStr) {
		for(IpAddress ip: getAllIpAddress())
		{
			if(ip.getIpAddress().equals(ipStr))
			{
				return ip;
			}
		}
		return null;
	}

}
