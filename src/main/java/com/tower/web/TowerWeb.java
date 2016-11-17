package com.tower.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tower.common.bean.AddTowerInfo;
import com.tower.common.bean.Area;
import com.tower.common.bean.City;
import com.tower.common.bean.MapPoint;
import com.tower.common.bean.Order;
import com.tower.common.bean.QueryTerms;
import com.tower.common.bean.SysUserInfo;
import com.tower.common.bean.Tower;
import com.tower.common.bean.TowerStore;
import com.tower.common.bean.TowerType;
import com.tower.common.bean.TowersInputData;
import com.tower.common.bean.TowersPage;
import com.tower.common.util.ExcelDC;
import com.tower.common.util.ExcelHelper;
import com.tower.common.util.FileUpload;
import com.tower.common.util.GPSUtil;
import com.tower.common.util.PageUtil;
import com.tower.common.util.ParamerUtil;
import com.tower.common.util.ResultUtil;
import com.tower.common.util.StrUtil;
import com.tower.common.util.TimeUtil;
import com.tower.service.AreaService;
import com.tower.service.SettingService;
import com.tower.service.TowerService;

@Controller
@RequestMapping("/tower")
public class TowerWeb {

	@Autowired
	TowerService towerService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private SettingService settingService;

	@RequestMapping("/tower_yes")
	public ModelAndView getTowerYes(HttpServletRequest request,
			QueryTerms queryTerms) {

		SysUserInfo adminInfo = ParamerUtil.getSysUserFromSesson(request);
		boolean isback = ParamerUtil.getIsBackPage(request);
		if (isback) {
			queryTerms = (QueryTerms) request.getSession().getAttribute(
					"tower_yes");
		} else {
			request.getSession().setAttribute("tower_yes", queryTerms);
		}
		int admintype = ParamerUtil.getAdminType(adminInfo.getAdminpower());
		List<Area> adminAreas = null;
		// 区域经理
		if (admintype == 2) {
			adminAreas = adminInfo.getAreas();
			queryTerms.setAdminareas(adminAreas);

		}
		String todate = queryTerms.getTodate();
		if (queryTerms.getTodate() != null
				&& !queryTerms.getTodate().equals("")) {
			queryTerms.setTodate(TimeUtil.getDailyEndTime(queryTerms
					.getTodate()));
		}

		List<City> citys = adminInfo.getCitys();
		if (adminInfo != null) {
			queryTerms.setAdminCitys(adminInfo.getCitys());
		}

		TowersPage towersPage = towerService.getTowersPage(queryTerms);

		TowersInputData towersInputData = towersPage.getTowersInputData();
		if (queryTerms != null) {

			towersInputData.setTowername(queryTerms.getTowername());
			towersInputData.setCityid(queryTerms.getCityid());
			towersInputData.setAreaid(queryTerms.getAreaid());
			towersInputData.setTowertype(queryTerms.getTowertype());
			towersInputData.setTowerstate(queryTerms.getTowerstate());
			towersInputData.setFromdate(queryTerms.getFromdate());
			towersInputData.setTodate(queryTerms.getTodate());
			towersInputData.setPageindex(queryTerms.getPageindex());
			towersInputData.setPageSize(queryTerms.getPageSize());
			// 当将todate修改后，还原
			towersInputData.setTodate(todate);
			towersInputData.setTowercreatetype(queryTerms.getTowercreatetype());
		}

		towersPage.setCitys(citys);
		if (queryTerms.getCityid() != 0) {
			Map map = new HashMap();
			map.put("cityid", queryTerms.getCityid());
			if (admintype == 2) {
				map.put("areaids", adminInfo.getAdminarea().split(","));
			}

			List<Area> area = areaService.getAreas(map);
			towersPage.setAreas(area);
		} else if (citys != null && citys.size() == 1) {
			Map map = new HashMap();
			map.put("cityid", citys.get(0).getId());
			if (admintype == 2) {
				map.put("areaids", adminInfo.getAdminarea().split(","));
			}

			List<Area> area = areaService.getAreas(map);
			towersPage.setAreas(area);
		}

		// 设置页面
		PageUtil pageUtil = new PageUtil(queryTerms.getPageindex(),queryTerms.getPageSize(),
				towersPage.getTowercount());
		towersPage.setPageinfo(pageUtil.getPage());
		towersPage.setAdminPower(adminInfo.getAdminpower());
		towersPage.setTowercreattypes(towerService.getTowerCreateType());

		System.out.println("towercount = " + towersPage.getTowercount());
		System.out.println("pageindex = " + towersPage.getPageIndex());

		return new ModelAndView("/tower/tower_yes", "towersPage", towersPage);
	}

