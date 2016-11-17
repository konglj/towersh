package com.tower.mapper;

import java.util.Map;

public interface InfoMapper {

	public int updateInfo(Map map);
	
	public int updatePwd(Map map);
	
	public int checkPwd(Map map);
}
