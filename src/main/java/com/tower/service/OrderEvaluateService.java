package com.tower.service;

import java.util.Map;

public interface OrderEvaluateService {

	public int getOrderEvaluateByOrderId(Map map);
	
	public int updateAdminEvaluate(Map map);
	
	public int insertAdminEvaluate(Map map);
	
	public int updateUserEvaluate(Map map);
	
	public int insertUserEvaluate(Map map);
}