	@RequestMapping("/tower_no")
	public ModelAndView getTowerNo(HttpServletRequest request,
			QueryTerms queryTerms) {
		boolean isback = ParamerUtil.getIsBackPage(request);
		if (isback) {
			queryTerms = (QueryTerms) request.getSession().getAttribute(
					"tower_no");
		} else {
			request.getSession().setAttribute("tower_no", queryTerms);
		}
		SysUserInfo adminInfo = ParamerUtil.getSysUserFromSesson(request);
		int admintype = ParamerUtil.getAdminType(adminInfo.getAdminpower());
		List<Area> adminAreas = null;
		// 区域经理
		if (admintype == 2) {
			adminAreas = adminInfo.getAreas();
			queryTerms.setAdminareas(adminAreas);

		}
		queryTerms.setAdminInfo(adminInfo);
		String todate = queryTerms.getTodate();
		if (queryTerms.getTodate() != null
				&& !queryTerms.getTodate().equals("")) {
			queryTerms.setTodate(TimeUtil.getDailyEndTime(queryTerms
					.getTodate()));
		}

		List<City> citys = adminInfo.getCitys();
		if (adminInfo != null) {
			queryTerms.setAdminCitys(adminInfo.getCitys());
		}

		queryTerms.setTowervisible(0);
		TowersPage noTowersPage = towerService.getNoTowersPage(queryTerms);

		TowersInputData towersInputData = noTowersPage.getTowersInputData();
		if (queryTerms != null) {
			towersInputData.setTowername(queryTerms.getTowername());
			towersInputData.setCityid(queryTerms.getCityid());
			towersInputData.setAreaid(queryTerms.getAreaid());
			towersInputData.setTowertype(queryTerms.getTowertype());
			// towersInputData.setTowerstate(queryTerms.getTowerstate());
			towersInputData.setFromdate(queryTerms.getFromdate());
			towersInputData.setTodate(queryTerms.getTodate());
			towersInputData.setPageindex(queryTerms.getPageindex());
			towersInputData.setPageSize(queryTerms.getPageSize());
			towersInputData.setTodate(todate);
			towersInputData.setTowersource(queryTerms.getTowersource());
			towersInputData.setTowercreatetype(queryTerms.getTowercreatetype());
		}

		noTowersPage.setCitys(citys);

		if (queryTerms.getCityid() != 0) {
			Map map = new HashMap();
			map.put("cityid", queryTerms.getCityid());
			if (admintype == 2) {
				map.put("areaids", adminInfo.getAdminarea().split(","));
			}

			List<Area> area = areaService.getAreas(map);
			noTowersPage.setAreas(area);
		} else if (citys != null && citys.size() == 1) {
			Map map = new HashMap();
			map.put("cityid", citys.get(0).getId());
			if (admintype == 2) {
				map.put("areaids", adminInfo.getAdminarea().split(","));
			}

			List<Area> area = areaService.getAreas(map);
			noTowersPage.setAreas(area);
		}

		// 设置页面
		PageUtil pageUtil = new PageUtil(queryTerms.getPageindex(),queryTerms.getPageSize(),
				noTowersPage.getTowercount());
		noTowersPage.setPageinfo(pageUtil.getPage());
		noTowersPage.setAdminPower(adminInfo.getAdminpower());
		System.out.println("towercount = " + noTowersPage.getTowercount());
		System.out.println("pageindex = " + noTowersPage.getPageIndex());
		noTowersPage.setTowercreattypes(towerService.getTowerCreateType());

		return new ModelAndView("/tower/tower_no", "noTowersPage", noTowersPage);
	}

