package com.tower.common.bean;

public class TowersInputData {
	
	private String towername;
	
	/**
	 * City表中的 VARCHAR id 城市编号
	 */
	private int cityid;
	
	/**
	 * 地区表中的 int ID 地区主键
	 */
	private int areaid;
	
	/**
	 * 对应 站址表中的站址等级 0-4,0代表所有的类型
	 */
	private int towertype = 0;
	
	/**
	 * 对应 站址表中的站址状态-1--1,-1代表所有的状态，0：未抢单 ，1：已抢单
	 */
	private int towerstate = -1;
	
	private String fromdate;
	
	private String todate;
	
	private int pageindex = 1;
	
	private int towersource=-1;//站址来源
	
	private int towercreatetype;//站址创建类型

	/**
	 * 每页显示条数
	 */
	private int pageSize=10;
	
	
	public String getTowername() {
		return towername;
	}

	public void setTowername(String towername) {
		this.towername = towername;
	}

	public int getCityid() {
		return cityid;
	}

	public void setCityid(int cityid) {
		this.cityid = cityid;
	}

	public int getAreaid() {
		return areaid;
	}

	public void setAreaid(int areaid) {
		this.areaid = areaid;
	}

	public int getTowertype() {
		return towertype;
	}

	public void setTowertype(int towertype) {
		this.towertype = towertype;
	}
	
	public int getTowerstate() {
		return towerstate;
	}

	public void setTowerstate(int towerstate) {
		this.towerstate = towerstate;
	}

	public String getFromdate() {
		return fromdate;
	}

	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}

	public String getTodate() {
		return todate;
	}

	public void setTodate(String todate) {
		this.todate = todate;
	}

	public int getPageindex() {
		return pageindex;
	}

	public void setPageindex(int pageindex) {
		this.pageindex = pageindex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTowersource() {
		return towersource;
	}

	public void setTowersource(int towersource) {
		this.towersource = towersource;
	}

	public int getTowercreatetype() {
		return towercreatetype;
	}

	public void setTowercreatetype(int towercreatetype) {
		this.towercreatetype = towercreatetype;
	}
	
	
	
}
