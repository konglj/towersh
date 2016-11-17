package com.tower.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jcraft.jsch.jce.MD5;
import com.tower.common.Config;
import com.tower.common.bean.Area;
import com.tower.common.bean.City;
import com.tower.common.bean.DataTower;
import com.tower.common.bean.DataTowerPage;
import com.tower.common.bean.DataTowerParameter;
import com.tower.common.bean.DataUser;
import com.tower.common.bean.DataUserPage;
import com.tower.common.bean.DataUserSourcePage;
import com.tower.common.bean.IndexPage;
import com.tower.common.bean.OrderPage;
import com.tower.common.bean.QueryTerms;
import com.tower.common.bean.Setting;
import com.tower.common.bean.SysUserInfo;
import com.tower.common.bean.TxPage;
import com.tower.common.bean.UserManagePage;
import com.tower.common.bean.UserParameter;
import com.tower.common.bean.UserSourceAnalysis;
import com.tower.common.util.ExcelDC;
import com.tower.common.util.MD5Encryption;
import com.tower.common.util.PageUtil;
import com.tower.common.util.PageUtil1;
import com.tower.common.util.ParamerUtil;
import com.tower.common.util.ResultUtil;
import com.tower.common.util.StrUtil;
import com.tower.common.util.TimeUtil;
import com.tower.service.AreaService;
import com.tower.service.LoginService;
import com.tower.service.SettingService;
import com.tower.service.TowerService;
import com.tower.service.UserService;

@Controller
@RequestMapping("/")
public class LoginWeb {

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private TowerService towerService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AreaService areaService;

	@Autowired
	private SettingService settingService;
	
	@RequestMapping("/login")
	public ModelAndView getLogin() {

		return new ModelAndView("/login");
	}

	@RequestMapping("/footer")
	public ModelAndView getFooter() {

		return new ModelAndView("/footer");
	}

	@RequestMapping("/top")
	public ModelAndView getTop(HttpServletRequest request) {
		SysUserInfo userInfo=ParamerUtil.getSysUserFromSesson(request);
		//int roleid=userInfo.getAdminpower();
		//获取权限列表
		String powerw=","+userInfo.getAdminpowerw()+",";
		Map map=new HashMap();
		map.put("name", userInfo.getAdminid());
		map.put("powerw", powerw);
		map.put("adminpower", userInfo.getAdminpower());
		return new ModelAndView("/top","info",map);
	}
	
	@RequestMapping("/service")
	public ModelAndView getService(HttpServletRequest request) {
		return new ModelAndView("/service");
	}

	@RequestMapping("/go_login")
	@ResponseBody
	public Object login(HttpServletRequest request,
			@RequestParam(value = "userid") String adminid,
			@RequestParam(value = "userpsd") String password) {
		
		Setting set=settingService.getSetting("indate");
		if(set==null||set.getContent()==null||set.getContent().equals("")){
			return ResultUtil.generateResponseMsg("error","您登录的网站已过期！");
		}
		String indate=Config.getIndate();
		
		if(indate==null||indate.equals("")){
			return ResultUtil.generateResponseMsg("error","您登录的网站已过期！");
		}
		String md5Indate="bolion-"+indate+"-123?";
		md5Indate=MD5Encryption.generatePassword(md5Indate).toUpperCase();
		if(!md5Indate.equals(set.getContent())){
			return ResultUtil.generateResponseMsg("error","您登录的网站已过期！");
		}
		//String nowday=TimeUtil.getNowDay();
		boolean result=TimeUtil.isTrialTimeOut(indate);
		if(result){
			return ResultUtil.generateResponseMsg("error","您登录的网站已过期！");
		}
		
		// 验证用户
		Map map = new HashMap();
		map.put("adminid", adminid);
		map.put("password", password);
		SysUserInfo sysUserInfo = loginService.getLoginInfo(map);
		if (sysUserInfo == null) {
			return ResultUtil.generateResponseMsg("error", StrUtil.login_error);
		}

		ParamerUtil.setSysUserInSesson(request, sysUserInfo);

		return ResultUtil.generateResponseMsg("success");
	}

	@RequestMapping("/main")
	public ModelAndView getMain() {

		return new ModelAndView("/main");
	}