	@RequestMapping("/tower_dsh")
	public ModelAndView getTowerDhs(HttpServletRequest request,
			QueryTerms queryTerms) {
		boolean isback = ParamerUtil.getIsBackPage(request);
		if (isback) {
			queryTerms = (QueryTerms) request.getSession().getAttribute(
					"tower_dsh");
		} else {
			request.getSession().setAttribute("tower_dsh", queryTerms);
		}
		SysUserInfo adminInfo = ParamerUtil.getSysUserFromSesson(request);
		int admintype = ParamerUtil.getAdminType(adminInfo.getAdminpower());
		List<Area> adminAreas = null;
		// 区域经理
		if (admintype == 2) {
			adminAreas = adminInfo.getAreas();
			queryTerms.setAdminareas(adminAreas);

		}
		queryTerms.setAdminInfo(adminInfo);
		String todate = queryTerms.getTodate();
		if (queryTerms.getTodate() != null
				&& !queryTerms.getTodate().equals("")) {
			queryTerms.setTodate(TimeUtil.getDailyEndTime(queryTerms
					.getTodate()));
		}

		List<City> citys = adminInfo.getCitys();
		if (adminInfo != null) {
			queryTerms.setAdminCitys(adminInfo.getCitys());
		}

		queryTerms.setTowervisible(2);
		TowersPage noTowersPage = towerService.getNoTowersPage(queryTerms);

		TowersInputData towersInputData = noTowersPage.getTowersInputData();
		if (queryTerms != null) {
			towersInputData.setTowername(queryTerms.getTowername());
			towersInputData.setCityid(queryTerms.getCityid());
			towersInputData.setAreaid(queryTerms.getAreaid());
			towersInputData.setTowertype(queryTerms.getTowertype());
			// towersInputData.setTowerstate(queryTerms.getTowerstate());
			towersInputData.setFromdate(queryTerms.getFromdate());
			towersInputData.setTodate(queryTerms.getTodate());
			towersInputData.setPageindex(queryTerms.getPageindex());
			towersInputData.setPageSize(queryTerms.getPageSize());
			towersInputData.setTodate(todate);
			towersInputData.setTowercreatetype(queryTerms.getTowercreatetype());
		}

		noTowersPage.setCitys(citys);

		if (queryTerms.getCityid() != 0) {
			Map map = new HashMap();
			map.put("cityid", queryTerms.getCityid());
			if (admintype == 2) {
				map.put("areaids", adminInfo.getAdminarea().split(","));
			}

			List<Area> area = areaService.getAreas(map);
			noTowersPage.setAreas(area);
		} else if (citys != null && citys.size() == 1) {
			Map map = new HashMap();
			map.put("cityid", citys.get(0).getId());
			if (admintype == 2) {
				map.put("areaids", adminInfo.getAdminarea().split(","));
			}

			List<Area> area = areaService.getAreas(map);
			noTowersPage.setAreas(area);
		}

		// 设置页面
		PageUtil pageUtil = new PageUtil(queryTerms.getPageindex(),queryTerms.getPageSize(),
				noTowersPage.getTowercount());
		noTowersPage.setPageinfo(pageUtil.getPage());
		noTowersPage.setAdminPower(adminInfo.getAdminpower());
		noTowersPage.setTowercreattypes(towerService.getTowerCreateType());
		return new ModelAndView("/tower/tower_dsh", "noTowersPage",
				noTowersPage);
	}

