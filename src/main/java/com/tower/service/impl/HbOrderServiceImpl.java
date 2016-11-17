package com.tower.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tower.common.bean.Order;
import com.tower.common.bean.OrderInfo;
import com.tower.common.bean.OrderPage;
import com.tower.common.bean.OrderTower;
import com.tower.common.bean.OrderUser;
import com.tower.common.bean.Score;
import com.tower.common.bean.SysUserInfo;
import com.tower.common.bean.TowerReport;
import com.tower.common.bean.TowerShInfo;
import com.tower.common.bean.TxSource;
import com.tower.common.bean.UserChargeInfo;
import com.tower.common.bean.UserInfo;
import com.tower.common.bean.UserOrderInfo;
import com.tower.common.util.ParamerUtil;
import com.tower.common.util.ScoreUtil;
import com.tower.common.util.TimeUtil;
import com.tower.common.wxmsg.WXCommon;
import com.tower.common.wxmsg.WXInfo;
import com.tower.mapper.HbOrderMapper;
import com.tower.mapper.LoginAndSysMapper;
import com.tower.mapper.OrderMapper;
import com.tower.mapper.ReportTowerMapper;
import com.tower.mapper.TowerShInfoMapper;
import com.tower.mapper.UserMapper;
import com.tower.service.HbOrderService;

@Service
public class HbOrderServiceImpl implements HbOrderService {

	@Autowired
	private HbOrderMapper hbOrderMapper;

	@Autowired
	private TowerShInfoMapper towerShInfoMapper;

	@Autowired
	private LoginAndSysMapper sysMapper;

	@Autowired
	private ReportTowerMapper reportTowerMapper;

	@Autowired
	private UserMapper userMapper;


	@Override
	public List<Order> getOrders(OrderPage orderPage) {
		int count = hbOrderMapper.getOrdersCount(orderPage);
		orderPage.setPagerowtotal(count);
		return hbOrderMapper.getOrders(orderPage);

	}

	@Override
	public void getOrderInfo(OrderPage orderPage) {
		// 根据订单获取订单信息
		OrderTower order = hbOrderMapper.getOrderTower(orderPage.getOrderid());
		if (order == null)
			return;
		// 获取用户信息
		OrderUser orderUser = hbOrderMapper.getOrderUser(order.getTowerwxid());
		orderPage.setOrdertower(order);
		orderPage.setOrderuser(orderUser);
		// 获取操作日志

		List<TowerShInfo> shs = new ArrayList();
		shs = towerShInfoMapper.getShInfos(order.getId());
		orderPage.setShinfos(shs);

	}

