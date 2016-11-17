package com.tower.common.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.tower.common.Config;
import com.tower.common.bean.OutDatePhone;
import com.tower.common.bean.Setting;
import com.tower.common.bean.SysUserInfo;
import com.tower.common.bean.TxRecord;
import com.tower.common.util.ParamerUtil;
import com.tower.common.util.SmsUtil;
import com.tower.common.util.TimeUtil;
import com.tower.common.wxmsg.WXCommon;
import com.tower.common.wxmsg.WXInfo;
import com.tower.mapper.TowersMapper;
import com.tower.service.ManagerService;
import com.tower.service.OrderService;
import com.tower.service.SettingService;
import com.tower.service.TowerService;
import com.tower.service.TxService;

@Component
public class TaskCancel {

	@Autowired
	private OrderService orderService;

	@Autowired
	private TowerService towerService;
	
	@Autowired
	private TxService txService;
	
	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private SettingService settingService;

	//取消订单
	@Scheduled(cron="0 0/30 * * * ?")
	//@Scheduled(fixedRate = 1000 * 60*30)
	public void task() {
		System.out.println("1111 start");
		List<Map> orders = orderService.getCheckCancelOrder();
		if (orders != null && orders.size() > 0) {
			System.out.println("1111 start size:"+orders.size());
			for (Map map : orders) {
				System.out.println("1111 order id:"+map.get("orderid"));
				System.out.println("1111 order Tower_wx_id:"+map.get("towerwxid"));
				System.out.println("1111 order Tower_id:"+map.get("towerid"));
				try {
					int hour=Integer.valueOf(map.get("towerhour").toString());
					int count=0;
					//到期
					if(hour<0){
				       count=orderService.updateOrderCancel(map);
					}else{
						//到期24小时
						map.put("towersendmsg", 1);
						count=orderService.updateOrderCancel24(map);
						if(count>0){
							Map mapArea=new HashMap();
							mapArea.put("powers", new String[]{"2","3","12","13","14","15"});
							mapArea.put("city", map.get("towercity"));
							mapArea.put("area", map.get("towerarea"));
							sendMsg(Integer.valueOf(map.get("orderid").toString()), mapArea,
									WXInfo.task_cancel_24_title,
									WXInfo.task_cancel_24_name,
									WXInfo.task_cancel_24_type,
									WXInfo.task_cancel_24_remark);
						}
						
					}
				System.out.println("1111 start update:"+count);
				} catch (Exception e) {
					System.out.println("1111 start update cancel error");
				}
			}

		}

		System.out.println("111");
	}

	//订单失效24小时内通知区域管理员
	
	@Scheduled(cron = "0 0 12 * * ?")
	public void getPosterOrderUndo() {
		System.out.println("33333 start");
		try {

			List<Map> orders = orderService.getCheckPostUnDo(Config.postundoday);
			if (orders != null && orders.size() > 0) {
				for (Map map : orders) {
					try {
						
						orderService.updateSendMessage(map);
						
					} catch (Exception e) {
						e.printStackTrace();
					
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("33");
	}

	
	/**
	 * 付款确认24小时后自动确认
	 */
	@Scheduled(fixedRate = 1000 * 60*60)
	public void task_money_autocommit() {
		System.out.println("auto start");
		List<TxRecord> txRecords=txService.getAutoTxs();
		if(txRecords!=null&&txRecords.size()>0){
			for (int i = 0; i <txRecords.size(); i++) {
				try {
					int count=txService.updateAutoTx(txRecords.get(i));
				} catch (Exception e) {
				}
			}
		}
		System.out.println("auto");
	}

	
	

	/**
	 * 平台到期
	 */
	@Scheduled(fixedRate = 1000 * 60*60*6)
	public void checkOutDate() {
		System.out.println("auto start pingtai");
		try {
			
	
		List<OutDatePhone> lists=settingService.getOutPhones();
		Setting set=settingService.getSetting("indatesend");
		if(set==null||set.getContent()==null||set.getContent().equals(""))
			return;
		String nowday=TimeUtil.getNetDay();
		if(nowday==null||nowday.equals(""))
			return;
		if(!set.getContent().equals(nowday))
			return;
		List<OutDatePhone> phones=new ArrayList<OutDatePhone>();
		if(lists!=null&&lists.size()>0){
			for (OutDatePhone outDatePhone : lists) {
				boolean result=SmsUtil.sendSms(outDatePhone.getPhone(), Config.getIndate());
				if(result){
				 phones.add(outDatePhone);
				}
			}
		}
		if(phones.size()>0){
			Map map=new HashMap();
			map.put("phones", phones);
			settingService.updateOutSend(map);
		}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	
	private void sendMsg(int orderid, Map map, String first, String key1,
			String key2, String remark) {
		// 发送订单提交消息模板
		// 获取管理员微信ID

		List<SysUserInfo> userInfos = managerService.getAdminWxByArea(map);
		for (SysUserInfo sysUserInfo : userInfos) {
			boolean sendResult = WXCommon.sendCancel24Msg(orderid,
					sysUserInfo.getAdminwxid(), first, key1, key2, remark);
		}

	}
}