	@RequestMapping("/left")
	public ModelAndView getLeft(HttpServletRequest request) {
		SysUserInfo userInfo=ParamerUtil.getSysUserFromSesson(request);
		//int roleid=userInfo.getAdminpower();
		//获取权限列表
		String powerw=","+userInfo.getAdminpowerw()+",";
		return new ModelAndView("/left","powerw",powerw);
	}

	@RequestMapping("/index")
	public ModelAndView getIndex(HttpServletRequest request, IndexPage indexpage) {
		if(indexpage==null)
			indexpage=new IndexPage();
		SysUserInfo userInfo=ParamerUtil.getSysUserFromSesson(request);
		indexpage.setUser(userInfo);
		indexpage.setMessages(loginService.getTopMessage(indexpage));
		indexpage.setNotices(loginService.getTopNotice(indexpage));
		int adminpower=ParamerUtil.getAdminType(userInfo.getAdminpower());
	    indexpage.setAdminCitys(userInfo.getCitys());
		if(adminpower==0)
			indexpage.setAdminCitys(null);
		//获取统计信息
		Map map = new HashMap();
		map.put("admincity", userInfo.getAdmincityid());
		map.put("adminareas", userInfo.getAreas());
		map.put("adminpower", userInfo.getAdminpower());
		UserManagePage userManagePage=new UserManagePage();
		userManagePage.setAdminCitys(indexpage.getAdminCitys());
		
		int admintype = ParamerUtil.getAdminType(userInfo.getAdminpower());
		List<Area> adminAreas = null;
		// 区域经理
		if (admintype == 2) {
			adminAreas = userInfo.getAreas();

		}
		
		QueryTerms queryTerms=new QueryTerms();
		queryTerms.setAdminCitys(indexpage.getAdminCitys());
		queryTerms.setAdminareas(adminAreas);
		
		OrderPage orderPage=new OrderPage();
		orderPage.setAdminCitys(indexpage.getAdminCitys());
		orderPage.setAdminareas(adminAreas);
		
		indexpage.setUsercount(loginService.getUsercount(userManagePage));
		indexpage.setTowercount(loginService.getTowercount(queryTerms));
		orderPage.setSelectstates(ParamerUtil.getIngOrderStates());
		indexpage.setIngordercount(loginService.getIngOrdercount(orderPage));
		
		orderPage.setSelectstates(ParamerUtil.getSuccessOrderStates());
		indexpage.setSuccordercount(loginService.getSuccOrdercount(orderPage));
		
		return new ModelAndView("/index1","indexpage",indexpage);
	}
	
	@RequestMapping("/report")
	public ModelAndView getReport() {

		return new ModelAndView("/report");
	}
	
	@RequestMapping("/data")
	public ModelAndView getData() {

		return new ModelAndView("/data");
	}
	
	@RequestMapping("/toweranalysis")
	public ModelAndView getDataTower(HttpServletRequest request,DataTowerPage datatowerPage) {
		if(datatowerPage==null)
			datatowerPage=new DataTowerPage();
		
		DataTowerParameter parameter=datatowerPage.getParameter();
		SysUserInfo sysUserInfo=ParamerUtil.getSysUserFromSesson(request);
		int admintype = ParamerUtil.getAdminType(sysUserInfo.getAdminpower());
		List<Area> adminAreas = null;
		// 区域经理
		if (admintype == 2) {
			adminAreas = sysUserInfo.getAreas();
			datatowerPage.setAdminareas(adminAreas);
		}
		List<Area> towerareas=new ArrayList<Area>();
		List<City> towercitys=sysUserInfo.getCitys();
		if (parameter.getTowercity() != 0) {
			Map map = new HashMap();
			map.put("cityid", parameter.getTowercity());
			if (admintype == 2) {
				map.put("areaids", sysUserInfo.getAdminarea().split(","));
			}

			towerareas = areaService.getAreas(map);
		}else if(towercitys != null && towercitys.size() == 1){
			Map map = new HashMap();
			map.put("cityid", towercitys.get(0).getId());
			if (admintype == 2) {
				map.put("areaids", sysUserInfo.getAdminarea().split(","));
			}

			towerareas = areaService.getAreas(map);

		}
		
		datatowerPage.setAdminCitys(sysUserInfo.getCitys());
		List<DataTower> towers=towerService.getDataTowers(datatowerPage);
		datatowerPage.setDatatowers(towers);
		datatowerPage.setTowercitys(towercitys);
		datatowerPage.setTowerareas(towerareas);
		//设置页面
		PageUtil pageUtil=new PageUtil(datatowerPage.getPageNo(),datatowerPage.getPagerowtotal());
		datatowerPage.setPageinfo(pageUtil.getPage());
		return new ModelAndView("/toweranalysis","datatowerPage",datatowerPage);
	}
	
