package com.tower.web;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tower.common.bean.ManagerPage;
import com.tower.common.bean.ManagerPower;
import com.tower.common.bean.Message;
import com.tower.common.bean.MessagePage;
import com.tower.common.bean.SysUserInfo;
import com.tower.common.util.PageUtil;
import com.tower.common.util.ParamerUtil;
import com.tower.common.util.ResultUtil;
import com.tower.service.ManagerService;
import com.tower.service.MessageService;

@Controller
@RequestMapping("/message")
public class MessageWeb {

	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ManagerService managerService;
	
	@RequestMapping("/message")
	public ModelAndView Getmessages(HttpServletRequest request, MessagePage messagePage) {
		
		
		try{
		 	List<ManagerPower> powers = managerService.getManagerPowers(new ManagerPage());
		 	messagePage.setPowers(powers);
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
		SysUserInfo userInfo=ParamerUtil.getSysUserFromSesson(request);
		messagePage.setAdminPower(userInfo.getAdminpower());
		messagePage.setAdminid(userInfo.getId());
		
		try{
			List<Message> messages = messageService.getMessages(messagePage);
			messagePage.setMessages(messages);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try{
			int messageCount = messageService.getMessagesCount(messagePage);
			messagePage.setMessageCount(messageCount);
			System.out.println("messageCount="+messageCount);
		} catch(Exception e) {
			
		}
		
		// 设置页面
	    PageUtil pageUtil = new PageUtil(messagePage.getPageNo(),
	    		messagePage.getMessageCount());
	    messagePage.setPageinfo(pageUtil.getPage());
		
		return new ModelAndView("/message/messagemanage", "messagePage", messagePage);
	}
	
	@RequestMapping("/get_message")
	public ModelAndView getMEssage(int messageid) {
		Message message = messageService.getMessage(messageid);

		return new ModelAndView("/message/message_info", "message", message);

	}
	
	@RequestMapping("/edit_message")
	public ModelAndView editmessage(HttpServletRequest request,int messageid) {

		Message message = messageService.getMessage(messageid);
		if (message == null) {
			return null;
		}
		SysUserInfo sysUserInfo=ParamerUtil.getSysUserFromSesson(request);
		MessagePage messagePage = new MessagePage();
		messagePage.setMessage(message);
		
		return new ModelAndView("/message/editmessage", "messagePage", messagePage);
	}
	
	@RequestMapping("/updatemessage")
	@ResponseBody
	public Object updatemessage(HttpServletRequest request, String title, String content, int id) {

		Map map=new HashMap();
		map.put("id", id);
		map.put("title", title);
		map.put("content", content);
		int count=0;
		try {
			count =messageService.changeMessage(map);
		} catch (Exception e) {
			
		}
		if(count==0)
			return ResultUtil.generateResponseMsg("error");
		return ResultUtil.generateResponseMsg("success");
	}
	
	@RequestMapping("/addmessage")
	public ModelAndView addAdmin(HttpServletRequest request) {

		Message message = new Message();
		MessagePage messagePage = new MessagePage();
		messagePage.setMessage(message);
		return new ModelAndView("/message/addmessage", "messagePage", messagePage);
	}
	
	
	@RequestMapping("/add_message")
	@ResponseBody
	public Object addmessage(HttpServletRequest request, String title, String content) {

		SysUserInfo adminInfo = ParamerUtil.getSysUserFromSesson(request);
		
		Map map=new HashMap();
		map.put("title", title);
		map.put("content", content);
		map.put("messager", adminInfo.getId());
		map.put("url","");
		
		int count=0;
		try {
			count =messageService.addMessage(map);
		} catch (Exception e) {
			
		}
		if(count==0)
			return ResultUtil.generateResponseMsg("error");
		return ResultUtil.generateResponseMsg("success");
	}
	
	@RequestMapping("/view_message")
	public ModelAndView getMessage(HttpServletRequest request,int messageid) {

		Message message = messageService.getMessage(messageid);
		if (message == null) {
			return null;
		}
		MessagePage messagePage = new MessagePage();
		messagePage.setMessage(message);
		return new ModelAndView("/message/viewmessage", "messagePage", messagePage);
	}
	
	@RequestMapping("/del_message")
	@ResponseBody
	public Object delmessage(HttpServletRequest request,String id) {
		Map map=new HashMap();
		map.put("id", id);
		
		int count=0;
		try {
			count =messageService.deleteMessage(map);
		} catch (Exception e) {
			
		}
		if(count==0)
			return ResultUtil.generateResponseMsg("error");
		return ResultUtil.generateResponseMsg("success");
	}
	
}
