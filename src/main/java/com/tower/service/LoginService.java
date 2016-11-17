package com.tower.service;

import java.util.List;
import java.util.Map;

import com.tower.common.bean.IndexPage;
import com.tower.common.bean.Message;
import com.tower.common.bean.Notice;
import com.tower.common.bean.OrderPage;
import com.tower.common.bean.QueryTerms;
import com.tower.common.bean.SysUserInfo;
import com.tower.common.bean.UserManagePage;
import com.tower.mapper.UserMapper;


public interface LoginService {

	public SysUserInfo getLoginInfo(Map map);
	
	public List<Message> getTopMessage(IndexPage indexPage);
	
	public List<Notice> getTopNotice(IndexPage indexPage);
	
	public int getUsercount(UserManagePage usermanagepage); 
	
	public int getTowercount(QueryTerms queryTerms);
	
	public int getIngOrdercount(OrderPage orderPage);
	
	public int getSuccOrdercount(OrderPage orderPage);
	
}
