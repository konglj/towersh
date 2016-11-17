package com.tower.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tower.common.bean.Area;
import com.tower.common.bean.City;
import com.tower.mapper.AreaMapper;
import com.tower.service.AreaService;

@Service
public class AreaSeriviceImpl implements AreaService{

	@Autowired
	private AreaMapper areaMapper;
	@Override
	public List<Area> getAreas(Map map) {
		List<Area> list=areaMapper.getAreas(map);
		return list;
	}

	@Override
	public List<City> getCitys(Map map) {
		return areaMapper.getCitys(map);
	}
	
	@Override
	public List<Area> getOtherAreas(Map map){
		return areaMapper.getOtherAreas(map);
	}

	@Override
	public Area getAreaByCityNameAndAreaName(Map map) {
		return areaMapper.getAreaByCityNameAndAreaName(map);
	}

	@Override
	public String getCityname(String cityid) {
		
		Map map=areaMapper.getCityname(cityid);
		String cityname="";
		if(map!=null){
			cityname=(String)map.get("City_name");
		}
		if(cityname==null)
			cityname="";
		return cityname;
	}

	@Override
	public List<City> getCityAndAreas(Map map) {
		return areaMapper.getCityAndAreas(map);
	}

	@Override
	public String getCitysStrByAreas(Map map) {
		return areaMapper.getCitysStrByAreas(map);
	}

}
