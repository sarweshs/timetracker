package com.ibm.timetracker.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CsvWriter {
	private static final Logger LOG = Logger.getLogger("CsvWriter");
	public static void main(String[] args) {
		updateLastLineToCSV("c:/cpo776.csv", "mytestanother");
	}
	
	public static void writeNewLineToCSV(String fileName, String data)
	{
		LOG.entering("writeNewLineToCSV", "====");
		try(FileWriter fw = new FileWriter(fileName,true)) {
			fw.write(data + "\n");
			fw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOG.log(Level.SEVERE, "Could not write:" + data + "\n error:" + e.getMessage());
		}
		LOG.exiting("writeNewLineToCSV", "====");
	}
	
	public static void updateLastLineToCSV(String fileName, String data)
	{
		LOG.entering("updateLastLineToCSV", "====");
		List<String> list = CsvReader.getAllLines(fileName);
		if(list.size() > 0)
		{
			list.remove(list.size() - 1);
		}
		list.add(data);
		try(FileWriter fw = new FileWriter(fileName)) {
			for(String d:list)
			{
				fw.write(d + "\n");
			}
			fw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOG.log(Level.SEVERE, "Could not write:" + data + "\n error:" + e.getMessage());
		}
		LOG.exiting("updateLastLineToCSV", "====");
	}
	
}