	@RequestMapping("/tower_add")
	public ModelAndView getTowerAdd(HttpServletRequest request) {
		SysUserInfo adminInfo = ParamerUtil.getSysUserFromSesson(request);
		List<City> citys = adminInfo.getCitys();

		int city = citys.get(0).getId();

		List<Area> areas = new ArrayList<Area>();

		int adminType = ParamerUtil.getAdminType(adminInfo.getAdminpower());

		Map map = new HashMap();
		map.put("cityid", city);
		if (adminType == 2) {
			map.put("areaids", adminInfo.getAdminarea().split(","));
		}
		areas = areaService.getAreas(map);

		TowersPage addPage = new TowersPage();
		addPage.setCitys(citys);
		addPage.setAreas(areas);
		List<TowerType> types = towerService.getTowertype();
		addPage.setTowertypes(types);
		List<TowerStore> towerstores=towerService.getTowerStores();
		addPage.setTowerstores(towerstores);
		addPage.setTowercreattypes(towerService.getTowerCreateType());
		return new ModelAndView("/tower/tower_add", "addPage", addPage);
	}

	@RequestMapping("/tower_edit")
	public ModelAndView getTowerEdit(HttpServletRequest request, int towerid,
			int page) {
		SysUserInfo adminInfo = ParamerUtil.getSysUserFromSesson(request);
		List<City> citys = adminInfo.getCitys();

		List<Area> areas = new ArrayList<Area>();

		int adminType = ParamerUtil.getAdminType(adminInfo.getAdminpower());

		TowersPage towersPage = new TowersPage();
		towersPage.setCitys(citys);

		List<TowerType> types = towerService.getTowertype();
		towersPage.setTowertypes(types);
		Tower tower = towerService.getTowerInfo(towerid);
		towersPage.setTower(tower);

		Map map = new HashMap();
		map.put("cityid", tower.getCityid());
		if (adminType == 2) {
			map.put("areaids", adminInfo.getAdminarea().split(","));
		}
		areas = areaService.getAreas(map);

		towersPage.setAreas(areas);
		towersPage.setPage(page);
		towersPage.setTowercreattypes(towerService.getTowerCreateType());
		List<TowerStore> towerstores=towerService.getTowerStores();
		towersPage.setTowerstores(towerstores);

		;
		return new ModelAndView("/tower/tower_edit", "addPage", towersPage);
	}

	@RequestMapping("/update_tower")
	@ResponseBody
	public Object updateTower(HttpServletRequest request, AddTowerInfo tower) {

		int count = towerService.updateTower(tower);
		if (count == 0)
			return ResultUtil.generateResponseMsg("error");

		return ResultUtil.generateResponseMsg("success");

	}

	@RequestMapping("/applyaddtower")
	@ResponseBody
	public Object applyAddTower(HttpServletRequest request, AddTowerInfo tower) {

		SysUserInfo adminInfo = ParamerUtil.getSysUserFromSesson(request);

		// 租用面积

		// 系统编号
		try {
			String towerid = towerService.getAreaOrder(tower.getCityid(),
					tower.getTowerarea());
			tower.setTowerid(towerid);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("获取区域对象sql错误发生");
			return ResultUtil.generateResponseMsg("error",
					StrUtil.add_tower_error);
		}

		// 站址状态
		tower.setTowerstate(0);

		// 区域经理
		String areamanager = tower.getManagerphone();
		tower.setTowermanager(areamanager);
		tower.setTowermanagername(tower.getManagername());

		// 是否上架
		tower.setTowervisible(2);

		// 是否红包站址
		tower.setTowerhb(0);

		// 添加人
		tower.setToweradduser(adminInfo.getId());

		// 订单编号
		tower.setTowerorderid("");

		// 站址图片
		tower.setTowerimg("");

		int insertResult = 0;
		try {
			insertResult = towerService.addTower(tower);
		} catch (Exception e) {
			System.out.println("插入sql错误发生");
			e.printStackTrace();
		}

		if (insertResult != 1) {
			return ResultUtil.generateResponseMsg("error",
					StrUtil.add_tower_error);

		}
		return ResultUtil.generateResponseMsg("success");
	}

