package com.tower.common.bean;

import java.util.List;

public class SysUserInfo {
	
	private int id;
	
	private String adminid;
	
	private String adminpsd;
	
	private int adminpower;//权限
	
	private String adminpowername;//权限名称
	
	private String adminpowerw;//权限列表
	
	private String adminarea;
	
	private String adminname;
	
	private String adminwxid;
	
	private String adminphone;
	
	private int adminisbind;
	
	private List<City> citys;
	
	private List<Area> areas;
	
	private List<City> allcitys;
	
	private String admindltime;
	
	
	private String admincityid;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAdminid() {
		return adminid;
	}

	public void setAdminid(String adminid) {
		this.adminid = adminid;
	}

	public String getAdminpsd() {
		return adminpsd;
	}

	public void setAdminpsd(String adminpsd) {
		this.adminpsd = adminpsd;
	}

	public int getAdminpower() {
		return adminpower;
	}

	public void setAdminpower(int adminpower) {
		this.adminpower = adminpower;
	}
	
	
	
	

	public String getAdminpowername() {
		return adminpowername;
	}

	public void setAdminpowername(String adminpowername) {
		this.adminpowername = adminpowername;
	}

	public String getAdminpowerw() {
		return adminpowerw;
	}

	public void setAdminpowerw(String adminpowerw) {
		this.adminpowerw = adminpowerw;
	}

	public String getAdminarea() {
		return adminarea;
	}

	public void setAdminarea(String adminarea) {
		this.adminarea = adminarea;
	}

	public String getAdminname() {
		return adminname;
	}

	public void setAdminname(String adminname) {
		this.adminname = adminname;
	}

	public String getAdminwxid() {
		return adminwxid;
	}

	public void setAdminwxid(String adminwxid) {
		this.adminwxid = adminwxid;
	}

	public String getAdminphone() {
		return adminphone;
	}

	public void setAdminphone(String adminphone) {
		this.adminphone = adminphone;
	}

	public int getAdminisbind() {
		return adminisbind;
	}

	public void setAdminisbind(int adminisbind) {
		this.adminisbind = adminisbind;
	}

	public String getAdmindltime() {
		return admindltime;
	}

	public void setAdmindltime(String admindltime) {
		this.admindltime = admindltime;
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

	public List<City> getAllcitys() {
		return allcitys;
	}

	public void setAllcitys(List<City> allcitys) {
		this.allcitys = allcitys;
	}

	public String getAdmincityid() {
		return admincityid;
	}

	public void setAdmincityid(String admincityid) {
		this.admincityid = admincityid;
	}
	
	
	
	

}
