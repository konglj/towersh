package com.tower.mapper;

import java.util.List;
import java.util.Map;

import com.tower.common.bean.DataUserSourcePage;
import com.tower.common.bean.OrderInfo;
import com.tower.common.bean.SendNoticeParameter;
import com.tower.common.bean.UserChargeInfo;
import com.tower.common.bean.UserInfo;
import com.tower.common.bean.UserManagePage;
import com.tower.common.bean.UserMoney;
import com.tower.common.bean.UserMoneyPage;
import com.tower.common.bean.UserSourceAnalysis;
import com.tower.common.bean.UserType;

public interface UserMapper {
	
	public List<UserInfo> getUserInfos(UserManagePage usermanagepage);
	
	public int getUserInfosCount(UserManagePage usermanagepage);
	
	public List<UserMoney> getUserMoneys(UserMoneyPage usermoneypage);
	
	public int getUserMoneysCount(UserMoneyPage usermoneypage);
	
	public UserInfo getUserDetail(String wxid);
	
	public List<OrderInfo> getOrderInfos(String wxid);
	
	public int updateuserstate(Map map);
	
	public int updateusertype(Map map);
	
	public int updateUserBz(Map map);
	
	public  List<Map>    getDcUser(UserManagePage usermanagepage);
	
	public  List<UserInfo>    getDcUserImage(UserManagePage usermanagepage);
	
	public List<Map>  getDcUserFee(UserMoneyPage userMoneyPage);
	
	
	public int updateUserLevle(UserChargeInfo chargeInfo);
	
	public Map getUserArea(Map map);
	
	public List<UserType> getUserTypes();
	
	public List<UserInfo> getUserInfosByTwNotice(SendNoticeParameter parameter);
	
	public int getIndexPageUserInfo(UserManagePage userManagePage);
	
	public List<UserSourceAnalysis> getUserSources(DataUserSourcePage userSourcePage);

	public List<Map> getDcDataUserScores(DataUserSourcePage userSourcePage);
}
