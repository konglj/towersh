package com.tower.common.bean;

import java.util.List;

public class TxRecord {
	
	private int id;
	
	private String wxid;
	
	private String txid;
	
	private int cardid;
	
	private int fee;
	
	private int state;
	
	private String statename;
	
	private String adddate;
	
	private String bankbackid;
	
	private String bz;
	
	private String completedate;
	
	private String username;
	
	private String usertele;
	
	private String levelname;
	
	private String typename;
	
	private String areaname;
	
	private String cityname;
	
	private String usercompany;
	
	private int charge;
	
	private int getnow;
	
	private int allgeting;
	
	private int gettingnow;
	
	private String bankaccount;
	
	private String bankname;
	
	private String bankopen;
	
	private String bankcardid;
	
	List <TxSource> txsources;
	
	List <TxDoinfo> txdoinfos;
	
	public int getId() {
		return id;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsertele() {
		return usertele;
	}

	public void setUsertele(String usertele) {
		this.usertele = usertele;
	}

	public String getLevelname() {
		return levelname;
	}

	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getUsercompany() {
		return usercompany;
	}

	public void setUsercompany(String usercompany) {
		this.usercompany = usercompany;
	}

	public int getCharge() {
		return charge;
	}

	public void setCharge(int charge) {
		this.charge = charge;
	}

	public int getGetnow() {
		return getnow;
	}

	public void setGetnow(int getnow) {
		this.getnow = getnow;
	}

	public int getAllgeting() {
		return allgeting;
	}

	public void setAllgeting(int allgeting) {
		this.allgeting = allgeting;
	}

	public int getGettingnow() {
		return gettingnow;
	}

	public void setGettingnow(int gettingnow) {
		this.gettingnow = gettingnow;
	}

	public String getBankaccount() {
		return bankaccount;
	}

	public void setBankaccount(String bankaccount) {
		this.bankaccount = bankaccount;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getBankopen() {
		return bankopen;
	}

	public void setBankopen(String bankopen) {
		this.bankopen = bankopen;
	}

	public String getBankcardid() {
		return bankcardid;
	}

	public void setBankcardid(String bankcardid) {
		this.bankcardid = bankcardid;
	}

	public List<TxSource> getTxsources() {
		return txsources;
	}

	public void setTxsources(List<TxSource> txsources) {
		this.txsources = txsources;
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

	public String getTxid() {
		return txid;
	}

	public void setTxid(String txid) {
		this.txid = txid;
	}

	public int getCardid() {
		return cardid;
	}

	public void setCardid(int cardid) {
		this.cardid = cardid;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getAdddate() {
		return adddate;
	}

	public void setAdddate(String adddate) {
		this.adddate = adddate;
	}

	public String getBankbackid() {
		return bankbackid;
	}

	public void setBankbackid(String bankbackid) {
		this.bankbackid = bankbackid;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getCompletedate() {
		return completedate;
	}

	public void setCompletedate(String completedate) {
		this.completedate = completedate;
	}

	public List<TxDoinfo> getTxdoinfos() {
		return txdoinfos;
	}

	public void setTxdoinfos(List<TxDoinfo> txdoinfos) {
		this.txdoinfos = txdoinfos;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}
	
}
