package com.tower.service.impl;

import java.util.List;
import java.util.Map;

import javax.xml.ws.soap.Addressing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tower.common.bean.OutDatePhone;
import com.tower.common.bean.Setting;
import com.tower.mapper.SettingMapper;
import com.tower.service.SettingService;

@Service
public class SettingServiceImpl implements SettingService {

	@Autowired
	private SettingMapper settingMapper;
	
	@Override
	public int updateTowerGs() {
		return settingMapper.updateTowerGs();
	}

	@Override
	public Setting getSetting(String name) {
		return settingMapper.getSetting(name);
	}

	@Override
	public List<OutDatePhone> getOutPhones() {
		return settingMapper.getOutPhones();
	}

	@Override
	public int updateOutSend(Map map) {
		return settingMapper.updateOutSend(map);
	}

}
