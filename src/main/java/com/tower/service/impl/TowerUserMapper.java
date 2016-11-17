package com.tower.service.impl;

import com.tower.common.bean.UserInfo;

public interface TowerUserMapper {
	
	public UserInfo getUserInfoByWxid(String wxid);

}
