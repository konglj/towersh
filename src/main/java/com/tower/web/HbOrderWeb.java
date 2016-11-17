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

import com.tower.common.bean.Area;
import com.tower.common.bean.City;
import com.tower.common.bean.Order;
import com.tower.common.bean.OrderInfo;
import com.tower.common.bean.OrderPage;
import com.tower.common.bean.OrderParameter;
import com.tower.common.bean.SysUserInfo;
import com.tower.common.bean.TowerShInfo;
import com.tower.common.util.ExcelDC;
import com.tower.common.util.FileUpload;
import com.tower.common.util.PageUtil;
import com.tower.common.util.ParamerUtil;
import com.tower.common.util.ResultUtil;
import com.tower.service.AreaService;
import com.tower.service.HbOrderService;
import com.tower.service.ManagerService;
import com.tower.service.OrderService;

@Controller
@RequestMapping("/hborder")
public class HbOrderWeb {

	@Autowired
	private HbOrderService hbOrderService;

	@Autowired
	private AreaService areaService;
	
	@Autowired
	private ManagerService managerService;

	@RequestMapping("/order")
	public ModelAndView getOrders(HttpServletRequest request,
			OrderPage orderPage) {
		if (orderPage == null)
			orderPage = new OrderPage();

		OrderParameter parameter = orderPage.getParameter();
		SysUserInfo sysUserInfo = ParamerUtil.getSysUserFromSesson(request);
		List<City> citys = sysUserInfo.getCitys();
		List<Area> areas = new ArrayList<Area>();// sysUserInfo.getAreas();
		if (parameter.getTowercity() == 0) {
			if (citys == null || citys.size() == 0) {
				citys = new ArrayList<City>();
			} else if (citys.size() == 1) {
				orderPage.getParameter().setTowercity(citys.get(0).getId());
				areas = sysUserInfo.getAreas();
			}
		} else {
			if (citys.size() == 1) {
				areas = sysUserInfo.getAreas();
			} else if (citys.size() > 1) {
				Map map = new HashMap();
				map.put("cityid", parameter.getTowercity());
				areas = areaService.getAreas(map);
			}
		}

		int adminType = ParamerUtil.getAdminType(sysUserInfo.getAdminpower());
		if (adminType == 1) {
			orderPage.setAdmincity(citys.get(0).getCityid());
		} else if (adminType == 2) {
			orderPage.setAdminareas(sysUserInfo.getAreas());
		}

		List<Order> orders = hbOrderService.getOrders(orderPage);
		orderPage.setOrders(orders);
		orderPage.setTowercitys(citys);
		orderPage.setTowerareas(areas);
		List<City> usercitys = areaService.getCitys(null);
		List<Area> userareas = new ArrayList<Area>();
		if (parameter.getUsercity() !=0) {
			Map map = new HashMap();
			map.put("cityid", parameter.getUsercity());
			userareas = areaService.getAreas(map);
		}
		orderPage.setUsercitys(usercitys);
		orderPage.setUserareas(userareas);
		// 设置页面
		PageUtil pageUtil = new PageUtil(orderPage.getPageno(),
				orderPage.getPagerowtotal());
		orderPage.setPageinfo(pageUtil.getPage());
		return new ModelAndView("/hbtower/order", "orderPage", orderPage);
	}
	
	
	
	
	@RequestMapping("/fee_sh")
	public ModelAndView getFeeSh(HttpServletRequest request,
			OrderPage orderPage) {
		if (orderPage == null)
			orderPage = new OrderPage();
		orderPage.setIsShPage(1);

		OrderParameter parameter = orderPage.getParameter();
		SysUserInfo sysUserInfo = ParamerUtil.getSysUserFromSesson(request);
		List<City> citys = sysUserInfo.getCitys();
		List<Area> areas = new ArrayList<Area>();// sysUserInfo.getAreas();
		if (parameter.getTowercity() == 0) {
			if (citys == null || citys.size() == 0) {
				citys = new ArrayList<City>();
			} else if (citys.size() == 1) {
				orderPage.getParameter().setTowercity(citys.get(0).getId());
				areas = sysUserInfo.getAreas();
			}
		} else {
			if (citys.size() == 1) {
				areas = sysUserInfo.getAreas();
			} else if (citys.size() > 1) {
				Map map = new HashMap();
				map.put("cityid", parameter.getTowercity());
				areas = areaService.getAreas(map);
			}
		}

		int adminType = ParamerUtil.getAdminType(sysUserInfo.getAdminpower());
		if (adminType == 1) {
			orderPage.setAdmincity(citys.get(0).getCityid());
		} else if (adminType == 2) {
			orderPage.setAdminareas(sysUserInfo.getAreas());
		}

		List<Order> orders = hbOrderService.getOrders(orderPage);
		orderPage.setOrders(orders);
		orderPage.setTowercitys(citys);
		orderPage.setTowerareas(areas);
		List<City> usercitys = areaService.getCitys(null);
		List<Area> userareas = new ArrayList<Area>();
		if (parameter.getUsercity() !=0) {
			Map map = new HashMap();
			map.put("cityid", parameter.getUsercity());
			userareas = areaService.getAreas(map);
		}
		orderPage.setUsercitys(usercitys);
		orderPage.setUserareas(userareas);
		// 设置页面
		PageUtil pageUtil = new PageUtil(orderPage.getPageno(),
				orderPage.getPagerowtotal());
		orderPage.setPageinfo(pageUtil.getPage());
		return new ModelAndView("/hbtower/fee_sh", "orderPage", orderPage);
	}

	
	

