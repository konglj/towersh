package com.tower.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tower.common.bean.Area;
import com.tower.common.bean.City;
import com.tower.common.bean.IndexPage;
import com.tower.common.bean.Message;
import com.tower.common.bean.Notice;
import com.tower.common.bean.OrderPage;
import com.tower.common.bean.QueryTerms;
import com.tower.common.bean.SysUserInfo;
import com.tower.common.bean.TxPage;
import com.tower.common.bean.UserManagePage;
import com.tower.common.util.PageUtil;
import com.tower.common.util.ParamerUtil;
import com.tower.mapper.AreaMapper;
import com.tower.mapper.LoginAndSysMapper;
import com.tower.mapper.OrderMapper;
import com.tower.mapper.TowersMapper;
import com.tower.mapper.UserMapper;
import com.tower.service.A;
import com.tower.service.LoginService;

@Service
public class LoginServiceImpl  implements LoginService{

	@Autowired
	private LoginAndSysMapper loginMapper;
	
	@Autowired
	private AreaMapper areaMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private TowersMapper towersMapper;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Override
	public SysUserInfo getLoginInfo(Map map) {
		//获取用户信息
		SysUserInfo sysUserInfo=loginMapper.getLoginInfo( map);
		if(null==sysUserInfo)
			return null;
	   //更新登录时间
		loginMapper.upateDlTime(sysUserInfo.getId());
		//判断用户角色
		int adminType=ParamerUtil.getAdminType(sysUserInfo.getAdminpower());
		Map mapArea=new HashMap();
		List<City> citys=new ArrayList<City>();
		if(adminType==0){
			citys=areaMapper.getCitys(null);
		}else {
			String ids=sysUserInfo.getAdmincityid();
			if(ids.endsWith(","))
				ids=ids.substring(0,ids.length()-1);
			String[]cityids=ids.split(",");
			if(cityids.length>0){
				mapArea.clear();
				mapArea.put("cityids", cityids);
				citys=areaMapper.getCitys(mapArea);
			}
			
		}
		
		if(adminType==2){
			String adminareas=sysUserInfo.getAdminarea();
			mapArea.clear();
			mapArea.put("areaids",adminareas.split(","));
			List<Area> areas=areaMapper.getAreas(mapArea);
			sysUserInfo.setAreas(areas);
			
		}
		sysUserInfo.setCitys(citys);
		return sysUserInfo;
	}

	@Override
	public List<Message> getTopMessage(IndexPage indexPage) {
		Map map=new HashMap();
		return loginMapper.getTopMessages(map);
	}

	@Override
	public List<Notice> getTopNotice(IndexPage indexPage) {
		Map map=new HashMap();
		return loginMapper.getTopNotices(map);
	}

	@Override
	public int getUsercount(UserManagePage usermanagepage) {
		return userMapper.getIndexPageUserInfo(usermanagepage);
	}

	@Override
	public int getTowercount(QueryTerms queryTerms) {
		return towersMapper.getIndexPageTowersCount(queryTerms);
	}

	@Override
	public int getIngOrdercount(OrderPage orderPage) {
		return orderMapper.getIndexPageOrdersCount(orderPage);
	}

	@Override
	public int getSuccOrdercount(OrderPage orderPage) {
		return orderMapper.getIndexPageOrdersCount(orderPage);
	}
	
}
