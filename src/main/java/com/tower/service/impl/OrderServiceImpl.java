package com.tower.service.impl;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.UUID;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tower.common.Config;
import com.tower.common.bean.Order;
import com.tower.common.bean.OrderInfo;
import com.tower.common.bean.OrderPage;
import com.tower.common.bean.OrderState;
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
import com.tower.common.util.MoneyUtil;
import com.tower.common.util.PageUtil;
import com.tower.common.util.ParamerUtil;
import com.tower.common.util.PowerUtil;
import com.tower.common.util.ScoreUtil;
import com.tower.common.util.StrUtil;
import com.tower.common.util.TemplateUtil;
import com.tower.common.util.TimeUtil;
import com.tower.common.wxmsg.WXCommon;
import com.tower.common.wxmsg.WXInfo;
import com.tower.mapper.LoginAndSysMapper;
import com.tower.mapper.ManagerMapper;
import com.tower.mapper.OrderEvaluateMapper;
import com.tower.mapper.OrderMapper;
import com.tower.mapper.ReportTowerMapper;
import com.tower.mapper.TowerShInfoMapper;
import com.tower.mapper.TowersMapper;
import com.tower.mapper.UserMapper;
import com.tower.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private TowerShInfoMapper towerShInfoMapper;

	@Autowired
	private LoginAndSysMapper sysMapper;

	@Autowired
	private ReportTowerMapper reportTowerMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private OrderEvaluateMapper evaluateMapper;

	@Autowired
	private ManagerMapper managerMapper;
	
	@Autowired
	private TowersMapper towersMapper;

	/**
	 * 获取订单列表
	 */
	@Override
	public List<Order> getOrders(OrderPage orderPage) {
		int count = orderMapper.getOrdersCount(orderPage);
		orderPage.setPagerowtotal(count);
		return orderMapper.getOrders(orderPage);

	}

	/**
	 * 获取订单详情
	 */
	@Override
	public void getOrderInfo(OrderPage orderPage) {
		// 根据订单获取订单信息
		OrderTower order = orderMapper.getOrderTower(orderPage.getOrderid());
		if (order == null)
			return;
		// 获取用户信息
		OrderUser orderUser = orderMapper.getOrderUser(order.getTowerwxid());
		orderPage.setOrdertower(order);
		orderPage.setOrderuser(orderUser);
		// 获取操作日志

		List<TowerShInfo> shs = new ArrayList();
		shs = towerShInfoMapper.getShInfos(order.getId());
		orderPage.setShinfos(shs);

	}

	/**
	 * 订单审核（延期审核、目标站址审核）
	 */
	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public synchronized  int shOrder(Map map) {
		// 根据订单获取订单状态
		OrderTower order = orderMapper.getOrderTower(Integer.valueOf(map.get(
				"orderid").toString()));
		if (order == null)
			return 0;
		// 获取订单将要修改后的状态
		int towersate = order.getTowerstate();
		int updatetowerstate = ParamerUtil.getUpdateOrderState(towersate,
				Integer.valueOf(map.get("result").toString()));
		if(updatetowerstate==-1||updatetowerstate==towersate)
			return 0;
		// 修改订单状态
		map.put("towerstate", updatetowerstate);
		int count = 0;
		count = orderMapper.updateOrderState(map);
		if (count == 0)
			throw new RuntimeException();
		// 站点还原
		if (updatetowerstate == 3 || updatetowerstate == 18) {
			
			map.put("towerid", order.getTowerid());
			map.put("towerstate", 0);
			count = orderMapper.updateTowerState(map);
			if (count == 0)
				throw new RuntimeException();
			// 修改抢单数 失败+1 进行中-1 审核拒绝+1
			map.put("wxid", order.getTowerwxid());
			map.put("failcount", 1);
			map.put("ingcount", -1);
			map.put("rejectcount", 1);
			count = orderMapper.updateTowerUserOrderInfo(map);
			if (count == 0)
				throw new RuntimeException();

			try {
				// 修改站址统计表
				// 站址拒绝次数+1
				//连续失败次数>=1 即连本次超过两次
				int conrejectcount=1;
				if(order.getConrejectcount()>=Config.conrejectcount){
					conrejectcount=0-order.getConrejectcount();
					//降级处理
					if(order.getTowerlevelid()<4){
						Map mapLevel=new HashMap();
						mapLevel.put("towerid", order.getTowerid());
						mapLevel.put("towerlevel", Config.jjtowerlevel);
						count=towersMapper.updateTowerLevel(mapLevel);
						if (count == 0)
							throw new RuntimeException();
					}
				}
				int towerid = Integer.valueOf(order.getTowerid());
				count = reportTowerMapper.checkTowerExist(towerid);
				TowerReport report = new TowerReport();
				report.setTowerid(towerid);
				report.setRejectcount(1);
				report.setConrejectcount(conrejectcount);
				if (count == 0) {
					reportTowerMapper.insertTowerReport(report);
				} else {
					reportTowerMapper.upateTowerReport(report);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
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
		if (updatetowerstate == 21 || updatetowerstate == 12) {
			Integer yqcount = 0;
			try {
				yqcount = ((Integer) map.get("yqcount"));
				if (yqcount != null) {
					yqcount = yqcount / 24;
				}
			} catch (Exception e) {
			}
			shInfo.setOrdershinfo("延期天数" + yqcount + "天         "
					+ (String) map.get("shinfo"));
		} else {
			shInfo.setOrdershinfo((String) map.get("shinfo"));
		}
		shInfo.setOrdertype(ParamerUtil.xihuaPower(Integer.valueOf(map.get("adminpower").toString()),updatetowerstate));
		count = towerShInfoMapper.insertTowerShInfo(shInfo);

		if (count == 0)
			throw new RuntimeException();
		// 发送审核通过信息
		try {
			String time = TimeUtil.getNow();
			if (updatetowerstate == 12) {
				sendShOrderYQMsg(order.getId(), order.getTowername(),
						order.getTowerwxid(), WXInfo.order_sh_yq_success_title,
						order.getOrderid(), time,
						WXInfo.order_sh_yq_success_remark);

			} else if (updatetowerstate == 14) {
				sendShOrderYQMsg(order.getId(), order.getTowername(),
						order.getTowerwxid(), WXInfo.order_sh_yq_error_title,
						order.getOrderid(), time,
						WXInfo.order_sh_yq_error_remark);
			} else if (updatetowerstate == 19) {
				sendShOrderYQMsg(order.getId(), order.getTowername(),
						order.getTowerwxid(),
						WXInfo.order_sh_address_success_title,
						order.getOrderid(), time,
						WXInfo.order_sh_address_success_remark);

			} else if (updatetowerstate == 18) {
				sendShOrderYQMsg(order.getId(), order.getTowername(),
						order.getTowerwxid(),
						WXInfo.order_sh_address_error_title,
						order.getOrderid(), time,
						WXInfo.order_sh_address_error_remark);
			}else if(updatetowerstate==17){
				Map mapArea=new HashMap();
				mapArea.put("powers", new String[]{"12","14","15"});
				mapArea.put("city", order.getTowercityid());
				sendMsg(Integer.valueOf(order.getId()), mapArea,
						WXInfo.task_address_order_title,
						WXInfo.task_address_order_name,
						WXInfo.task_address_order_type,
						WXInfo.task_address_order_remark);
			}else if(updatetowerstate==21){
				Map mapArea=new HashMap();
				mapArea.put("powers", new String[]{"12","14","15"});
				mapArea.put("city", order.getTowercityid());
				sendMsg(Integer.valueOf(order.getId()), mapArea,
						WXInfo.task_yq_order_title,
						WXInfo.task_yq_order_name,
						WXInfo.task_yq_order_type,
						WXInfo.task_yq_order_remark);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return 1;
	}

	/**
	 * 审核拒绝
	 */
	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public synchronized  int updateOrderShjj(Map map) {
		// 根据订单获取订单状态
		OrderTower order = orderMapper.getOrderTower(Integer.valueOf(map.get(
				"orderid").toString()));
		if (order == null)
			return 0;
		// 获取订单将要修改后的状态
		// 修改订单状态
		int state = order.getTowerstate();
		int updateState = ParamerUtil.getUpdateOrderState(state, 0);
		if(updateState==-1||updateState==state)
			return 0;
		map.put("towerstate", updateState);
		int count = 0;
		count = orderMapper.updateOrderState(map);
		if (count == 0)
			throw new RuntimeException();
		// 站点还原
		map.put("towerid", order.getTowerid());
		map.put("towerstate", 0);
		count = orderMapper.updateTowerState(map);
		if (count == 0)
			throw new RuntimeException();
		// 修改抢单数 审核拒绝 +1 拒绝+1
		map.put("wxid", order.getTowerwxid());
		map.put("failcount", 1);
		map.put("succcount", 0);
		map.put("rejectcount", 1);
		if (updateState == 23) {
			map.put("ingcount", 0);
		} else {
			map.put("ingcount", -1);
		}
		count = orderMapper.updateTowerUserOrderInfo(map);
		try {
			// 修改站址统计表
			// 站址拒绝+1
			//连续失败次数>=1 即连本次超过两次
			int conrejectcount=1;
			if(order.getConrejectcount()>=Config.conrejectcount){
				conrejectcount=0-order.getConrejectcount();
				//降级处理
				if(order.getTowerlevelid()<4){
					Map mapLevel=new HashMap();
					mapLevel.put("towerid", order.getTowerid());
					mapLevel.put("towerlevel", Config.jjtowerlevel);
					count=towersMapper.updateTowerLevel(mapLevel);
					if (count == 0)
						throw new RuntimeException();
				}
			}
			int towerid = Integer.valueOf(order.getTowerid());
			count = reportTowerMapper.checkTowerExist(towerid);
			TowerReport report = new TowerReport();
			report.setTowerid(towerid);
			report.setRejectcount(1);
			report.setConrejectcount(conrejectcount);
			if (count == 0) {
				reportTowerMapper.insertTowerReport(report);
			} else {
				reportTowerMapper.upateTowerReport(report);

			}
		} catch (Exception e) {
			e.printStackTrace();
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
		shInfo.setOrderstate(updateState);
		shInfo.setOrdertype(ParamerUtil.xihuaPower(Integer.valueOf(map.get("adminpower").toString()),updateState));
		shInfo.setOrdershinfo((String) map.get("shinfo"));
		count = towerShInfoMapper.insertTowerShInfo(shInfo);

		if (count == 0)
			throw new RuntimeException();

		return 1;
	}

	/*
	 * @Override
	 * 
	 * @Transactional public int updateTowerFirstFeeAply(Map map) { //
	 * 根据订单获取订单状态 OrderTower order =
	 * orderMapper.getOrderTower(Integer.valueOf(map.get(
	 * "orderid").toString())); if (order == null) return 0;
	 * 
	 * // 修改订单表 map.put("towerstate", 5); int count =
	 * orderMapper.updateOrderState(map); if (count == 0) throw new
	 * RuntimeException(); SysUserInfo userInfo =
	 * sysMapper.getSysUserInfo(Integer.valueOf(map
	 * .get("adminid").toString())); TowerShInfo shInfo = new TowerShInfo();
	 * shInfo.setOrderid(order.getId());
	 * shInfo.setOrdername(userInfo.getAdminname());
	 * shInfo.setOrderuser(userInfo.getAdminname());
	 * shInfo.setOrderphone(userInfo.getAdminphone()); shInfo.setOrderstate(5);
	 * shInfo.setOrdertype(2); shInfo.setOrdershinfo((String)
	 * map.get("shinfo")); count = towerShInfoMapper.insertTowerShInfo(shInfo);
	 * if (count == 0) throw new RuntimeException();
	 * 
	 * return 1; }
	 * 
	 * @Override
	 * 
	 * @Transactional public int updateTowerEndFeeAply(Map map) { // 根据订单获取订单状态
	 * OrderTower order = orderMapper.getOrderTower(Integer.valueOf(map.get(
	 * "orderid").toString())); if (order == null) return 0;
	 * 
	 * // 修改订单表 map.put("towerstate", 8); map.put("endfee", order.getTowerfee()
	 * - order.getTowerfirstfee()); int count =
	 * orderMapper.updateOrderState(map); if (count == 0) throw new
	 * RuntimeException(); SysUserInfo userInfo =
	 * sysMapper.getSysUserInfo(Integer.valueOf(map
	 * .get("adminid").toString())); TowerShInfo shInfo = new TowerShInfo();
	 * shInfo.setOrderid(order.getId());
	 * shInfo.setOrdername(userInfo.getAdminname());
	 * shInfo.setOrderuser(userInfo.getAdminname());
	 * shInfo.setOrderphone(userInfo.getAdminphone()); shInfo.setOrderstate(8);
	 * shInfo.setOrdertype(2); shInfo.setOrdershinfo((String)
	 * map.get("shinfo")); count = towerShInfoMapper.insertTowerShInfo(shInfo);
	 * if (count == 0) throw new RuntimeException();
	 * 
	 * return 1; }
	 * 
	 * @Override
	 * 
	 * @Transactional public int updateFeeSh(Map map) { // 根据订单获取订单状态 OrderTower
	 * order = orderMapper.getOrderTower(Integer.valueOf(map.get(
	 * "orderid").toString())); if (order == null) return 0; // 获取订单将要修改后的状态 int
	 * towersate = order.getTowerstate(); int updatetowerstate =
	 * ParamerUtil.getUpdateOrderState(towersate,
	 * Integer.valueOf(map.get("result").toString())); // 修改订单状态
	 * map.put("towerstate", updatetowerstate); int count = 0; count =
	 * orderMapper.updateOrderState(map); if (count == 0) throw new
	 * RuntimeException(); // 插入插入操作日志表 // tower_sh_doinfo SysUserInfo userInfo
	 * = sysMapper.getSysUserInfo(Integer.valueOf(map
	 * .get("adminid").toString())); TowerShInfo shInfo = new TowerShInfo();
	 * shInfo.setOrderid(order.getId());
	 * shInfo.setOrdername(userInfo.getAdminname());
	 * shInfo.setOrderuser(userInfo.getAdminname());
	 * shInfo.setOrderphone(userInfo.getAdminphone());
	 * shInfo.setOrderstate(updatetowerstate); shInfo.setOrdertype(2);
	 * shInfo.setOrdershinfo((String) map.get("shinfo")); count =
	 * towerShInfoMapper.insertTowerShInfo(shInfo);
	 * 
	 * if (count == 0) throw new RuntimeException(); if (updatetowerstate == 7
	 * || updatetowerstate == 10) return 1;
	 * 
	 * // 修改总金额和+150积分 +2个经验值 UserChargeInfo chargeInfo = new UserChargeInfo();
	 * Score score = new Score(); score.setWxid(order.getTowerwxid());
	 * 
	 * if (updatetowerstate == 9) {
	 * score.setScorecount(ScoreUtil.fee_end_score);
	 * score.setScorereason(ScoreUtil.reason_end_fee);
	 * 
	 * chargeInfo.setScore(ScoreUtil.fee_end_score);
	 * chargeInfo.setExperience(ScoreUtil.fee_exp);
	 * chargeInfo.setGetnow(order.getTowerendfee());
	 * chargeInfo.setWxid(order.getTowerwxid()); count =
	 * orderMapper.updateUserCharge(chargeInfo); } else if (updatetowerstate ==
	 * 6) { score.setScorecount(ScoreUtil.fee_first_score);
	 * score.setScorereason(ScoreUtil.reason_first_fee); // 修改用户金额
	 * chargeInfo.setScore(ScoreUtil.fee_first_score);
	 * chargeInfo.setExperience(ScoreUtil.fee_exp);
	 * chargeInfo.setGetnow(order.getTowerfirstfee());
	 * chargeInfo.setCharge(order.getTowerfee());
	 * chargeInfo.setWxid(order.getTowerwxid()); count =
	 * orderMapper.updateUserCharge(chargeInfo); }
	 * 
	 * if (count == 0) throw new RuntimeException(); // 插入提现来源吧 TxSource
	 * txSource = new TxSource();
	 * txSource.setOrderid(String.valueOf(order.getId()));
	 * txSource.setWxid(order.getTowerwxid()); if (updatetowerstate == 9) {
	 * txSource.setFee(order.getTowerendfee()); txSource.setFeetype(1); } else
	 * if (updatetowerstate == 6) { txSource.setFee(order.getTowerfirstfee());
	 * txSource.setFeetype(0); } count = orderMapper.insertTxSource(txSource);
	 * 
	 * if (count == 0) throw new RuntimeException(); // 插入积分来源表 count =
	 * orderMapper.insertScore(score); if (count == 0) throw new
	 * RuntimeException(); // 查看用户积分和经验值，与用户级别对别 chargeInfo = new
	 * UserChargeInfo(); chargeInfo =
	 * orderMapper.getUserCharge(order.getTowerwxid()); int level =
	 * ParamerUtil.getUserLevel(chargeInfo.getScore(),
	 * chargeInfo.getExperience()); if (level > chargeInfo.getUserlevel()) { //
	 * 修改用户level chargeInfo.setUserlevel(level); count =
	 * userMapper.updateUserLevle(chargeInfo); if (count == 0) throw new
	 * RuntimeException(); } // 发送用户金额变动信息 try { // 付首款审核审核通过 if
	 * (updatetowerstate == 6) { String time = TimeUtil.getNow();
	 * sendMoneyMsg(order.getTowerwxid(), WXInfo.money_add_title,
	 * WXInfo.money_add_type_first, String.valueOf(order.getTowerfirstfee()),
	 * time, WXInfo.money_add_remark); } else if (updatetowerstate == 9) { //
	 * 付尾款审核通过 String time = TimeUtil.getNow();
	 * sendMoneyMsg(order.getTowerwxid(), WXInfo.money_add_title,
	 * WXInfo.money_add_type_end, String.valueOf(order.getTowerendfee()), time,
	 * WXInfo.money_add_remark); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return 1; }
	 * 
	 * @Override
	 * 
	 * @Transactional public int updateOrderYq(Map map) { // 根据订单获取订单状态
	 * OrderTower order = orderMapper.getOrderTower(Integer.valueOf(map.get(
	 * "orderid").toString())); if (order == null) return 0; // 获取订单将要修改后的状态 //
	 * 修改订单状态 map.put("towerstate", 12); int count = 0; count =
	 * orderMapper.updateOrderState(map); if (count == 0) throw new
	 * RuntimeException(); // 插入插入操作日志表 // tower_sh_doinfo SysUserInfo userInfo
	 * = sysMapper.getSysUserInfo(Integer.valueOf(map
	 * .get("adminid").toString())); TowerShInfo shInfo = new TowerShInfo();
	 * shInfo.setOrderid(order.getId());
	 * shInfo.setOrdername(userInfo.getAdminname());
	 * shInfo.setOrderuser(userInfo.getAdminname());
	 * shInfo.setOrderphone(userInfo.getAdminphone()); shInfo.setOrderstate(12);
	 * shInfo.setOrdertype(2); shInfo.setOrdershinfo((String)
	 * map.get("shinfo")); count = towerShInfoMapper.insertTowerShInfo(shInfo);
	 * 
	 * if (count == 0) throw new RuntimeException();
	 * 
	 * return 1; }
	 * 
	 * @Override
	 * 
	 * @Transactional public int updateOrderShjj(Map map) { // 根据订单获取订单状态
	 * OrderTower order = orderMapper.getOrderTower(Integer.valueOf(map.get(
	 * "orderid").toString())); if (order == null) return 0; // 获取订单将要修改后的状态 //
	 * 修改订单状态 map.put("towerstate", 3); int count = 0; count =
	 * orderMapper.updateOrderState(map); if (count == 0) throw new
	 * RuntimeException(); // 站点还原 map.put("towerid", order.getTowerid());
	 * map.put("towerstate", 0); count = orderMapper.updateTowerState(map); if
	 * (count == 0) throw new RuntimeException(); // 修改抢单数 失败+1 成功数-1
	 * map.put("wxid", order.getTowerwxid()); map.put("failcount", 1);
	 * map.put("succcount", -1); map.put("rejectcount", 1); count =
	 * orderMapper.updateTowerUserOrderInfo(map); try { // 修改站址统计表 // 站址拒绝+1 int
	 * towerid = Integer.valueOf(order.getTowerid()); count =
	 * reportTowerMapper.checkTowerExist(towerid); TowerReport report = new
	 * TowerReport(); report.setTowerid(towerid); report.setRejectcount(1); if
	 * (count == 0) { reportTowerMapper.insertTowerReport(report); } else {
	 * reportTowerMapper.upateTowerReport(report);
	 * 
	 * } } catch (Exception e) { }
	 * 
	 * if (count == 0) throw new RuntimeException(); // 插入插入操作日志表 //
	 * tower_sh_doinfo SysUserInfo userInfo =
	 * sysMapper.getSysUserInfo(Integer.valueOf(map
	 * .get("adminid").toString())); TowerShInfo shInfo = new TowerShInfo();
	 * shInfo.setOrderid(order.getId());
	 * shInfo.setOrdername(userInfo.getAdminname());
	 * shInfo.setOrderuser(userInfo.getAdminname());
	 * shInfo.setOrderphone(userInfo.getAdminphone()); shInfo.setOrderstate(3);
	 * shInfo.setOrdertype(2); shInfo.setOrdershinfo((String)
	 * map.get("shinfo")); count = towerShInfoMapper.insertTowerShInfo(shInfo);
	 * 
	 * if (count == 0) throw new RuntimeException();
	 * 
	 * return 1; }
	 */

	/**
	 * 导出订单
	 */
	@Override
	public List<Map> getDcOrders(OrderPage orderPage) {
		return orderMapper.getDcOrders(orderPage);
	}

	/**
	 * 获取超时订单
	 */
	@Override
	public List<Map> getCheckCancelOrder() {
		return orderMapper.getCheckCancelOrder();
	}

	/**
	 * 超时订单处理
	 */
	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public synchronized int updateOrderCancel(Map map) {
		String wxid = (String) map.get("towerwxid");
		// 根据订单获取订单状态
		OrderTower order = orderMapper.getOrderTower(Integer.valueOf(map.get(
				"orderid").toString()));
		if(order==null||(order.getTowerstate()!=0 && order.getTowerstate()!=12 &&order.getTowerstate()!=14&&order.getTowerstate()!=19))
			return 0;
		// 修改订单状态
		int count = 0;
		map.put("towerstate", 11);
		count = orderMapper.updateOrderState(map);
		System.out.println("updateOrderState:"+count);
		if (count == 0)
			return 0;
		System.out.println("11122333");
		// 获取用户抢单统计表
		UserOrderInfo userOrderInfo = orderMapper.getUserOrderInfo(wxid);
		System.out.println("1112233366666");
		// 修改用户抢单统计表 失败数+1、连续弃单数+1、,超时弃单数+1
		count = orderMapper.updateUserCancelOrderCount(map);
		
		System.out.println("updateUserCancelOrderCount:"+count);
		// 如果累计10次逾期 则冻结用户
		if (userOrderInfo.getTimecount() > 0
				&& (userOrderInfo.getTimecount() + 1) % 10 == 0) {
			Map mapUser = new HashMap();
			mapUser.put("state", 0);
			mapUser.put("wxid",wxid);
			count = userMapper.updateuserstate(mapUser);
			if (count == 0)
				throw new RuntimeException();
		}
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
			count = orderMapper.updateUserCharge(chargeInfo);
			if (count == 0)
				throw new RuntimeException();
			// 插入积分来源表
			for (Score score : scores) {
				count = orderMapper.insertScore(score);
				if (count == 0)
					throw new RuntimeException();
			}
		}
		// 修改站址状态为0 ，为可抢状态
		map.put("towerstate", 0);
		count = orderMapper.updateTowerState(map);
		
		System.out.println("updateTowerState:"+count);
		if (count == 0)
			throw new RuntimeException();
		// 修改站址统计表中的超时弃单数+1
		//连续失败次数>=1 即连本次超过两次
		int conrejectcount=1;
		if(order.getConrejectcount()>=Config.conrejectcount){
			conrejectcount=0-order.getConrejectcount();
			//降级处理
			if(order.getTowerlevelid()<4){
				Map mapLevel=new HashMap();
				mapLevel.put("towerid", order.getTowerid());
				mapLevel.put("towerlevel", Config.jjtowerlevel);
				count=towersMapper.updateTowerLevel(mapLevel);
				if (count == 0)
					throw new RuntimeException();
			}
		}
		int towerid = Integer.valueOf(map.get("towerid").toString());
		count = reportTowerMapper.checkTowerExist(towerid);
		TowerReport report = new TowerReport();
		report.setTowerid(towerid);
		report.setTimecount(1);
		report.setConrejectcount(conrejectcount);
		System.out.println("checkTowerExist:"+count);
		if (count == 0) {
			reportTowerMapper.insertTowerReport(report);
		} else {
			reportTowerMapper.upateTowerReport(report);

		}

		// 写日志
		// 插入插入操作日志表
		// tower_sh_doinfo
		try {
			
	
		UserInfo userInfo = userMapper.getUserDetail(wxid);
		TowerShInfo shInfo = new TowerShInfo();
		shInfo.setOrderid(Integer.valueOf(map.get(
				"orderid").toString()));
		shInfo.setOrdername(userInfo.getUsername());
		shInfo.setOrderuser(userInfo.getUsername());
		shInfo.setOrderphone(userInfo.getUsertele());
		shInfo.setOrderstate(11);
		shInfo.setOrdertype(0);
		count = towerShInfoMapper.insertTowerShInfo(shInfo);
		System.out.println("insertTowerShInfo:"+count);
		if (count == 0)
			throw new RuntimeException();
		} catch (Exception e) {
			System.out.println("ERROR:"+wxid);
		}
		return count;
	}

	/**
	 * 租赁合同申请
	 */
	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public synchronized int updateTowerRentHtAply(int adminid, String shinfo,
			OrderInfo orderInfo, int adminpower) {
		// 根据订单获取订单状态
		OrderTower order = orderMapper.getOrderTower(orderInfo.getId());
		if (order == null ||order.getTowerstate()!=2)
			return 0;

		// 修改订单表
		orderInfo.setTowerstate(5);
		int count = orderMapper.updateOrderByRentHt(orderInfo);
		if (count == 0)
			throw new RuntimeException();
		SysUserInfo userInfo = sysMapper.getSysUserInfo(adminid);
		TowerShInfo shInfo = new TowerShInfo();
		shInfo.setOrderid(order.getId());
		shInfo.setOrdername(userInfo.getAdminname());
		shInfo.setOrderuser(userInfo.getAdminname());
		shInfo.setOrderphone(userInfo.getAdminphone());
		shInfo.setOrderstate(5);
		shInfo.setOrdertype(ParamerUtil.xihuaPower(adminpower,5));
		shInfo.setOrdershinfo(shinfo);
		count = towerShInfoMapper.insertTowerShInfo(shInfo);
		if (count == 0)
			throw new RuntimeException();

		return 1;
	}

	/**
	 * 三方合同申请
	 */
	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public synchronized  int updateTowerThreeHtAply(String templatePath,String threePath,int adminid, String shinfo,
			OrderInfo orderInfo, int adminpower) {
		// 根据订单获取订单状态
		OrderTower order = orderMapper.getOrderTower(orderInfo.getId());
		if (order == null||(order.getTowerstate()!=5&&order.getTowerstate()!=22) )
			return 0;
	
		//金额格式化
		String money=MoneyUtil.digitUppercase(orderInfo.getTowerfactfee())+"("+orderInfo.getTowerfactfee()+")";
		
		//生成三方合同文件
		Map map=new HashMap();
		map.put("towername", order.getTowername());
		map.put("towerfactaddress", order.getTowerfactaddress());
		map.put("towerfactfee",money);
		map.put("towertime", TimeUtil.getNowDay());
		String filename=UUID.randomUUID().toString().replace("-", "")+".doc";
		String imagePath="\\shanghai_towerfile\\images\\order\\ht\\"+filename;
		if(!TemplateUtil.toPreview( templatePath, threePath+filename, StrUtil.three_ht_name, map)){
			throw new RuntimeException();
		}   
		orderInfo.setTowerthreeht(imagePath);

		

		// 修改订单表
		orderInfo.setTowerstate(7);
		int count = orderMapper.updateOrderByThreeHt(orderInfo);
		if (count == 0)
			throw new RuntimeException();
		SysUserInfo userInfo = sysMapper.getSysUserInfo(adminid);
		TowerShInfo shInfo = new TowerShInfo();
		shInfo.setOrderid(order.getId());
		shInfo.setOrdername(userInfo.getAdminname());
		shInfo.setOrderuser(userInfo.getAdminname());
		shInfo.setOrderphone(userInfo.getAdminphone());
		shInfo.setOrderstate(7);
		shInfo.setOrdertype(ParamerUtil.xihuaPower(adminpower,7));
		shInfo.setOrdershinfo(shinfo);
		count = towerShInfoMapper.insertTowerShInfo(shInfo);
		if (count == 0)
			throw new RuntimeException();
		// 发送给建维经理
		try {
			Map mapTower = new HashMap();
			mapTower.put("city", order.getTowercityid());
			mapTower.put("powers", new int[] { 4, 13, 14, 15 });
			List<SysUserInfo> admins = getAdminWxByCity(mapTower);
			for (SysUserInfo sysUserInfo : admins) {
				WXCommon.sendShOrderThreeMsg(order.getId(),
						sysUserInfo.getAdminwxid());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 1;
	}

	/**
	 * 三方合同审核
	 */

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public synchronized int updateTowerThreeSh(Map map) {
		// 根据订单获取订单状态
		OrderTower order = orderMapper.getOrderTower(Integer.valueOf(map.get(
				"orderid").toString()));
		if (order == null||order.getTowerstate()!=7)
			return 0;
		// 获取订单将要修改后的状态
		int towersate = order.getTowerstate();
		int updatetowerstate = ParamerUtil.getUpdateOrderState(towersate,
				Integer.valueOf(map.get("result").toString()));
		if(towersate==updatetowerstate||updatetowerstate==-1)
			return 0;
		// 修改订单状态
		map.put("towerstate", updatetowerstate);
		int count = 0;
		count = orderMapper.updateOrderState(map);
		if (count == 0)
			throw new RuntimeException();
		// 站点还原
		if (updatetowerstate == 4) {
			// 修改用户订单 成功数+1，进行中-1
			map.put("wxid", order.getTowerwxid());
			map.put("succcount", 1);
			map.put("ingcount", -1);
			count = orderMapper.updateTowerUserOrderInfo(map);
			if (count == 0)
				throw new RuntimeException();
		}

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
		shInfo.setOrdertype(ParamerUtil.xihuaPower(Integer.valueOf(map.get("adminpower").toString()),updatetowerstate));
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
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	/**
	 * 付款申请
	 */
	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public synchronized  int updateTowerFeeAply(Map map) {
		// 根据订单获取订单状态
		OrderTower order = orderMapper.getOrderTower(Integer.valueOf(map.get(
				"orderid").toString()));
		if (order == null||(order.getTowerstate()!=4&&order.getTowerstate()!=10))
			return 0;
		// 修改订单表
		map.put("towerstate", 8);
		int count = orderMapper.updateOrderState(map);
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
		shInfo.setOrdertype(ParamerUtil.xihuaPower(Integer.valueOf(map.get("adminpower").toString()),8));
		shInfo.setOrdershinfo((String) map.get("shinfo"));
		count = towerShInfoMapper.insertTowerShInfo(shInfo);
		if (count == 0)
			throw new RuntimeException();

		/*
		 * // 发送给建维经理 try { Map mapTower = new HashMap(); mapTower.put("city",
		 * order.getTowercityid()); mapTower.put("powers", new int[]
		 * {4,13,14,15}); List<SysUserInfo> admins = getAdminWxByCity(mapTower);
		 * for (SysUserInfo sysUserInfo : admins) {
		 * WXCommon.sendShOrderThreeMsg(order.getId(),
		 * sysUserInfo.getAdminwxid());
		 * 
		 * }
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 */

		return 1;
	}

	/**
	 * 付款审核
	 */
	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public synchronized int updateTowerFeeSh(Map map) {
		// 根据订单获取订单状态
		OrderTower order = orderMapper.getOrderTower(Integer.valueOf(map.get(
				"orderid").toString()));
		if (order == null)
			return 0;
		// 获取订单将要修改后的状态
		int towersate = order.getTowerstate();
		int updatetowerstate = ParamerUtil.getUpdateOrderState(towersate,
				Integer.valueOf(map.get("result").toString()));
		if(towersate==updatetowerstate||(updatetowerstate!=9&&updatetowerstate!=10))
			return  0;
		// 修改订单状态
		map.put("towerstate", updatetowerstate);
		int count = 0;
		count = orderMapper.updateOrderState(map);
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
		shInfo.setOrdertype(ParamerUtil.xihuaPower(Integer.valueOf(map.get("adminpower").toString()),updatetowerstate));
		shInfo.setOrdershinfo((String) map.get("shinfo"));
		count = towerShInfoMapper.insertTowerShInfo(shInfo);

		if (count == 0)
			throw new RuntimeException();
		// 审核拒绝不用给积分等信息
		if (updatetowerstate == 10)
			return 1;

		// 修改总金额和+200积分 +2个经验值
		UserChargeInfo chargeInfo = new UserChargeInfo();
		chargeInfo.setScore(ScoreUtil.fee_score);
		chargeInfo.setExperience(ScoreUtil.fee_exp);
		chargeInfo.setGetnow(order.getTowerfactfee());
		chargeInfo.setWxid(order.getTowerwxid());
		count = orderMapper.updateUserCharge(chargeInfo);
		if (count == 0)
			throw new RuntimeException();
		// 插入积分来源表
		Score score = new Score();
		score.setWxid(order.getTowerwxid());
		score.setScorecount(ScoreUtil.fee_score);
		score.setScorereason(ScoreUtil.reason_fee);
		count = orderMapper.insertScore(score);
		if (count == 0)
			throw new RuntimeException();

		// 插入提现来源吧
		TxSource txSource = new TxSource();
		txSource.setOrderid(String.valueOf(order.getId()));
		txSource.setWxid(order.getTowerwxid());
		txSource.setFee(order.getTowerfactfee());
		txSource.setFeetype(1);
		count = orderMapper.insertTxSource(txSource);
		if (count == 0)
			throw new RuntimeException();

		if (count == 0)
			throw new RuntimeException();
		// 查看用户积分和经验值，与用户级别对别
		chargeInfo = new UserChargeInfo();
		chargeInfo = orderMapper.getUserCharge(order.getTowerwxid());
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
			// 付款审核通过
			String time = TimeUtil.getNow();
			sendMoneyMsg(order.getTowerwxid(), WXInfo.money_add_title,
					WXInfo.money_add_type_end,
					String.valueOf(order.getTowerfactfee()), time,
					WXInfo.money_add_remark);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		int towerid = Integer.valueOf(order.getTowerid());
		count = reportTowerMapper.checkTowerExist(towerid);
		TowerReport report = new TowerReport();
		report.setTowerid(towerid);
		report.setConrejectcount(0-order.getConrejectcount());
		if (count == 0) {
			reportTowerMapper.insertTowerReport(report);
		} else {
			reportTowerMapper.upateTowerReport(report);

		}

		return 1;
	}

	@Override
	public List<OrderState> getOrderStates() {
		return orderMapper.getOrderStates();
	}

	/**
	 * 评价订单
	 */
	@Override
	@Transactional
	public int updateOrderPjProgress(Map map) {
		// 更新订单表评价进度
		int count = 0;
		count = orderMapper.updateOrderPjProgress(map);
		if (count == 0)
			throw new RuntimeException();
		count = 0;
		int evaluate = evaluateMapper.getOrderEvaluateByOrderId(map);
		if (evaluate == 0) {
			count = evaluateMapper.insertAdminEvaluate(map);
		} else {
			count = evaluateMapper.updateAdminEvaluate(map);
		}
		if (count == 0)
			throw new RuntimeException();
		// 插入插入操作日志表
		// tower_sh_doinfo
		SysUserInfo userInfo = sysMapper.getSysUserInfo(Integer.valueOf(map
				.get("adminid").toString()));
		TowerShInfo shInfo = new TowerShInfo();
		shInfo.setOrderid(Integer.valueOf(map.get("orderid").toString()));
		shInfo.setOrdername(userInfo.getAdminname());
		shInfo.setOrderuser(userInfo.getAdminname());
		shInfo.setOrderphone(userInfo.getAdminphone());
		shInfo.setOrderstate(24);
		shInfo.setOrdertype(ParamerUtil.xihuaPower(Integer.valueOf(map.get("adminpower").toString()),24));
		shInfo.setOrdershinfo((String) map.get("shinfo"));
		count = towerShInfoMapper.insertTowerShInfo(shInfo);

		if (count == 0)
			throw new RuntimeException();

		return count;
	}
	
	/**
	 * 交单day天未处理，发送给用户消息
	 */
	@Override
	public List<Map> getCheckPostUnDo(int day) {
		return orderMapper.getCheckPostUnDo(day);
	}

	/**
	 * 更新发送交单未处理发送记录
	 */
	@Override
	@Transactional
	public int updateSendMessage(Map map) {
		int count=orderMapper.updateSendMessage(map);
		if(count>0){
			boolean result=sendSystemMsg(Integer.valueOf(map.get("orderid").toString()),map.get("towerwxid").toString(),
					WXInfo.order_post_undo_title,WXInfo.order_post_undo_user,String.format(WXInfo.order_post_undo_content, map.get("towername").toString()),WXInfo.order_post_undo__remark);
		  if(!result){
			  throw new RuntimeException();
		  }
		}
		
		return count;
	}
	
	
	/**
	 * 
	 * @param noticeid
	 * @param wxid
	 * @param first
	 * @param key1
	 * @param key2
	 * @param remark
	 * @return
	 */
	
	private boolean sendSystemMsg(int orderid, String wxid, String first,
			String key1, String key2, String remark) {
		// 发送订单提交消息模板
		// 获取管理员微信ID
		return WXCommon.sendSystemMsg(orderid, wxid, first, key1, key2,
				remark);

	}

	/**
	 * 资金变化模板消息
	 * 
	 * @param wxid
	 * @param first
	 * @param key1
	 * @param key2
	 * @param key3
	 * @param remark
	 */
	private void sendMoneyMsg(String wxid, String first, String key1,
			String key2, String key3, String remark) {
		// 发送订单提交消息模板
		// 获取管理员微信ID
		boolean sendResult = WXCommon.sendMoneyMsg(wxid, first, key1, key2,
				key3, remark);

	}

	/**
	 * 订单审核通过目标消息
	 * 
	 * @param orderid
	 * @param wxid
	 * @param first
	 * @param key1
	 * @param key2
	 * @param remark
	 */
	private void sendShOrderSuccessMsg(int orderid, String wxid, String first,
			String key1, String key2, String remark) {
		// 发送订单提交消息模板
		// 获取管理员微信ID
		boolean sendResult = WXCommon.sendShOrderSuccessMsg(orderid, wxid,
				first, key1, key2, remark);

	}

	/**
	 * 延期处理目标消息/三方合同处理消息
	 * 
	 * @param orderid
	 * @param towername
	 * @param wxid
	 * @param first
	 * @param key1
	 * @param key2
	 * @param remark
	 */
	private void sendShOrderYQMsg(int orderid, String towername, String wxid,
			String first, String key1, String key2, String remark) {
		// 发送订单提交消息模板
		// 获取管理员微信ID
		key2 = String.format(key2, towername);
		boolean sendResult = WXCommon.sendShOrderYQMsg(orderid, wxid, first,
				key1, key2, remark);

	}

	private List<SysUserInfo> getAdminWxByCity(Map map) {
		List<SysUserInfo> sysUserInfos = new ArrayList<SysUserInfo>();
		sysUserInfos = managerMapper.getAdminWxByArea(map);
		if (sysUserInfos == null)
			sysUserInfos = new ArrayList<SysUserInfo>();
		return sysUserInfos;
	}

	private void sendMsg(int orderid, Map map, String first, String key1,
			String key2, String remark) {
		// 发送订单提交消息模板
		// 获取管理员微信ID

		List<SysUserInfo> userInfos = managerMapper.getAdminWxByArea(map);
		for (SysUserInfo sysUserInfo : userInfos) {
			boolean sendResult = WXCommon.sendTaskMsg(orderid,
					sysUserInfo.getAdminwxid(), first, key1, key2, remark);
		}

	}

	@Override
	public int updateOrderCancel24(Map map) {
		return orderMapper.updateOrderCancel24(map);
	}
	
}
