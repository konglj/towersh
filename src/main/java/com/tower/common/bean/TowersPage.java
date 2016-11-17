package com.tower.common.bean;

import java.util.List;
import java.util.Map;

public class TowersPage {
	
	private int page;//0 待审核  1 为未上架  2 上架
	
	private List<TowerType> towertypes;
	
	private List<TowerStore> towerstores;
	
	private Tower tower;
	
	private int towervisible;

	/**
	 * 界面表示用的 站址信息集合
	 */
	private List<Tower> towers;
	
	/**
	 * 界面表示用的  可选城市集合
	 */
	private List<City> citys;
	
	/**
	 * 界面表示用的  可选地区集合
	 */
	private List<Area> areas;
	
	/**
	 * 界面表示用的  Page信息Map
	 */
	private Map  pageinfo;
	
	/**
	 * 检索出 站址信息个数总和
	 */
	private int towercount;
	
	/**
	 * 当前的 页数
	 */
	private int pageIndex;
	
	/**
	 * 每页显示条数
	 */
	private int pageSize=10;
	
	private int adminPower;
	
	private List<Map> towercreattypes;
	
	/**
	 * 用户输入的内容
	 */
	private TowersInputData towersInputData = new TowersInputData();
	
	

	public TowersInputData getTowersInputData() {
		return towersInputData;
	}

	public void setTowersInputData(TowersInputData towersInputData) {
		this.towersInputData = towersInputData;
	}

	public List<Tower> getTowers() {
		return towers;
	}
	
	public void setTowers(List<Tower> towers) {
		this.towers = towers;
	}
	
	public List<City> getCitys() {
		return citys;
	}

	public void setCitys(List<City> citys) {
		this.citys = citys;
	}
	
	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}
	
	public Map getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(Map pageinfo) {
		this.pageinfo = pageinfo;
	}

	public int getTowercount() {
		return towercount;
	}

	public void setTowercount(int towercount) {
		this.towercount = towercount;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Tower getTower() {
		return tower;
	}

	public void setTower(Tower tower) {
		this.tower = tower;
	}

	public List<TowerType> getTowertypes() {
		return towertypes;
	}

	public void setTowertypes(List<TowerType> towertypes) {
		this.towertypes = towertypes;
	}

	public int getTowervisible() {
		return towervisible;
	}

	public void setTowervisible(int towervisible) {
		this.towervisible = towervisible;
	}

	public int getAdminPower() {
		return adminPower;
	}

	public void setAdminPower(int adminPower) {
		this.adminPower = adminPower;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<TowerStore> getTowerstores() {
		return towerstores;
	}

	public void setTowerstores(List<TowerStore> towerstores) {
		this.towerstores = towerstores;
	}

	public List<Map> getTowercreattypes() {
		return towercreattypes;
	}

	public void setTowercreattypes(List<Map> towercreattypes) {
		this.towercreattypes = towercreattypes;
	}
	
	
	
	


}
