package com.tower.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tower.common.bean.Area;
import com.tower.common.bean.City;
import com.tower.common.bean.SysUserInfo;
import com.tower.common.bean.TxPage;
import com.tower.common.bean.TxRecord;
import com.tower.common.bean.UserInfo;
import com.tower.common.bean.UserManagePage;
import com.tower.common.bean.UserMoney;
import com.tower.common.bean.UserMoneyPage;
import com.tower.common.bean.UserParameter;
import com.tower.common.bean.UserType;
import com.tower.common.util.ExcelDC;
import com.tower.common.util.FileUtil;
import com.tower.common.util.PageUtil;
import com.tower.common.util.ParamerUtil;
import com.tower.common.util.ResultUtil;
import com.tower.common.util.TimeUtil;
import com.tower.common.util.WeiXinApi;
import com.tower.common.wxmsg.WXCommon;
import com.tower.service.AreaService;
import com.tower.service.UserService;

@Controller
@RequestMapping("/user")
public class UserWeb {

	@Autowired
	private UserService userService;

	@Autowired
	private AreaService areaService;

	@RequestMapping("/usermanage")
	public ModelAndView getUsers(HttpServletRequest request,
			UserManagePage userManagePage) {

		boolean isback = ParamerUtil.getIsBackPage(request);
		if (isback) {
			userManagePage = (UserManagePage) request.getSession()
					.getAttribute("usermanage");
		} else {
			request.getSession().setAttribute("usermanage", userManagePage);
		}

		if (userManagePage == null)
			userManagePage = new UserManagePage();

		SysUserInfo sysUserInfo = ParamerUtil.getSysUserFromSesson(request);
		List<City> usercitys = sysUserInfo.getCitys();
		userManagePage.setAdminCitys(usercitys);
		int admintype = ParamerUtil.getAdminType(sysUserInfo.getAdminpower());
		if (admintype == 0)
			userManagePage.setAdminCitys(null);

		List<UserInfo> userinfos = userService.getUserInfos(userManagePage);
		userManagePage.setUserinfos(userinfos);
		userManagePage.setUsercitys(usercitys);

		List<UserType> usertypes = userService.getUserTypes();
		userManagePage.setUserTypes(usertypes);

		userManagePage.setAdmimPower(sysUserInfo.getAdminpower());

		// 设置页面
		PageUtil pageUtil = new PageUtil(userManagePage.getPageno(),
				userManagePage.getPageSize(), userManagePage.getPagerowtotal());
		userManagePage.setPageinfo(pageUtil.getPage());
		return new ModelAndView("/user/usermanage", "userManagePage",
				userManagePage);
	}

	@RequestMapping("/usermoney")
	public ModelAndView getUserMoneys(HttpServletRequest request,
			UserMoneyPage userMoneyPage) {
		boolean isback = ParamerUtil.getIsBackPage(request);
		if (isback) {
			userMoneyPage = (UserMoneyPage) request.getSession().getAttribute(
					"usermoney");
		} else {
			request.getSession().setAttribute("usermoney", userMoneyPage);
		}
		if (userMoneyPage == null)
			userMoneyPage = new UserMoneyPage();

		SysUserInfo sysUserInfo = ParamerUtil.getSysUserFromSesson(request);

		List<City> usercitys = sysUserInfo.getCitys();
		userMoneyPage.setAdminCitys(usercitys);
		int admintype = ParamerUtil.getAdminType(sysUserInfo.getAdminpower());
		if (admintype == 0)
			userMoneyPage.setAdminCitys(null);

		List<UserMoney> usermoneys = userService.getUserMoneys(userMoneyPage);
		userMoneyPage.setUsermoneys(usermoneys);
		userMoneyPage.setUsercitys(usercitys);
		List<UserType> usertypes = userService.getUserTypes();
		userMoneyPage.setUserTypes(usertypes);

		// 设置页面
		PageUtil pageUtil = new PageUtil(userMoneyPage.getPageno(),
				userMoneyPage.getPageSize(), userMoneyPage.getPagerowtotal());
		userMoneyPage.setPageinfo(pageUtil.getPage());
		return new ModelAndView("/user/usermoney", "userMoneyPage",
				userMoneyPage);
	}

