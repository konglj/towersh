package com.tower.common.bean;

import java.util.List;
import java.util.Map;

public class DataUserPage {

	//下辖的管理员
	private List<DataUser> datausers;
	
	//检索参数
	private UserParameter parameter = new UserParameter();
	
	private int datauserid;
	
	//检索页No
	private int pageNo =1;
	
	//总数据条数
	private int pagerowtotal;
	
	/**
	 * 界面表示用的  Page信息Map
	 */
	private Map  pageinfo;
	
	//管理地市
	private String adminCity;
	
	private List<Area> adminareas;
	
	private List<City> usercitys;
	
	private List<Area> userareas;
	
	private List<City> adminCitys;

	public List<DataUser> getDatausers() {
		return datausers;
	}

	public void setDatausers(List<DataUser> datausers) {
		this.datausers = datausers;
	}

	public UserParameter getParameter() {
		return parameter;
	}

	public void setParameter(UserParameter parameter) {
		this.parameter = parameter;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
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

	public String getAdminCity() {
		return adminCity;
	}

	public void setAdminCity(String adminCity) {
		this.adminCity = adminCity;
	}

	public List<Area> getAdminareas() {
		return adminareas;
	}

	public void setAdminareas(List<Area> adminareas) {
		this.adminareas = adminareas;
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

	public int getDatauserid() {
		return datauserid;
	}

	public void setDatauserid(int datauserid) {
		this.datauserid = datauserid;
	}

	public List<City> getAdminCitys() {
		return adminCitys;
	}

	public void setAdminCitys(List<City> adminCitys) {
		this.adminCitys = adminCitys;
	}
	
	
}
