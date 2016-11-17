package com.tower.service;

import java.util.List;
import java.util.Map;

import com.tower.common.bean.Order;
import com.tower.common.bean.OrderInfo;
import com.tower.common.bean.OrderPage;
import com.tower.common.bean.OrderState;

public interface OrderService {
	
	/**
	 * 获取订单列表
	 * @param orderPage
	 * @return
	 */
	public List<Order> getOrders(OrderPage orderPage);

	/**
	 * 获取订单详情
	 * @param orderPage
	 */
	public void getOrderInfo(OrderPage orderPage);
	
	/**
	 * 审核订单
	 * @param map
	 * @return
	 */
	public int shOrder(Map  map);
	
	/*
	public int updateTowerFirstFeeAply(Map map);
	
	public int updateTowerEndFeeAply(Map map);
	
	public int updateFeeSh(Map map);
	
	public int updateOrderYq(Map map);
	
	
	*/
	/**
	 * 审核拒绝
	 * @param map
	 * @return
	 */
	public int updateOrderShjj(Map map);
	
	/**
	 * 导出订单
	 * @param orderPage
	 * @return
	 */
	public List<Map> getDcOrders(OrderPage orderPage);
	
	/**
	 * 超时弃单列表
	 * @return
	 */
	public List<Map> getCheckCancelOrder();
	
	/**
	 * 自动弃单
	 * @param map
	 * @return
	 */
	public int updateOrderCancel(Map map);

	
	/**
	 * 到期前24小时
	 * @param map
	 * @return
	 */
	public int updateOrderCancel24(Map map);
	
	/**
	 * 提交租赁合同
	 * @param adminid
	 * @param shinfo
	 * @param orderInfo
	 * @return
	 */
	public int updateTowerRentHtAply(int adminid,String shinfo,OrderInfo orderInfo,int adminpower);
	
	/**
	 * 提交三方合同申请
	 * @param adminid
	 * @param shinfo
	 * @param orderInfo
	 * @return
	 */
	public int updateTowerThreeHtAply(String templatePath,String threePath,int adminid,String shinfo,OrderInfo orderInfo ,int adminpower);
	
	/**
	 * 三方合同审核
	 * @param map
	 * @return
	 */
	public int updateTowerThreeSh(Map map);
	
	/**
	 * 提交付款申请
	 * @param map
	 * @return
	 */
	public int updateTowerFeeAply(Map map);
	
	/**
	 * 付款审核
	 * @param map
	 * @return
	 */
	public int updateTowerFeeSh(Map map);
	
	/**
	 * 订单状态表
	 * @return
	 */
	public List<OrderState> getOrderStates();
	
	/**
	 * 更新评价订单进度
	 * @param orderid
	 * @return
	 */
	public int updateOrderPjProgress(Map map);
	
	
	/**
	 * 交单三天未处理列表
	 * @return
	 */
	public List<Map> getCheckPostUnDo(int day);
	
	public int updateSendMessage(Map map);
	
	
}

