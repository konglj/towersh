package com.tower.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tower.common.bean.AdminManagerInfo;
import com.tower.common.bean.BindUser;
import com.tower.common.bean.BindUserPage;
import com.tower.common.bean.ManagerPage;
import com.tower.common.bean.ManagerPower;
import com.tower.common.bean.Power;
import com.tower.common.bean.SysUserInfo;
import com.tower.mapper.ManagerMapper;
import com.tower.mapper.TowersMapper;
import com.tower.service.ManagerService;

@Service
public class ManagerServiceImpl implements ManagerService{
	
	@Autowired
	private ManagerMapper managerMapper;
	
	@Autowired
	private TowersMapper towersMapper;

	@Override
	@Transactional
	public List<ManagerPower> getManagerPowers(ManagerPage managerPage) {
		return managerMapper.getManagerPowers(managerPage);
	}

	@Override
	@Transactional
	public int getSysUsersCount(ManagerPage managerPage) {
		return managerMapper.getSysUsersCount(managerPage);
	}

	@Override
	@Transactional
	public List<AdminManagerInfo> getAdmins(ManagerPage managerPage) {
		managerPage.setPagerowtotal(managerMapper.getSysUsersCount(managerPage));
		return managerMapper.getAdmins(managerPage);
	}
	
	@Override
	@Transactional
	public AdminManagerInfo getAdmin(int id) {
		return managerMapper.getAdmin(id);
	}
	
	@Override
	@Transactional
	public int changeAdmin(Map map) {
		int count = 0;
		count = managerMapper.changeAdmin(map);
		if (count == 0)
			throw new RuntimeException();
		return 1;
	}
	
	@Override
	@Transactional
	public int addAdmin(Map map) {
		int count = 0;
		count = managerMapper.addAdmin(map);
		if (count == 0)
			throw new RuntimeException();
		return 1;
	}
	
	@Override
	@Transactional
	public int changeAdminPwd(int ID) {
		int count = 0;
		count = managerMapper.changeAdminpwd(ID);
		if (count == 0)
			throw new RuntimeException();
		return 1;
	}
	
	@Override
	@Transactional
	public int deleteAdmin(Map map) {
		String ids=map.get("ids").toString();
		if(ids.endsWith(","))
			ids=ids.substring(0,ids.length()-1);
		String [] idds=ids.split(",");
		map.put("ids",idds);

		int count = 0;
		count = managerMapper.delAdmin(map);
		if (count == 0)
			throw new RuntimeException();
		return 1;
	}
	
	public List<Power> getChildPowers(Map map){
		return managerMapper.getChildPowers(map);
	}

	@Override
	public int checkAdminidExist(String adminid) {
		return managerMapper.checkAdminidExist(adminid);
	}

	@Override
	public void getBindsUsers(BindUserPage bindUserPage) {
		bindUserPage.setBindusers( managerMapper.getBindsUsers( bindUserPage));
		bindUserPage.setPagerowtotal(managerMapper.getBindsUsersCount(bindUserPage));
		
	
 	}

	@Override
	public int bindUser(Map map) {
		return managerMapper.bindUser(map);
	}

	@Override
	public int unBindUser(Map map) {
		return managerMapper.unBindUser(map);
	}

	@Override
	public List<SysUserInfo> getAdminWxByArea(Map map) {
		if(map.get("city")==null||map.get("area")==null){
		Map mapArea=towersMapper.getTowerArea(map);
		if(mapArea==null)
			return null;
		map.put("city", mapArea.get("city"));
		map.put("area", mapArea.get("area"));
		}
		List<SysUserInfo> sysUserInfo=managerMapper.getAdminWxByArea(map);
		return sysUserInfo;
	}

}
