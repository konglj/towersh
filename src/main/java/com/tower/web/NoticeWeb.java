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

import com.tower.common.bean.Notice;
import com.tower.common.bean.NoticePage;
import com.tower.common.bean.SysUserInfo;
import com.tower.common.util.PageUtil;
import com.tower.common.util.ParamerUtil;
import com.tower.common.util.ResultUtil;
import com.tower.service.NoticeService;

@Controller
@RequestMapping("/notice")
public class NoticeWeb {

	@Autowired
	private NoticeService noticeService;
	
	@RequestMapping("/notice")
	public ModelAndView GetNotices(HttpServletRequest request, NoticePage noticePage) {
		
		SysUserInfo userInfo=ParamerUtil.getSysUserFromSesson(request);
		noticePage.setAdminPower(userInfo.getAdminpower());
		try{
			List<Notice> notices = noticeService.getNotices(noticePage);
			noticePage.setNotices(notices);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try{
			int noticeCount = noticeService.getNoticesCount(noticePage);
			noticePage.setNoticeCount(noticeCount);
			System.out.println("noticeCount="+noticeCount);
		} catch(Exception e) {
			
		}
		
		// 设置页面
	    PageUtil pageUtil = new PageUtil(noticePage.getPageNo(),
	    		noticePage.getNoticeCount());
	    noticePage.setPageinfo(pageUtil.getPage());
		
		return new ModelAndView("/notice/noticemanage", "noticePage", noticePage);
	}
	
	@RequestMapping("/get_notice")
	public ModelAndView getNotice(int towerid) {
		Notice notice = noticeService.getNotice(towerid);

		return new ModelAndView("/notice/notice_info", "notice", notice);

	}
	
	@RequestMapping("/edit_notice")
	public ModelAndView editnotice(HttpServletRequest request,int noticeid) {

		Notice notice = noticeService.getNotice(noticeid);
		if (notice == null) {
			return null;
		}
		SysUserInfo sysUserInfo=ParamerUtil.getSysUserFromSesson(request);
		NoticePage noticePage = new NoticePage();
		noticePage.setNotice(notice);
		
		return new ModelAndView("/notice/editnotice", "noticePage", noticePage);
	}
	
	@RequestMapping("/updatenotice")
	@ResponseBody
	public Object updateNotice(HttpServletRequest request, String title, String content, int id) {

		Map map=new HashMap();
		map.put("id", id);
		map.put("title", title);
		map.put("content", content);
		int count=0;
		try {
			count =noticeService.changeNotice(map);
		} catch (Exception e) {
			
		}
		if(count==0)
			return ResultUtil.generateResponseMsg("error");
		return ResultUtil.generateResponseMsg("success");
	}
	
	@RequestMapping("/addnotice")
	public ModelAndView addAdmin(HttpServletRequest request) {

		Notice notice = new Notice();
		NoticePage noticePage = new NoticePage();
		noticePage.setNotice(notice);
		return new ModelAndView("/notice/addnotice", "noticePage", noticePage);
	}
	
	@RequestMapping("/add_notice")
	@ResponseBody
	public Object addNotice(HttpServletRequest request, String title, String content) {

		SysUserInfo adminInfo = ParamerUtil.getSysUserFromSesson(request);
		
		Map map=new HashMap();
		map.put("title", title);
		map.put("content", content);
		map.put("publisher", adminInfo.getId());
		map.put("url", "");
		
		int count=0;
		try {
			count =noticeService.addNotice(map);
		} catch (Exception e) {
			
		}
		if(count==0)
			return ResultUtil.generateResponseMsg("error");
		return ResultUtil.generateResponseMsg("success");
	}
	
	@RequestMapping("/view_notice")
	public ModelAndView getNotice(HttpServletRequest request,int noticeid) {

		Notice notice = noticeService.getNotice(noticeid);
		if (notice == null) {
			return null;
		}
		NoticePage noticePage = new NoticePage();
		noticePage.setNotice(notice);
		return new ModelAndView("/notice/viewnotice", "noticePage", noticePage);
	}
	
	@RequestMapping("/del_notice")
	@ResponseBody
	public Object delNotice(HttpServletRequest request,String id) {
		Map map=new HashMap();
		map.put("id", id);
		
		int count=0;
		try {
			count =noticeService.deleteNotice(map);
		} catch (Exception e) {
			
		}
		if(count==0)
			return ResultUtil.generateResponseMsg("error");
		return ResultUtil.generateResponseMsg("success");
	}
	
}
