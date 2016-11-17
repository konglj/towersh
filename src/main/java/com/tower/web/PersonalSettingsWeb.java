package com.tower.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.functors.IfClosure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tower.common.Config;
import com.tower.common.bean.AddTowerInfo;
import com.tower.common.bean.Area;
import com.tower.common.bean.City;
import com.tower.common.bean.QueryTerms;
import com.tower.common.bean.SysUserInfo;
import com.tower.common.util.PageUtil;
import com.tower.common.util.PageUtil1;
import com.tower.common.util.ParamerUtil;
import com.tower.common.util.ResultUtil;
import com.tower.common.util.StrUtil;
import com.tower.service.InfoService;
import com.tower.service.LoginService;

@Controller
@RequestMapping("/personalSettings")
public class PersonalSettingsWeb {

	@Autowired
	private InfoService infoService;

	@RequestMapping("/info")
	public ModelAndView personalSettings(HttpServletRequest request) {

		SysUserInfo adminInfo = ParamerUtil.getSysUserFromSesson(request);
		int adminType=ParamerUtil.getAdminType(adminInfo.getAdminpower());
		String amdminArea = "";
		if(adminType==0){//超级管理员
			amdminArea = Config.getProvince();
		}else {//地市管理员
			List<City> citys= adminInfo.getCitys();
			for (City city : citys) {
				amdminArea +=city.getCityname()+",";
			}
			
		}
		if(amdminArea.endsWith(","))
			amdminArea=amdminArea.substring(0,amdminArea.length()-1);
		System.out.println("amdminArea="+amdminArea);
		
		Map sysuserInfo = new HashMap();
		sysuserInfo.put("adminId", adminInfo.getAdminid());
		sysuserInfo.put("amdminArea", amdminArea);
		sysuserInfo.put("amdminName", adminInfo.getAdminname());
		sysuserInfo.put("amdminPhone", adminInfo.getAdminphone());
		
		return new ModelAndView("/personalSettings/info", "sysuserInfo", sysuserInfo);
	}
	
	@RequestMapping("/updateinfo")
	@ResponseBody
	public Object updateinfo(HttpServletRequest request,String name,String phone) {
		
		SysUserInfo adminInfo = ParamerUtil.getSysUserFromSesson(request);
		
		Map map = new HashMap();
		if (adminInfo != null) {
			int id = adminInfo.getId();
			
			//String oldpsd = request.getParameter("oldpsd");
			//String newpsd = request.getParameter("newpsd");
			//String againpsd = request.getParameter("againpsd");
			
			//map.put("oldpsd", oldpsd);
			//map.put("newpsd", newpsd);
			//map.put("againpsd", againpsd);
			map.put("name", name);
			map.put("phone", phone);
			map.put("id", id);
		}
		int updateResult = 0;
		
		try {
			updateResult = infoService.updateInfo(map);
			adminInfo.setAdminname(name);
			adminInfo.setAdminphone(phone);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (updateResult != 1) {
			return ResultUtil.generateResponseMsg("error", StrUtil.update_info_error);
			
		}
		return ResultUtil.generateResponseMsg("success");
	}

	
	@RequestMapping("/updatePwd")
	@ResponseBody
	public Object updatePwd(HttpServletRequest request,String newpsd,String oldpsd) {
		
		SysUserInfo adminInfo = ParamerUtil.getSysUserFromSesson(request);
		
		Map map = new HashMap();
		if (adminInfo != null) {
			int id = adminInfo.getId();
			map.put("oldpsd", oldpsd);
			map.put("newpsd", newpsd);
			map.put("id", id);
		}
		int updateResult = 0;
		updateResult=infoService.checkPwd(map);
		if(updateResult==0){
			return ResultUtil.generateResponseMsg("error", StrUtil.update_admin_pwd_error);
		}
		
		try {
			updateResult = 0;
			updateResult = infoService.updatePwd(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (updateResult != 1) {
			return ResultUtil.generateResponseMsg("error", StrUtil.update_info_error);
			
		}
		return ResultUtil.generateResponseMsg("success");
	}
}
