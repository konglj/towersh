package com.tower.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tower.common.bean.OrderTower;
import com.tower.common.bean.SysUserInfo;
import com.tower.common.bean.TowerShInfo;
import com.tower.common.bean.TxDoinfo;
import com.tower.common.bean.TxPage;
import com.tower.common.bean.TxRecord;
import com.tower.common.bean.TxSource;
import com.tower.common.util.ParamerUtil;
import com.tower.common.wxmsg.WXCommon;
import com.tower.common.wxmsg.WXInfo;
import com.tower.mapper.LoginAndSysMapper;
import com.tower.mapper.ManagerMapper;
import com.tower.mapper.TowerShInfoMapper;
import com.tower.mapper.TowersMapper;
import com.tower.mapper.TxMapper;
import com.tower.mapper.UserMapper;
import com.tower.service.TxService;

@Service
public class TxServiceImpl implements TxService {

	@Autowired
	private TxMapper txMapper;
	
	@Autowired
	private TowerShInfoMapper towerShInfoMapper;

	@Autowired
	private LoginAndSysMapper sysMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	
	@Override
	public List<TxRecord> getTxs(TxPage txPage) {
		int count=txMapper.getTxsCount(txPage);
		txPage.setPagerowtotal(count);
		return txMapper.getTxs(txPage);
	}
	
	@Override
	public TxRecord getTx(TxPage txPage) {
		TxRecord txrecord = txMapper.getTx(txPage.getTxid());
		List <TxSource> txsources = txMapper.getTxSources(txPage);
		txrecord.setTxsources(txsources);
		String txid = txrecord.getTxid();
		List <TxDoinfo> txdoinfos = txMapper.getTxDoinfos(txid);
		txrecord.setTxdoinfos(txdoinfos);
		return txrecord;
	}

