package com.tower.common.bean;

public class AdminManagerInfo {

	//管理员Id
	private int id;
	
	//账户StringId
	private String adminid;
	
	private String adminpsd;
	
	//管理员姓名
	private String adminname;
	
	//角色Id
	private int adminpower;
	
	//角色名
	private String adminpowerName;
	
	//微信id
	private String wxid;
	
	//微信昵称
	private String wxname;
	
	//微信绑定电话
	private String wxphone;
	
	private String adminareacity;
	
	private String adminarea;
	
	//添加时间
	private String adddate;
	
	//是否绑定
	private int adminisbind;

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

	public String getAdminname() {
		return adminname;
	}

	public void setAdminname(String adminname) {
		this.adminname = adminname;
	}

	public int getAdminpower() {
		return adminpower;
	}

	public void setAdminpower(int adminpower) {
		this.adminpower = adminpower;
	}

	public String getAdminpowerName() {
		return adminpowerName;
	}

	public void setAdminpowerName(String adminpowerName) {
		this.adminpowerName = adminpowerName;
	}

	public String getWxid() {
		return wxid;
	}

	public void setWxid(String wxid) {
		this.wxid = wxid;
	}

	public String getWxname() {
		return wxname;
	}

	public void setWxname(String wxname) {
		this.wxname = wxname;
	}

	public String getWxphone() {
		return wxphone;
	}

	public void setWxphone(String wxphone) {
		this.wxphone = wxphone;
	}

	public String getAdddate() {
		return adddate;
	}

	public void setAdddate(String adddate) {
		this.adddate = adddate;
	}

	public String getAdminpsd() {
		return adminpsd;
	}

	public void setAdminpsd(String adminpsd) {
		this.adminpsd = adminpsd;
	}

	public String getAdminareacity() {
		return adminareacity;
	}

	public void setAdminareacity(String adminareacity) {
		this.adminareacity = adminareacity;
	}

	public String getAdminarea() {
		return adminarea;
	}

	public void setAdminarea(String adminarea) {
		this.adminarea = adminarea;
	}

	public int getAdminisbind() {
		return adminisbind;
	}

	public void setAdminisbind(int adminisbind) {
		this.adminisbind = adminisbind;
	}
	
	
}
