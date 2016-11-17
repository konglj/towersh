package com.tower.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tower.common.bean.AdminManagerInfo;
import com.tower.common.bean.BindUser;
import com.tower.common.bean.BindUserPage;
import com.tower.common.bean.ManagerPage;
import com.tower.common.bean.ManagerPower;
import com.tower.common.bean.Message;
import com.tower.common.bean.MessagePage;
import com.tower.common.bean.Power;
import com.tower.mapper.ManagerMapper;
import com.tower.mapper.MessageMapper;
import com.tower.service.ManagerService;
import com.tower.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService{
	
	@Autowired
	private MessageMapper messageMapper;

	@Override
	@Transactional
	public List<Message> getMessages(MessagePage MessagePage) {
		// TODO Auto-generated method stub
		return messageMapper.getMessages(MessagePage);
	}

	@Override
	@Transactional
	public int getMessagesCount(MessagePage MessagePage) {
		// TODO Auto-generated method stub
		return messageMapper.getMessagesCount(MessagePage);
	}

	@Override
	@Transactional
	public Message getMessage(int ID) {
		// TODO Auto-generated method stub
		return messageMapper.getMessage(ID);
	}

	@Override
	@Transactional
	public int deleteMessage(Map map) {
		int count = 0;
		count = messageMapper.delMessage(map);
		if (count == 0)
			throw new RuntimeException();
		return 1;
	}

	@Override
	public int changeMessage(Map map) {
		int count = 0;
		count = messageMapper.changeMessage(map);
		if (count == 0)
			throw new RuntimeException();
		return 1;
	}

	@Override
	public int addMessage(Map map) {
		int count = 0;
		count = messageMapper.addMessage(map);
		if (count == 0)
			throw new RuntimeException();
		return 1;
	}

}
