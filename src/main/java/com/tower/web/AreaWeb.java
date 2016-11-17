package com.tower.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tower.common.bean.Area;
import com.tower.common.bean.SysUserInfo;
import com.tower.common.util.ParamerUtil;
import com.tower.common.util.ResultUtil;
import com.tower.service.AreaService;

@Controller
@RequestMapping("/area")
public class AreaWeb {
	
	@Autowired
	private AreaService areaService;
	
	@RequestMapping("/get_area")
	@ResponseBody
	public Object getAreaByCity(HttpServletRequest request,String cityid){
		SysUserInfo sysUserInfo = ParamerUtil.getSysUserFromSesson(request);
		int admintype=ParamerUtil.getAdminType(sysUserInfo.getAdminpower());
		
		List<Area> list=new ArrayList<Area>();
		Map  map=new HashMap();
		map.put("cityid", cityid);
		if(admintype==2){
			map.put("areaids", sysUserInfo.getAdminarea().split(","));
		}
	
		list=areaService.getAreas(map);
		if(list==null)
			return ResultUtil.generateResponseMsg("error");
		return ResultUtil.generateResponseMsg("success", list);
	}
	

}
