package com.tower.service;

import java.util.List;
import java.util.Map;

import com.tower.common.bean.Message;
import com.tower.common.bean.MessagePage;

public interface MessageService {

	public List<Message> getMessages(MessagePage MessagePage);
	
	public int getMessagesCount(MessagePage MessagePage);

	public Message getMessage(int ID);

	public int deleteMessage(Map map);

	public int changeMessage(Map map);

	public int addMessage(Map map);
	
	
}
