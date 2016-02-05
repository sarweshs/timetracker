package com.ibm.timetracker.util;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

public class FileUtility {

	public static void writeToFile(String file, String data)
	{
		try(FileWriter fw = new FileWriter(file))
		{
			fw.write(data);
			fw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String readFromFile(String file)
	{
		StringBuffer data = new StringBuffer();
		try(LineNumberReader fr = new LineNumberReader(new FileReader(file)))
		{
			String str = fr.readLine();
			while(str != null)
			{
				data.append(str);
				str = fr.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
		return data.toString();
	}
}
