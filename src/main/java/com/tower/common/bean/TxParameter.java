package com.tower.common.bean;

public class TxParameter {
	
	private String usertele;
	
	private String username;
	
	private int userarea;
	
	private int usercity=0;
	
	private int userlevel = -1;
	
	private int usertype = -1;
	
	private int state =-2;

	private String towername;
	
	private String towerno;
	
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

	public int getUserarea() {
		return userarea;
	}

	public void setUserarea(int userarea) {
		this.userarea = userarea;
	}

	public int getUsercity() {
		return usercity;
	}

	public void setUsercity(int usercity) {
		this.usercity = usercity;
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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getTowername() {
		return towername;
	}

	public void setTowername(String towername) {
		this.towername = towername;
	}

	public String getTowerno() {
		return towerno;
	}

	public void setTowerno(String towerno) {
		this.towerno = towerno;
	}
	
	
}

