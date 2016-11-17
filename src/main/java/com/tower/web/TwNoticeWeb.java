package com.tower.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tower.common.Config;
import com.tower.common.bean.Area;
import com.tower.common.bean.City;
import com.tower.common.bean.Order;
import com.tower.common.bean.OrderPage;
import com.tower.common.bean.OrderParameter;
import com.tower.common.bean.SendNoticeParameter;
import com.tower.common.bean.SysUserInfo;
import com.tower.common.bean.TwNotice;
import com.tower.common.bean.TwNoticePage;
import com.tower.common.bean.TwNoticeParameter;
import com.tower.common.bean.TwNoticeSend;
import com.tower.common.bean.UserManagePage;
import com.tower.common.bean.UserType;
import com.tower.common.util.FileUpload;
import com.tower.common.util.HttpRequst;
import com.tower.common.util.PageUtil;
import com.tower.common.util.ParamerUtil;
import com.tower.common.util.ResultUtil;
import com.tower.service.AreaService;
import com.tower.service.QRCodeService;
import com.tower.service.TwnoticeService;
import com.tower.service.UserService;

@Controller
@RequestMapping("/twnotice")
public class TwNoticeWeb {

	@Autowired
	private TwnoticeService twnoticeService;
	
	@Autowired
	private QRCodeService qrCodeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AreaService areaService;
	
	@RequestMapping("/notice")
	public ModelAndView getTwNotices(HttpServletRequest request,TwNoticePage twNoticePage){
		
		boolean isback=ParamerUtil.getIsBackPage(request);
		if(isback){
			twNoticePage=(TwNoticePage)request.getSession().getAttribute("twnotice");
		}else{
			request.getSession().setAttribute("twnotice", twNoticePage);
		}
		
		if (twNoticePage == null)
			twNoticePage = new TwNoticePage();

		TwNoticeParameter parameter = twNoticePage.getParameter();
		SysUserInfo sysUserInfo = ParamerUtil.getSysUserFromSesson(request);
		List<City> citys = sysUserInfo.getCitys();
		twNoticePage.setAdmincitys(citys);
		twnoticeService.getTwNotices(twNoticePage);
		if(twNoticePage.getTwNotices()!=null&&twNoticePage.getTwNotices().size()>0){
			String path = request.getSession().getServletContext()
					.getRealPath("/");
			setEwm(path,twNoticePage.getTwNotices());
			
		}
		
		// 设置页面
		PageUtil pageUtil = new PageUtil(twNoticePage.getPageno(),
				twNoticePage.getPagerowtotal());
		twNoticePage.setPageinfo(pageUtil.getPage());
		return new ModelAndView("/twnotice/noticemanage","twNoticePage",twNoticePage);
	}
	
	@RequestMapping("/editnotice")
	public ModelAndView editTwNotice(HttpServletRequest request,int noticeid){
		TwNotice twNotice=null;
		if(noticeid!=0){
			twNotice=twnoticeService.getTwNotice(noticeid);
		}
		if(twNotice==null)
			twNotice=new TwNotice();
		return new ModelAndView("/twnotice/editnotice","twNotice",twNotice);
	}
	
