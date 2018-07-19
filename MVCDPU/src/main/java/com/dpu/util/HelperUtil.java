package com.dpu.util;

public class HelperUtil {

	public static boolean chkStringIsNotEmpty(Object str) {
		String string = String.valueOf(str);
		if (string != null && !"".equalsIgnoreCase(string.trim()) && string.length() > 0 && !"null".equalsIgnoreCase(string)) {
			return true;
		}
		return false;
	}
}
