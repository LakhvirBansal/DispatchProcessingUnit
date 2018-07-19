package com.dpu.util;

public final class ErrorCodeConstants {

	static ErrorCodeConstants constants = new ErrorCodeConstants();
	
	public static  Integer _10001 = 1060001;
	public static  Integer _10002 = 1060002;
	public static  Integer _10003 = 1060003;
	public static  Integer _10004 = 1060004;
	public static  Integer _10005 = 1060005;
	public static  Integer _10006 = 1060006;
	public static  Integer _10007 = 1060007;
	public static  Integer _10008 = 1060008;
	public static  Integer _10009 = 1060009;
	public static  Integer _10010 = 1060010;
	public static  Integer _10011 = 1060011;
	public static  Integer _10012 = 1060012;
	public static  Integer _10013 = 1060013;
	public static  Integer _10014 = 1060014;
	public static  Integer _10015 = 1060015;
	public static  Integer _10016 = 1060016;
	public static  Integer _10017 = 1060017;
	public static  Integer _10018 = 1060018;
	public static  Integer _10019 = 1060019;
	public static  Integer _10020 = 1060020;
	public static  Integer _10120 = 1060120;
		
	public static Integer getCodeValue(Integer code){
		Integer value = code;
		try {
			value = Integer.valueOf(String.valueOf(ErrorCodeConstants.class.getDeclaredField("_" + code).get(null)));
		} catch (Exception e) {
			return code;
		} 
		
		return value;
	}
	public static String getCodeValueAsString(String code){
		String value = code;
		try {
			value = String.valueOf(ErrorCodeConstants.class
					.getDeclaredField("_" + code).get(null));
		} catch (Exception e) {
			return code;
		} 
		
		return value;
	}
}