	@RequestMapping("/useranalysis")
	public ModelAndView getDataUser(HttpServletRequest request,DataUserPage datauserPage) {
		
		if(datauserPage==null)
			datauserPage=new DataUserPage();
		
		SysUserInfo sysUserInfo=ParamerUtil.getSysUserFromSesson(request);
		List<City> usercitys=sysUserInfo.getCitys();
		datauserPage.setAdminCitys(sysUserInfo.getCitys());
		int admintype=ParamerUtil.getAdminType(sysUserInfo.getAdminpower());
		if(admintype==0)
			datauserPage.setAdminCitys(null);

		
		List<DataUser> datausers=userService.getDataUsers(datauserPage);
		datauserPage.setDatausers(datausers);
		datauserPage.setUsercitys(usercitys);
		//设置页面
		PageUtil pageUtil=new PageUtil(datauserPage.getPageNo(),datauserPage.getPagerowtotal());
		datauserPage.setPageinfo(pageUtil.getPage());
		return new ModelAndView("/useranalysis","datauserPage",datauserPage);
	}
	

	@RequestMapping("/towerstateanalysis")
	public ModelAndView getDataTowerState(HttpServletRequest request,DataTowerPage datatowerPage) {
		if(datatowerPage==null)
			datatowerPage=new DataTowerPage();
		
		SysUserInfo sysUserInfo=ParamerUtil.getSysUserFromSesson(request);
		
		datatowerPage.setAdminCitys(sysUserInfo.getCitys());
		List<Map> states=towerService.getTowerStates(datatowerPage);
		//设置页面
		return new ModelAndView("/towerstateanalysis","states",states);
	}

