package com.tower.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tower.common.bean.AddTowerInfo;
import com.tower.common.bean.Area;
import com.tower.common.bean.DataTower;
import com.tower.common.bean.DataTowerPage;
import com.tower.common.bean.Order;
import com.tower.common.bean.QueryTerms;
import com.tower.common.bean.Tower;
import com.tower.common.bean.TowerStore;
import com.tower.common.bean.TowerType;
import com.tower.common.bean.TowersPage;
import com.tower.mapper.DataTowerMapper;
import com.tower.mapper.OrderMapper;
import com.tower.mapper.TowersMapper;
import com.tower.service.TowerService;

@Service
public class TowerServiceImpl implements TowerService {

	@Autowired
	private TowersMapper towersMapper;
	
	@Autowired
	private DataTowerMapper datatowerMapper;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Override
	@Transactional
	public List<Tower> getTowers(QueryTerms queryTerms) {
		return towersMapper.getTowers(queryTerms);
	}

	@Override
	@Transactional
	public TowersPage getTowersPage(QueryTerms queryTerms) {
		TowersPage towersPage = new TowersPage();
		List<Tower> towers = getTowers(queryTerms);
		towersPage.setTowers(towers);
		int towercount = getTowersCount(queryTerms);
		towersPage.setTowercount(towercount);
		return towersPage;
	}

	@Override
	@Transactional
	public int getTowersCount(QueryTerms queryTerms) {
		return towersMapper.getTowersCount(queryTerms);
	}

	@Override
	public List<Tower> getNoTowers(QueryTerms queryTerms) {
		return towersMapper.getNoTowers(queryTerms);
	}

	@Override
	public int getNoTowersCount(QueryTerms queryTerms) {
		return towersMapper.getNoTowersCount(queryTerms);
	}

	@Override
	public TowersPage getNoTowersPage(QueryTerms queryTerms) {
		TowersPage noTowersPage = new TowersPage();
		List<Tower> towers = getNoTowers(queryTerms);
		noTowersPage.setTowers(towers);
		int towercount = getNoTowersCount(queryTerms);
		noTowersPage.setTowercount(towercount);
		return noTowersPage;
	}

	@Override
	public int addTower(AddTowerInfo tower) {
		return towersMapper.addTower(tower);
	}

	@Override
	public Area getAreaById(Map map) {
		return towersMapper.getAreaById(map);
	}

	@Override
	public int updateTowerVisible(Map map) {
		
		return towersMapper.updateTowerVisible(map);
	}

	@Override
	public int delTower(Map map) {
		return towersMapper.delTower(map);
	}

	@Override
	public Tower getTowerInfo(int towerid) {
		return towersMapper.getTowerInfo(towerid);
	}
	
	@Override
	@Transactional
	public String getAreaOrder(int city,int area) {
		Map map = towersMapper.getareabyid(area);
		if(map==null)
			return "error";
		String areaid=(String)map.get("area");
		String cityid=(String)map.get("city");
		Map result = towersMapper.getareaorder(map);
		int ordercount = Integer.parseInt(result.get("count").toString());
		int count = 0;
		if ( ordercount == 0) {
			//插入记录
			count = towersMapper.insertareaorder(map);
			if (count ==0)
				return "error";
			return cityid + areaid + String.format("%05d", 1);		
		} else {
			//更新序号+1
			count = towersMapper.updateareaorder(map);
			if (count ==0)
				return "error";
			return cityid + areaid + String.format("%05d", Integer.parseInt(result.get("orderno").toString())+1);
		}
	}

	@Override
	public int updateTower(AddTowerInfo tower) {
		return towersMapper.updateTower(tower);
	}

	@Override
	public List<TowerType> getTowertype() {
		return towersMapper.getTowertype();
	}
	
	@Override
	public Map<String,Integer> getTowertypeMap(){
		List<TowerType> list=towersMapper.getTowertype();
		Map<String,Integer> map=new HashMap<String, Integer>();
		for (TowerType towerType : list) {
			map.put(towerType.getTowertypename(),towerType.getId());
		}
		return map;
		
	}

	@Override
	@Transactional
	
	public int insertTowers(List<AddTowerInfo> towers) {
		for (AddTowerInfo tower : towers) {
			System.out.println(tower.getAreaname());
		   int count= towersMapper.addTower(tower);
		   
		   if(count==0)
			   throw new RuntimeException();
		}
	
		return 1;
	}
	@Override
	public List<Map> dcTowers(QueryTerms queryTerms){
		return towersMapper.dcTowers(queryTerms);
	}
	
	@Override
	public List<DataTower> getDataTowers(DataTowerPage datatowerPage) {
		int count = datatowerMapper.getDataTowersCount(datatowerPage);
		datatowerPage.setPagerowtotal(count);
		return datatowerMapper.getDataTowers(datatowerPage);
	}

	@Override
	public List<Map> getDcDataTowers(DataTowerPage datatowerPage) {
		return datatowerMapper.getDcDataTowers(datatowerPage);
	}

	@Override
	public List<Order> getTowerOrderHistory(int towerid) {
		return orderMapper.getOrderHistorys(towerid);
	}

	@Override
	public List<Map> getDcTowerOrderHistory(int towerid) {
		return orderMapper.getDcTowerOrderHistorys(towerid);
	}

	@Override
	public List<TowerStore> getTowerStores() {
		return towersMapper.getTowerStores();
	}
	

	@Override
	public Map<String,Integer> getTowerStoreMap(){
		List<TowerStore> list=towersMapper.getTowerStores();
		Map<String,Integer> map=new HashMap<String, Integer>();
		for (TowerStore towerStore : list) {
			map.put(towerStore.getStorename(),towerStore.getStoreid());
		}
		return map;
		
	}
	
	@Override
	public Map<String,Integer> getTowerCreateTypeMap(){
		List<Map> list=towersMapper.getTowerCreateType();
		Map<String,Integer> map=new HashMap<String, Integer>();
		for (Map maptype : list) {
			map.put(maptype.get("name").toString(),Integer.valueOf(maptype.get("id").toString()));
		}
		return map;
		
	}

	@Override
	public List<Map> getUnOrderTower() {
		return towersMapper.getUnOrderTower();
	}

	@Override
	public int updateTowerLevel(Map map) {
		return towersMapper.updateTowerLevel(map);
	}

	@Override
	public List<Map> getTowerStates(DataTowerPage datatowerPage) {
		return datatowerMapper.getTowerStates(datatowerPage);
	}

	@Override
	public List<Map> getTowerCreateType() {
		return towersMapper.getTowerCreateType();
	}
}
