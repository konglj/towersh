package com.tower.mapper;

import java.util.List;
import java.util.Map;

import com.tower.common.bean.AddTowerInfo;
import com.tower.common.bean.Area;
import com.tower.common.bean.DataTowerPage;
import com.tower.common.bean.QueryTerms;
import com.tower.common.bean.Tower;
import com.tower.common.bean.TowerStore;
import com.tower.common.bean.TowerType;

public interface TowersMapper {

	public List<Tower> getTowers(QueryTerms queryTerms);
	
	public int  getTowersCount(QueryTerms queryTerms);
	
	public List<Tower> getNoTowers(QueryTerms queryTerms);
	
	public int  getNoTowersCount(QueryTerms queryTerms);
	
	public int addTower(AddTowerInfo tower);
	
	public Area getAreaById(Map map);
	
	
	public int updateTowerVisible(Map map);
	
	public int delTower(Map map);
	
	public Tower getTowerInfo(int towerid);
	
    public Map getareaorder(Map map);
	
	public int updateareaorder(Map map);
	
	public int insertareaorder(Map map);
	
	public Map getareabyid(int area);
	
	public int updateTower(AddTowerInfo tower);
	
	public List<TowerType> getTowertype();
	
	
	public Map getTowerArea(Map map);
	
	public List<Map> dcTowers(QueryTerms queryTerms);
	//站址分析
    public List<Map> getDcDataTowers(DataTowerPage datatowerPage);
    
    
    public int  getIndexPageTowersCount(QueryTerms queryTerms);
    
    public List<TowerStore> getTowerStores();
    
    public int updateTowerLevel(Map map);
    
    public List<Map> getUnOrderTower();
    
    /**
     * 修改未上架站址来源，0 新增，1 下架
     * @param map
     * @return
     */
    public int updateTowerSource(Map map);
    
    
    /**
     * 建站类型 列表
     * @return
     */
    public List<Map> getTowerCreateType();
	
}


