package com.tower.common.bean;

import java.util.List;

public class IndexPage {
	
	private SysUserInfo user;
	
	private List<Notice> notices;
	
	private List<Message> messages;
	
	private Integer usercount;
	
	private Integer towercount;
	
	private Integer ingordercount;
	
	private Integer succordercount;

	private List<City> adminCitys;
	
	public Integer getUsercount() {
		return usercount;
	}

	public void setUsercount(Integer usercount) {
		this.usercount = usercount;
	}

	public Integer getTowercount() {
		return towercount;
	}

	public void setTowercount(Integer towercount) {
		this.towercount = towercount;
	}

	public Integer getIngordercount() {
		return ingordercount;
	}

	public void setIngordercount(Integer ingordercount) {
		this.ingordercount = ingordercount;
	}

	public Integer getSuccordercount() {
		return succordercount;
	}

	public void setSuccordercount(Integer succordercount) {
		this.succordercount = succordercount;
	}


	public SysUserInfo getUser() {
		return user;
	}

	public void setUser(SysUserInfo user) {
		this.user = user;
	}

	public List<Notice> getNotices() {
		return notices;
	}

	public void setNotices(List<Notice> notices) {
		this.notices = notices;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public List<City> getAdminCitys() {
		return adminCitys;
	}

	public void setAdminCitys(List<City> adminCitys) {
		this.adminCitys = adminCitys;
	}
	
	

}
