package com.tower.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tower.mapper.InfoMapper;
import com.tower.service.InfoService;

@Service
public class InfoServiceImpl implements InfoService {

	@Autowired
	private InfoMapper infoMapper;
	
	
	@Override
	public int updateInfo(Map map) {
		return infoMapper.updateInfo(map);
	}


	@Override
	public int updatePwd(Map map) {
		return infoMapper.updatePwd(map);
	}


	@Override
	public int checkPwd(Map map) {
		return infoMapper.checkPwd(map);
	}

	
}
