package com.tower.service;

import java.util.List;
import java.util.Map;

import com.tower.common.bean.DataTower;
import com.tower.common.bean.DataTowerPage;
import com.tower.common.bean.DataUser;
import com.tower.common.bean.DataUserPage;
import com.tower.common.bean.DataUserSourcePage;
import com.tower.common.bean.UserInfo;
import com.tower.common.bean.UserManagePage;
import com.tower.common.bean.UserMoney;
import com.tower.common.bean.UserMoneyPage;
import com.tower.common.bean.UserSourceAnalysis;
import com.tower.common.bean.UserType;

public interface UserService {
	
	public List<UserInfo> getUserInfos(UserManagePage usermanagepage);
	
	public List<UserMoney> getUserMoneys(UserMoneyPage usermoneypage);

	UserInfo getUserdetail(String wxid);

	int updateuserstate(Map map);

	public int updateusertype(Map map);
	
	public int updateUserBz(Map map);
	
	public List<Map>  getDcUser(UserManagePage usermanagepage);
	
	public List<UserInfo>  getDcUserImage(UserManagePage usermanagepage);
	
	public List<Map>  getDcUserFee(UserMoneyPage userMoneyPage);
	
	public List<DataUser> getDataUsers(DataUserPage datauserPage);
	
	public List<Map> getDcDataUsers(DataUserPage datauserPage);
	
	public List<UserType> getUserTypes();
	
	/**
	 * 获取用户来源分析
	 * @param map
	 * @return
	 */
	public List<UserSourceAnalysis> getUserSources(DataUserSourcePage userSourcePage);
	
	public List<Map> getDcDataUserScores(DataUserSourcePage userSourcePage);
	
}
