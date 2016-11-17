package com.tower.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebParam.Mode;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.fit.cssbox.demo.ImageRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tower.common.Config;
import com.tower.common.bean.Area;
import com.tower.common.bean.City;
import com.tower.common.bean.Order;
import com.tower.common.bean.OrderInfo;
import com.tower.common.bean.OrderPage;
import com.tower.common.bean.OrderParameter;
import com.tower.common.bean.OrderState;
import com.tower.common.bean.QueryTerms;
import com.tower.common.bean.SysUserInfo;
import com.tower.common.bean.TowerShInfo;
import com.tower.common.util.ExcelDC;
import com.tower.common.util.FileUpload;
import com.tower.common.util.PageUtil;
import com.tower.common.util.ParamerUtil;
import com.tower.common.util.ResultUtil;
import com.tower.common.util.TemplateUtil;
import com.tower.common.util.TimeUtil;
import com.tower.common.wxmsg.WXCommon;
import com.tower.service.AreaService;
import com.tower.service.ManagerService;
import com.tower.service.OrderEvaluateService;
import com.tower.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderWeb {

	@Autowired
	private OrderService orderService;

	@Autowired
	private AreaService areaService;

	@Autowired
	private ManagerService managerService;

	@Autowired
	private OrderEvaluateService evaluateService;

	/**
	 * 获取订单列表
	 * 
	 * @param request
	 * @param orderPage
	 * @return
	 */
	@RequestMapping("/order")
	public ModelAndView getOrders(HttpServletRequest request,
			OrderPage orderPage) {

		boolean isback = ParamerUtil.getIsBackPage(request);
		if (isback) {
			orderPage = (OrderPage) request.getSession().getAttribute("order");
		} else {
			request.getSession().setAttribute("order", orderPage);
		}

		if (orderPage == null)
			orderPage = new OrderPage();

		OrderParameter parameter = orderPage.getParameter();
		SysUserInfo sysUserInfo = ParamerUtil.getSysUserFromSesson(request);
		int admintype = ParamerUtil.getAdminType(sysUserInfo.getAdminpower());
		int state = orderPage.getParameter().getTowerstate();
		if (parameter.getTowerstate() == -3) {

			parameter.setAdminorderstates(ParamerUtil.getAdminOrder(sysUserInfo
					.getAdminpower()));
			if (parameter.getAdminorderstates()[0] == -1) {
				parameter.setAdminorderstates(null);
			}
		} else if (parameter.getTowerstate() == -2) {
			parameter.setAdminorderstates(ParamerUtil.getAdminOrder(sysUserInfo
					.getAdminpower()));
		}
		List<Area> adminAreas = null;
		// 区域经理
		if (admintype == 2) {
			adminAreas = sysUserInfo.getAreas();
			orderPage.setAdminareas(adminAreas);

		}
		List<City> citys = sysUserInfo.getCitys();
		List<Area> areas = new ArrayList<Area>();// sysUserInfo.getAreas();
		if (parameter.getTowercity() != 0) {
			Map map = new HashMap();
			map.put("cityid", parameter.getTowercity());
			if (admintype == 2) {
				map.put("areaids", sysUserInfo.getAdminarea().split(","));
			}

			areas = areaService.getAreas(map);
		}else if(citys != null && citys.size() == 1){
			Map map = new HashMap();
			map.put("cityid", citys.get(0).getId());
			if (admintype == 2) {
				map.put("areaids", sysUserInfo.getAdminarea().split(","));
			}

			areas = areaService.getAreas(map);

		}

		orderPage.setAdminCitys(citys);

		List<Order> orders = orderService.getOrders(orderPage);

		if (state == -3) {
			if (parameter.getAdminorderstates() == null)
				parameter.setTowerstate(-1);
			else {
				parameter.setTowerstate(-2);
			}
		}

		orderPage.setOrders(orders);
		orderPage.setTowercitys(citys);
		orderPage.setTowerareas(areas);
		List<City> usercitys = areaService.getCitys(null);
		orderPage.setUsercitys(usercitys);
		// 设置页面
		PageUtil pageUtil = new PageUtil(orderPage.getPageno(),orderPage.getPageSize(),
				orderPage.getPagerowtotal());
		orderPage.setPageinfo(pageUtil.getPage());
		orderPage.setAdminpower(sysUserInfo.getAdminpower());
		List<OrderState> orderStates = orderService.getOrderStates();
		orderPage.setOrderStates(orderStates);
		return new ModelAndView("/order/order", "orderPage", orderPage);
	}

	/**
	 * 订单审核列表
	 * 
	 * @param request
	 * @param orderPage
	 * @return
	 */
	@RequestMapping("/fee_sh")
	public ModelAndView getFeeSh(HttpServletRequest request, OrderPage orderPage) {

		boolean isback = ParamerUtil.getIsBackPage(request);
		if (isback) {
			orderPage = (OrderPage) request.getSession().getAttribute("fee_sh");
		} else {
			request.getSession().setAttribute("fee_sh", orderPage);
		}

		if (orderPage == null)
			orderPage = new OrderPage();
		orderPage.setIsShPage(1);

		OrderParameter parameter = orderPage.getParameter();
		SysUserInfo sysUserInfo = ParamerUtil.getSysUserFromSesson(request);
		int admintype = ParamerUtil.getAdminType(sysUserInfo.getAdminpower());
		List<Area> adminAreas = null;
		// 区域经理
		if (admintype == 2) {
			adminAreas = sysUserInfo.getAreas();
			orderPage.setAdminareas(adminAreas);

		}

		/*
		 * if ((sysUserInfo.getAdminpower() == 4||sysUserInfo.getAdminpower() ==
		 * 13||sysUserInfo.getAdminpower() == 14||sysUserInfo.getAdminpower() ==
		 * 15) && parameter.getTowerstate() == -2)
		 * orderPage.getParameter().setTowerstate(8); if
		 * (parameter.getTowerstate() == -2) parameter.setTowerstate(-1);
		 */
		List<City> citys = sysUserInfo.getCitys();
		List<Area> areas = new ArrayList<Area>();// sysUserInfo.getAreas();
		if (parameter.getTowercity() != 0) {
			Map map = new HashMap();
			map.put("cityid", parameter.getTowercity());
			if (admintype == 2) {
				map.put("areaids", sysUserInfo.getAdminarea().split(","));
			}

			areas = areaService.getAreas(map);
		}else if(citys != null && citys.size() == 1){
			Map map = new HashMap();
			map.put("cityid", citys.get(0).getId());
			if (admintype == 2) {
				map.put("areaids", sysUserInfo.getAdminarea().split(","));
			}

			areas = areaService.getAreas(map);

		}
		
		orderPage.setAdminCitys(citys);

		List<Order> orders = orderService.getOrders(orderPage);
		orderPage.setOrders(orders);
		orderPage.setTowercitys(citys);
		orderPage.setTowerareas(areas);
		List<City> usercitys = areaService.getCitys(null);
		orderPage.setUsercitys(usercitys);
		orderPage.setAdminpower(sysUserInfo.getAdminpower());
		// 设置页面
		PageUtil pageUtil = new PageUtil(orderPage.getPageno(),orderPage.getPageSize(),
				orderPage.getPagerowtotal());
		orderPage.setPageinfo(pageUtil.getPage());
		return new ModelAndView("/order/fee_sh", "orderPage", orderPage);
	}

	/**
	 * 订单详情
	 * 
	 * @param request
	 * @param orderPage
	 * @return
	 */
	/*
	 * @RequestMapping("/order_info") public ModelAndView
	 * getOrderInfo(HttpServletRequest request, OrderPage orderPage) {
	 * 
	 * orderService.getOrderInfo(orderPage); if (orderPage == null) { return
	 * null; } SysUserInfo sysUserInfo =
	 * ParamerUtil.getSysUserFromSesson(request);
	 * 
	 * int power = sysUserInfo.getAdminpower(); int state =
	 * orderPage.getOrdertower().getTowerstate(); String url = "/order/"; if
	 * (power == 1||power==11) { url += "order_info"; } else { switch (state) {
	 * case 0: url += "order_info"; break; case 1: url += "order_info"; break;
	 * 
	 * case 2: url += "order_rent_ht"; break;
	 * 
	 * case 3: url += "order_info"; break;
	 * 
	 * case 4: if(power==4){ url += "order_info"; }else{ url += "order_fee"; }
	 * break;
	 * 
	 * case 5: if(power==4){ url += "order_info"; }else{ url +=
	 * "order_three_ht"; } break;
	 * 
	 * case 6: url += "order_info"; break;
	 * 
	 * case 7: if (power == 1 || power == 4) { url += "order_three_sh"; } else {
	 * url += "order_info"; } break;
	 * 
	 * case 8: url += "order_info"; break;
	 * 
	 * case 9: url += "order_info"; break;
	 * 
	 * case 10: url += "order_fee"; break;
	 * 
	 * case 11:
	 * 
	 * url += "order_info"; break;
	 * 
	 * case 12: url += "order_sh"; break;
	 * 
	 * case 13: if (power == 1 || power == 3) { url += "order_sh"; } else { url
	 * += "order_info"; } break; case 14: url += "order_info"; break; case 15:
	 * if (power == 1 || power == 3) { url += "order_sh"; } else { url +=
	 * "order_info"; } break; case 16: case 17: if (power == 1 || power == 2) {
	 * url += "order_sh"; } else { url += "order_info"; } break; case 18: case
	 * 19:
	 * 
	 * url += "order_info"; break;
	 * 
	 * case 20: case 21: if (power == 1 || power == 2) { url += "order_sh"; }
	 * else { url += "order_info"; } break; case 22: url += "order_three_ht";
	 * break; case 23: url += "order_info"; break; default: break; } }
	 * 
	 * orderPage.setYqmincount(Config.yqmincount);
	 * orderPage.setYqmaxcount(Config.yqmaxcount); return new ModelAndView(url,
	 * "orderPage", orderPage); }
	 */

	/**
	 * 订单详情
	 * 
	 * @param request
	 * @param orderPage
	 * @return
	 */
	@RequestMapping("/order_info")
	public ModelAndView getOrderInfo(HttpServletRequest request,
			OrderPage orderPage) {
		orderService.getOrderInfo(orderPage);
		if (orderPage == null) {
			return null;
		}
		String url = "/order/order_info";
		return new ModelAndView(url, "orderPage", orderPage);
	}

	/**
	 * 订单审核（延期、审核拒绝、目标建站地址）
	 * 
	 * @param request
	 * @param orderPage
	 * @return
	 */
	@RequestMapping("/order_sh_info")
	public ModelAndView getOrderShInfo(HttpServletRequest request,
			OrderPage orderPage) {

		orderService.getOrderInfo(orderPage);
		if (orderPage == null) {
			return null;
		}

		String url = "/order/order_sh";

		orderPage.setYqmincount(Config.yqmincount);
		orderPage.setYqmaxcount(Config.yqmaxcount);
		return new ModelAndView(url, "orderPage", orderPage);
	}

	/**
	 * 提交租赁合同页面
	 * 
	 * @param request
	 * @param orderPage
	 * @return
	 */
	@RequestMapping("/order_rent_ht_info")
	public ModelAndView getOrderRentHtInfo(HttpServletRequest request,
			OrderPage orderPage) {

		orderService.getOrderInfo(orderPage);
		if (orderPage == null) {
			return null;
		}
		String url = "/order/order_rent_ht";

		return new ModelAndView(url, "orderPage", orderPage);
	}

	/**
	 * 提交三方合同页面
	 * 
	 * @param request
	 * @param orderPage
	 * @return
	 */
	@RequestMapping("/order_three_ht_info")
	public ModelAndView getOrderThreeHtInfo(HttpServletRequest request,
			OrderPage orderPage) {

		orderService.getOrderInfo(orderPage);
		if (orderPage == null) {
			return null;
		}
		String url = "/order/order_three_ht";
		return new ModelAndView(url, "orderPage", orderPage);
	}

	/**
	 * 三方合同审核页面
	 * 
	 * @param request
	 * @param orderPage
	 * @return
	 */
	@RequestMapping("/order_three_sh_info")
	public ModelAndView getOrderThreeShInfo(HttpServletRequest request,
			OrderPage orderPage) {

		orderService.getOrderInfo(orderPage);
		if (orderPage == null) {
			return null;
		}
		String url = "/order/order_three_sh";
		return new ModelAndView(url, "orderPage", orderPage);
	}

	/**
	 * 提交付款申请页面
	 * 
	 * @param request
	 * @param orderPage
	 * @return
	 */
	@RequestMapping("/order_fee_info")
	public ModelAndView getOrderFeeInfo(HttpServletRequest request,
			OrderPage orderPage) {

		orderService.getOrderInfo(orderPage);
		if (orderPage == null) {
			return null;
		}
		String url = "/order/order_fee";

		return new ModelAndView(url, "orderPage", orderPage);
	}

	/**
	 * 付款审核页面
	 * 
	 * @param request
	 * @param orderPage
	 * @return
	 */
	@RequestMapping("/fee_sh_info")
	public ModelAndView getFeeShInfo(HttpServletRequest request,
			OrderPage orderPage) {

		orderService.getOrderInfo(orderPage);

		return new ModelAndView("/order/fee_sh_info", "orderPage", orderPage);
	}

	/**
	 * 订单审核
	 * 
	 * @param request
	 * @param orderid
	 * @param result
	 * @param shinfo
	 * @param yqcount
	 * @return
	 */
	@RequestMapping("/order_sh")
	@ResponseBody
	public Object orderSh(HttpServletRequest request, int orderid, int result,
			String shinfo, int yqcount) {

		Map map = new HashMap();
		map.put("orderid", orderid);
		map.put("result", result);
		map.put("shinfo", shinfo);
		map.put("yqcount", yqcount * 24);

		SysUserInfo admin = ParamerUtil.getSysUserFromSesson(request);
		map.put("adminid", admin.getId());
		map.put("adminname", admin.getAdminname());
		map.put("adminpower", admin.getAdminpower());
		int count = 0;
		try {
			count = orderService.shOrder(map);
		} catch (Exception e) {

			e.printStackTrace();
		}
		if (count == 0)
			return ResultUtil.generateResponseMsg("error");

		return ResultUtil.generateResponseMsg("success");
	}

	/**
	 * 提交租赁和通
	 * 
	 * @param request
	 * @param orderInfo
	 * @param shinfo
	 * @return
	 */
	@RequestMapping("/order_rent_ht")
	@ResponseBody
	public Object orderRentHt(HttpServletRequest request, OrderInfo orderInfo,
			String shinfo) {

		if (orderInfo == null)
			return ResultUtil.generateResponseMsg("error");
		
		if(orderInfo.getIscheckht()==2){
		/*	
		String filepath = "\\images\\order\\ht\\";
		String image = FileUpload.uploadFile(request, filepath, "rentht");
		if (image == null)
			return ResultUtil.generateResponseMsg("error");
		orderInfo.setTowerhtimag(image);
		*/
			String image=orderInfo.getTowerhtimag();
			if (image == null||image.equals(""))
				return ResultUtil.generateResponseMsg("error");
		}
		else if(orderInfo.getIscheckht()==1){
			if(orderInfo.getTowerhtid()==null||orderInfo.getTowerhtid().equals("")){
				return ResultUtil.generateResponseMsg("error");
			}
		}else{
			orderInfo.setTowerhtid("0");
		}
		SysUserInfo admin = ParamerUtil.getSysUserFromSesson(request);
	

		int count = 0;
		try {
			count = orderService.updateTowerRentHtAply(admin.getId(), shinfo,
					orderInfo, admin.getAdminpower());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (count == 0)
			return ResultUtil.generateResponseMsg("error");

		return ResultUtil.generateResponseMsg("success");
	}

	/**
	 * 提交三方合同
	 * 
	 * @param request
	 * @param orderInfo
	 * @param shinfo
	 * @return
	 */
	@RequestMapping("/order_three_ht")
	@ResponseBody
	public Object orderThreeHt(HttpServletRequest request, OrderInfo orderInfo,
			String shinfo) {
		

		if (orderInfo == null)
			return ResultUtil.generateResponseMsg("error");
		//String filepath = "\\images\\order\\ht\\";
		//String image = FileUpload.uploadFile(request, filepath, "threeht");
		//if (image == null)
		//	return ResultUtil.generateResponseMsg("error");
		//orderInfo.setTowerthreeht(image);
		SysUserInfo admin = ParamerUtil.getSysUserFromSesson(request);
		int count = 0;
		try {
			String templatePath=request.getSession().getServletContext().getRealPath("/");
			String threePath=new File(templatePath).getParent()+"\\shanghai_towerfile\\images\\order\\ht\\";
			templatePath=request.getSession().getServletContext().getRealPath(TemplateUtil.TEMPLATE_PATH);
			count = orderService.updateTowerThreeHtAply(templatePath,threePath,admin.getId(), shinfo,
					orderInfo, admin.getAdminpower());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (count == 0)
			return ResultUtil.generateResponseMsg("error");

		return ResultUtil.generateResponseMsg("success");
	}

	/**
	 * 三方合同审核
	 * 
	 * @param request
	 * @param orderid
	 * @param shinfo
	 * @param result
	 * @return
	 */
	@RequestMapping("/order_three_sh")
	@ResponseBody
	public Object orderThreeSh(HttpServletRequest request, int orderid,
			String shinfo, int result) {

		if (orderid == 0)
			return ResultUtil.generateResponseMsg("error");
		SysUserInfo admin = ParamerUtil.getSysUserFromSesson(request);
		int count = 0;
		Map map = new HashMap();
		map.put("orderid", orderid);
		map.put("result", result);
		map.put("shinfo", shinfo);
		map.put("adminpower", admin.getAdminpower());
		map.put("adminid", admin.getId());
		try {
			count = orderService.updateTowerThreeSh(map);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (count == 0)
			return ResultUtil.generateResponseMsg("error");

		return ResultUtil.generateResponseMsg("success");
	}

	/**
	 * 付款申请
	 * 
	 * @param request
	 * @param orderid
	 * @return
	 */
	@RequestMapping("/order_fee")
	@ResponseBody
	public Object orderFee(HttpServletRequest request, int orderid,String towerprojectno,String towerprojectname,String image) {

		if (orderid == 0)
			return ResultUtil.generateResponseMsg("error");
		/*
		String filepath = "\\images\\order\\ht\\";
		String image = FileUpload.uploadFile(request, filepath, "dxfwxyfile");
		if (image == null)
			return ResultUtil.generateResponseMsg("error");
		*/
		if (image == null||image.equals(""))
			return ResultUtil.generateResponseMsg("error");
		Map map = new HashMap();
		map.put("orderid", orderid);
		map.put("image", image);
		map.put("towerprojectno", towerprojectno);
		map.put("towerprojectname", towerprojectname);

		SysUserInfo admin = ParamerUtil.getSysUserFromSesson(request);
		map.put("adminid", admin.getId());
		map.put("adminpower", admin.getAdminpower());
		int count = 0;
		try {
			count = orderService.updateTowerFeeAply(map);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (count == 0)
			return ResultUtil.generateResponseMsg("error");

		return ResultUtil.generateResponseMsg("success");
	}

	/**
	 * 付款申请审核
	 * 
	 * @param request
	 * @param orderid
	 * @return
	 */
	@RequestMapping("/order_fee_sh")
	@ResponseBody
	public Object orderFeeSh(HttpServletRequest request, int orderid,
			String shinfo, int result) {

		if (orderid == 0)
			return ResultUtil.generateResponseMsg("error");
		SysUserInfo admin = ParamerUtil.getSysUserFromSesson(request);
		int count = 0;
		Map map = new HashMap();
		map.put("orderid", orderid);
		map.put("result", result);
		map.put("shinfo", shinfo);
		map.put("adminpower", admin.getAdminpower());
		map.put("adminid", admin.getId());
		try {
			count = orderService.updateTowerFeeSh(map);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (count == 0)
			return ResultUtil.generateResponseMsg("error");

		return ResultUtil.generateResponseMsg("success");
	}

	@RequestMapping("/order_shjj")
	@ResponseBody
	public Object orderShjj(HttpServletRequest request, int orderid,
			String shinfo) {
		if (orderid == 0)
			return ResultUtil.generateResponseMsg("error");
		Map<String, Comparable> map = new HashMap();
		map.put("orderid", orderid);
		map.put("shinfo", shinfo);
		SysUserInfo admin = ParamerUtil.getSysUserFromSesson(request);
		map.put("adminid", admin.getId());
		map.put("adminpower", admin.getAdminpower());
		int count = 0;
		try {
			count = orderService.updateOrderShjj(map);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (count == 0)
			return ResultUtil.generateResponseMsg("error");

		return ResultUtil.generateResponseMsg("success");
	}

	@RequestMapping("/order_evaluate")
	@ResponseBody
	public Object orderEvaluate(HttpServletRequest request, int orderid,
			String admincontent) {
		if (orderid == 0)
			return ResultUtil.generateResponseMsg("error");
		Map<String, Comparable> map = new HashMap();
		map.put("orderid", orderid);
		map.put("admincontent", admincontent);
		map.put("progress", 2);
		SysUserInfo admin = ParamerUtil.getSysUserFromSesson(request);
		map.put("adminid", admin.getId());
		map.put("adminpower", admin.getAdminpower());
		int count = 0;
		try {
			count = orderService.updateOrderPjProgress(map);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (count == 0)
			return ResultUtil.generateResponseMsg("error");

		return ResultUtil.generateResponseMsg("success");
	}

	/*
	 * 
	 * @RequestMapping("/order_first_fee")
	 * 
	 * @ResponseBody public Object orderFirstFee(HttpServletRequest request, int
	 * orderid, String htno, int firstfee, int endfee) {
	 * 
	 * if (orderid == 0) return ResultUtil.generateResponseMsg("error"); String
	 * filepath = "\\images\\sq\\feesh\\"; String image =
	 * FileUpload.uploadFile(request, filepath, "firstfile"); if (image == null)
	 * return ResultUtil.generateResponseMsg("error"); Map map = new HashMap();
	 * map.put("orderid", orderid); map.put("htid", htno); map.put("image",
	 * image); map.put("firstfee", firstfee); map.put("endfee", endfee);
	 * 
	 * SysUserInfo admin = ParamerUtil.getSysUserFromSesson(request);
	 * map.put("adminid", admin.getId()); int count = 0; try { count =
	 * orderService.updateTowerFirstFeeAply(map); } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * if (count == 0) return ResultUtil.generateResponseMsg("error");
	 * 
	 * return ResultUtil.generateResponseMsg("success"); }
	 * 
	 * 
	 * @RequestMapping("/order_end_fee")
	 * 
	 * @ResponseBody public Object orderEndFee(HttpServletRequest request, int
	 * orderid) {
	 * 
	 * if (orderid == 0) return ResultUtil.generateResponseMsg("error"); String
	 * filepath = "\\images\\sq\\feesh\\"; String image =
	 * FileUpload.uploadFile(request, filepath, "endfile"); if (image == null)
	 * return ResultUtil.generateResponseMsg("error"); Map map = new HashMap();
	 * map.put("orderid", orderid); map.put("image", image); SysUserInfo admin =
	 * ParamerUtil.getSysUserFromSesson(request); map.put("adminid",
	 * admin.getId()); int count = 0; try { count =
	 * orderService.updateTowerEndFeeAply(map); } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * if (count == 0) return ResultUtil.generateResponseMsg("error");
	 * 
	 * return ResultUtil.generateResponseMsg("success"); }
	 * 
	 * @RequestMapping("/update_fee_sh")
	 * 
	 * @ResponseBody public Object orderFeeSh(HttpServletRequest request, int
	 * orderid, String shinfo, int result) {
	 * 
	 * if (orderid == 0) return ResultUtil.generateResponseMsg("error"); Map map
	 * = new HashMap(); map.put("orderid", orderid); map.put("shinfo", shinfo);
	 * map.put("result", result); SysUserInfo admin =
	 * ParamerUtil.getSysUserFromSesson(request); map.put("adminid",
	 * admin.getId()); int count = 0; try { count =
	 * orderService.updateFeeSh(map); } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * if (count == 0) return ResultUtil.generateResponseMsg("error");
	 * 
	 * return ResultUtil.generateResponseMsg("success"); }
	 * 
	 * @RequestMapping("/order_yq")
	 * 
	 * @ResponseBody public Object orderYq(HttpServletRequest request, int
	 * orderid) { if (orderid == 0) return
	 * ResultUtil.generateResponseMsg("error"); Map map = new HashMap();
	 * map.put("orderid", orderid); SysUserInfo admin =
	 * ParamerUtil.getSysUserFromSesson(request); map.put("adminid",
	 * admin.getId()); int count = 0; try { count =
	 * orderService.updateOrderYq(map); } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * if (count == 0) return ResultUtil.generateResponseMsg("error");
	 * 
	 * return ResultUtil.generateResponseMsg("success"); }
	 * 
	 * @RequestMapping("/order_shjj")
	 * 
	 * @ResponseBody public Object orderShjj(HttpServletRequest request, int
	 * orderid) { if (orderid == 0) return
	 * ResultUtil.generateResponseMsg("error"); Map map = new HashMap();
	 * map.put("orderid", orderid); SysUserInfo admin =
	 * ParamerUtil.getSysUserFromSesson(request); map.put("adminid",
	 * admin.getId()); int count = 0; try { count =
	 * orderService.updateOrderShjj(map); } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * if (count == 0) return ResultUtil.generateResponseMsg("error");
	 * 
	 * return ResultUtil.generateResponseMsg("success"); }
	 */

	@RequestMapping("/h2")
	public ModelAndView getH2() {

		return new ModelAndView("/order/h2");
	}

	/**
	 * 导出订单
	 * 
	 * @param request
	 * @param response
	 * @param orderPage
	 * @throws Exception
	 */
	@RequestMapping("/dc_orders")
	@ResponseBody
	public void dcTowers(HttpServletRequest request,
			HttpServletResponse response, OrderPage orderPage) throws Exception {
		List<Area> adminAreas = null;
		SysUserInfo sysUserInfo = ParamerUtil.getSysUserFromSesson(request);
		int admintype = ParamerUtil.getAdminType(sysUserInfo.getAdminpower());
		int state = orderPage.getParameter().getTowerstate();
		if ( orderPage.getParameter().getTowerstate() == -3) {

			 orderPage.getParameter().setAdminorderstates(ParamerUtil.getAdminOrder(sysUserInfo
					.getAdminpower()));
			if ( orderPage.getParameter().getAdminorderstates()[0] == -1) {
				 orderPage.getParameter().setAdminorderstates(null);
			}
		} else if ( orderPage.getParameter().getTowerstate() == -2) {
			orderPage.getParameter().setAdminorderstates(ParamerUtil.getAdminOrder(sysUserInfo
					.getAdminpower()));
		}
		// 区域经理
		if (admintype == 2) {
			adminAreas = sysUserInfo.getAreas();
			orderPage.setAdminareas(adminAreas);

		}
		List<City> citys = sysUserInfo.getCitys();
		List<Area> areas = new ArrayList<Area>();// sysUserInfo.getAreas();
		if (orderPage.getParameter().getTowercity() != 0) {
			Map map = new HashMap();
			map.put("cityid", orderPage.getParameter().getTowercity());
			if (admintype == 2) {
				map.put("areaids", sysUserInfo.getAdminarea().split(","));
			}

			areas = areaService.getAreas(map);
		}else if(citys != null && citys.size() == 1){
			Map map = new HashMap();
			map.put("cityid", citys.get(0).getId());
			if (admintype == 2) {
				map.put("areaids", sysUserInfo.getAdminarea().split(","));
			}

			areas = areaService.getAreas(map);

		}

		orderPage.setAdminCitys(citys);

		String filename = "orderinfo";
		ExcelDC ecDc = new ExcelDC(request, "order_info.xls");
		try {
			if (orderPage.getParameter().getTowername() != null) {
				orderPage.getParameter().setTowername(
						new String(orderPage.getParameter().getTowername()
								.getBytes("ISO8859-1"), "utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
		}
		try {
			if (orderPage.getParameter().getUsername() != null) {
				orderPage.getParameter().setUsername(
						new String(orderPage.getParameter().getUsername()
								.getBytes("ISO8859-1"), "utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
		}
		List<Map> list = orderService.getDcOrders(orderPage);
		HSSFWorkbook workbook = ecDc.getOrders(list);
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		try {
			workbook.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);

		filename = new String(("订单详情("+TimeUtil.getNowDay()+").xls").getBytes(), "iso-8859-1");
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ filename + "");

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

	/**
	 * 订单详情
	 * 
	 * @param request
	 * @param orderPage
	 * @return
	 */
	@RequestMapping("/order_pj")
	public ModelAndView getOrderPj(HttpServletRequest request,
			OrderPage orderPage) {

		orderService.getOrderInfo(orderPage);
		if (orderPage == null) {
			return null;
		}
		SysUserInfo sysUserInfo = ParamerUtil.getSysUserFromSesson(request);

		int power = sysUserInfo.getAdminpower();
		String url = "/order/order_pj";

		orderPage.setYqmincount(Config.yqmincount);
		orderPage.setYqmaxcount(Config.yqmaxcount);
		return new ModelAndView(url, "orderPage", orderPage);
	}

	@RequestMapping("/update_ht_file")
	@ResponseBody
	public Object updateUserTeamType(HttpServletRequest request){
		
		String filepath = "\\images\\order\\ht\\";
		String image=FileUpload.uploadFile(request, filepath, "file");
		if(image==null)
			return ResultUtil.generateResponseMsg("error","上传失败！");
		return ResultUtil.generateResponseMsg("success",image);
		
	}
	
	
	@RequestMapping("/del_old_img")
	@ResponseBody
	public Object delOldImg(HttpServletRequest request,String oldimg){
		
		try {
			if(oldimg!=null&&!oldimg.equals("")){
				//删除上次传的图片
				String path = request.getSession().getServletContext()
						.getRealPath("/");
		        if(path.endsWith("\\"))
		        	path=path.substring(0,path.length()-1);
				path=path.substring(0,path.lastIndexOf('\\'));
				path+=oldimg;
				File  file=new File(path);
				if(file.exists()){
					file.delete();
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultUtil.generateResponseMsg("success");
		
	}
	
	
	
	@RequestMapping("/getOrderInfoImg")
	@ResponseBody
	public void getOrderInfoImg(HttpServletRequest request,
			HttpServletResponse response,int orderid,String orderno)  throws Exception {
	
	 ImageRenderer render = new ImageRenderer();
     System.out.println("kaishi");
     String url ="http://localhost/towersh/order/order_info.html?orderid="+orderid+"&cutimg=1";
     String path = request.getSession().getServletContext()
				.getRealPath("/");
     if(path.endsWith("\\"))
     	path=path.substring(0,path.length()-1);
		path=path.substring(0,path.lastIndexOf('\\'));
     path=path+"/shanghai_towerfile/orderinfocut/";
     String filename=orderid+".png";
     File file=new File(path,filename);
     if(!file.exists())
    	 file.getParentFile().mkdirs();
     FileOutputStream out = new FileOutputStream(file);
     System.out.println("qqqqqqqq");
     try {
    	 render.renderURL(url, out, ImageRenderer.Type.PNG);
    	 System.out.println("mmmmmmmm");
	} catch (Exception e) {
		System.out.println("111111111111111111");
		System.out.println();
	}
    
     InputStream in = null;
     if(out!=null)
    	 out.close();
     in = new FileInputStream(file);
    
     response.reset();
     filename="订单详情("+orderno+").png";
     filename=new String(filename.getBytes(), "iso-8859-1");
     response.setContentType("application/vnd.ms-excel;charset=utf-8");
     response.setHeader("Content-Disposition", "attachment;filename="+filename+"");

     ServletOutputStream servletOutputStream = response.getOutputStream();

     BufferedInputStream bis = null;
     BufferedOutputStream bos = null;

     try {

         bis = new BufferedInputStream(in);
         bos = new BufferedOutputStream(servletOutputStream);
         byte[] buff = new byte[2048];
         int bytesRead;

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
