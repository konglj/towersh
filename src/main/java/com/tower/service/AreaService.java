package com.tower.service;

import java.util.List;
import java.util.Map;

import com.tower.common.bean.Area;
import com.tower.common.bean.City;

public interface AreaService {
	
	public List<Area> getAreas(Map map);
	
	public List<City> getCitys(Map map);
	
	public List<Area> getOtherAreas(Map map);
	
	public Area getAreaByCityNameAndAreaName(Map map);
	
	public String getCityname(String cityid);
	
	/**
	 * 关联
	 * @param map
	 * @return
	 */
	public List<City> getCityAndAreas(Map map);
	
	
	/*
	 *根据给予的区域ID集合获取对应的城市ID 
	 */
	public String getCitysStrByAreas(Map map);
	
	

	
	
}
