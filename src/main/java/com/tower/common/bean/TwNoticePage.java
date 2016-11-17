package com.tower.common.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TwNoticePage {
	
	private int pageno=1;
	
	private int pagerowtotal;
	
	
	/**
	 * 界面表示用的  Page信息Map
	 */
	private Map  pageinfo;
	
	private List<TwNotice> twNotices=new ArrayList<TwNotice>();
	
	private TwNoticeParameter parameter=new TwNoticeParameter();
	
    private List<Area> adminareas;
	
	private String admincity;
	
	private List<City> admincitys;
	
	private String  province;
	
	//管理员类型， 0 超级和打款  1 城市管理和城市财务 2 地区管理
	private int admintype;
	
	//用户类型列表
	private List<UserType> userTypes=new ArrayList<UserType>();
	
	//选择的用户级别
	private int sendUserLevel=0;
	
	//选择的用户类型
	private int sendUserType=0;
	
	private TwNotice twNotice=new TwNotice();
	
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

	public Map getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(Map pageinfo) {
		this.pageinfo = pageinfo;
	}

	public List<TwNotice> getTwNotices() {
		return twNotices;
	}

	public void setTwNotices(List<TwNotice> twNotices) {
		this.twNotices = twNotices;
	}

	public TwNoticeParameter getParameter() {
		return parameter;
	}

	public void setParameter(TwNoticeParameter parameter) {
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
	
	

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public List<City> getAdmincitys() {
		return admincitys;
	}

	public void setAdmincitys(List<City> admincitys) {
		this.admincitys = admincitys;
	}
	
	

	public int getAdmintype() {
		return admintype;
	}

	public void setAdmintype(int admintype) {
		this.admintype = admintype;
	}

	public List<UserType> getUserTypes() {
		return userTypes;
	}

	public void setUserTypes(List<UserType> userTypes) {
		this.userTypes = userTypes;
	}

	public int getSendUserLevel() {
		return sendUserLevel;
	}

	public void setSendUserLevel(int sendUserLevel) {
		this.sendUserLevel = sendUserLevel;
	}

	public int getSendUserType() {
		return sendUserType;
	}

	public void setSendUserType(int sendUserType) {
		this.sendUserType = sendUserType;
	}

	public TwNotice getTwNotice() {
		return twNotice;
	}

	public void setTwNotice(TwNotice twNotice) {
		this.twNotice = twNotice;
	}

	public List<City> getAdminCitys() {
		return adminCitys;
	}

	public void setAdminCitys(List<City> adminCitys) {
		this.adminCitys = adminCitys;
	}

	
	
	
	
}