	@RequestMapping("/save_notice")
	@ResponseBody
	public Object addTwNotice(HttpServletRequest request,TwNotice twNotice){
		SysUserInfo userInfo=ParamerUtil.getSysUserFromSesson(request);
		twNotice.setAdduser(userInfo.getId());
		//twNotice.setUrlroot(Config.getWebip());
		int count=0;
		try {
			
		
		if(twNotice.getId()==0)
		   count=twnoticeService.insertTwNotice(twNotice);
		else
			count=twnoticeService.updateTwNotice(twNotice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(count==0)
			ResultUtil.generateResponseMsg("error","保存失败");
		return ResultUtil.generateResponseMsg("success");
	}
	
	@RequestMapping("/del_notice")
	@ResponseBody
	public Object delTwNotice(HttpServletRequest request,int id){
		int count=0;
		try {
			
		
		   count=twnoticeService.delTwNotice(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(count==0)
			return ResultUtil.generateResponseMsg("error","删除失败");
		return ResultUtil.generateResponseMsg("success");
	}
	
	@RequestMapping("/view_notice")
	public ModelAndView getTwNoticeInfo(HttpServletRequest request,int noticeid){
		TwNotice twNotice=null;
	    twNotice=twnoticeService.getTwNoticeInfo(noticeid);
		if(twNotice==null)
			twNotice=new TwNotice();
		return new ModelAndView("/twnotice/viewnotice","twNotice",twNotice);
	}
	
	
	@RequestMapping("/noticeinfo")
	public ModelAndView getTwNoticePhone(HttpServletRequest request,int noticeid){
		TwNotice twNotice=null;
			twNotice=twnoticeService.getTwNotice(noticeid);
		if(twNotice==null)
			twNotice=new TwNotice();
		return new ModelAndView("/twnotice/notice","twNotice",twNotice);
	}
	
	
	@RequestMapping("/sengmsg")
	public ModelAndView SendTwNotice(HttpServletRequest request,int noticeid){
		TwNotice twNotice=twnoticeService.getTwNotice(noticeid);
		List<UserType> userTypes=userService.getUserTypes();
		TwNoticePage twNoticePage=new TwNoticePage();
		twNoticePage.setUserTypes(userTypes);
		twNoticePage.setTwNotice(twNotice);
		SysUserInfo sysUserInfo = ParamerUtil.getSysUserFromSesson(request);
		List<City> citys = sysUserInfo.getCitys();
		twNoticePage.setAdmincitys(citys);
		twNoticePage.setProvince(Config.getProvince());
		//获取用户类型
		return new ModelAndView("/twnotice/sendmsg","twNoticePage",twNoticePage);
	}
	
	

	@RequestMapping("/send")
	@ResponseBody
	public Object sendTwNotices(HttpServletRequest request,SendNoticeParameter parameter ){
		SysUserInfo sysUserInfo=ParamerUtil.getSysUserFromSesson(request);
		parameter.setSenduser(sysUserInfo.getId());
		parameter.setSendusername(sysUserInfo.getAdminname());
		if(parameter.getCitys()==null||parameter.getCitys().size()==0)
			return ResultUtil.generateResponseMsg("error","请发送选择区域！");
		int count=0;
		try {
			count=twnoticeService.sendNotice(parameter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(count==0)
			return ResultUtil.generateResponseMsg("error","发送失败！");
		
		//获取用户类型
		return ResultUtil.generateResponseMsg("success");
	}
	
	@RequestMapping("/send_list")
	public ModelAndView sendTwNoticeSendList(HttpServletRequest request,int noticeid ){
	    List<TwNoticeSend> list=twnoticeService.getTwNoticeSends(noticeid);
	    if(list==null)
	    	list=new ArrayList<TwNoticeSend>();
	    return new ModelAndView("/twnotice/send_info","sends",list);
		
	}
	
	private void setEwm(String path,List<TwNotice> list){
		for (TwNotice twNotice : list) {
			String ccbPath = path + "/assets/img/mid.png";
			String ewm="/images/ewm/" + twNotice.getId()+".jpg";
			String ewmPath = path +ewm;
			try {
				qrCodeService.QRCodeEncoder(ewmPath, "jpg", twNotice.getUrlroot(), 140);
				twNotice.setEwmurl(".."+ewm);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	@RequestMapping("/upload_image")
	@ResponseBody
	public Object  uploadImage(HttpServletRequest request){
		String filepath="images\\twnotice\\";
		String image=FileUpload.uploadFile(request, filepath, "imgFile");
		Map map=new HashMap();
		if(image==null){
			map.put("error", 1);
			map.put("message", "上传失败");
		}else{
			map.put("error", 0);
			String imagePath=Config.getSystemip().subSequence(0, Config.getSystemip().lastIndexOf("/"))+image.replace("\\", "/");
			map.put("url",imagePath );
		}
		
		return map;
	}
 }
