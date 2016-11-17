package com.tower.common.bean;

import java.util.List;
import java.util.Map;

public class DataTowerPage {

	//下辖的管理员
	private List<DataTower> datatowers;
	
	private int datatowerid;
	
	//检索参数
	private DataTowerParameter parameter = new DataTowerParameter();
	
	//检索页No
	private int pageNo =1;
	
	//总数据条数
	private int pagerowtotal;
	
	/**
	 * 界面表示用的  Page信息Map
	 */
	private Map  pageinfo;
	
	//管理地市
	private String adminCity;
	
	private List<Area> adminareas;
	
	private List<City> towercitys;
	
	private List<Area> towerareas;
	
    private List<City> adminCitys;
	

	
	public int getDatatowerid() {
		return datatowerid;
	}

	public void setDatatowerid(int datatowerid) {
		this.datatowerid = datatowerid;
	}

	public List<DataTower> getDatatowers() {
		return datatowers;
	}

	public void setDatatowers(List<DataTower> datatowers) {
		this.datatowers = datatowers;
	}

	public DataTowerParameter getParameter() {
		return parameter;
	}

	public void setParameter(DataTowerParameter parameter) {
		this.parameter = parameter;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPagerowtotal() {
		return pagerowtotal;
	}

	public void setPagerowtotal(int pagerowtotal) {
		this.pagerowtotal = pagerowtotal;
	}

	public Map getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(Map pageinfo) {
		this.pageinfo = pageinfo;
	}

	public String getAdminCity() {
		return adminCity;
	}

	public void setAdminCity(String adminCity) {
		this.adminCity = adminCity;
	}

	public List<Area> getAdminareas() {
		return adminareas;
	}

	public void setAdminareas(List<Area> adminareas) {
		this.adminareas = adminareas;
	}

	public List<City> getTowercitys() {
		return towercitys;
	}

	public void setTowercitys(List<City> towercitys) {
		this.towercitys = towercitys;
	}

	public List<Area> getTowerareas() {
		return towerareas;
	}

	public void setTowerareas(List<Area> towerareas) {
		this.towerareas = towerareas;
	}

	public List<City> getAdminCitys() {
		return adminCitys;
	}

	public void setAdminCitys(List<City> adminCitys) {
		this.adminCitys = adminCitys;
	}
	
	
	
}
