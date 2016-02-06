package com.ibm.timetracker.model.v2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComputerConfigV2 implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String computerName;
	
	private String currentDate;
	
	private List<MacAndIpV2>listMacs;

	public String getComputerName() {
		return computerName;
	}

	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}

	private List<MacAndIpV2> getListMacs() {
		if(listMacs == null)
		{
			listMacs = new ArrayList<>();
		}
		return listMacs;
	}
	
	public void addMacAddress(MacAndIpV2 mac, boolean writeToJson)
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

	public MacAndIpV2 findMacWithIp(String macStr, String ipStr) {
		// TODO Auto-generated method stub
		for(MacAndIpV2 mac:getListMacs())
		{
			if(mac.getMacAddress().equals(macStr) && mac.getIpAddress().equals(ipStr))
			{
				return mac;
			}
		}
		return null;
	}
	
}
