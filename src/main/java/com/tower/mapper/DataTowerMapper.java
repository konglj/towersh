package com.tower.mapper;

import java.util.List;
import java.util.Map;

import com.tower.common.bean.DataTower;
import com.tower.common.bean.DataTowerPage;

public interface DataTowerMapper {
	
	public List<DataTower> getDataTowers(DataTowerPage datatowerPage);
	
	public int getDataTowersCount(DataTowerPage datatowerPage);
	
	public List<Map> getDcDataTowers(DataTowerPage datatowerPage);
	
	public List<Map> getTowerStates(DataTowerPage datatowerPage);

}


