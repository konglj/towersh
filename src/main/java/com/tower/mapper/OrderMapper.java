package com.tower.mapper;

import java.util.List;
import java.util.Map;

import com.tower.common.bean.Order;
import com.tower.common.bean.OrderInfo;
import com.tower.common.bean.OrderPage;
import com.tower.common.bean.OrderState;
import com.tower.common.bean.OrderTower;
import com.tower.common.bean.OrderUser;
import com.tower.common.bean.ReportOrder;
import com.tower.common.bean.ReportOrderPage;
import com.tower.common.bean.Score;
import com.tower.common.bean.TxSource;
import com.tower.common.bean.UserChargeInfo;
import com.tower.common.bean.UserOrderInfo;

public interface OrderMapper {
	
	public List<Order> getOrders(OrderPage orderPage);
	
	public int getOrdersCount(OrderPage orderPage);
	
	public OrderTower getOrderTower(int id);
	
	public OrderUser getOrderUser(String wxid);
	
	public int updateOrderState(Map map);
	
	public int updateTowerState(Map map);
	
	public List<Map> getCheckCancelOrder();
	
	public List<Map> getCheckPostUnDo(int day);
	
	public int updateSendMessage(Map map);
	
	
	
	public List<OrderState> getOrderStates();
	
	public int updateOrderByRentHt(OrderInfo orderInfo);
	
	public int updateOrderByThreeHt(OrderInfo orderInfo);
	
	public int getIndexPageOrdersCount(OrderPage orderPage);
	
	/**
	 * 更新评价订单进度
	 * @param orderid
	 * @return
	 */
	public int updateOrderPjProgress(Map map);
	

	//获取站址的下单历史
	public List<Order> getOrderHistorys(int towerid);
	
	public List<Map> getDcTowerOrderHistorys(int towerid);

	
	/*
	 * 订单统计
	 */
	
	public int updateTowerUserOrderInfo(Map map);
	
	
	public int insertUserOrder(UserOrderInfo userOrderInfo);
	
	public int checkUserOrder(String wxid);
	
	public UserOrderInfo getUserOrderInfo(String wxid);
	
	public int updateUserCancelOrderCount(Map map);
	/**
	 * 修改金额
	 */
	public int updateUserCharge(UserChargeInfo chargeInfo);
	
	public int insertTxSource(TxSource txSource);
	
	public UserChargeInfo getUserCharge(String wxid);
	
	/**
	 * 积分
	 */
	
	public int insertScore(Score score);
	
	
	

	/**
	 * 导出
	 */
	public List<Map> getDcOrders(OrderPage orderPage);
	
    //-------------------------------------------------------------------------
	
	/***
	 * 修改24小时前提醒状态
	 * @param map
	 * @return
	 */
	public int updateOrderCancel24(Map map);

}