	@Override
	public TxSource getTxSources(TxPage txPage ) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	@Transactional
	public int shTx(Map map) {
		int count = 0;
		int state = Integer.valueOf(map.get("state").toString());
		String txid = map.get("txid").toString();
		String shinfo = map.get("shinfo").toString();
		String fee = map.get("fee").toString();
		TxRecord txRecord=txMapper.getTxRecord(txid);
		//修改提现状态
		count = txMapper.doinfo(map);
		if (count == 0)
			throw new RuntimeException();
		
		
		// 插入提现审核日志表表
				// tower_sh_doinfo
		SysUserInfo userInfo = sysMapper.getSysUserInfo(Integer.valueOf(map
						.get("adminid").toString()));
		
		// 审核拒绝情况
		if (state == 2) {
			// 修改用户费用
			count = txMapper.shnocharge(map);
			if (count == 0)
				throw new RuntimeException();
		
			//将用户的提现来源的提现编号改为空。
			count = txMapper.shnotxsource(map);
			if (count == 0)
				throw new RuntimeException();
			
			//审核失败发送消息
			try {
				sendDkApplySuccessMsg(txRecord.getWxid(), WXInfo.money_dk_apply_title_error, userInfo.getAdminname(), txid, WXInfo.money_dk_apply_remark);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		TxDoinfo txdoinfo = new TxDoinfo();
		txdoinfo.setTxid(txid);
		txdoinfo.setDouserid(userInfo.getAdminname());
		txdoinfo.setDousername(userInfo.getAdminname());
		txdoinfo.setDousertele(userInfo.getAdminphone());
		txdoinfo.setUsertype(1);
		txdoinfo.setNowstate(state);
		txdoinfo.setShinfo(shinfo);
		
		count = txMapper.addTxdoinfo(txdoinfo);

		if (count == 0)
			throw new RuntimeException();

		return 1;
	}
	
	//批量审核
	@Override
	@RequestMapping
	public int plShTx(Map map){
		int count = 0;
		String [] ids=(String [])map.get("txids");
		TxRecord txRecord=null;
		for (String id : ids) {
			txRecord=txMapper.getTxRecordById(Integer.valueOf(id));
			//修改提现状态
			Map mapRecord=new HashMap();
			mapRecord.put("txid", txRecord.getTxid());
			mapRecord.put("state", map.get("state"));
			count = txMapper.doinfo(mapRecord);
			if(count==0)
				throw new RuntimeException();
			// 插入提现审核日志表表
			// tower_sh_doinfo
        	SysUserInfo userInfo = sysMapper.getSysUserInfo(Integer.valueOf(map
					.get("adminid").toString()));
        	TxDoinfo txdoinfo = new TxDoinfo();
    		txdoinfo.setTxid(txRecord.getTxid());
    		txdoinfo.setDouserid(userInfo.getAdminname());
    		txdoinfo.setDousername(userInfo.getAdminname());
    		txdoinfo.setDousertele(userInfo.getAdminphone());
    		txdoinfo.setUsertype(1);
    		txdoinfo.setNowstate(1);
    		txdoinfo.setShinfo("");
    		
    		count = txMapper.addTxdoinfo(txdoinfo);

    		if (count == 0)
    			throw new RuntimeException();
		}
	
		

		
	
		return 1;
	}
	
	@Override
	@Transactional
	public int dkTx(Map map) {
		
		int count = 0;
		int state = Integer.valueOf(map.get("state").toString());
		String txid = map.get("txid").toString();
		String shinfo = map.get("shinfo").toString();
		String fee = map.get("fee").toString();
		TxRecord txRecord=txMapper.getTxRecord(txid);
		if (shinfo != null && shinfo.equals("AB000000")) {
			//银行单号为"AB000000"的记录置为打款失败
			state = 4;
			map.put("state", 4);
		}
		//打款成功 是加入银行流水号
		if(map.get("state").toString().equals("3")){
			map.put("bankbackid",  map.get("shinfo"));
		}
		//修改提现状态
		count = txMapper.dkdoinfo(map);
		if (count == 0)
			throw new RuntimeException();
		if (state == 3) {
			// 打款成功情况
			// 修改用户费用
			count = txMapper.dkyescharge(map);
			if (count == 0)
				throw new RuntimeException();
			
		} else if (state == 4) {
			// 打款失败情况
			// 修改用户费用
			count = txMapper.shnocharge(map);
			if (count == 0)
				throw new RuntimeException();
			//将用户的提现来源的提现编号改为空。
			
			count = txMapper.shnotxsource(map);
			if (count == 0)
				throw new RuntimeException();
			
		}
		// 插入提现审核日志表
		// tower_sh_doinfo
		SysUserInfo userInfo = sysMapper.getSysUserInfo(Integer.valueOf(map
				.get("adminid").toString()));
		TxDoinfo txdoinfo = new TxDoinfo();
		txdoinfo.setTxid(txid);
		txdoinfo.setDouserid(userInfo.getAdminname());
		txdoinfo.setDousername(userInfo.getAdminname());
		txdoinfo.setDousertele(userInfo.getAdminphone());
		txdoinfo.setUsertype(1);
		txdoinfo.setNowstate(state);
		txdoinfo.setShinfo(shinfo);
		
		count = txMapper.addTxdoinfo(txdoinfo);

		if (count == 0)
			throw new RuntimeException();
		//发送打款成功后的通知信息
		try {
			if(state==3){
				String card=txRecord.getBankcardid();
				card=card.substring(0,3)+"**************"+card.substring(card.length()-4);
				sendDkSuccessMsg(txid,txRecord.getWxid(),WXInfo.money_dk_title, card,WXInfo.money_dk_qz_type,String.valueOf(txRecord.getFee()),WXInfo.money_dk_lxfs,WXInfo.money_dk_remark);
			}else if(state==4){
				//打款失败发送消息
			    sendDkApplySuccessMsg(txRecord.getWxid(), WXInfo.money_dk_apply_title_error, userInfo.getAdminname(), txid, WXInfo.money_dk_apply_remark);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	@Override
	public List<Map> getDcTxs(TxPage txPage){
		
		return txMapper.getDcTxs(txPage);
	}

	@Override
	public List<Map> getDcTxApplys(TxPage txPage) {
		return txMapper.getDcTxApplys(txPage);
	}
	
	@Override
	@Transactional
	public int dkTx(List<Map> list,int adminid) {
		for (Map map : list) {
			int count = 0;
			int state = Integer.valueOf(map.get("state").toString());
			String txid = map.get("txid").toString();
			String shinfo = map.get("bankbackid").toString();
			String fee = map.get("fee").toString();
			TxRecord txRecord=txMapper.getTxRecord(txid);
			//修改提现状态
			count = txMapper.dkdoinfo(map);
			if (count == 0)
				throw new RuntimeException();
			if (state == 3) {
				// 打款成功情况
				// 修改用户费用
				count = txMapper.dkyescharge(map);
				if (count == 0)
					throw new RuntimeException();
				
			} else if (state == 4) {
				// 打款失败情况
				// 修改用户费用
				count = txMapper.shnocharge(map);
				if (count == 0)
					throw new RuntimeException();
				//将用户的提现来源的提现编号改为空。
			
				count = txMapper.shnotxsource(map);
				if (count == 0)
					throw new RuntimeException();
				
				
			}
			// tower_sh_doinfo
			SysUserInfo userInfo = sysMapper.getSysUserInfo(adminid);
			TxDoinfo txdoinfo = new TxDoinfo();
			txdoinfo.setTxid(txid);
			txdoinfo.setDouserid(userInfo.getAdminname());
			txdoinfo.setDousername(userInfo.getAdminname());
			txdoinfo.setDousertele(userInfo.getAdminphone());
			txdoinfo.setUsertype(1);
			txdoinfo.setNowstate(state);
			txdoinfo.setShinfo(shinfo);
			
			
			
			count = txMapper.addTxdoinfo(txdoinfo);
			if (count == 0)
				throw new RuntimeException();
			
			//发送打款成功后的通知信息
			try {
				if(state==3){
					String card=txRecord.getBankcardid();
					card=card.substring(0,3)+"**************"+card.substring(card.length()-4);
					sendDkSuccessMsg(txid,txRecord.getWxid(),WXInfo.money_dk_title, card,WXInfo.money_dk_qz_type,String.valueOf(txRecord.getFee()),WXInfo.money_dk_lxfs,WXInfo.money_dk_remark);
				}else if(state==4){
					//打款失败发送消息
				    sendDkApplySuccessMsg(txRecord.getWxid(), WXInfo.money_dk_apply_title_error, userInfo.getAdminname(), txid, WXInfo.money_dk_apply_remark);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return 1;
	}
	
	private void sendDkSuccessMsg(String txid,String wxid,String first,String key1,String key2,String key3,String key4,String remark ){
		//发送订单提交消息模板
		//获取管理员微信ID
			boolean sendResult=WXCommon.sendDkSuccessMsg(txid,wxid,first,key1,key2,key3,key4,remark);
      }
	
	private void sendDkApplySuccessMsg(String wxid,String first,String key1,String key2,String remark ){
		//打款申请审核拒绝和打款失败
			boolean sendResult=WXCommon.sendDkApplySuccessMsg(wxid,first,key1,key2,remark);
      }

	@Override
	public List<TxRecord> getAutoTxs() {
		return txMapper.getAutoTxs();
	}

	@Override
	@Transactional
	public int updateAutoTx(TxRecord txRecord) {
		int count=0;
		count=txMapper.updateAutoTx(txRecord.getId());
		if(count==0)
			return  0;
		TxDoinfo txdoinfo = new TxDoinfo();
		txdoinfo.setTxid(txRecord.getTxid());
		txdoinfo.setDouserid(txRecord.getUsername());
		txdoinfo.setDousername(txRecord.getUsername());
		txdoinfo.setDousertele(txRecord.getUsertele());
		txdoinfo.setUsertype(0);
		txdoinfo.setNowstate(6);
		txdoinfo.setShinfo("");
		
		
		
		count = txMapper.addTxdoinfo(txdoinfo);
		if(count==0)
			throw new RuntimeException();
		return 1;
	}

	
	
	
}
