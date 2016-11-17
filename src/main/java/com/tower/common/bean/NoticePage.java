package com.tower.common.bean;

import java.util.List;
import java.util.Map;

public class NoticePage {
	
	//检索参数
	private ManagerQueryItems queryItem = new ManagerQueryItems();
	
	//检索页No
	private int pageNo =1;
	
	//总数据条数
	private int noticeCount;
	
	private int adminPower;
	
	/**
	 * 界面表示用的  Page信息Map
	 */
	private Map  pageinfo;
	
	private List<Notice> notices;
	
	private Notice notice;

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public ManagerQueryItems getQueryItem() {
		return queryItem;
	}

	public void setQueryItem(ManagerQueryItems queryItem) {
		this.queryItem = queryItem;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getNoticeCount() {
		return noticeCount;
	}

	public void setNoticeCount(int noticeCount) {
		this.noticeCount = noticeCount;
	}

	public Map getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(Map pageinfo) {
		this.pageinfo = pageinfo;
	}

	public List<Notice> getNotices() {
		return notices;
	}

	public void setNotices(List<Notice> notices) {
		this.notices = notices;
	}

	public int getAdminPower() {
		return adminPower;
	}

	public void setAdminPower(int adminPower) {
		this.adminPower = adminPower;
	}
	
	

}
