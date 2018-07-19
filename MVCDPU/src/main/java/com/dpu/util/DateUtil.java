package com.dpu.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
 
	public static Date changeStringToDate(String dateVal) {
		String[] stArr = dateVal.split("-");
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(stArr[0]), Integer.parseInt(stArr[1])-1, Integer.parseInt(stArr[2]));
		Date date = cal.getTime();
		return date;
	}
	
	public static String rearrangeDate(String date) {
		String reArrangedDate = null;
		if(date != null) {
			String[] arr = date.split("/");
			String month = arr[0];
			String dt = arr[1];
			String yr = arr[2];
			reArrangedDate = yr + "-" + month + "-" + dt;
		}
		return reArrangedDate;
	}
}
