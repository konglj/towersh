package com.tower.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebParam.Mode;
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
import com.tower.common.bean.Order;
import com.tower.common.bean.QueryTerms;
import com.tower.common.bean.SysUserInfo;
import com.tower.common.bean.Tower;
import com.tower.common.bean.TowerType;
import com.tower.common.bean.TowersInputData;
import com.tower.common.bean.TowersPage;
import com.tower.common.util.ExcelDC;
import com.tower.common.util.ExcelHelper;
import com.tower.common.util.FileUpload;
import com.tower.common.util.PageUtil;
import com.tower.common.util.ParamerUtil;
import com.tower.common.util.ResultUtil;
import com.tower.common.util.StrUtil;
import com.tower.common.util.TimeUtil;
import com.tower.service.AreaService;
import com.tower.service.HbTowerService;

@Controller
@RequestMapping("/hbtower")
public class HbTowerWeb {
	@Autowired
	HbTowerService hbTowerService;

	@Autowired
	private AreaService areaService;

	/*
	@RequestMapping("/tower_yes")
	public ModelAndView getTowerYes(HttpServletRequest request,
			QueryTerms queryTerms) {

		SysUserInfo adminInfo = ParamerUtil.getSysUserFromSesson(request);
		queryTerms.setAdminInfo(adminInfo);
		String todate = queryTerms.getTodate();
		if (queryTerms.getTodate() != null
				&& !queryTerms.getTodate().equals("")) {
			queryTerms.setTodate(TimeUtil.getDailyEndTime(queryTerms
					.getTodate()));
		}

		List<City> citys = null;
		List<Area> areas = null;
		if (adminInfo != null) {

			citys = adminInfo.getCitys();
			areas = new ArrayList<Area>();// adminInfo.getAreas();

			if (queryTerms.getCityid() == null || queryTerms.getCityid() == "") {
				if (citys == null || citys.size() == 0) {
					citys = new ArrayList<City>();
				} else if (citys.size() == 1) {
					queryTerms.setCityid(citys.get(0).getCityid());
					areas = adminInfo.getAreas();
				}
			} else {
				if (citys.size() == 1) {
					areas = adminInfo.getAreas();
				} else if (citys.size() > 1) {
					Map map = new HashMap();
					map.put("cityid", queryTerms.getCityid());
					areas = areaService.getAreas(map);
				}
			}

			int adminType = ParamerUtil.getAdminType(adminInfo.getAdminpower());
			if (adminType == 1) {
				queryTerms.setAdmincity(citys.get(0).getCityid());
			} else if (adminType == 2) {
				queryTerms.setAdminareas(adminInfo.getAreas());
			}

		}

		TowersPage towersPage = hbTowerService.getTowersPage(queryTerms);

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
			// 当将todate修改后，还原
			towersInputData.setTodate(todate);
		}


		towersPage.setCitys(citys);
		towersPage.setAreas(areas);

		// 设置页面
		PageUtil pageUtil = new PageUtil(queryTerms.getPageindex(),
				towersPage.getTowercount());
		towersPage.setPageinfo(pageUtil.getPage());

		System.out.println("towercount = " + towersPage.getTowercount());
		System.out.println("pageindex = " + towersPage.getPageIndex());


		return new ModelAndView("/hbtower/tower_yes", "towersPage", towersPage);
	}

	@RequestMapping("/tower_no")
	public ModelAndView getTowerNo(HttpServletRequest request,
			QueryTerms queryTerms) {
		SysUserInfo adminInfo = ParamerUtil.getSysUserFromSesson(request);
		queryTerms.setAdminInfo(adminInfo);
		String todate = queryTerms.getTodate();
		if (queryTerms.getTodate() != null
				&& !queryTerms.getTodate().equals("")) {
			queryTerms.setTodate(TimeUtil.getDailyEndTime(queryTerms
					.getTodate()));
		}

		List<City> citys = null;
		List<Area> areas = null;
		if (adminInfo != null) {
			citys = adminInfo.getCitys();
			areas = new ArrayList<Area>();// adminInfo.getAreas();

			if (queryTerms.getCityid() == null || queryTerms.getCityid() == "") {
				if (citys == null || citys.size() == 0) {
					citys = new ArrayList<City>();
				} else if (citys.size() == 1) {
					queryTerms.setCityid(citys.get(0).getCityid());
					areas = adminInfo.getAreas();
				}
			} else {
				if (citys.size() == 1) {
					areas = adminInfo.getAreas();
				} else if (citys.size() > 1) {
					Map map = new HashMap();
					map.put("cityid", queryTerms.getCityid());
					areas = areaService.getAreas(map);
				}
			}

			int adminType = ParamerUtil.getAdminType(adminInfo.getAdminpower());
			if (adminType == 1) {
				queryTerms.setAdmincity(citys.get(0).getCityid());
			} else if (adminType == 2) {
				queryTerms.setAdminareas(adminInfo.getAreas());
			}

			// System.out.println("adminArea:"+adminArea);
			// System.out.println("adminName:"+adminName);
			// System.out.println("adminPower:"+adminPower);
		}

		TowersPage noTowersPage = hbTowerService.getNoTowersPage(queryTerms);

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
			towersInputData.setTodate(todate);
		}

		// towersPage.setTowersInputData(towersInputData);

		noTowersPage.setCitys(citys);
		noTowersPage.setAreas(areas);

		// 设置页面
		PageUtil pageUtil = new PageUtil(queryTerms.getPageindex(),
				noTowersPage.getTowercount());
		noTowersPage.setPageinfo(pageUtil.getPage());

		System.out.println("towercount = " + noTowersPage.getTowercount());
		System.out.println("pageindex = " + noTowersPage.getPageIndex());


		return new ModelAndView("/hbtower/tower_no", "noTowersPage", noTowersPage);
	}
	*/

