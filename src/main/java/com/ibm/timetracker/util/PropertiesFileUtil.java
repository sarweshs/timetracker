package com.ibm.timetracker.util;

import java.util.ResourceBundle;

public class PropertiesFileUtil {
	private static final ResourceBundle rb = ResourceBundle.getBundle("com.ibm.timetracker.application");
	
	public static String getProperty(String key)
	{
		return rb.getString(key);
	}
}
