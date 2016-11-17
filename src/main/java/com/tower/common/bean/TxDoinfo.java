package com.tower.common.bean;

import java.util.List;

public class TxDoinfo {
	
	private int id;
	
	private String txid;
	
	private String douserid;
	
	private String dousername;
	
	private String dousertele;
	
	private int usertype;
	
	private String usertypename;
	
	private int nowstate;
	
	private String nowstatename;
	
	private String dodate;
	
	private String shinfo;
	
	private String bz;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTxid() {
		return txid;
	}

	public void setTxid(String txid) {
		this.txid = txid;
	}

	public String getDouserid() {
		return douserid;
	}

	public void setDouserid(String douserid) {
		this.douserid = douserid;
	}

	public String getDousername() {
		return dousername;
	}

	public void setDousername(String dousername) {
		this.dousername = dousername;
	}

	public String getDousertele() {
		return dousertele;
	}

	public void setDousertele(String dousertele) {
		this.dousertele = dousertele;
	}

	public int getUsertype() {
		return usertype;
	}

	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}

	public String getUsertypename() {
		return usertypename;
	}

	public void setUsertypename(String usertypename) {
		this.usertypename = usertypename;
	}

	public int getNowstate() {
		return nowstate;
	}

	public void setNowstate(int nowstate) {
		this.nowstate = nowstate;
	}

	public String getNowstatename() {
		return nowstatename;
	}

	public void setNowstatename(String nowstatename) {
		this.nowstatename = nowstatename;
	}

	public String getShinfo() {
		return shinfo;
	}

	public void setShinfo(String shinfo) {
		this.shinfo = shinfo;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getDodate() {
		return dodate;
	}

	public void setDodate(String dodate) {
		this.dodate = dodate;
	}
}
