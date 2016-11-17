package com.tower.mapper;

import java.util.List;

import com.tower.common.bean.TowerShInfo;

public interface TowerShInfoMapper {
	
	public List<TowerShInfo> getShInfos(int orderid);
	
	public int insertTowerShInfo(TowerShInfo shInfos);
	

}
