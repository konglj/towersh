package com.tower.common.bean;

import java.util.List;
import java.util.Map;

public class TxPage {
	
	private int pageno=1;
	
	private int pagerowtotal;
	
	private int pageSize=10;
	
	private Map  pageinfo;
	
	private TxRecord txrecord;
	
	private TxParameter parameter=new TxParameter();
	
	private List<Area> adminareas;
	
	private String admincity;
	
    private List<City> usercitys;
	
	private List<Area> userareas;
	
	private List<TxRecord> txrecords;
	
	private List<City> adminCitys;
	
	private List<UserType> userTypes;
	
	private int adminpower;
	
	private String txid;

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

	public TxRecord getTxrecord() {
		return txrecord;
	}

	public void setTxrecord(TxRecord txrecord) {
		this.txrecord = txrecord;
	}

	public TxParameter getParameter() {
		return parameter;
	}

	public void setParameter(TxParameter parameter) {
		this.parameter = parameter;
	}

	public List<Area> getAdminareas() {
		return adminareas;
	}

	public void setAdminareas(List<Area> adminareas) {
		this.adminareas = adminareas;
	}

	public String getAdmincity() {
		return admincity;
	}

	public void setAdmincity(String admincity) {
		this.admincity = admincity;
	}

	public List<City> getUsercitys() {
		return usercitys;
	}

	public void setUsercitys(List<City> usercitys) {
		this.usercitys = usercitys;
	}

	public List<Area> getUserareas() {
		return userareas;
	}

	public void setUserareas(List<Area> userareas) {
		this.userareas = userareas;
	}

	public List<TxRecord> getTxrecords() {
		return txrecords;
	}

	public void setTxrecords(List<TxRecord> txrecords) {
		this.txrecords = txrecords;
	}

	public List<City> getAdminCitys() {
		return adminCitys;
	}

	public void setAdminCitys(List<City> adminCitys) {
		this.adminCitys = adminCitys;
	}

	public List<UserType> getUserTypes() {
		return userTypes;
	}

	public void setUserTypes(List<UserType> userTypes) {
		this.userTypes = userTypes;
	}

	public int getAdminpower() {
		return adminpower;
	}

	public void setAdminpower(int adminpower) {
		this.adminpower = adminpower;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getTxid() {
		return txid;
	}

	public void setTxid(String txid) {
		this.txid = txid;
	}
	
	
	
	
}
