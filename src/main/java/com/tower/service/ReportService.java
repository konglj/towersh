package com.tower.service;

import java.util.List;
import java.util.Map;

import com.tower.common.bean.Order;
import com.tower.common.bean.ReportOrder;
import com.tower.common.bean.ReportOrderPage;
import com.tower.common.bean.ReportTx;
import com.tower.common.bean.ReportTxPage;
import com.tower.common.bean.ReportUser;
import com.tower.common.bean.ReportUserPage;

public interface ReportService {
	
	//订单报表
	public List<ReportOrder> getReportOrders(ReportOrderPage reportOrderPage);
	
	//导出订单报表
	public List<Map> getDCReportOrders(ReportOrderPage reportOrderPage);
	
	
	
	
	//收款报表
	public List<ReportTx> getReportTxs(ReportTxPage reportTxPage);
	
	//收款报表导出
	public List<Map> getDCReportTxs(ReportTxPage reportTxPage);
	
	
	
	//用户报表
	public List<ReportUser> getReportUsers(ReportUserPage reportUserPage);
	
	//用户报表导出
	public List<Map> getDCReportUsers(ReportUserPage reportUserPage);
	
	

}
