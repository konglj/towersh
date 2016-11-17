package com.tower.web;

import java.awt.Window.Type;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.xmlbeans.impl.common.IdentityConstraint.IdState;
import org.fit.cssbox.demo.ImageRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tower.common.bean.AddTowerInfo;
import com.tower.common.bean.Area;
import com.tower.common.bean.City;
import com.tower.common.bean.OrderPage;
import com.tower.common.bean.SysUserInfo;
import com.tower.common.bean.TxPage;
import com.tower.common.bean.TxParameter;
import com.tower.common.bean.TxRecord;
import com.tower.common.bean.UserManagePage;
import com.tower.common.bean.UserMoneyPage;
import com.tower.common.bean.UserType;
import com.tower.common.util.ExcelDC;
import com.tower.common.util.ExcelHelper;
import com.tower.common.util.FileUpload;
import com.tower.common.util.HttpUtil;
import com.tower.common.util.PageUtil;
import com.tower.common.util.ParamerUtil;
import com.tower.common.util.ResultUtil;
import com.tower.common.util.TimeUtil;
import com.tower.service.AreaService;
import com.tower.service.TxService;
import com.tower.service.UserService;

@Controller
@RequestMapping("/money")
public class MoneyWeb {
	
	@Autowired
	private TxService txService;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/tx")
	public ModelAndView getTxshs(HttpServletRequest request,TxPage txPage){
		
		boolean isback=ParamerUtil.getIsBackPage(request);
		if(isback){
			txPage=(TxPage)request.getSession().getAttribute("tx");
		}else{
			request.getSession().setAttribute("tx", txPage);
		}
		
		if(txPage==null)
			txPage=new TxPage();
		
		TxParameter parameter=txPage.getParameter();
		if(parameter.getState()==-2){
			parameter.setState(0);
		}
		SysUserInfo sysUserInfo=ParamerUtil.getSysUserFromSesson(request);
		List<City> citys=sysUserInfo.getCitys();
		txPage.setAdminCitys(citys);
		if (parameter.getUsercity() == 0) {
		} else {
			
				Map map = new HashMap();
				map.put("cityid", parameter.getUsercity());
			
		}
		
		List<City> usercitys=sysUserInfo.getCitys();
		List<TxRecord> txrecords=txService.getTxs(txPage);
		txPage.setTxrecords(txrecords);
		
		if(parameter.getUsercity()!=0){
			
			
		}
		txPage.setUsercitys(usercitys);
		List<UserType> usertypes=userService.getUserTypes();
		txPage.setUserTypes(usertypes);
		txPage.setAdminpower(sysUserInfo.getAdminpower());
		//设置页面
		PageUtil pageUtil = new PageUtil(txPage.getPageno(),
				txPage.getPageSize(), txPage.getPagerowtotal());
		txPage.setPageinfo(pageUtil.getPage());
		return new ModelAndView("/money/tx","txPage",txPage);
	}
	
