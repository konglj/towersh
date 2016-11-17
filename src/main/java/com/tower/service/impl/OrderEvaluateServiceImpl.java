package com.tower.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tower.mapper.OrderEvaluateMapper;
import com.tower.service.OrderEvaluateService;

@Service
public class OrderEvaluateServiceImpl implements OrderEvaluateService {
	
	@Autowired
	private OrderEvaluateMapper evaluateMapper;

	@Override
	public int getOrderEvaluateByOrderId(Map map) {
		return evaluateMapper.getOrderEvaluateByOrderId(map) ;
	}

	@Override
	public int updateAdminEvaluate(Map map) {
		return evaluateMapper.updateAdminEvaluate(map);
	}

	@Override
	public int insertAdminEvaluate(Map map) {
		return evaluateMapper.insertAdminEvaluate(map);
	}

	@Override
	public int updateUserEvaluate(Map map) {
		return evaluateMapper.updateUserEvaluate(map);
	}

	@Override
	public int insertUserEvaluate(Map map) {
		return evaluateMapper.insertUserEvaluate(map);
	}

}
