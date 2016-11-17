package com.tower.mapper;

import java.util.List;
import java.util.Map;

import com.tower.common.bean.OutDatePhone;
import com.tower.common.bean.Setting;

public interface SettingMapper {
	
	public int updateTowerGs();

	public Setting getSetting(String name);
	
	public List<OutDatePhone> getOutPhones();
	
	public int updateOutSend(Map map);
}
