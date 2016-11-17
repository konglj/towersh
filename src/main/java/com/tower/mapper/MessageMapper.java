package com.tower.mapper;

import java.util.List;
import java.util.Map;

import com.tower.common.bean.Message;
import com.tower.common.bean.MessagePage;

public interface MessageMapper {
	
	public List<Message> getMessages(MessagePage messagePage);
	
	public int getMessagesCount(MessagePage messagePage);
	
	public Message getMessage(int ID);
	
	public int changeMessage(Map map);
	
	public int addMessage(Map map);
	
	public int delMessage(Map map);
	
}