	@RequestMapping("/order_info")
	public ModelAndView getOrderInfo(HttpServletRequest request,
			OrderPage orderPage) {

		hbOrderService.getOrderInfo(orderPage);
		if (orderPage == null) {
			return null;
		}
		int state = orderPage.getOrdertower().getTowerstate();
		String url = "/hbtower/";
		switch (state) {
		case 0:
			url+="order_sh";
			break;
		case 1:
			url+="order_info";
			break;

		case 2:
			url+="order_sh";
			break;

		case 3:
			url+="order_info";
			break;

		case 4:
			url+="order_first_fee";
			break;

		case 5:
			url+="order_info";
			break;

		case 6:
			url+="order_end_fee";
			break;

		case 7:
			url+="order_first_fee";
			break;

		case 8:
			url+="order_info";
			break;

		case 9:
			url+="order_info";
			break;

		case 10:
			url+="order_end_fee";
			break;

		case 11:
		
			url+="order_info";
			break;

		case 12:
			url+="order_sh";
			break;

		case 13:
			url+="order_sh";
			break;
		case 14:
			url+="order_sh";
			break;


		default:
			break;
		}
		return new ModelAndView(url, "orderPage", orderPage);
	}

	

	@RequestMapping("/fee_sh_info")
	public ModelAndView getFeeShInfo(HttpServletRequest request,
			OrderPage orderPage) {

		hbOrderService.getOrderInfo(orderPage);
		
		return new ModelAndView("/hbtower/fee_sh_info", "orderPage", orderPage);
	}

	
	@RequestMapping("/order_sh")
	@ResponseBody
	public Object orderSh(HttpServletRequest request,int orderid,int result,String shinfo){
		
		Map map=new HashMap();
		map.put("orderid", orderid);
		map.put("result", result);
		map.put("shinfo", shinfo);
		
		
		SysUserInfo admin=ParamerUtil.getSysUserFromSesson(request);
		map.put("adminid", admin.getId());
		map.put("adminname", admin.getAdminname());
		int count=0;
		try {
			count =hbOrderService.shOrder(map);
		} catch (Exception e) {
			
		}
		if(count==0)
			return ResultUtil.generateResponseMsg("error");
	 
		
		return ResultUtil.generateResponseMsg("success");
	}
	
