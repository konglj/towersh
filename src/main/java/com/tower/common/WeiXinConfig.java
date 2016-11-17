package com.tower.common;

public class WeiXinConfig {
	
    private static String wxtoken=null;
	
	private static long   wxtokentime=0;
	
	
	

	public static String getWxtoken() {
		return wxtoken;
	}

	public static void setWxtoken(String wxtoken) {
		WeiXinConfig.wxtoken = wxtoken;
	}

	public static long getWxtokentime() {
		return wxtokentime;
	}

	public static void setWxtokentime(long wxtokentime) {
		WeiXinConfig.wxtokentime = wxtokentime;
	}

	
	
	
	
	

}
