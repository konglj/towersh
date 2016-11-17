package com.tower.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tower.common.bean.Order;
import com.tower.common.bean.ReportOrder;
import com.tower.common.bean.ReportOrderPage;
import com.tower.common.bean.ReportTx;
import com.tower.common.bean.ReportTxPage;
import com.tower.common.bean.ReportUser;
import com.tower.common.bean.ReportUserPage;
import com.tower.mapper.ReportMapper;
import com.tower.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReportMapper reportMapper;
	@Override
	public List<ReportOrder> getReportOrders(ReportOrderPage reportOrderPage) {
		int count = reportMapper.getOrdersCount(reportOrderPage);
		reportOrderPage.setPagerowtotal(count);
		return reportMapper.getReportOrders(reportOrderPage);

	}
	@Override
	public List<Map> getDCReportOrders(ReportOrderPage reportOrderPage) {
		return reportMapper.getDCReportOrders(reportOrderPage);
	}
	@Override
	public List<ReportTx> getReportTxs(ReportTxPage reportTxPage) {
		int count = reportMapper.getReportTxsCount(reportTxPage);
		reportTxPage.setPagerowtotal(count);
		return reportMapper.getReportTxs(reportTxPage);
	}
	@Override
	public List<Map> getDCReportTxs(ReportTxPage reportTxPage) {
		return reportMapper.getDCReportTxs(reportTxPage);
	}
	@Override
	public List<ReportUser> getReportUsers(ReportUserPage reportUserPage) {
		int count = reportMapper.getReportUsersCount(reportUserPage);
		reportUserPage.setPagerowtotal(count);
		return reportMapper.getReportUsers(reportUserPage);
	}
	@Override
	public List<Map> getDCReportUsers(ReportUserPage reportUserPage) {
		return reportMapper.getDCReportUsers(reportUserPage);
	}

}
