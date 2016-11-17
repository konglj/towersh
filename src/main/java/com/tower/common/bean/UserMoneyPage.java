package com.tower.common.bean;

import java.util.List;
import java.util.Map;

public class UserMoneyPage {
	
	private int pageno=1;
	
	private int pagerowtotal;
	
	private int pageSize=10;
	
	private Map  pageinfo;
	
	private  UserParameter parameter=new UserParameter();
	
	private List<Area> adminareas;
	
	private String admincity;
	
    private List<City> usercitys;
	
	private List<Area> userareas;
	
	private List<UserMoney> usermoneys;

	private List<UserType> userTypes;
	
	private List<City> adminCitys;
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

	
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Map getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(Map pageinfo) {
		this.pageinfo = pageinfo;
	}

	public  UserParameter getParameter() {
		return parameter;
	}

	public void setParameter(UserParameter parameter) {
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

	public List<UserMoney> getUsermoneys() {
		return usermoneys;
	}

	public void setUsermoneys(List<UserMoney> usermoneys) {
		this.usermoneys = usermoneys;
	}

	public List<UserType> getUserTypes() {
		return userTypes;
	}

	public void setUserTypes(List<UserType> userTypes) {
		this.userTypes = userTypes;
	}

	public List<City> getAdminCitys() {
		return adminCitys;
	}

	public void setAdminCitys(List<City> adminCitys) {
		this.adminCitys = adminCitys;
	}

	
	
}
