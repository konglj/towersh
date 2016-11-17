package com.tower.common.bean;

import java.util.List;

public class City {

    private int rownumber;
	
	private int id;
	
	private String cityid;
	
	private String cityname;
	
	List <Area> areas;
	
	private int ischeck=0;
	
	private int customcode;//为id*10000
	
	

	public int getRownumber() {
		return rownumber;
	}

	public void setRownumber(int rownumber) {
		this.rownumber = rownumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	public int getIscheck() {
		return ischeck;
	}

	public void setIscheck(int ischeck) {
		this.ischeck = ischeck;
	}

	public int getCustomcode() {
		return customcode;
	}

	public void setCustomcode(int customcode) {
		this.customcode = customcode;
	}
	
	

	
	
}
