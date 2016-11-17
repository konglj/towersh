package com.tower.common.util;

import java.util.HashMap;
import java.util.Map;

public class ResultUtil {

	
	public static Map generateResponseMsg(String result, Object msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		map.put("msg", msg);
		return map;
	}
	public static Map generateResponseMsg(String result) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		return map;
	}
}