	@RequestMapping("/user_info")
	public ModelAndView getUserInfo(HttpServletRequest request, String wxid) {

		UserInfo userinfo = userService.getUserdetail(wxid);
		if (userinfo == null) {
			return null;
		}
		UserManagePage usermanagePage = new UserManagePage();
		usermanagePage.setUserinfo(userinfo);
		return new ModelAndView("/user/user_info", "usermanagePage",
				usermanagePage);
	}

	@RequestMapping("/updatestate")
	@ResponseBody
	public Object updateState(HttpServletRequest request, String wxid, int state) {

		Map map = new HashMap();
		map.put("wxid", wxid);
		map.put("state", state);
		int count = 0;
		try {
			count = userService.updateuserstate(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (count == 0)
			return ResultUtil.generateResponseMsg("error");
		return ResultUtil.generateResponseMsg("success");
	}

	@RequestMapping("/updateveritystate")
	@ResponseBody
	public Object updateVerityState(HttpServletRequest request, String wxid) {

		Map map = new HashMap();
		map.put("wxid", wxid);
		map.put("verifystate", 1);
		int count = 0;
		try {
			count = userService.updateuserstate(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (count == 0)
			return ResultUtil.generateResponseMsg("error");
		//发送推送消息
		try {
			WXCommon.sendVerityMsg(wxid);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultUtil.generateResponseMsg("success");
	}

	@RequestMapping("/modifytype")
	public ModelAndView modifytype(HttpServletRequest request, String wxid) {

		UserInfo userinfo = userService.getUserdetail(wxid);
		if (userinfo == null) {
			return null;
		}

		UserManagePage usermanagePage = new UserManagePage();
		List<UserType> usertypes = userService.getUserTypes();
		usermanagePage.setUserTypes(usertypes);

		usermanagePage.setUserinfo(userinfo);
		return new ModelAndView("/user/modifytype", "usermanagePage",
				usermanagePage);
	}

	@RequestMapping("/modifybz")
	public ModelAndView modifyBz(HttpServletRequest request, String wxid) {

		UserInfo userinfo = userService.getUserdetail(wxid);
		if (userinfo == null) {
			return null;
		}
		UserManagePage usermanagePage = new UserManagePage();
		usermanagePage.setUserinfo(userinfo);
		return new ModelAndView("/user/modifybz", "usermanagePage",
				usermanagePage);
	}

	@RequestMapping("/changetype")
	@ResponseBody
	public Object changestate(HttpServletRequest request, String wxid,
			String usertype) {

		Map map = new HashMap();
		map.put("wxid", wxid);
		map.put("usertype", usertype);
		int count = 0;
		try {
			count = userService.updateusertype(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (count == 0)
			return ResultUtil.generateResponseMsg("error");
		return ResultUtil.generateResponseMsg("success");
	}

	@RequestMapping("/changebz")
	@ResponseBody
	public Object changebz(HttpServletRequest request, String wxid,
			String userbz) {

		Map map = new HashMap();
		map.put("wxid", wxid);
		map.put("userbz", userbz);
		int count = 0;
		try {
			count = userService.updateUserBz(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (count == 0)
			return ResultUtil.generateResponseMsg("error");
		return ResultUtil.generateResponseMsg("success");
	}

	@RequestMapping("/h4")
	public ModelAndView getH4() {

		return new ModelAndView("/user/h4");
	}

	@RequestMapping("/dc_users")
	@ResponseBody
	public void dcUser(HttpServletRequest request,
			HttpServletResponse response, UserManagePage usermanagepage)
			throws Exception {
		SysUserInfo sysUserInfo = ParamerUtil.getSysUserFromSesson(request);
		List<City> usercitys = sysUserInfo.getCitys();
		usermanagepage.setAdminCitys(usercitys);
		String filename = "";
		ExcelDC ecDc = new ExcelDC(request, "user_info.xls");
		try {
			if (usermanagepage.getParameter().getUsername() != null) {
				usermanagepage.getParameter().setUsername(
						new String(usermanagepage.getParameter().getUsername()
								.getBytes("ISO8859-1"), "utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
		}
		try {
			if (usermanagepage.getParameter().getUserbz() != null) {
				usermanagepage.getParameter().setUserbz(
						new String(usermanagepage.getParameter().getUserbz()
								.getBytes("ISO8859-1"), "utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
		}
		List<Map> list = userService.getDcUser(usermanagepage);
		HSSFWorkbook workbook = ecDc.getUsers(list);
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		try {
			workbook.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);

		filename = new String(("用户信息("+TimeUtil.getNowDay()+").xls").getBytes(), "iso-8859-1");
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

	@RequestMapping("/dc_user_image")
	@ResponseBody
	public void dcUserImage(HttpServletRequest request,
			HttpServletResponse response, UserManagePage usermanagepage)
			throws Exception {

		try {
			if (usermanagepage.getParameter().getUsername() != null) {
				usermanagepage.getParameter().setUsername(
						new String(usermanagepage.getParameter().getUsername()
								.getBytes("ISO8859-1"), "utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
		}
		try {
			if (usermanagepage.getParameter().getUserbz() != null) {
				usermanagepage.getParameter().setUserbz(
						new String(usermanagepage.getParameter().getUserbz()
								.getBytes("ISO8859-1"), "utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
		}
		List<UserInfo> list = userService.getDcUserImage(usermanagepage);
		String zipFileName = "";
		File file=null;
		try {

			// 将图片备份到指定位置
			String path = request.getSession().getServletContext()
					.getRealPath("/");
			if (path.endsWith("\\"))
				path = path.substring(0, path.length() - 1);
			path = path.substring(0, path.lastIndexOf('\\'));
			String fromPath = path;
			path = path + "\\shanghai_towerfile\\tmp\\"
					+ UUID.randomUUID().toString().replace("-", "") + "\\";
			 file = new File(path);

			boolean result = false;
			if (!file.exists() && !file.isDirectory()) {
				result = file.mkdirs();
			}
			String toPath = path;
			String filename = "";
			String fromfie = "";

			File imageFile = null;
			File toFile = null;
			for (UserInfo userInfo : list) {
				if (userInfo.getUserteamimage() != null) {
					filename = path + userInfo.getUsername() + "-"
							+ userInfo.getUsertele() + "-"
							+ userInfo.getUsercompany();
					fromfie = fromPath + userInfo.getUserteamimage();
					imageFile = new File(fromfie);
					if (imageFile.exists() && imageFile.isFile()) {
						toFile = new File(filename
								+ fromfie.substring(fromfie.lastIndexOf(".")));
						if (toFile != null && !toFile.exists())
							toFile.createNewFile();
						FileUtil.fileCopyForTransfer(imageFile, toFile);
					}
				}
			}
			zipFileName = path + "证件.zip"; // 打包后文件名字
			FileUtil.zip(zipFileName, path);
		} catch (Exception e) {
		}

		BufferedInputStream is = new BufferedInputStream(new FileInputStream(
				zipFileName));

		if (is != null) {
			String filename = new String(("证件("+TimeUtil.getNowDay()+").zip").getBytes(), "iso-8859-1");
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
			
			//删除文件
			try {
				FileUtil.deleteDir(file);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	@RequestMapping("/dc_fee")
	@ResponseBody
	public void dcFee(HttpServletRequest request, HttpServletResponse response,
			UserMoneyPage userMoneyPage) throws Exception {
		SysUserInfo sysUserInfo = ParamerUtil.getSysUserFromSesson(request);

		List<City> usercitys = sysUserInfo.getCitys();
		userMoneyPage.setAdminCitys(usercitys);
		String filename = "";
		ExcelDC ecDc = new ExcelDC(request, "fee.xls");
		try {
			if (userMoneyPage.getParameter().getUsername() != null) {
				userMoneyPage.getParameter().setUsername(
						new String(userMoneyPage.getParameter().getUsername()
								.getBytes("ISO8859-1"), "utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
		}
		List<Map> list = userService.getDcUserFee(userMoneyPage);
		HSSFWorkbook workbook = ecDc.getUsersFee(list);
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		try {
			workbook.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);

		filename = new String(("用户钱包("+TimeUtil.getNowDay()+").xls").getBytes(), "iso-8859-1");
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

}
