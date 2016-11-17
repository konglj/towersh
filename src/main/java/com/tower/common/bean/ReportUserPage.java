package com.tower.common.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportUserPage {

	private int pageno = 1;

	private int pagerowtotal;

	private Map pageinfo;

	private List<ReportUser> reportUser = new ArrayList< ReportUser>();

	private ReportUserParameter parameter = new ReportUserParameter();

	private List<Area> adminareas;

	private String admincity;

	private List<City> usercitys;

	private List<Area> userareas;
	
    private List<City> adminCitys;
	
	private List<UserType> userTypes;


	

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

	public List<ReportUser> getReportUser() {
		return reportUser;
	}

	public void setReportUser(List<ReportUser> reportUser) {
		this.reportUser = reportUser;
	}

	public ReportUserParameter getParameter() {
		return parameter;
	}

	public void setParameter(ReportUserParameter parameter) {
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
	
	

}
