package com.tower.common.bean;

import java.util.List;

public class SendNoticeParameter {
	
	private String noticetitle;
	
	private int noticeid;
	
	private int userlevel;
	
	private int usertype;
	
	private int admintype;
	
	
	
	private List<String> citys;
	
	
	private List<Integer> areas;

	private int senduser;
	
	private String sendusername;

	
	
	public String getNoticetitle() {
		return noticetitle;
	}


	public void setNoticetitle(String noticetitle) {
		this.noticetitle = noticetitle;
	}


	public int getNoticeid() {
		return noticeid;
	}


	public void setNoticeid(int noticeid) {
		this.noticeid = noticeid;
	}


	public int getUserlevel() {
		return userlevel;
	}


	public void setUserlevel(int userlevel) {
		this.userlevel = userlevel;
	}


	public int getUsertype() {
		return usertype;
	}


	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}

	

	public int getAdmintype() {
		return admintype;
	}


	public void setAdmintype(int admintype) {
		this.admintype = admintype;
	}


	public List<String> getCitys() {
		return citys;
	}


	public void setCitys(List<String> citys) {
		this.citys = citys;
	}


	public List<Integer> getAreas() {
		return areas;
	}


	public void setAreas(List<Integer> areas) {
		this.areas = areas;
	}


	public int getSenduser() {
		return senduser;
	}


	public void setSenduser(int senduser) {
		this.senduser = senduser;
	}


	public String getSendusername() {
		return sendusername;
	}


	public void setSendusername(String sendusername) {
		this.sendusername = sendusername;
	}
	
	
	
	
	

}
