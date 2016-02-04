package com.ibm.timetracker.timer;

import java.net.SocketException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import com.ibm.timetracker.util.CsvReader;
import com.ibm.timetracker.util.CsvWriter;
import com.ibm.timetracker.util.DateUtil;
import com.ibm.timetracker.util.MacAddressAndIpFinder;

class MyTimerTask extends TimerTask {
	private static final Logger LOG = Logger.getLogger("MyTimerTask");
	private static final String COMMA = ",";
	public void run() {
		System.out.println("Updating data...");
		//checkAndWriteInFile(MacAddressAndIpFinder.getData());
		try {
			checkAndWriteInFile(MacAddressAndIpFinder.getAllNetworkData());
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			LOG.severe("Could not get Data at");
		}
		System.out.println("data updated...");
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
	public static void main(String[] args) {
		TimerTask tasknew = new MyTimerTask();
		Timer timer = new Timer();

		timer.scheduleAtFixedRate(tasknew, 5000, 5000);
	}

	// this method performs the task
	public void run() {
		System.out.println("working at fixed rate delay");
	}
	
	
}