	@RequestMapping("/dc_towerstates")
	@ResponseBody
	public void dcTowerStates(HttpServletRequest request,
			HttpServletResponse response,DataTowerPage datatowerPage)   throws Exception {
		if(datatowerPage==null)
			datatowerPage=new DataTowerPage();
		SysUserInfo sysUserInfo=ParamerUtil.getSysUserFromSesson(request);
		
		datatowerPage.setAdminCitys(sysUserInfo.getCitys());
		List<Map> list=towerService.getTowerStates(datatowerPage);
		ExcelDC ecDc = new ExcelDC(request, "towerstate.xls");
		
		
		HSSFWorkbook workbook = ecDc.getDataTowerStates(list);
		 ByteArrayOutputStream os = new ByteArrayOutputStream();

	        try {
	        	workbook.write(os);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		  byte[] content = os.toByteArray();
	        InputStream is = new ByteArrayInputStream(content);
	        String filename = new String(("选址进度("+TimeUtil.getNowDay()+").xls").getBytes(), "iso-8859-1");
	        // 设置response参数，可以打开下载页面
	        response.reset();
	        response.setContentType("application/vnd.ms-excel;charset=utf-8");
	        response.setHeader("Content-Disposition", "attachment;filename="+filename);

	        ServletOutputStream out = response.getOutputStream();

	        BufferedInputStream bis = null;
	        BufferedOutputStream bos = null;

	        try {

	            bis = new BufferedInputStream(is);
	            bos = new BufferedOutputStream(out);

	            byte[] buff = new byte[2048];
	            int bytesRead;

	            // Simple read/write loop.
	            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
	                bos.write(buff, 0, bytesRead);
	            }

	        } catch (final IOException e) {
	            throw e;
	        } finally {
	            if (bis != null)
	                bis.close();
	            if (bos != null)
	                bos.close();
	        }
	}

	
	
	
	
	
	@RequestMapping("/dc_datatowers")
	@ResponseBody
	public void dcTowers(HttpServletRequest request,
			HttpServletResponse response,DataTowerPage datatowerPage)   throws Exception {
		SysUserInfo sysUserInfo=ParamerUtil.getSysUserFromSesson(request);
		int admintype = ParamerUtil.getAdminType(sysUserInfo.getAdminpower());
		List<Area> adminAreas = null;
		// 区域经理
		if (admintype == 2) {
			adminAreas = sysUserInfo.getAreas();
			datatowerPage.setAdminareas(adminAreas);
		}
		List<Area> towerareas=new ArrayList<Area>();
		List<City> towercitys=sysUserInfo.getCitys();
		if (datatowerPage.getParameter().getTowercity() != 0) {
			Map map = new HashMap();
			map.put("cityid", datatowerPage.getParameter().getTowercity());
			if (admintype == 2) {
				map.put("areaids", sysUserInfo.getAdminarea().split(","));
			}

			towerareas = areaService.getAreas(map);
		}else if(towercitys != null && towercitys.size() == 1){
			Map map = new HashMap();
			map.put("cityid", towercitys.get(0).getId());
			if (admintype == 2) {
				map.put("areaids", sysUserInfo.getAdminarea().split(","));
			}

			towerareas = areaService.getAreas(map);

		}
		
		datatowerPage.setAdminCitys(sysUserInfo.getCitys());
		ExcelDC ecDc = new ExcelDC(request, "data_tower.xls");
		try {
			if (datatowerPage.getParameter().getTowername() != null) {
				datatowerPage.getParameter().setTowername(new String(datatowerPage.getParameter().getTowername().getBytes("ISO8859-1"), "utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
		}
		List<Map> list = towerService.getDcDataTowers(datatowerPage);
		HSSFWorkbook workbook = ecDc.getDataTowers(list);
		 ByteArrayOutputStream os = new ByteArrayOutputStream();

	        try {
	        	workbook.write(os);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		  byte[] content = os.toByteArray();
	        InputStream is = new ByteArrayInputStream(content);
	        String filename = new String(("站址活跃("+TimeUtil.getNowDay()+").xls").getBytes(), "iso-8859-1");
	        // 设置response参数，可以打开下载页面
	        response.reset();
	        response.setContentType("application/vnd.ms-excel;charset=utf-8");
	        response.setHeader("Content-Disposition", "attachment;filename="+filename);

	        ServletOutputStream out = response.getOutputStream();

	        BufferedInputStream bis = null;
	        BufferedOutputStream bos = null;

	        try {

	            bis = new BufferedInputStream(is);
	            bos = new BufferedOutputStream(out);

	            byte[] buff = new byte[2048];
	            int bytesRead;

	            // Simple read/write loop.
	            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
	                bos.write(buff, 0, bytesRead);
	            }

	        } catch (final IOException e) {
	            throw e;
	        } finally {
	            if (bis != null)
	                bis.close();
	            if (bos != null)
	                bos.close();
	        }
	}

	
	@RequestMapping("/dc_datausers")
	@ResponseBody
	public void dcTowers(HttpServletRequest request,
			HttpServletResponse response,DataUserPage datauserPage)   throws Exception {
		SysUserInfo sysUserInfo=ParamerUtil.getSysUserFromSesson(request);
		List<City> usercitys=sysUserInfo.getCitys();
		datauserPage.setAdminCitys(sysUserInfo.getCitys());
		int admintype=ParamerUtil.getAdminType(sysUserInfo.getAdminpower());
		if(admintype==0)
			datauserPage.setAdminCitys(null);
		ExcelDC ecDc = new ExcelDC(request, "data_user.xls");
		try {
			if (datauserPage.getParameter().getUsername() != null) {
				datauserPage.getParameter().setUsername(new String(datauserPage.getParameter().getUsername().getBytes("ISO8859-1"), "utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
		}
		List<Map> list = userService.getDcDataUsers(datauserPage);
		HSSFWorkbook workbook = ecDc.getDataUsers(list);
		 ByteArrayOutputStream os = new ByteArrayOutputStream();

	        try {
	        	workbook.write(os);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		  byte[] content = os.toByteArray();
	        InputStream is = new ByteArrayInputStream(content);
	    	String filename = new String(("用户活跃("+TimeUtil.getNowDay()+").xls").getBytes(), "iso-8859-1");
	        // 设置response参数，可以打开下载页面
	        response.reset();
	        response.setContentType("application/vnd.ms-excel;charset=utf-8");
	        response.setHeader("Content-Disposition", "attachment;filename="+filename);

	        ServletOutputStream out = response.getOutputStream();

	        BufferedInputStream bis = null;
	        BufferedOutputStream bos = null;

	        try {

	            bis = new BufferedInputStream(is);
	            bos = new BufferedOutputStream(out);

	            byte[] buff = new byte[2048];
	            int bytesRead;

	            // Simple read/write loop.
	            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
	                bos.write(buff, 0, bytesRead);
	            }

	        } catch (final IOException e) {
	            throw e;
	        } finally {
	            if (bis != null)
	                bis.close();
	            if (bos != null)
	                bos.close();
	        }
	}
	
	
	
	
	@RequestMapping("/usersourceanalysis")
	public ModelAndView getUsersource(HttpServletRequest request,DataUserSourcePage userSourcePage) {

		if(userSourcePage==null)
			userSourcePage=new DataUserSourcePage();
		
		SysUserInfo sysUserInfo=ParamerUtil.getSysUserFromSesson(request);
		userSourcePage.setAdminCitys(sysUserInfo.getCitys());
		int admintype=ParamerUtil.getAdminType(sysUserInfo.getAdminpower());
		if(admintype==0)
			userSourcePage.setAdminCitys(null);
		
		List<UserSourceAnalysis> datausers=userService.getUserSources(userSourcePage);
		userSourcePage.setUserSources(datausers);
		//设置页面
		PageUtil pageUtil=new PageUtil(userSourcePage.getPageNo(),userSourcePage.getPagerowtotal());
		userSourcePage.setPageinfo(pageUtil.getPage());
		return new ModelAndView("/usersourceanalysis","userSourcePage",userSourcePage);
	}
	@RequestMapping("/dc_userscores")
	@ResponseBody
	public void dcUserscores(HttpServletRequest request,
			HttpServletResponse response,DataUserSourcePage userSourcePage)   throws Exception {

		if(userSourcePage==null)
			userSourcePage=new DataUserSourcePage();
		
		SysUserInfo sysUserInfo=ParamerUtil.getSysUserFromSesson(request);
		userSourcePage.setAdminCitys(sysUserInfo.getCitys());
		int admintype=ParamerUtil.getAdminType(sysUserInfo.getAdminpower());
		if(admintype==0)
			userSourcePage.setAdminCitys(null);
		ExcelDC ecDc = new ExcelDC(request, "data_usersource.xls");
		List<Map> list = userService.getDcDataUserScores(userSourcePage);
		HSSFWorkbook workbook = ecDc.getDataUserScources(list);
		 ByteArrayOutputStream os = new ByteArrayOutputStream();

	        try {
	        	workbook.write(os);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		  byte[] content = os.toByteArray();
	        InputStream is = new ByteArrayInputStream(content);
	    	String filename = new String(("用户来源统计("+TimeUtil.getNowDay()+").xls").getBytes(), "iso-8859-1");
	        // 设置response参数，可以打开下载页面
	        response.reset();
	        response.setContentType("application/vnd.ms-excel;charset=utf-8");
	        response.setHeader("Content-Disposition", "attachment;filename="+filename);

	        ServletOutputStream out = response.getOutputStream();

	        BufferedInputStream bis = null;
	        BufferedOutputStream bos = null;

	        try {

	            bis = new BufferedInputStream(is);
	            bos = new BufferedOutputStream(out);

	            byte[] buff = new byte[2048];
	            int bytesRead;

	            // Simple read/write loop.
	            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
	                bos.write(buff, 0, bytesRead);
	            }

	        } catch (final IOException e) {
	            throw e;
	        } finally {
	            if (bis != null)
	                bis.close();
	            if (bos != null)
	                bos.close();
	        }
	}
	
	

	
}
