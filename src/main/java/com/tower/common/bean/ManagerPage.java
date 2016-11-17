package com.tower.common.bean;

import java.util.List;
import java.util.Map;

import com.tower.common.Config;

public class ManagerPage {
	
	//当用户为地区时  在管理员区域而不在用户中的
	
	private List<Area> otherareas;

	//本管理员id
	private String adminid;
	
	//本管理员id
	private int power;
	
	private String adminname;
	
	private String adminCitys;
	//下辖的管理员
	private List<AdminManagerInfo> admins;
	
	private AdminManagerInfo admin;
	
	//检索参数
	private ManagerQueryItems queryItem = new ManagerQueryItems();
	
	//用户角色列表
	private List<ManagerPower> powers;
	
	//检索页No
	private int pageNo =1;
	
	//总数据条数
	private int adminCount;
	
	/**
	 * 界面表示用的  Page信息Map
	 */
	private Map  pageinfo;
	
	//本管理者
	private SysUserInfo manager;
	
	//管理地市
	private String adminCity;
	
	private List<Area> adminareas;
	
	private List<City> usercitys;
	
	private List<Area> userareas;
	
	private String areaid;
	
	private List<Power> childPowers;
	
	private int pagerowtotal;
	
	private String province=Config.getProvince();
	
	private int admintype;

	private String defaultcityname;
	
	private String adminjiedaos;//区域管理员细化到街道时用
	
	private String adminqyarea;
	
	public List<AdminManagerInfo> getAdmins() {
		return admins;
	}

	public void setAdmins(List<AdminManagerInfo> admins) {
		this.admins = admins;
	}

	public AdminManagerInfo getAdmin() {
		return admin;
	}

	public void setAdmin(AdminManagerInfo admin) {
		this.admin = admin;
	}

	public ManagerQueryItems getQueryItem() {
		return queryItem;
	}

	public void setQueryItem(ManagerQueryItems queryItem) {
		this.queryItem = queryItem;
	}

	public List<ManagerPower> getPowers() {
		return powers;
	}

	public void setPowers(List<ManagerPower> powers) {
		this.powers = powers;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getAdminCount() {
		return adminCount;
	}

	public void setAdminCount(int adminCount) {
		this.adminCount = adminCount;
	}

	public Map getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(Map pageinfo) {
		this.pageinfo = pageinfo;
	}

	public SysUserInfo getManager() {
		return manager;
	}

	public void setManager(SysUserInfo manager) {
		this.manager = manager;
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

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public List<Power> getChildPowers() {
		return childPowers;
	}

	public void setChildPowers(List<Power> childPowers) {
		this.childPowers = childPowers;
	}

	public String getAdminid() {
		return adminid;
	}

	public void setAdminid(String adminid) {
		this.adminid = adminid;
	}

	public List<Area> getOtherareas() {
		return otherareas;
	}

	public void setOtherareas(List<Area> otherareas) {
		this.otherareas = otherareas;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getPagerowtotal() {
		return pagerowtotal;
	}

	public void setPagerowtotal(int pagerowtotal) {
		this.pagerowtotal = pagerowtotal;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getAdminname() {
		return adminname;
	}

	public void setAdminname(String adminname) {
		this.adminname = adminname;
	}

	public String getAdminCitys() {
		return adminCitys;
	}

	public void setAdminCitys(String adminCitys) {
		this.adminCitys = adminCitys;
	}

	public int getAdmintype() {
		return admintype;
	}

	public void setAdmintype(int admintype) {
		this.admintype = admintype;
	}

	public String getDefaultcityname() {
		return defaultcityname;
	}

	public void setDefaultcityname(String defaultcityname) {
		this.defaultcityname = defaultcityname;
	}

	public String getAdminjiedaos() {
		return adminjiedaos;
	}

	public void setAdminjiedaos(String adminjiedaos) {
		this.adminjiedaos = adminjiedaos;
	}

	public String getAdminqyarea() {
		return adminqyarea;
	}

	public void setAdminqyarea(String adminqyarea) {
		this.adminqyarea = adminqyarea;
	}
	

	
	
	
	
	
	

}