	@RequestMapping("/txmanage")
	public ModelAndView getTxdks(HttpServletRequest request,TxPage txPage){
		
		boolean isback=ParamerUtil.getIsBackPage(request);
		if(isback){
			txPage=(TxPage)request.getSession().getAttribute("txmanage");
		}else{
			request.getSession().setAttribute("txmanage", txPage);
		}
		if(txPage==null)
			txPage=new TxPage();
		
		TxParameter parameter=txPage.getParameter();
		if(parameter.getState()==-2){
			parameter.setState(1);
		}
		SysUserInfo sysUserInfo=ParamerUtil.getSysUserFromSesson(request);
		List<City> citys=sysUserInfo.getCitys();
		//List<Area> areas=new ArrayList<Area>();//sysUserInfo.getAreas();

		if (parameter.getUsercity() == 0) {
			if (citys == null || citys.size() == 0) {
				citys = new ArrayList<City>();
			} else if (citys.size() == 1) {
				txPage.getParameter().setUsercity(citys.get(0).getId());
		//		areas = sysUserInfo.getAreas();
			}
		} else {
			if (citys.size() == 1) {
			//	areas = sysUserInfo.getAreas();
			} else if (citys.size() > 1) {
				Map map = new HashMap();
				map.put("cityid", parameter.getUsercity());
			//	areas = areaService.getAreas(map);
			}
		}
		
		int adminType=ParamerUtil.getAdminType(sysUserInfo.getAdminpower());
		if(adminType==1){
			txPage.setAdmincity(citys.get(0).getCityid());
		}else if(adminType==2){
			txPage.setAdminareas(sysUserInfo.getAreas());
		}
		
		List<TxRecord> txrecords=txService.getTxs(txPage);
		txPage.setTxrecords(txrecords);
		List<Area> userareas=new ArrayList<Area>();
		List<City> usercitys=sysUserInfo.getCitys();
		if(adminType==1){
			txPage.setAdmincity(citys.get(0).getCityid());
			
			Map map=new HashMap();
			map.put("cityid", parameter.getUsercity());
			userareas=areaService.getAreas(map);
		}else if(adminType==2){
			txPage.setAdminareas(sysUserInfo.getAreas());
			userareas=sysUserInfo.getAreas();
		}
		
		txPage.setUsercitys(usercitys);
		txPage.setUserareas(userareas);
		List<UserType> usertypes=userService.getUserTypes();
		txPage.setUserTypes(usertypes);
		txPage.setAdminpower(sysUserInfo.getAdminpower());
		//设置页面
		PageUtil pageUtil = new PageUtil(txPage.getPageno(),
				txPage.getPageSize(), txPage.getPagerowtotal());
		txPage.setPageinfo(pageUtil.getPage());
		return new ModelAndView("/money/txmanage","txPage",txPage);
	}
	
