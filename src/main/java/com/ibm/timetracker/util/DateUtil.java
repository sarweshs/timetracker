package com.ibm.timetracker.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static void main(String[] args) throws ParseException {
		/*Date curDate = new Date();

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

		String DateToStr = format.format(curDate);
		System.out.println(DateToStr);

		format = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		DateToStr = format.format(curDate);
		System.out.println(DateToStr);

		format = new SimpleDateFormat("dd MMMM yyyy zzzz", Locale.ENGLISH);
		DateToStr = format.format(curDate);
		System.out.println(DateToStr);

		format = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
		DateToStr = format.format(curDate);
		System.out.println(DateToStr);

		try {
			Date strToDate = format.parse(DateToStr);
			System.out.println(strToDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		String formattedDate = getFormattedDate("dd-MMM-yyyy");
		System.out.println(formattedDate);
		System.out.println(getFormattedDate("HH:mm"));
		System.out.println(getDate(formattedDate));
	}
	
	public static String getFormattedDate(String formatStr)
	{
		Date curDate = new Date();

		SimpleDateFormat format = new SimpleDateFormat(formatStr);

		String dateToStr = format.format(curDate);
		System.out.println(dateToStr);
		return dateToStr;
	}
	
	public static Date getDate(String inputStr) throws ParseException
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date inputDate = dateFormat.parse(inputStr);
		return inputDate;
	}

}
