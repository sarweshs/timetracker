package com.ibm.timetracker.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ibm.timetracker.util.DecimalFormatUtil;

public class ComputerConfig implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int computerId;
	
	private String computerName;
	
	private String currentDate;
	
	private List<MacAddress>listMacs;
	
	public int getComputerId() {
		return computerId;
	}

	public void setComputerId(int computerId) {
		this.computerId = computerId;
	}

	public String getComputerName() {
		return computerName;
	}

	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}

	private List<MacAddress> getListMacs() {
		if(listMacs == null)
		{
			listMacs = new ArrayList<>();
		}
		return listMacs;
	}
	
	public void addMacAddress(MacAddress mac, boolean writeToJson)
	{
		getListMacs().add(mac);
		if(writeToJson)
		{
			System.out.println("Added Mac");
		}
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public void clearListMacAddress() {
		// TODO Auto-generated method stub
		getListMacs().clear();
	}

	public MacAddress findMacWithIp(String macStr, String ipStr) {
		// TODO Auto-generated method stub
		for(MacAddress mac:getListMacs())
		{
			if(mac.getMacAddress().equals(macStr))
			{
				for(IpAddress ip: mac.getAllIpAddress())
				{
					if(ip.getIpAddress().equals(ipStr))
					{
						return mac;
					}
				}
			}
		}
		return null;
	}

	public String getTotalTime() {
		double totalTime = 0;
		for(MacAddress mac:getListMacs())
		{
			for(IpAddress ip:mac.getAllIpAddress())
			{
				totalTime = totalTime + ip.getHours();
			}
		}
		return DecimalFormatUtil.getFormattedData(totalTime);
	}
	
	
	
}
