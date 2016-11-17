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
import com.tower.common.bean.TowerType;
import com.tower.common.bean.TowersPage;
import com.tower.mapper.DataTowerMapper;
import com.tower.mapper.HbOrderMapper;
import com.tower.mapper.HbTowersMapper;
import com.tower.service.HbTowerService;

@Service
public class HbTowerServiceImpl implements HbTowerService {

	@Autowired
	private HbTowersMapper hbTowersMapper;
	
	@Autowired
	private DataTowerMapper datatowerMapper;
	
	@Autowired
	private HbOrderMapper hbOrderMapper;
	
	@Override
	@Transactional
	public List<Tower> getTowers(QueryTerms queryTerms) {
		return hbTowersMapper.getTowers(queryTerms);
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
		return hbTowersMapper.getTowersCount(queryTerms);
	}

	@Override
	public List<Tower> getNoTowers(QueryTerms queryTerms) {
		return hbTowersMapper.getNoTowers(queryTerms);
	}

	@Override
	public int getNoTowersCount(QueryTerms queryTerms) {
		return hbTowersMapper.getNoTowersCount(queryTerms);
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
		return hbTowersMapper.addTower(tower);
	}

	@Override
	public Area getAreaById(Map map) {
		return hbTowersMapper.getAreaById(map);
	}

	@Override
	public int updateTowerVisible(Map map) {
		
		return hbTowersMapper.updateTowerVisible(map);
	}

	@Override
	public int delTower(Map map) {
		return hbTowersMapper.delTower(map);
	}

	@Override
	public Tower getTowerInfo(int towerid) {
		return hbTowersMapper.getTowerInfo(towerid);
	}
	
	@Override
	@Transactional
	public String getAreaOrder(int city,int area) {
		String areaid = hbTowersMapper.getareabyid(area);
		Map map = new HashMap();
		map.put("city", city);
		map.put("area", areaid);
		
		Map result = hbTowersMapper.getareaorder(map);
		int ordercount = Integer.parseInt(result.get("count").toString());
		int count = 0;
		if ( ordercount == 0) {
			//插入记录
			count = hbTowersMapper.insertareaorder(map);
			if (count ==0)
				return "error";
			return city + areaid + String.format("%05d", 1);		
		} else {
			//更新序号+1
			count = hbTowersMapper.updateareaorder(map);
			if (count ==0)
				return "error";
			return city + areaid + String.format("%05d", Integer.parseInt(result.get("orderno").toString())+1);
		}
	}

	@Override
	public int updateTower(AddTowerInfo tower) {
		return hbTowersMapper.updateTower(tower);
	}

	@Override
	public List<TowerType> getTowertype() {
		return hbTowersMapper.getTowertype();
	}
	
	@Override
	public Map<String,Integer> getTowertypeMap(){
		List<TowerType> list=hbTowersMapper.getTowertype();
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
		   int count= hbTowersMapper.addTower(tower);
		   if(count==0)
			   throw new RuntimeException();
		}
	
		return 1;
	}
	@Override
	public List<Map> dcTowers(QueryTerms queryTerms){
		return hbTowersMapper.dcTowers(queryTerms);
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
		return hbOrderMapper.getOrderHistorys(towerid);
	}

	@Override
	public List<Map> getDcTowerOrderHistory(int towerid) {
		return hbOrderMapper.getDcTowerOrderHistorys(towerid);
	}
}
