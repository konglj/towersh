package com.tower.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAnyGetter;

import net.sf.json.JSONObject;

public class MapUtil {
	
	public static Map jsonToObject(JSONObject jsonObj ) throws Exception {
		Iterator<String> nameItr = jsonObj.keys();
		String name;
		Map<String, String> outMap = new HashMap<String, String>();
		while (nameItr.hasNext()) {
			name = nameItr.next();
			outMap.put(name, jsonObj.getString(name));
		}
		return outMap;
	}
	public static Map jsonToObject(String json ) throws Exception {
		JSONObject jsonObj=JSONObject.fromObject(json);
		Iterator<String> nameItr = jsonObj.keys();
		String name;
		Map<String, String> outMap = new HashMap<String, String>();
		while (nameItr.hasNext()) {
			name = nameItr.next();
			outMap.put(name, jsonObj.getString(name));
		}
		return outMap;
	}

}