	@RequestMapping("/tower_add")
	public ModelAndView getTowerAdd(HttpServletRequest request) {
		SysUserInfo adminInfo = ParamerUtil.getSysUserFromSesson(request);
		List<City> citys = adminInfo.getCitys();
		Map map = new HashMap();
		String city = citys.get(0).getCityid();

		List<Area> areas = new ArrayList<Area>();

		int adminType = ParamerUtil.getAdminType(adminInfo.getAdminpower());
		if (adminType == 0 || adminType == 1) {
			map.put("cityid", city);
			areas = areaService.getAreas(map);
		} else if (adminType == 2) {
			areas = adminInfo.getAreas();
		}

		TowersPage addPage = new TowersPage();
		addPage.setCitys(citys);
		addPage.setAreas(areas);
		List<TowerType> types = hbTowerService.getTowertype();
		addPage.setTowertypes(types);
		;

		return new ModelAndView("/hbtower/tower_add", "addPage", addPage);
	}

	@RequestMapping("/tower_edit")
	public ModelAndView getTowerEdit(HttpServletRequest request, int towerid) {
		SysUserInfo adminInfo = ParamerUtil.getSysUserFromSesson(request);
		List<City> citys = adminInfo.getCitys();
		Map map = new HashMap();
		int city = citys.get(0).getId();
		Tower tower = null;
		if (towerid != 0) {
			tower = hbTowerService.getTowerInfo(towerid);
			city = tower.getCityid();
		}
		map.put("cityid", city);
		List<Area> areas = areaService.getAreas(map);
		TowersPage addPage = new TowersPage();
		addPage.setCitys(citys);
		addPage.setAreas(areas);
		addPage.setTower(tower);
		List<TowerType> types = hbTowerService.getTowertype();
		addPage.setTowertypes(types);
		return new ModelAndView("/hbtower/tower_edit", "addPage", addPage);
	}

