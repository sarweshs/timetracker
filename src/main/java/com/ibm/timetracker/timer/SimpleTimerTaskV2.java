package com.ibm.timetracker.timer;

import java.io.File;
import java.net.SocketException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import com.ibm.timetracker.model.IpAddress;
import com.ibm.timetracker.model.v2.ComputerConfigV2;
import com.ibm.timetracker.model.v2.MacAndIpV2;
import com.ibm.timetracker.service.client.RestClient;
import com.ibm.timetracker.util.CsvReader;
import com.ibm.timetracker.util.CsvWriter;
import com.ibm.timetracker.util.DateUtil;
import com.ibm.timetracker.util.JsonUtil;
import com.ibm.timetracker.util.MacAddressAndIpFinder;
import com.ibm.timetracker.util.PropertiesFileUtil;

class MyTimerTaskV2 extends TimerTask {
	private static final Logger LOG = Logger.getLogger("MyTimerTask");
	private static final String COMMA = ",";
	private static int uploadCount = 0;
	
	public MyTimerTaskV2()
	{
		
	}
	public void run() {
		System.out.println("Updating data for " + (uploadCount++) + "time");
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
		validateData(allNetworkData,SimpleTimerTaskV2.compConfig);
	}

	
	private static void validateData(String allNetworkData,ComputerConfigV2 compconfig) {
		// TODO Auto-generated method stub
		String now = DateUtil.getFormattedDate("dd-MMM-yyyy");
		String time = DateUtil.getFormattedDate("HH:mm");
		String[]ipMacArray = allNetworkData.split("#");
		if(!now.equals(compconfig.getCurrentDate()))
		{
			String jsonString = getJson(compconfig, compconfig.getCurrentDate() + ".json");
			RestClient.postData(jsonString);
			compconfig.clearListMacAddress();
			compconfig.setCurrentDate(now);
			
			for(String ipMacComb : ipMacArray)
			{
				String []ipMac = ipMacComb.split(":");
				MacAndIpV2 ma = createMac(ipMac, compconfig.getComputerName(), time, time);
				compconfig.addMacAddress(ma, false);
			}
		}
		else
		{
			for(String ipMacComb : ipMacArray)
			{
				String []ipMac = ipMacComb.split(":");
				MacAndIpV2 mac = compconfig.findMacWithIp(ipMac[1], ipMac[0]);
				if(mac != null)
				{
					mac.setEndTime(time);
				}//end mac null
				else
				{
					MacAndIpV2 newMac = createMac(ipMac, compconfig.getComputerName(), time, time);
					compconfig.addMacAddress(newMac, false);
				}
				
			}//end for
		}
		String jsonString = getJson(compconfig, compconfig.getCurrentDate() + ".json");
		if(uploadCount % SimpleTimerTaskV2.uploadFrequency == 0)
		{
			RestClient.postData(jsonString);
		}
		
	}
	
	private static String getJson(ComputerConfigV2 compConfig, String fileName)
	{
		String jsonString = null;
		if(SimpleTimerTaskV2.encryptJson.equalsIgnoreCase("true"))
		{
			fileName = "encrypted_" + fileName;
			jsonString = JsonUtil.createEncryptedJSON(compConfig, fileName);
		}
		else
		{
			jsonString = JsonUtil.createJSON(compConfig, fileName);
		}
		
		return jsonString;
	}
	
	private static MacAndIpV2 createMac(String [] ipMac, String computerName, String startTime, String endTime )
	{
		MacAndIpV2 ma = new MacAndIpV2();
		ma.setComputerName(computerName);
		ma.setMacAddress(ipMac[1]);
		ma.setIpAddress(ipMac[0]);
		ma.setStartTime(startTime);
		ma.setEndTime(endTime);
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

public class SimpleTimerTaskV2 {
	private static final String computerName;
	public static final ComputerConfigV2 compConfig;
	private static final String jsonPath;
	public static final int uploadFrequency;
	public static final String encryptJson;
	static{
		computerName = MacAddressAndIpFinder.getComputerName();
		jsonPath = "c:/tmp/";
		String formattedDate = DateUtil.getFormattedDate("dd-MMM-yyyy");
		encryptJson = PropertiesFileUtil.getProperty("json.encrypt");
		//String filePath = jsonPath + formattedDate + ".json";
		String filePath = formattedDate + ".json";
		if(encryptJson.equalsIgnoreCase("true"))
		{
			filePath = "encrypted_" + filePath;
		}
		if(new File(filePath).exists())
		{
			compConfig = (ComputerConfigV2)JsonUtil.getObjectFromJson(filePath);
		}
		else
		{
			compConfig = new ComputerConfigV2();
			compConfig.setComputerName(computerName);
			compConfig.setCurrentDate(formattedDate);
		}
		String uploadFreq = PropertiesFileUtil.getProperty("json.upload.frequency");
		if(uploadFreq == null)
		{
			uploadFreq = "10";
		}
		uploadFrequency = Integer.parseInt(uploadFreq);
	}
	public static void main(String[] args) {
		TimerTask tasknew = new MyTimerTaskV2();
		Timer timer = new Timer();
		String freq = PropertiesFileUtil.getProperty("timer.frequency");
		if(freq == null)
		{
			freq = "20000";
		}
		long freqLong = Long.parseLong(freq);
		timer.scheduleAtFixedRate(tasknew, 1000, freqLong);
	}

	// this method performs the task
	public void run() {
		System.out.println("working at fixed rate delay");
	}
	
	
}