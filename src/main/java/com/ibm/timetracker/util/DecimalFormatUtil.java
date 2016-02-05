package com.ibm.timetracker.util;

import java.text.DecimalFormat;

public class DecimalFormatUtil {
	private static final DecimalFormat df = new DecimalFormat("####0.00");;
	
	public static String getFormattedData(double data)
	{
		return df.format(data);
	}

}
