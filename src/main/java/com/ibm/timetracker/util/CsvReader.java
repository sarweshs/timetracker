package com.ibm.timetracker.util;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CsvReader {
	
	private static final Logger LOG = Logger.getLogger("CsvReader");
	public static void main(String[] args) {
		System.out.println(getLastLineFromCsv("c:/cpo776.csv"));
	}
	
	public static String getLastLineFromCsv(String fileName)
	{
		LOG.entering("getLastLineFromCsv", "====");
		String retVal = null;
		try(LineNumberReader lnr = new LineNumberReader(new FileReader(fileName))) {
			String str = lnr.readLine();
			
			while(str != null && !str.trim().equals(""))
			{
				retVal = str;
				str = lnr.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOG.log(Level.SEVERE, "Could not read:" + fileName + "\n error:" + e.getMessage());
		}
		LOG.exiting("getLastLineFromCsv", "====");
		return retVal;
	}
	
	public static List<String> getAllLines(String fileName)
	{
		LOG.entering("getAllLines", "====");
		List<String> list = new ArrayList<>();
		try(LineNumberReader lnr = new LineNumberReader(new FileReader(fileName))) {
			String str = lnr.readLine();
			while(str != null && !str.trim().equals(""))
			{
				list.add(str);
				str = lnr.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOG.log(Level.SEVERE, "Could not read:" + fileName + "\n error:" + e.getMessage());
		}
		LOG.exiting("getAllLines", "====");
		return list;
	}

}
