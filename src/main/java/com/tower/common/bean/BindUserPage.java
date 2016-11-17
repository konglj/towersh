package com.tower.common.bean;

import java.util.List;
import java.util.Map;

public class BindUserPage {
	
	private int adminid;
	
	private String usertele;
	
	private String username;
	
   private int pageno=1;
	
	private int pagerowtotal;
	
	private Map  pageinfo;
	
	private List<BindUser> bindusers;

	
	
	public int getAdminid() {
		return adminid;
	}

	public void setAdminid(int adminid) {
		this.adminid = adminid;
	}

	public String getUsertele() {
		return usertele;
	}

	public void setUsertele(String usertele) {
		this.usertele = usertele;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getPageno() {
		return pageno;
	}

	public void setPageno(int pageno) {
		this.pageno = pageno;
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

	public List<BindUser> getBindusers() {
		return bindusers;
	}

	public void setBindusers(List<BindUser> bindusers) {
		this.bindusers = bindusers;
	}
	
	
	

}
