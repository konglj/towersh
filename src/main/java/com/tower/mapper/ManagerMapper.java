package com.tower.mapper;

import java.util.List;
import java.util.Map;

import com.tower.common.bean.AdminManagerInfo;
import com.tower.common.bean.BindUser;
import com.tower.common.bean.BindUserPage;
import com.tower.common.bean.ManagerPage;
import com.tower.common.bean.ManagerPower;
import com.tower.common.bean.Power;
import com.tower.common.bean.SysUserInfo;

public interface ManagerMapper {
	
	public List<ManagerPower> getManagerPowers(ManagerPage managerPage);
	
	public List<AdminManagerInfo> getAdmins(ManagerPage managerPage);
	
	public int getSysUsersCount(ManagerPage managerPage);
	
	public AdminManagerInfo getAdmin(int ID);
	
	public int changeAdmin(Map map);
	
	public int addAdmin(Map map);
	
	public int changeAdminpwd(int ID);
	
	public int delAdmin(Map map);
	
	public List<Power> getChildPowers(Map map);
	
	public int checkAdminidExist(String adminid);
	
	public List<BindUser> getBindsUsers(BindUserPage bindUserPage);
	
	public int getBindsUsersCount(BindUserPage bindUserPage);
	
	
      public int bindUser(Map map);
	
	public int unBindUser(Map map);		
	
	public List<SysUserInfo> getAdminWxByArea(Map map);
	
}