	@RequestMapping("/getManagerDkImg")
	@ResponseBody
	public void getManagerDkImg(HttpServletRequest request,
			HttpServletResponse response,int id,String txid)  throws Exception {
	
	 ImageRenderer render = new ImageRenderer();
     System.out.println("kaishi");
     String url ="http://localhost/towersh/money/txdk.html?txid="+id+"&cutimg=1";
     String path = request.getSession().getServletContext()
				.getRealPath("/");
     if(path.endsWith("\\"))
     	path=path.substring(0,path.length()-1);
		path=path.substring(0,path.lastIndexOf('\\'));
     path=path+"/shanghai_towerfile/txdk/";
     String filename=id+".png";
     File file=new File(path,filename);
     if(!file.exists())
    	 file.getParentFile().mkdirs();
     FileOutputStream out = new FileOutputStream(file);
     render.renderURL(url, out, ImageRenderer.Type.PNG);
     InputStream in = null;
     if(out!=null)
    	 out.close();
     in = new FileInputStream(file);
    
     response.reset();
     filename="提现详情("+txid+").png";
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
	
	@RequestMapping("/getManagerInfoImg")
	@ResponseBody
	public void getManagerImg(HttpServletRequest request,
			HttpServletResponse response,int id,String txid)  throws Exception {
	
	 ImageRenderer render = new ImageRenderer();
     System.out.println("kaishi");
     String url ="http://localhost/towersh/money/tx_info.html?txid="+id+"&cutimg=1";
     String path = request.getSession().getServletContext()
				.getRealPath("/");
     if(path.endsWith("\\"))
     	path=path.substring(0,path.length()-1);
		path=path.substring(0,path.lastIndexOf('\\'));
     path=path+"/shanghai_towerfile/txdk/";
     String filename=id+".png";
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
     filename="提现详情("+txid+").png";
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
	
	@RequestMapping("/tx_info")
	public ModelAndView getTxInfo(HttpServletRequest request,
			String txid) {

		TxPage txPage=new TxPage();
		txPage.setTxid(txid);
		SysUserInfo sysUserInfo=ParamerUtil.getSysUserFromSesson(request);
		int admintype = ParamerUtil.getAdminType(sysUserInfo.getAdminpower());
		List<City> citys=sysUserInfo.getCitys();
		txPage.setAdminCitys(citys);
		// 区域经理
		if (admintype == 2) {
			txPage.setAdminareas( sysUserInfo.getAreas());
		}
		TxRecord txrecord = txService.getTx(txPage);
		if (txrecord == null) {
			return null;
		}
		TxPage txpage = new TxPage();
		txpage.setTxrecord(txrecord);
		return new ModelAndView("/money/tx_info", "txPage", txpage);
	}	
	
	@RequestMapping("/txdk")
	public ModelAndView getTxdkInfo(HttpServletRequest request,
			String txid) {
		TxPage txPage=new TxPage();
		txPage.setTxid(txid);
		SysUserInfo sysUserInfo=ParamerUtil.getSysUserFromSesson(request);
		int admintype = ParamerUtil.getAdminType(sysUserInfo.getAdminpower());
		List<City> citys=sysUserInfo.getCitys();
		txPage.setAdminCitys(citys);
		// 区域经理
		if (admintype == 2) {
			txPage.setAdminareas( sysUserInfo.getAreas());
		}
		TxRecord txrecord = txService.getTx(txPage);
		if (txrecord == null) {
			return null;
		}
		TxPage txpage = new TxPage();
		txpage.setTxrecord(txrecord);
		return new ModelAndView("/money/tx_dk", "txPage", txpage);
	}
	
	@RequestMapping("/txsh")
	public ModelAndView getTxshInfo(HttpServletRequest request,
			String txid) {
		TxPage txPage=new TxPage();
		txPage.setTxid(txid);
		SysUserInfo sysUserInfo=ParamerUtil.getSysUserFromSesson(request);
		int admintype = ParamerUtil.getAdminType(sysUserInfo.getAdminpower());
		List<City> citys=sysUserInfo.getCitys();
		txPage.setAdminCitys(citys);
		// 区域经理
		if (admintype == 2) {
			txPage.setAdminareas( sysUserInfo.getAreas());
		}
		TxRecord txrecord = txService.getTx(txPage);
		if (txrecord == null) {
			return null;
		}
		TxPage txpage = new TxPage();
		txpage.setTxrecord(txrecord);
		return new ModelAndView("/money/tx_sh", "txPage", txpage);
	}

	@RequestMapping("/tx_sh")
	@ResponseBody
	public Object txSh(HttpServletRequest request,String txid,int result,String shinfo,int fee){
		
		Map map=new HashMap();
		map.put("txid", txid);
		map.put("state", result);
		map.put("shinfo", shinfo);
		map.put("fee", fee);
		
		SysUserInfo admin=ParamerUtil.getSysUserFromSesson(request);
		map.put("adminid", admin.getId());
		int count=0;
		try {
			count =txService.shTx(map);
		} catch (Exception e) {
			
		}
		if(count==0)
			return ResultUtil.generateResponseMsg("error");
		
		return ResultUtil.generateResponseMsg("success");
	}
	@RequestMapping("/pl_tx_sh")
	@ResponseBody
	public Object plTxSh(HttpServletRequest request,String selecttxids){
		if(selecttxids==null||selecttxids.equals(""))
			return ResultUtil.generateResponseMsg("error");
		Map map=new HashMap();
		if(selecttxids.endsWith(","))
			selecttxids=selecttxids.substring(0,selecttxids.length()-1);
		String[] ids = selecttxids.split(",");
		map.put("txids", ids);
		map.put("state", 1);
		
		SysUserInfo admin=ParamerUtil.getSysUserFromSesson(request);
		map.put("adminid", admin.getId());
		int count=0;
		try {
			count =txService.plShTx(map);
		} catch (Exception e) {
			
		}
		if(count==0)
			return ResultUtil.generateResponseMsg("error");
		
		return ResultUtil.generateResponseMsg("success");
	}
	
	
	@RequestMapping("/tx_dk")
	@ResponseBody
	public Object txDk(HttpServletRequest request,String txid,int result,String shinfo,int fee){
		
		Map map=new HashMap();
		map.put("txid", txid);
		map.put("state", result);
		map.put("shinfo", shinfo);
		map.put("fee", fee);
		
		SysUserInfo admin=ParamerUtil.getSysUserFromSesson(request);
		map.put("adminid", admin.getId());
		int count=0;
		try {
			count =txService.dkTx(map);
		} catch (Exception e) {
			
		}
		if(count==0)
			return ResultUtil.generateResponseMsg("error");
		
		return ResultUtil.generateResponseMsg("success");
	}

	@RequestMapping("/h3")
	public ModelAndView getH3(){
	
		return new ModelAndView("/money/h3");
	}
	@RequestMapping("/tx_pl_insert")
	public ModelAndView getPl(){
	
		return new ModelAndView("/money/tx_pl_insert");
	}
	
	@RequestMapping("/tx_dr")
	@ResponseBody
	public Object txDR(HttpServletRequest request){
		String filepath = "\\tmp\\excel\\";
		String excel = FileUpload.uploadFile_excel(request, filepath,
				"file");
		if (excel == null)
			return ResultUtil.generateResponseMsg("error");
		int count = 0;
		List<Map> txs = null;
		SysUserInfo adminInfo = ParamerUtil.getSysUserFromSesson(request);
		try {
			txs = ExcelHelper.getTxRecode(excel);
		}catch(Exception e){
			
			return ResultUtil.generateResponseMsg("error");
		}
		SysUserInfo admin=ParamerUtil.getSysUserFromSesson(request);
		 count=txService.dkTx(txs, admin.getId());
		 if(count==0)
				return ResultUtil.generateResponseMsg("error");
			
			return ResultUtil.generateResponseMsg("success");
		
		
	}
	
	
	@RequestMapping("/dc_moneys")
	@ResponseBody
	public void dcMoney(HttpServletRequest request,
			HttpServletResponse response, TxPage txPage)  throws Exception {
		SysUserInfo sysUserInfo=ParamerUtil.getSysUserFromSesson(request);
		List<City> citys=sysUserInfo.getCitys();
		txPage.setAdminCitys(citys);
		String filename="";
		ExcelDC ecDc = new ExcelDC(request, "tx_aply.xls");
		try {
			if (txPage.getParameter().getUsername() != null) {
				txPage.getParameter().setUsername(new String(txPage.getParameter().getUsername().getBytes("ISO8859-1"), "utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
		}
		
		try {
			if (txPage.getParameter().getTowername() != null) {
				txPage.getParameter().setTowername(new String(txPage.getParameter().getTowername().getBytes("ISO8859-1"), "utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
		}
		List<Map> list = txService.getDcTxApplys(txPage);
		HSSFWorkbook workbook = ecDc.getTxApplys(list);
		 ByteArrayOutputStream os = new ByteArrayOutputStream();

	        try {
	        	workbook.write(os);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		  byte[] content = os.toByteArray();
	        InputStream is = new ByteArrayInputStream(content);

	       filename=new String(("提现申请("+TimeUtil.getNowDay()+").xls").getBytes(), "iso-8859-1");
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
	
	
	@RequestMapping("/dc_txmanagers")
	@ResponseBody
	public void dcTxmanagerMoney(HttpServletRequest request,
			HttpServletResponse response, TxPage txPage)  throws Exception {
		SysUserInfo sysUserInfo=ParamerUtil.getSysUserFromSesson(request);
		List<City> citys=sysUserInfo.getCitys();
		txPage.setAdminCitys(citys);
		String filename="";
		ExcelDC ecDc = new ExcelDC(request, "tx_manager.xls");
		try {
			if (txPage.getParameter().getUsername() != null) {
				txPage.getParameter().setUsername(new String(txPage.getParameter().getUsername().getBytes("ISO8859-1"), "utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
		}
		try {
			if (txPage.getParameter().getTowername() != null) {
				txPage.getParameter().setTowername(new String(txPage.getParameter().getTowername().getBytes("ISO8859-1"), "utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
		}
		List<Map> list = txService.getDcTxs(txPage);
		HSSFWorkbook workbook = ecDc.getTxManagers(list);
		 ByteArrayOutputStream os = new ByteArrayOutputStream();

	        try {
	        	workbook.write(os);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		  byte[] content = os.toByteArray();
	        InputStream is = new ByteArrayInputStream(content);

	       filename=new String(("提现管理("+TimeUtil.getNowDay()+").xls").getBytes(), "iso-8859-1");
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
