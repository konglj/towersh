package com.tower.mapper;

import java.util.List;
import java.util.Map;

import com.tower.common.bean.Area;
import com.tower.common.bean.City;

public interface AreaMapper {
	
	public List<Area> getAreas(Map map);
	
	public List<City> getCitys(Map map);
	
	public List<Area> getOtherAreas(Map map);
	
	
	public Area getAreaByCityNameAndAreaName(Map map);
	
	
	public Map getCityname(String cityid);
	
	public  List<Map> getAreasMap(Map map);
	
	public  List<Map> getCitysMap(Map map);
	
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
