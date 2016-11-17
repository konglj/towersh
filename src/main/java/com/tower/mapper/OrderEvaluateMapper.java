package com.tower.mapper;

import java.util.Map;

/**
 * 订单评价表
 * @author win7
 *
 */
public interface OrderEvaluateMapper {
	
	public int getOrderEvaluateByOrderId(Map map);
	
	public int updateAdminEvaluate(Map map);
	
	public int insertAdminEvaluate(Map map);
	
	public int updateUserEvaluate(Map map);
	
	public int insertUserEvaluate(Map map);
	
	
	


}