	@RequestMapping("/update_tower")
	@ResponseBody
	public Object updateTower(HttpServletRequest request, AddTowerInfo tower) {

		int count = hbTowerService.updateTower(tower);
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
		// Map map = new HashMap();
		// map.put("id", tower.getTowerarea());
		try {
			// Area area = hbTowerService.getAreaById(map);
			String towerid = hbTowerService.getAreaOrder(tower.getId(),
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
		tower.setTowervisible(0);

		// 是否红包站址
		tower.setTowerhb(1);

		// 添加人
		tower.setToweradduser(adminInfo.getId());

		// 订单编号
		tower.setTowerorderid("");

		// 站址图片
		tower.setTowerimg("");

		int insertResult = 0;
		try {
			insertResult = hbTowerService.addTower(tower);
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

	

	@RequestMapping("/update_visible")
	@ResponseBody
	public Object upateVisible(String selecttoweids, int visible) {
		// 判读页面类型是否为下架
		// 下架处理了
		if (selecttoweids.endsWith(","))
			selecttoweids = selecttoweids.substring(0,
					selecttoweids.length() - 1);
		String[] ids = selecttoweids.split(",");
		Map map = new HashMap();
		map.put("visible", visible);
		map.put("ids", ids);
		int count = hbTowerService.updateTowerVisible(map);
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
		int count = hbTowerService.delTower(map);
		if (count > 0)
			return ResultUtil.generateResponseMsg("success");
		return ResultUtil.generateResponseMsg("error");

	}

	@RequestMapping("/get_tower_info")
	public ModelAndView getTowerInfo(int towerid) {
		Tower tower = hbTowerService.getTowerInfo(towerid);

		return new ModelAndView("/hbtower/tower_info", "tower", tower);

	}

	@RequestMapping("/get_areaorder")
	public String getAreaorder(int city, int area) {
		String orderno = hbTowerService.getAreaOrder(city, area);
		return orderno;
	}

	@RequestMapping("/upload_towers")
	@ResponseBody
	public Object towerUpload(HttpServletRequest request) {
		String filepath = "\\tmp\\excel\\";
		String excel = FileUpload.uploadFile_excel(request, filepath,
				"excelfile");
		if (excel == null)
			return ResultUtil.generateResponseMsg("error","上传失败");
		
		int count = 0;
		List<AddTowerInfo> towers = null;
		SysUserInfo adminInfo = ParamerUtil.getSysUserFromSesson(request);
		try {
			towers = ExcelHelper.readExcel(excel, adminInfo.getId());

		} catch (Exception e) {
			towers = null;
		}
		if (towers == null||towers.size()==0)
			return ResultUtil.generateResponseMsg("error", "读取站址失败");
		SysUserInfo sysUserInfo = ParamerUtil.getSysUserFromSesson(request);
		int admintype = ParamerUtil.getAdminType(sysUserInfo.getAdminpower());

		String cityname = "";
		List<Integer> adminAreas = new ArrayList<Integer>();
		if (admintype == 1) {
			// 如果为城市管理员。则获取城市名称
			cityname = areaService.getCityname(sysUserInfo.getAdmincityid());

		} else if (admintype == 2) {
			adminAreas = ParamerUtil.getAreaids(sysUserInfo.getAreas());
		}

		//
		// 根据站址类型获取站址类型ID
		Map<String, Integer> mapTypes = hbTowerService.getTowertypeMap();
		// 根据站址级别获取站址级别ID
		Map<String, Integer> mapLeves = ParamerUtil.getTowerLevelMap();
		for (AddTowerInfo addTowerInfo : towers) {
			// 根据城市名称和区域名称获取区域ID
			Map map = new HashMap();
			map.put("areaname", addTowerInfo.getAreaname());
			map.put("cityname", addTowerInfo.getCityname());
			Area area = areaService.getAreaByCityNameAndAreaName(map);
			if (area == null || area.getId() == 0)
				return ResultUtil.generateResponseMsg("error", "站址城市或区域错误");
			addTowerInfo.setTowerarea(area.getId());
			// 根据站址类型获取站址类型ID
			Integer towertype = mapTypes.get(addTowerInfo.getTowertype());
			//如果匹配不到站型 
			if (towertype == null || towertype == 0){
				//获取默认站型
				  towertype = mapTypes.get("其它");
				if (towertype == null || towertype == 0){
				   return ResultUtil.generateResponseMsg("error", "站址类型错误");
				}
			}
				
			addTowerInfo.setTowertype(String.valueOf(towertype));
			// 根据站址级别获取站址级别ID
			Integer towerlevel = mapLeves.get(addTowerInfo.getTowerlevelname());
			if (towerlevel == null || towerlevel == 0)
				return ResultUtil.generateResponseMsg("error", "站址级别错误");
			addTowerInfo.setTowerlevel(towerlevel);
			// 获取towerid
			String towerid = hbTowerService.getAreaOrder(area.getCityid(),
					area.getId());
			addTowerInfo.setTowerid(towerid);

			if (admintype == 1) {
				// 如果为城市管理员。则获取城市名称
				if(!addTowerInfo.getCityname().equals(cityname)){
					return ResultUtil.generateResponseMsg("error", "包含不是本管理城市下的站址");
				}
			} else if (admintype == 2) {
				if(!adminAreas.contains(area.getId())){
					return ResultUtil.generateResponseMsg("error", "包含不是本管理区域下的站址");
				}
			}
			addTowerInfo.setTowerhb(1);
			System.out.println(addTowerInfo.getTowername());
		}
		count = 0;
	
		try {
			
			count = hbTowerService.insertTowers(towers);

		} catch (Exception e) {
			return ResultUtil.generateResponseMsg("error", "添加失败");
		}

		return ResultUtil.generateResponseMsg("success");
	}

	@RequestMapping("/dc_towers")
	@ResponseBody
	public void dcTowers(HttpServletRequest request,
			HttpServletResponse response, QueryTerms queryTerms)
			throws Exception {
		ExcelDC ecDc = new ExcelDC(request, "tower_info.xls");
		List<Map> list = hbTowerService.dcTowers(queryTerms);
		HSSFWorkbook workbook = ecDc.getTowers(list);
		ByteArrayOutputStream os = new ByteArrayOutputStream();

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
		response.setHeader("Content-Disposition",
				"attachment;filename=tower_info.xls");

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
	public ModelAndView getTowerOrderHistory(int towerid){
		List<Order> list=hbTowerService.getTowerOrderHistory(towerid);
		if(list==null)
			list=new ArrayList<Order>();
		Map map=new HashMap();
		map.put("orders",list);
		map.put("towerid",towerid);
		return new ModelAndView("/hbtower/tower_order_info","info",map);
		
	}
	
	@RequestMapping("/dc_tower_order_info")
	public void dcTowerOrderHistory(HttpServletRequest request,HttpServletResponse response,int towerid)throws Exception{
		ExcelDC ecDc = new ExcelDC(request, "tower_order_history.xls");
		List<Map> list = hbTowerService.getDcTowerOrderHistory(towerid);
		HSSFWorkbook workbook = ecDc.getTowerOrderHistory(list);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		String  filename=new String(("抢单历史.xls").getBytes(), "iso-8859-1");
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
		response.setHeader("Content-Disposition", "attachment;filename="+filename+"");


		

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
}