	@RequestMapping("/order_first_fee")
	@ResponseBody
	public Object orderFirstFee(HttpServletRequest request,int orderid,String htno,int firstfee,int endfee){
		
		if(orderid==0)
			return ResultUtil.generateResponseMsg("error");
		String filepath="\\images\\sq\\feesh\\";
		String image=FileUpload.uploadFile(request, filepath, "firstfile");
		if(image==null)
			return ResultUtil.generateResponseMsg("error");
		Map map=new HashMap();
		map.put("orderid", orderid);
		map.put("htid", htno);
		map.put("image", image);
		map.put("firstfee", firstfee);
		map.put("endfee", endfee);
		
		SysUserInfo admin=ParamerUtil.getSysUserFromSesson(request);
		map.put("adminid", admin.getId());
		int count=0;
		try {
			count=hbOrderService.updateTowerFirstFeeAply(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(count==0)
			return ResultUtil.generateResponseMsg("error");
	 
		
		return ResultUtil.generateResponseMsg("success");
	}

	
	@RequestMapping("/order_end_fee")
	@ResponseBody
	public Object orderEndFee(HttpServletRequest request,int orderid){
		
		if(orderid==0)
			return ResultUtil.generateResponseMsg("error");
		String filepath="\\images\\sq\\feesh\\";
		String image=FileUpload.uploadFile(request, filepath, "endfile");
		if(image==null)
			return ResultUtil.generateResponseMsg("error");
		Map map=new HashMap();
		map.put("orderid", orderid);
		map.put("image", image);
		SysUserInfo admin=ParamerUtil.getSysUserFromSesson(request);
		map.put("adminid", admin.getId());
		int count=0;
		try {
			count=hbOrderService.updateTowerEndFeeAply(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(count==0)
			return ResultUtil.generateResponseMsg("error");
	 
		
		return ResultUtil.generateResponseMsg("success");
	}
	
	@RequestMapping("/update_fee_sh")
	@ResponseBody
	public Object orderFeeSh(HttpServletRequest request,int orderid,String shinfo,int result){
		
		if(orderid==0)
			return ResultUtil.generateResponseMsg("error");
		Map map=new HashMap();
		map.put("orderid", orderid);
		map.put("shinfo", shinfo);
		map.put("result", result);
		SysUserInfo admin=ParamerUtil.getSysUserFromSesson(request);
		map.put("adminid", admin.getId());
		int count=0;
		try {
			count=hbOrderService.updateFeeSh(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(count==0)
			return ResultUtil.generateResponseMsg("error");
	 
		
		return ResultUtil.generateResponseMsg("success");
	}
	
	
	@RequestMapping("/order_yq")
	@ResponseBody
	public Object orderYq(HttpServletRequest request,int orderid){
		if(orderid==0)
			return ResultUtil.generateResponseMsg("error");
		Map map=new HashMap();
		map.put("orderid", orderid);
		SysUserInfo admin=ParamerUtil.getSysUserFromSesson(request);
		map.put("adminid", admin.getId());
		int count=0;
		try {
			count=hbOrderService.updateOrderYq(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(count==0)
			return ResultUtil.generateResponseMsg("error");
	 
		
		return ResultUtil.generateResponseMsg("success");
	}
	

	@RequestMapping("/order_shjj")
	@ResponseBody
	public Object orderShjj(HttpServletRequest request,int orderid){
		if(orderid==0)
			return ResultUtil.generateResponseMsg("error");
		Map map=new HashMap();
		map.put("orderid", orderid);
		SysUserInfo admin=ParamerUtil.getSysUserFromSesson(request);
		map.put("adminid", admin.getId());
		int count=0;
		try {
			count=hbOrderService.updateOrderShjj(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(count==0)
			return ResultUtil.generateResponseMsg("error");
	 
		
		return ResultUtil.generateResponseMsg("success");
	}
	
	@RequestMapping("/h2")
	public ModelAndView getH2(){
	
		return new ModelAndView("/hbtower/h2");
	}
	
	
	@RequestMapping("/dc_orders")
	@ResponseBody
	public void dcTowers(HttpServletRequest request,
			HttpServletResponse response, OrderPage orderPage)  throws Exception {
		String filename="orderinfo";
		ExcelDC ecDc = new ExcelDC(request, "order_info.xls");
		List<Map> list = hbOrderService.getDcOrders(orderPage);
		HSSFWorkbook workbook = ecDc.getOrders(list);
		 ByteArrayOutputStream os = new ByteArrayOutputStream();

	        try {
	        	workbook.write(os);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		  byte[] content = os.toByteArray();
	        InputStream is = new ByteArrayInputStream(content);

	       filename=new String(("订单详情.xls").getBytes(), "iso-8859-1");
	        // 设置response参数，可以打开下载页面
	        response.reset();
	        response.setContentType("application/vnd.ms-excel;charset=utf-8");
	        response.setHeader("Content-Disposition", "attachment;filename="+filename+"");

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
