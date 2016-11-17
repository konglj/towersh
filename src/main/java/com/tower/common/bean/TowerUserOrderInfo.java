package com.tower.common.bean;

public class TowerUserOrderInfo {
	private int id;

	private String wxid;

	private int ordercount;

	private int succcount;

	private int ingcount;

	private int failcount;

	private int cancelcount;
	
	private int concancelcount;
	
	private String seetowerids;
	
	private String seetowersate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWxid() {
		return wxid;
	}

	public void setWxid(String wxid) {
		this.wxid = wxid;
	}

	public int getOrdercount() {
		return ordercount;
	}

	public void setOrdercount(int ordercount) {
		this.ordercount = ordercount;
	}

	public int getSucccount() {
		return succcount;
	}

	public void setSucccount(int succcount) {
		this.succcount = succcount;
	}

	public int getIngcount() {
		return ingcount;
	}

	public void setIngcount(int ingcount) {
		this.ingcount = ingcount;
	}

	public int getFailcount() {
		return failcount;
	}

	public void setFailcount(int failcount) {
		this.failcount = failcount;
	}

	public int getCancelcount() {
		return cancelcount;
	}

	public void setCancelcount(int cancelcount) {
		this.cancelcount = cancelcount;
	}

	public int getConcancelcount() {
		return concancelcount;
	}

	public void setConcancelcount(int concancelcount) {
		this.concancelcount = concancelcount;
	}

	public String getSeetowerids() {
		return seetowerids;
	}

	public void setSeetowerids(String seetowerids) {
		this.seetowerids = seetowerids;
	}

	public String getSeetowersate() {
		return seetowersate;
	}

	public void setSeetowersate(String seetowersate) {
		this.seetowersate = seetowersate;
	}
	
	

}
