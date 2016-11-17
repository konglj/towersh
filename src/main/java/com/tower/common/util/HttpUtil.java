package com.tower.common.util;

import javax.servlet.http.HttpServletRequest;

import com.sun.xml.internal.ws.resources.HttpserverMessages;

public class HttpUtil {
	
	public static String getWebRoot(HttpServletRequest request){
		 String basePath = request.getScheme() + "://"
				   + request.getServerName() + ":" + request.getServerPort()
				   + request.getContextPath() + "/";
		 return basePath;
	}
	
	public static String getHttpRoot(HttpServletRequest request){
		 String basePath = request.getScheme() + "://"
				   + request.getServerName() + ":" + request.getServerPort()
				   +  "/";
		 return basePath;
	}
	
	public static String getHttpServerIp(HttpServletRequest request){
		 return request.getRemoteAddr();
	}

}