	@Override
	@Transactional
	public int shOrder(Map map) {
		// 根据订单获取订单状态
		OrderTower order = hbOrderMapper.getOrderTower(Integer.valueOf(map.get(
				"orderid").toString()));
		if (order == null)
			return 0;
		// 获取订单将要修改后的状态
		int towersate = order.getTowerstate();
		int updatetowerstate = ParamerUtil.getUpdateOrderState(towersate,
				Integer.valueOf(map.get("result").toString()));
		// 修改订单状态
		map.put("towerstate", updatetowerstate);
		int count = 0;
		count = hbOrderMapper.updateOrderState(map);
		if (count == 0)
			throw new RuntimeException();
		// 站点还原
		if (updatetowerstate == 3) {
			map.put("towerid", order.getTowerid());
			map.put("towerstate", 0);
			count = hbOrderMapper.updateTowerState(map);
			if (count == 0)
				throw new RuntimeException();
			// 修改抢单数 失败+1 进行中-1 审核拒绝+1
			map.put("wxid", order.getTowerwxid());
			map.put("failcount", 1);
			map.put("ingcount", -1);
			map.put("rejectcount", 1);
			count = hbOrderMapper.updateTowerUserOrderInfo(map);
			if (count == 0)
				throw new RuntimeException();

			try {
				// 修改站址统计表
				// 站址拒绝次数+1
				int towerid = Integer.valueOf(order.getTowerid());
				count = reportTowerMapper.checkTowerExist(towerid);
				TowerReport report = new TowerReport();
				report.setTowerid(towerid);
				report.setRejectcount(1);
				if (count == 0) {
					reportTowerMapper.insertTowerReport(report);
				} else {
					reportTowerMapper.upateTowerReport(report);

				}
			} catch (Exception e) {
			}
		} else if (updatetowerstate == 4) {
			// 修改用户订单 成功数+1，进行中-1
			map.put("wxid", order.getTowerwxid());
			map.put("succcount", 1);
			map.put("ingcount", -1);
			count = hbOrderMapper.updateTowerUserOrderInfo(map);
			if (count == 0)
				throw new RuntimeException();

		}

		if (count == 0)
			throw new RuntimeException();
		// 插入插入操作日志表
		// tower_sh_doinfo
		SysUserInfo userInfo = sysMapper.getSysUserInfo(Integer.valueOf(map
				.get("adminid").toString()));
		TowerShInfo shInfo = new TowerShInfo();
		shInfo.setOrderid(order.getId());
		shInfo.setOrdername(userInfo.getAdminname());
		shInfo.setOrderuser(userInfo.getAdminname());
		shInfo.setOrderphone(userInfo.getAdminphone());
		shInfo.setOrderstate(updatetowerstate);
		shInfo.setOrdertype(2);
		shInfo.setOrdershinfo((String) map.get("shinfo"));
		count = towerShInfoMapper.insertTowerShInfo(shInfo);

		if (count == 0)
			throw new RuntimeException();

		// 发送审核通过信息
		try {
			if (updatetowerstate == 4) {
				String time = TimeUtil.getNow();
				sendShOrderSuccessMsg(order.getId(), order.getTowerwxid(),
						WXInfo.order_sh_success, order.getOrderid(), time,
						WXInfo.order_sh_success_remark);
			}else if(updatetowerstate==12){
				sendShOrderYQMsg(order.getId(), order.getTowername(),order.getTowerwxid(), WXInfo.order_sh_yq_success_title, (String)map.get("adminname"), WXInfo.order_sh_yq_success_content, WXInfo.order_sh_yq_success_remark);
				
				
			}else if(updatetowerstate==14){
				sendShOrderYQMsg(order.getId(), order.getTowername(),order.getTowerwxid(), WXInfo.order_sh_yq_error_title, (String)map.get("adminname"), WXInfo.order_sh_yq_error_content, WXInfo.order_sh_yq_error_remark);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return 1;
	}

	@Override
	@Transactional
	public int updateTowerFirstFeeAply(Map map) {
		// 根据订单获取订单状态
		OrderTower order = hbOrderMapper.getOrderTower(Integer.valueOf(map.get(
				"orderid").toString()));
		if (order == null)
			return 0;

		/*
		 * map.put("orderid", orderid); map.put("htid", htno);
		 * map.put("htimage", image);
		 */
		// 修改订单表
		map.put("towerstate", 5);
		int count = hbOrderMapper.updateOrderState(map);
		if (count == 0)
			throw new RuntimeException();
		SysUserInfo userInfo = sysMapper.getSysUserInfo(Integer.valueOf(map
				.get("adminid").toString()));
		TowerShInfo shInfo = new TowerShInfo();
		shInfo.setOrderid(order.getId());
		shInfo.setOrdername(userInfo.getAdminname());
		shInfo.setOrderuser(userInfo.getAdminname());
		shInfo.setOrderphone(userInfo.getAdminphone());
		shInfo.setOrderstate(5);
		shInfo.setOrdertype(2);
		shInfo.setOrdershinfo((String) map.get("shinfo"));
		count = towerShInfoMapper.insertTowerShInfo(shInfo);
		if (count == 0)
			throw new RuntimeException();

		return 1;
	}

	@Override
	@Transactional
	public int updateTowerEndFeeAply(Map map) {
		// 根据订单获取订单状态
		OrderTower order = hbOrderMapper.getOrderTower(Integer.valueOf(map.get(
				"orderid").toString()));
		if (order == null)
			return 0;

		/*
		 * map.put("orderid", orderid); map.put("htid", htno);
		 * map.put("htimage", image);
		 */
		// 修改订单表
		map.put("towerstate", 8);
		map.put("endfee", order.getTowerfee() - order.getTowerfirstfee());
		int count = hbOrderMapper.updateOrderState(map);
		if (count == 0)
			throw new RuntimeException();
		SysUserInfo userInfo = sysMapper.getSysUserInfo(Integer.valueOf(map
				.get("adminid").toString()));
		TowerShInfo shInfo = new TowerShInfo();
		shInfo.setOrderid(order.getId());
		shInfo.setOrdername(userInfo.getAdminname());
		shInfo.setOrderuser(userInfo.getAdminname());
		shInfo.setOrderphone(userInfo.getAdminphone());
		shInfo.setOrderstate(8);
		shInfo.setOrdertype(2);
		shInfo.setOrdershinfo((String) map.get("shinfo"));
		count = towerShInfoMapper.insertTowerShInfo(shInfo);
		if (count == 0)
			throw new RuntimeException();

		return 1;
	}

	@Override
	@Transactional
	public int updateFeeSh(Map map) {
		// 根据订单获取订单状态
		OrderTower order = hbOrderMapper.getOrderTower(Integer.valueOf(map.get(
				"orderid").toString()));
		if (order == null)
			return 0;
		// 获取订单将要修改后的状态
		int towersate = order.getTowerstate();
		int updatetowerstate = ParamerUtil.getUpdateOrderState(towersate,
				Integer.valueOf(map.get("result").toString()));
		// 修改订单状态
		map.put("towerstate", updatetowerstate);
		int count = 0;
		count = hbOrderMapper.updateOrderState(map);
		if (count == 0)
			throw new RuntimeException();
		/*
		 * if (updatetowerstate == 9) { // 修改 成功订单+1 进行中-1 map.put("wxid",
		 * order.getTowerwxid()); map.put("succcount", 1); map.put("ingcount",
		 * -1); count = orderMapper.updateTowerUserOrderInfo(map); }
		 */
		// 插入插入操作日志表
		// tower_sh_doinfo
		SysUserInfo userInfo = sysMapper.getSysUserInfo(Integer.valueOf(map
				.get("adminid").toString()));
		TowerShInfo shInfo = new TowerShInfo();
		shInfo.setOrderid(order.getId());
		shInfo.setOrdername(userInfo.getAdminname());
		shInfo.setOrderuser(userInfo.getAdminname());
		shInfo.setOrderphone(userInfo.getAdminphone());
		shInfo.setOrderstate(updatetowerstate);
		shInfo.setOrdertype(2);
		shInfo.setOrdershinfo((String) map.get("shinfo"));
		count = towerShInfoMapper.insertTowerShInfo(shInfo);

		if (count == 0)
			throw new RuntimeException();
		if (updatetowerstate == 7 || updatetowerstate == 10)
			return 1;

		// 修改总金额和+150积分 +2个经验值
		UserChargeInfo chargeInfo = new UserChargeInfo();
		Score score = new Score();
		score.setWxid(order.getTowerwxid());

		if (updatetowerstate == 9) {
			score.setScorecount(ScoreUtil.fee_end_score);
			score.setScorereason(ScoreUtil.reason_end_fee);

			chargeInfo.setScore(ScoreUtil.fee_end_score);
			chargeInfo.setExperience(ScoreUtil.fee_exp);
			chargeInfo.setGetnow(order.getTowerendfee());
			chargeInfo.setWxid(order.getTowerwxid());
			count = hbOrderMapper.updateUserCharge(chargeInfo);
		} else if (updatetowerstate == 6) {
			score.setScorecount(ScoreUtil.fee_first_score);
			score.setScorereason(ScoreUtil.reason_first_fee);
			// 修改用户金额
			chargeInfo.setScore(ScoreUtil.fee_first_score);
			chargeInfo.setExperience(ScoreUtil.fee_exp);
			chargeInfo.setGetnow(order.getTowerfirstfee());
			chargeInfo.setCharge(order.getTowerfee());
			chargeInfo.setWxid(order.getTowerwxid());
			count = hbOrderMapper.updateUserCharge(chargeInfo);
		}

		if (count == 0)
			throw new RuntimeException();
		// 插入提现来源吧
		TxSource txSource = new TxSource();
		txSource.setOrderid(String.valueOf(order.getId()));
		txSource.setWxid(order.getTowerwxid());
		if (updatetowerstate == 9) {
			txSource.setFee(order.getTowerendfee());
			txSource.setFeetype(1);
		} else if (updatetowerstate == 6) {
			txSource.setFee(order.getTowerfirstfee());
			txSource.setFeetype(0);
		}
		count = hbOrderMapper.insertTxSource(txSource);

		if (count == 0)
			throw new RuntimeException();
		// 插入积分来源表
		count = hbOrderMapper.insertScore(score);
		if (count == 0)
			throw new RuntimeException();
		// 查看用户积分和经验值，与用户级别对别
		chargeInfo = new UserChargeInfo();
		chargeInfo = hbOrderMapper.getUserCharge(order.getTowerwxid());
		int level = ParamerUtil.getUserLevel(chargeInfo.getScore(),
				chargeInfo.getExperience());
		if (level > chargeInfo.getUserlevel()) {
			// 修改用户level
			chargeInfo.setUserlevel(level);
			count = userMapper.updateUserLevle(chargeInfo);
			if (count == 0)
				throw new RuntimeException();
		}
		// 发送用户金额变动信息
		try {
			// 付首款审核审核通过
			if (updatetowerstate == 6) {
				String time = TimeUtil.getNow();
				sendMoneyMsg(order.getTowerwxid(), WXInfo.money_add_title,
						WXInfo.money_add_type_first,
						String.valueOf(order.getTowerfirstfee()), time,
						WXInfo.money_add_remark);
			} else if (updatetowerstate == 9) {
				// 付尾款审核通过
				String time = TimeUtil.getNow();
				sendMoneyMsg(order.getTowerwxid(), WXInfo.money_add_title,
						WXInfo.money_add_type_end,
						String.valueOf(order.getTowerendfee()), time,
						WXInfo.money_add_remark);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 1;
	}

	@Override
	@Transactional
	public int updateOrderYq(Map map) {
		// 根据订单获取订单状态
		OrderTower order = hbOrderMapper.getOrderTower(Integer.valueOf(map.get(
				"orderid").toString()));
		if (order == null)
			return 0;
		// 获取订单将要修改后的状态
		// 修改订单状态
		map.put("towerstate", 12);
		int count = 0;
		count = hbOrderMapper.updateOrderState(map);
		if (count == 0)
			throw new RuntimeException();
		// 插入插入操作日志表
		// tower_sh_doinfo
		SysUserInfo userInfo = sysMapper.getSysUserInfo(Integer.valueOf(map
				.get("adminid").toString()));
		TowerShInfo shInfo = new TowerShInfo();
		shInfo.setOrderid(order.getId());
		shInfo.setOrdername(userInfo.getAdminname());
		shInfo.setOrderuser(userInfo.getAdminname());
		shInfo.setOrderphone(userInfo.getAdminphone());
		shInfo.setOrderstate(12);
		shInfo.setOrdertype(2);
		shInfo.setOrdershinfo((String) map.get("shinfo"));
		count = towerShInfoMapper.insertTowerShInfo(shInfo);

		if (count == 0)
			throw new RuntimeException();

		return 1;
	}

	@Override
	@Transactional
	public int updateOrderShjj(Map map) {
		// 根据订单获取订单状态
		OrderTower order = hbOrderMapper.getOrderTower(Integer.valueOf(map.get(
				"orderid").toString()));
		if (order == null)
			return 0;
		// 获取订单将要修改后的状态
		// 修改订单状态
		map.put("towerstate", 3);
		int count = 0;
		count = hbOrderMapper.updateOrderState(map);
		if (count == 0)
			throw new RuntimeException();
		// 站点还原
		map.put("towerid", order.getTowerid());
		map.put("towerstate", 0);
		count = hbOrderMapper.updateTowerState(map);
		if (count == 0)
			throw new RuntimeException();
		// 修改抢单数 失败+1 成功数-1
		map.put("wxid", order.getTowerwxid());
		map.put("failcount", 1);
		map.put("succcount", -1);
		map.put("rejectcount", 1);
		count = hbOrderMapper.updateTowerUserOrderInfo(map);
		try {
			// 修改站址统计表
			// 站址拒绝+1
			int towerid = Integer.valueOf(order.getTowerid());
			count = reportTowerMapper.checkTowerExist(towerid);
			TowerReport report = new TowerReport();
			report.setTowerid(towerid);
			report.setRejectcount(1);
			if (count == 0) {
				reportTowerMapper.insertTowerReport(report);
			} else {
				reportTowerMapper.upateTowerReport(report);

			}
		} catch (Exception e) {
		}

		if (count == 0)
			throw new RuntimeException();
		// 插入插入操作日志表
		// tower_sh_doinfo
		SysUserInfo userInfo = sysMapper.getSysUserInfo(Integer.valueOf(map
				.get("adminid").toString()));
		TowerShInfo shInfo = new TowerShInfo();
		shInfo.setOrderid(order.getId());
		shInfo.setOrdername(userInfo.getAdminname());
		shInfo.setOrderuser(userInfo.getAdminname());
		shInfo.setOrderphone(userInfo.getAdminphone());
		shInfo.setOrderstate(3);
		shInfo.setOrdertype(2);
		shInfo.setOrdershinfo((String) map.get("shinfo"));
		count = towerShInfoMapper.insertTowerShInfo(shInfo);

		if (count == 0)
			throw new RuntimeException();

		return 1;
	}

	@Override
	public List<Map> getDcOrders(OrderPage orderPage) {
		return hbOrderMapper.getDcOrders(orderPage);
	}

	@Override
	public List<Map> getCheckCancelOrder() {
		return hbOrderMapper.getCheckCancelOrder();
	}

	@Override
	@Transactional
	public int updateOrderCancel(Map map) {
		String wxid = (String) map.get("towerwxid");
		// 修改订单状态
		int count = 0;
		map.put("towerstate", 11);
		count = hbOrderMapper.updateOrderState(map);
		if (count == 0)
			return 0;
		// 获取用户抢单统计表
		UserOrderInfo userOrderInfo = hbOrderMapper.getUserOrderInfo(wxid);
		// 修改用户抢单统计表 失败数+1、连续弃单数+1、进行中订单数-1,超时弃单数+1
		count = hbOrderMapper.updateUserCancelOrderCount(map);
		if (count == 0)
			throw new RuntimeException();
		int core = 0;
		// 积分来源统计
		List<Score> scores = new ArrayList<Score>();
		if (userOrderInfo.getCancelcount() >= 1) {
			// 自第二次起 弃单-100
			core += ScoreUtil.score_cancel_order;
			Score score = new Score();
			score.setWxid(wxid);
			score.setScorecount(ScoreUtil.score_cancel_order);
			score.setScorereason(ScoreUtil.reason_cancel_order);
			scores.add(score);
		}
		if (userOrderInfo.getCancelcount() != 0
				&& (userOrderInfo.getCancelcount() + 1) % 5 == 0) {
			// 弃单累计5次-10
			core += ScoreUtil.score_con_cancel_order;
			Score score = new Score();
			score.setWxid(wxid);
			score.setScorecount(ScoreUtil.score_con_cancel_order);
			score.setScorereason(ScoreUtil.reason_con_cancel_order);
			scores.add(score);

		}
		// 修改用户积分
		if (core < 0) {
			// 修改用户钱包中的积分
			UserChargeInfo chargeInfo = new UserChargeInfo();
			chargeInfo.setScore(core);
			chargeInfo.setWxid(wxid);
			count = hbOrderMapper.updateUserCharge(chargeInfo);
			if (count == 0)
				throw new RuntimeException();
			// 插入积分来源表
			for (Score score : scores) {
				count = hbOrderMapper.insertScore(score);
				if (count == 0)
					throw new RuntimeException();
			}
		}
		// 修改站址状态为0 ，为可抢状态
		map.put("towerstate", 0);
		count = hbOrderMapper.updateTowerState(map);
		if (count == 0)
			throw new RuntimeException();

		return 0;
	}

	private void sendMoneyMsg(String wxid, String first, String key1,
			String key2, String key3, String remark) {
		// 发送订单提交消息模板
		// 获取管理员微信ID
		boolean sendResult = WXCommon.sendMoneyMsg(wxid, first, key1, key2,
				key3, remark);

	}

	private void sendShOrderSuccessMsg(int orderid, String wxid, String first,
			String key1, String key2, String remark) {
		// 发送订单提交消息模板
		// 获取管理员微信ID
		boolean sendResult = WXCommon.sendShOrderSuccessMsg(orderid, wxid,
				first, key1, key2, remark);

	}
	
	private void sendShOrderYQMsg(int orderid, String towername,String wxid, String first,
			String key1, String key2, String remark) {
		// 发送订单提交消息模板
		// 获取管理员微信ID
		key2=String.format(key2, towername);
		boolean sendResult = WXCommon.sendShOrderYQMsg(orderid, wxid,
				first, key1, key2, remark);

	}

}