	@RequestMapping("/h1")
	public ModelAndView getTowerSM() {

		return new ModelAndView("/tower/h1");
	}

	@RequestMapping("/update_visible")
	@ResponseBody
	public Object upateVisible(String selecttoweids, int visible,int source) {
		// 判读页面类型是否为下架
		// 下架处理了
		if (selecttoweids.endsWith(","))
			selecttoweids = selecttoweids.substring(0,
					selecttoweids.length() - 1);
		String[] ids = selecttoweids.split(",");
		Map map = new HashMap();
		map.put("visible", visible);
		map.put("ids", ids);
		map.put("source",source);
		int count = towerService.updateTowerVisible(map);
		if (count > 0)
			return ResultUtil.generateResponseMsg("success");
		return ResultUtil.generateResponseMsg("error");

	}

	@RequestMapping("/tower_del")
	@ResponseBody
	public Object delTower(String selecttoweids) {
		// 判读页面类型是否为下架
		// 下架处理了
		if (selecttoweids.endsWith(","))
			selecttoweids = selecttoweids.substring(0,
					selecttoweids.length() - 1);
		String[] ids = selecttoweids.split(",");
		Map map = new HashMap();
		map.put("ids", ids);
		int count = towerService.delTower(map);
		if (count > 0)
			return ResultUtil.generateResponseMsg("success");
		return ResultUtil.generateResponseMsg("error");

	}
	
	/**
	 * 
	 * @param request
	 * @param towerid
	 * @param pagetype  0 tower  1 order
	 * @return
	 */

