package com.ibm.timetracker.timer;

import java.io.File;
import java.net.SocketException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import com.ibm.timetracker.model.ComputerConfig;
import com.ibm.timetracker.model.IpAddress;
import com.ibm.timetracker.model.MacAddress;
import com.ibm.timetracker.util.CsvReader;
import com.ibm.timetracker.util.CsvWriter;
import com.ibm.timetracker.util.DateUtil;
import com.ibm.timetracker.util.JsonUtil;
import com.ibm.timetracker.util.MacAddressAndIpFinder;

class MyTimerTask extends TimerTask {
	private static final Logger LOG = Logger.getLogger("MyTimerTask");
	private static final String COMMA = ",";
	
	public MyTimerTask()
	{
		
	}
	public void run() {
		System.out.println("Updating data...");
		//checkAndWriteInFile(MacAddressAndIpFinder.getData());
		try {
			//checkAndWriteInFile(MacAddressAndIpFinder.getAllNetworkData());
			String allNetworkData = MacAddressAndIpFinder.getAllNetworkData();
			updateConfigData(allNetworkData);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			LOG.severe("Could not get Data at");
		}
		System.out.println("data updated...");
	}
	
	private static synchronized void updateConfigData(String allNetworkData) {
		
		//SimpleTimerTask.c
		validateData(allNetworkData,SimpleTimerTask.compConfig);
	}

	
	private static void validateData(String allNetworkData,ComputerConfig compconfig) {
		// TODO Auto-generated method stub
		String now = DateUtil.getFormattedDate("dd-MMM-yyyy");
		String time = DateUtil.getFormattedDate("HH:mm");
		String[]ipMacArray = allNetworkData.split("#");
		if(!now.equals(compconfig.getCurrentDate()))
		{
			JsonUtil.createJSON(compconfig, compconfig.getCurrentDate() + ".json");
			compconfig.clearListMacAddress();
			compconfig.setCurrentDate(now);
			
			for(String ipMacComb : ipMacArray)
			{
				String []ipMac = ipMacComb.split(":");
				MacAddress ma = createMac(ipMac, compconfig.getComputerName(), time, time);
				compconfig.addMacAddress(ma, false);
			}
		}
		else
		{
			for(String ipMacComb : ipMacArray)
			{
				String []ipMac = ipMacComb.split(":");
				MacAddress mac = compconfig.findMacWithIp(ipMac[1], ipMac[0]);
				if(mac != null)
				{
					IpAddress ipAddr = mac.getIpAddressByIp(ipMac[0]);
					if(ipAddr != null)
					{
						ipAddr.setEndTime(time);
						//break;
					}
					else
					{
						ipAddr = createIpAddress(ipMac[0], mac.getMacAddress(), time, time);
						mac.addIpAddress(ipAddr, false);
					}
				}//end mac null
				else
				{
					MacAddress ma = createMac(ipMac, compconfig.getComputerName(), time, time);
					compconfig.addMacAddress(ma, false);
				}
				
			}//end for
		}
		JsonUtil.createJSON(compconfig, compconfig.getCurrentDate() + ".json");
		
	}
	
	private static MacAddress createMac(String [] ipMac, String computerName, String startTime, String endTime )
	{
		MacAddress ma = new MacAddress();
		ma.setComputerName(computerName);
		ma.setMacAddress(ipMac[1]);
		
		IpAddress ip = createIpAddress(ipMac[0], ma.getMacAddress(), startTime, endTime);
		ma.addIpAddress(ip, false);
		return ma;
	}
	
	private static IpAddress createIpAddress(String ipAddress, String macAddress, String startTime, String endTime)
	{
		IpAddress ip = new IpAddress();
		ip.setIpAddress(ipAddress);
		ip.setParentMac(macAddress);
		ip.setStartTime(startTime);
		ip.setEndTime(endTime);
		return ip;
	}
	
