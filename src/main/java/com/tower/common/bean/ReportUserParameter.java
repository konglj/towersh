package com.tower.common.bean;

public class ReportUserParameter {
	
	private String usertele;
	
	private String username;
	
	private int userarea;
	
	private int usercity;
	
	private int userlevel = -1;
	
	private int usertype = -1;
	
	private int userstate = -1;
	
    private String useraddtimestart;
	
	private String useraddtimeend;

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

	public int getUserstate() {
		return userstate;
	}

	public void setUserstate(int userstate) {
		this.userstate = userstate;
	}

	public String getUseraddtimestart() {
		return useraddtimestart;
	}

	public void setUseraddtimestart(String useraddtimestart) {
		this.useraddtimestart = useraddtimestart;
	}

	public String getUseraddtimeend() {
		return useraddtimeend;
	}

	public void setUseraddtimeend(String useraddtimeend) {
		this.useraddtimeend = useraddtimeend;
	}

}
