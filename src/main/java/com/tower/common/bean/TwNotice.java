package com.tower.common.bean;

import com.tower.common.Config;

public class TwNotice {
	
	private int id;
	
	private String title;
	
	private String content;
	
	private int adduser;
	
	private String addusername;
	
	private String addtime;
	
	private String urlroot;
	
	private String ewmurl;
	
	private int sendcount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getAdduser() {
		return adduser;
	}

	public void setAdduser(int adduser) {
		this.adduser = adduser;
	}

	public String getAddusername() {
		return addusername;
	}

	public void setAddusername(String addusername) {
		this.addusername = addusername;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getUrlroot() {
		return Config.getSystemip()+"/twnotice/noticeinfo.html?noticeid="+id;
	}


	public int getSendcount() {
		return sendcount;
	}

	public void setSendcount(int sendcount) {
		this.sendcount = sendcount;
	}

	public String getEwmurl() {
		return ewmurl;
	}

	public void setEwmurl(String ewmurl) {
		this.ewmurl = ewmurl;
	}
	
	
	
	
	
	
	

}