	private static synchronized void checkAndWriteInFile(String dataMap) {
		// TODO Auto-generated method stub
		//FileWriter fw = new
		StringBuilder data = null;
		String fileName = "c:/temp/mydata.csv";
		String lastLine = CsvReader.getLastLineFromCsv(fileName);
		String date = DateUtil.getFormattedDate("dd-MMM-yyyy");
		String time = DateUtil.getFormattedDate("HH:mm");
		if(lastLine != null)
		{
			String[] array = lastLine.split(COMMA);
			array[0] = array[0].trim();
			if(array[0].equalsIgnoreCase(date))
			{
				data = getDataToLog(date,array[1],time,dataMap);
				CsvWriter.updateLastLineToCSV(fileName, data.toString());
			}
			else
			{
				data = getDataToLog(date,time,time,dataMap);
				CsvWriter.writeNewLineToCSV(fileName, data.toString());
			}
		}
		else
		{
			data = getDataToLog(date,time,time,dataMap);
			CsvWriter.writeNewLineToCSV(fileName, data.toString());
		}
		
	}

	private static synchronized void checkAndWriteInFile(Map<String, String> dataMap) {
		// TODO Auto-generated method stub
		//FileWriter fw = new
		StringBuilder data = null;
		String fileName = "c:/temp/mydata.csv";
		String lastLine = CsvReader.getLastLineFromCsv(fileName);
		String date = DateUtil.getFormattedDate("dd-MMM-yyyy");
		String time = DateUtil.getFormattedDate("HH:mm");
		if(lastLine != null)
		{
			String[] array = lastLine.split(COMMA);
			array[0] = array[0].trim();
			if(array[0].equalsIgnoreCase(date))
			{
				data = getDataToLog(date,array[1],time,dataMap);
				CsvWriter.updateLastLineToCSV(fileName, data.toString());
			}
			else
			{
				data = getDataToLog(date,time,time,dataMap);
				CsvWriter.writeNewLineToCSV(fileName, data.toString());
			}
		}
		else
		{
			data = getDataToLog(date,time,time,dataMap);
			CsvWriter.writeNewLineToCSV(fileName, data.toString());
		}
		
	}

	private static StringBuilder getDataToLog(String date, String startTime,String endTime,
			Map<String, String> dataMap) {
		// TODO Auto-generated method stub
		StringBuilder data = new StringBuilder();
		data.append(date).append(COMMA).append(startTime).append(COMMA).append(dataMap.get("MAC_ADDRESS")).append(COMMA)
		.append(dataMap.get("IP_ADDRESS")).append(COMMA).append(endTime);
		return data;
	}
	
	private static StringBuilder getDataToLog(String date, String startTime,String endTime,
			String allData) {
		// TODO Auto-generated method stub
		StringBuilder data = new StringBuilder();
		data.append(date).append(COMMA).append(startTime).append(COMMA)
		.append(allData).append(COMMA).append(endTime);
		return data;
	}
}

public class SimpleTimerTask {
	public static final String computerName;
	public static final ComputerConfig compConfig;
	private static final String jsonPath;
	static{
		computerName = MacAddressAndIpFinder.getComputerName();
		jsonPath = "c:/tmp/";
		String formattedDate = DateUtil.getFormattedDate("dd-MMM-yyyy");
		//String filePath = jsonPath + formattedDate + ".json";
		String filePath = formattedDate + ".json";
		if(new File(filePath).exists())
		{
			compConfig = (ComputerConfig)JsonUtil.getObjectFromJson(filePath);
		}
		else
		{
			compConfig = new ComputerConfig();
			compConfig.setComputerName(computerName);
			compConfig.setCurrentDate(formattedDate);
		}
		
		
	}
	public static void main(String[] args) {
		TimerTask tasknew = new MyTimerTask();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(tasknew, 1000, 20000);
	}

	// this method performs the task
	public void run() {
		System.out.println("working at fixed rate delay");
	}
	
	
}