	@RequestMapping("/get_tower_info")
	public ModelAndView getTowerInfo(HttpServletRequest request, int towerid,int pagetype) {
		Tower tower = towerService.getTowerInfo(towerid);
		SysUserInfo userInfo = ParamerUtil.getSysUserFromSesson(request);
		tower.setAdminpower(userInfo.getAdminpower());

		try {
			if (tower.getTowerj() != null && !tower.getTowerj().equals("")
					&& tower.getTowerw() != null
					&& !tower.getTowerw().equals("")) {
				MapPoint point=new MapPoint();
				point = GPSUtil
						.gps_to_baidu( tower.getTowerj(),tower.getTowerw());
				if (point != null) {
					tower.setTowerjbaidu(point.getLng());
					tower.setTowerwbaidu(point.getLat());
				}
				if(tower.getTowerfirstj()!=null&&!tower.getTowerfirstj().equals("")&&tower.getTowerfirstw()!=null&&!tower.getTowerfirstw().equals("")){
					point = GPSUtil
							.gps_to_baidu(tower.getTowerfirstj(),tower.getTowerfirstw());
					if (point != null) {
						tower.setTowerfirstjbaidu(point.getLng());
						tower.setTowerfirstwbaidu(point.getLat());
					}else{
						tower.setTowerfirstjbaidu("0");
						tower.setTowerfirstwbaidu("0");
					}
				}
				if(tower.getTowersecondj()!=null&&!tower.getTowersecondj().equals("")&&tower.getTowersecondw()!=null&&!tower.getTowersecondw().equals("")){
					point = GPSUtil
							.gps_to_baidu( tower.getTowersecondj(),tower.getTowersecondw());
					if (point != null) {
						tower.setTowersecondjbaidu(point.getLng());
						tower.setTowersecondwbaidu(point.getLat());
					}else{
						tower.setTowersecondjbaidu("0");
						tower.setTowersecondwbaidu("0");
					}
				}
				/*
				point = GPSUtil
						.GpsToBaidu(tower.getTowerw(), tower.getTowerj());
				if (point != null) {
					tower.setTowerjbaidu(String.valueOf(point.getLng()));
					tower.setTowerwbaidu(String.valueOf(point.getLat()));
				}
				*/
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		tower.setPagetype(pagetype);
		return new ModelAndView("/tower/tower_info", "tower", tower);

	}

	@RequestMapping("/get_areaorder")
	public String getAreaorder(int city, int area) {
		String orderno = towerService.getAreaOrder(city, area);
		return orderno;
	}

	@RequestMapping("/upload_towers")
	@ResponseBody
	public Object towerUpload(HttpServletRequest request) {
		String filepath = "\\tmp\\excel\\";
		String excel = FileUpload.uploadFile_excel(request, filepath,
				"excelfile");
		if (excel == null)
			return ResultUtil.generateResponseMsg("error", "上传失败");

		int count = 0;
		List<AddTowerInfo> towers = null;
		SysUserInfo adminInfo = ParamerUtil.getSysUserFromSesson(request);
		try {
			towers = ExcelHelper.readExcel(excel, adminInfo.getId());

		} catch (Exception e) {
			towers = null;
		}
		if (towers == null || towers.size() == 0)
			return ResultUtil.generateResponseMsg("error", "读取站址失败");
		SysUserInfo sysUserInfo = ParamerUtil.getSysUserFromSesson(request);
		int admintype = ParamerUtil.getAdminType(sysUserInfo.getAdminpower());

		String cityname = "";
		// 获取管理员管辖区域
		List<City> adminCitys = sysUserInfo.getCitys();
		List<Integer> adminAreas = new ArrayList<Integer>();

		//
		// 根据站址类型获取站址类型ID
		Map<String, Integer> mapTypes = towerService.getTowertypeMap();
		// 根据站址级别获取站址级别ID
		Map<String, Integer> mapLeves = ParamerUtil.getTowerLevelMap();
		
		Map<String, Integer> mapStores=towerService.getTowerStoreMap();
		Map<String ,Integer> mapCreateType=towerService.getTowerCreateTypeMap();

		List<City> citys = new ArrayList<City>();
		citys = adminInfo.getCitys();
		Map map = null;
		for (AddTowerInfo addTowerInfo : towers) {

			// 根据城市名称和区域名称获取区域ID
			map = new HashMap();
			map.put("areaname", addTowerInfo.getAreaname().trim());
			map.put("cityname", addTowerInfo.getCityname().trim());
			Area area = areaService.getAreaByCityNameAndAreaName(map);
			if (area == null || area.getId() == 0)
				return ResultUtil.generateResponseMsg("error", "站址城市或区域错误");
			// 判读区域范围
			// 如果为城市管理员。则获取城市名称

			// 区域
			if (admintype == 1) {
				if (!ParamerUtil.isArea(citys, area.getCityid()))
					return ResultUtil.generateResponseMsg("error",
							"站址包含不是管辖范围的站址！");
			} else if (admintype == 2) {
				if (!ParamerUtil.isJieDao(adminInfo.getAreas(), area.getId()))
					return ResultUtil.generateResponseMsg("error",
							"站址包含不是管辖范围的站址！");

			}
			// 街道

			addTowerInfo.setTowerarea(area.getId());
			// 根据站址类型获取站址类型ID
			Integer towertype = mapTypes.get(addTowerInfo.getTowertype());
			// 如果匹配不到站型
			if (towertype == null || towertype == 0) {
				// 获取默认站型
				towertype = mapTypes.get("其它");
				if (towertype == null || towertype == 0) {
					return ResultUtil.generateResponseMsg("error", "站址类型错误");
				}
			}
			if(addTowerInfo.getTowercreatetypename()!=null)
				addTowerInfo.setTowercreatetypename(addTowerInfo.getTowercreatetypename().replace(" ", ""));
			Integer createType=mapCreateType.get(addTowerInfo.getTowercreatetypename());
			if(createType==null|| createType==0){
					return ResultUtil.generateResponseMsg("error", "站址建设类型错误");
			}
			addTowerInfo.setTowercreatetype(createType);
			addTowerInfo.setTowertype(String.valueOf(towertype));
			// 根据站址级别获取站址级别ID
			Integer towerlevel = mapLeves.get(addTowerInfo.getTowerlevelname());
			if (towerlevel == null || towerlevel == 0)
				return ResultUtil.generateResponseMsg("error", "站址级别错误");
			addTowerInfo.setTowerlevel(towerlevel);
			// 获取towerid
			String towerid = towerService.getAreaOrder(area.getCityid(),
					area.getId());
			addTowerInfo.setTowerid(towerid);
			addTowerInfo.setTowervisible(2);
			//所属库
			Integer towerstore=mapStores.get(addTowerInfo.getTowerstorename());
			if(towerstore==null||towerstore==0)
				return ResultUtil.generateResponseMsg("error", "站址库类型错误");
			addTowerInfo.setTowerstore(towerstore);			
		}
		count = 0;

		try {

			count = towerService.insertTowers(towers);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		
			return ResultUtil.generateResponseMsg("error", "添加失败");
		}

		return ResultUtil.generateResponseMsg("success");
	}

	@RequestMapping("/dc_towers")
	@ResponseBody
	public void dcTowers(HttpServletRequest request,
			HttpServletResponse response, QueryTerms queryTerms)
			throws Exception {
		SysUserInfo adminInfo = ParamerUtil.getSysUserFromSesson(request);
		int admintype = ParamerUtil.getAdminType(adminInfo.getAdminpower());
		List<Area> adminAreas = null;
		// 区域经理
		if (admintype == 2) {
			adminAreas = adminInfo.getAreas();
			queryTerms.setAdminareas(adminAreas);

		}
		String todate = queryTerms.getTodate();
		if (queryTerms.getTodate() != null
				&& !queryTerms.getTodate().equals("")) {
			queryTerms.setTodate(TimeUtil.getDailyEndTime(queryTerms
					.getTodate()));
		}

		List<City> citys = adminInfo.getCitys();
		if (adminInfo != null) {
			queryTerms.setAdminCitys(adminInfo.getCitys());
		}
		ExcelDC ecDc = new ExcelDC(request, "tower_info.xls");
		try {
			if (queryTerms.getTowername() != null) {
				queryTerms.setTowername(new String(queryTerms.getTowername()
						.getBytes("ISO8859-1"), "utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
		}
		List<Map> list = towerService.dcTowers(queryTerms);
		HSSFWorkbook workbook = ecDc.getTowers(list);
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		try {
			workbook.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		String filename="站址详情("+TimeUtil.getNowDay()+").xls";
		 filename = new String((filename).getBytes(), "iso-8859-1");
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition",
				"attachment;filename="+filename);

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

	@RequestMapping("/get_orders")
	public ModelAndView getTowerOrderHistory(int towerid) {
		List<Order> list = towerService.getTowerOrderHistory(towerid);
		if (list == null)
			list = new ArrayList<Order>();
		Map map = new HashMap();
		map.put("orders", list);
		map.put("towerid", towerid);
		return new ModelAndView("/tower/tower_order_info", "info", map);

	}

	@RequestMapping("/dc_tower_order_info")
	public void dcTowerOrderHistory(HttpServletRequest request,
			HttpServletResponse response, int towerid) throws Exception {
		ExcelDC ecDc = new ExcelDC(request, "tower_order_history.xls");
		List<Map> list = towerService.getDcTowerOrderHistory(towerid);
		HSSFWorkbook workbook = ecDc.getTowerOrderHistory(list);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		String filename = new String(("抢单历史("+TimeUtil.getNowDay()+").xls").getBytes(), "iso-8859-1");
		try {
			workbook.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);

		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ filename + "");

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		try {
			ServletOutputStream out = response.getOutputStream();
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);

			byte[] buff = new byte[2048];
			int bytesRead;

			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}

		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}

	@RequestMapping("/tower_gs")
	@ResponseBody
	public Object updateTowerGS() {
		int count = 0;
		try {
			count = settingService.updateTowerGs();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (count > 0)
			return ResultUtil.generateResponseMsg("success");
		return ResultUtil.generateResponseMsg("error");

	}

}
