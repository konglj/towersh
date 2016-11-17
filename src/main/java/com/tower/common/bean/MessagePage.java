package com.tower.common.bean;

import java.util.List;
import java.util.Map;

public class MessagePage {
	
	//检索参数
	private ManagerQueryItems queryItem = new ManagerQueryItems();
	
	//用户角色列表
	private List<ManagerPower> powers;
	
	//检索页No
	private int pageNo =1;
	
	//总数据条数
	private int messageCount;
	
	private int adminPower;
	
	private int adminid;
	/**
	 * 界面表示用的  Page信息Map
	 */
	private Map  pageinfo;
	
	private List<Message> messages;
	
	Message message;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
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

	public int getMessageCount() {
		return messageCount;
	}

	public void setMessageCount(int messageCount) {
		this.messageCount = messageCount;
	}

	public Map getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(Map pageinfo) {
		this.pageinfo = pageinfo;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public int getAdminPower() {
		return adminPower;
	}

	public void setAdminPower(int adminPower) {
		this.adminPower = adminPower;
	}

	public int getAdminid() {
		return adminid;
	}

	public void setAdminid(int adminid) {
		this.adminid = adminid;
	}
	
	

}
