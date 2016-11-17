package com.tower.common.bean;

import java.util.List;
import java.util.Map;

public class ReportOrderPage {

	
	private int pageno=1;
	
	private int pagerowtotal;
	
	private Map  pageinfo;
	
	private ReportOrder order=new ReportOrder();
	
	private ReportOrderParameter parameter=new ReportOrderParameter();
	
	private List<Area> adminareas;
	
	private String admincity;
	
	private List<City> towercitys;
	
	private List<Area> towerareas;
	
	private List<ReportOrder> orders;
	
	private List<City> adminCitys;

	private List<OrderState> orderstates;
	
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

	public ReportOrder getOrder() {
		return order;
	}

	public void setOrder(ReportOrder order) {
		this.order = order;
	}

	public ReportOrderParameter getParameter() {
		return parameter;
	}

	public void setParameter(ReportOrderParameter parameter) {
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

	public List<City> getTowercitys() {
		return towercitys;
	}

	public void setTowercitys(List<City> towercitys) {
		this.towercitys = towercitys;
	}

	public List<Area> getTowerareas() {
		return towerareas;
	}

	public void setTowerareas(List<Area> towerareas) {
		this.towerareas = towerareas;
	}

	public List<ReportOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<ReportOrder> orders) {
		this.orders = orders;
	}

	public List<City> getAdminCitys() {
		return adminCitys;
	}

	public void setAdminCitys(List<City> adminCitys) {
		this.adminCitys = adminCitys;
	}

	public List<OrderState> getOrderstates() {
		return orderstates;
	}

	public void setOrderstates(List<OrderState> orderstates) {
		this.orderstates = orderstates;
	}
	
	
	
	
	
	
	
	
	
	

	
}
