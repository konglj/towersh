package com.tower.common.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.rtf.RTFEditorKit;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.tower.common.Config;
import com.tower.common.WeiXinConfig;
import com.tower.common.bean.Area;
import com.tower.common.bean.City;
import com.tower.common.bean.SysUserInfo;
import com.tower.common.wxmsg.Token;
import com.tower.common.wxmsg.WXCommon;


public class ParamerUtil {

	public static SysUserInfo getSysUserFromSesson(HttpServletRequest request) {

		return (SysUserInfo) request.getSession().getAttribute("sysuser");
	}

	public static boolean getIsBackPage(HttpServletRequest request) {
		if (request.getParameter("isback") == null
				|| request.getParameter("isback").equals(""))
			return true;

		if (request.getParameter("isback").equals("0"))
			return false;
		return true;
	}

	public static void setSysUserInSesson(HttpServletRequest request,
			SysUserInfo sysUserInfo) {
		request.getSession().setAttribute("sysuser", sysUserInfo);
	}

	public static List<City> getSysUserCity(HttpServletRequest request) {
		SysUserInfo user = (SysUserInfo) request.getSession().getAttribute(
				"sysuser");
		if (user != null)
			return user.getCitys();
		return null;
	}

	public static List<Area> getSysUserArea(HttpServletRequest request) {
		SysUserInfo user = (SysUserInfo) request.getSession().getAttribute(
				"sysuser");
		if (user != null)
			return user.getAreas();
		return null;
	}

	public static int getAdminType(int power) {
		if (power == 1 || power == 6 || power == 8 )
			return 0;
		else if (power == 3)
			return 2;
		return 1;
	}

	public static Integer[] getAdminOrder(int power) {
		if (power == 1 || power == 6 || power == 8 || power == 11)
			return new Integer[] { -1 };
		if (power == 2)
			return new Integer[] { 2, 4, 5, 10,13,15, 16, 17, 20, 21, 22 };
		if (power == 3)
			return new Integer[] { 2, 4, 5, 10, 13, 15, 22 };
		if (power == 4)
			return new Integer[] { 7 };

		if (power == 12)
			return new Integer[] { 2, 4, 5, 10, 13, 15, 16, 17, 20, 21, 22 };

		if (power == 13)
			return new Integer[] { 2, 4, 5, 7, 10, 13, 15, 22 };

		if (power == 14)
			return new Integer[] { 2, 4, 5, 7, 10,13,15, 16, 17, 20, 21, 22 };

		if (power == 15)
			return new Integer[] { 2, 4, 5, 7, 10, 13, 15, 16, 17, 20, 21, 22 };

		return new Integer[] { -1 };
	}

	public static int getUpdateOrderState(int oldstate, int result) {

		int state = -1;
		switch (oldstate) {

		case 0:
			if (result == 0)
				state = 3;
			else
				state = 0;
			break;

		case 2:
			if (result == 0)
				state = 3;
			else
				state = 4;
			break;
		case 4:
			if (result == 0)
				state = 23;
			else
				state = 5;
			break;

		case 5:
			if (result == 0)
				state = 6;
			else
				state = 7;
			break;
		case 7:
			if (result == 0)
				state = 22;
			else
				state = 4;
			break;

		case 8:
			if (result == 0)
				state = 10;
			else
				state = 9;
			break;

		case 10:
			if (result == 0)
				state = 23;
			break;
		case 12:
		case 14:
			if (result == 0)
				state = 3;
			break;
		case 13:
			/*
			if (result == 0)
				state = 20;
			else
				state = 21;
			*/
			if (result == 0)
				state = 14;
			else
				state = 12;
			break;
		case 15:
			if (result == 0)
				state = 18;
			else
				state = 19;	
			break;
		case 16:
		case 17:
			if (result == 0)
				state = 18;
			else
				state = 19;
			break;
		case 19:
			if (result == 0)
				state = 3;
			break;
		case 20:
		case 21:
			if (result == 0)
				state = 14;
			else
				state = 12;
			break;
		case  22:
			if(result==0)
				state=6;

		default:
			break;
		}
		return state;

	}

	public int getCanQDCount(int level) {
		int count = 0;
		switch (level) {
		case 1:
			count = 2;
			break;
		case 2:
			count = 3;
			break;
		case 3:
			count = 4;
			break;
		case 4:
			count = 5;
			break;
		case 5:

			break;

		default:
			break;
		}
		return count;

	}

	public static int getUserLevel(int score, int ep) {
		if (score < 450 || ep < 4)
			return 1;

		if (score >= 450 && score < 900) {
			if (ep >= 4)
				return 2;
			else
				return 1;
		}
		if (score >= 900 && score < 1400) {
			if (ep >= 8)
				return 3;
			else
				return 2;
		}
		if (score >= 1400) {
			if (ep >= 12)
				return 4;
			else
				return 3;
		}

		return 0;
	}

	public static Map<String, Integer> getTowerLevelMap() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("一级站址", 1);
		map.put("二级站址", 2);
		map.put("三级站址", 3);
		map.put("四级站址", 4);
		return map;
	}

	public static List<Integer> getAreaids(List<Area> areas) {
		List<Integer> list = new ArrayList<Integer>();
		for (Area area : areas) {
			list.add(area.getId());
		}

		return list;
	}

	public static boolean isExcel2003(String filePath) {
		try {
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(filePath));
			if (POIFSFileSystem.hasPOIFSHeader(bis)) {
				System.out.println("Excel版本为excel2003及以下");
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public static boolean isArea(List<City> cities, int areaid) {
		for (City city : cities) {
			if (city.getId() == areaid)
				return true;
		}
		return false;
	}

	public static boolean isJieDao(List<Area> jiedaos, int areaid) {
		for (Area area : jiedaos) {
			if (area.getId() == areaid)
				return true;
		}
		return false;
	}

	public static Integer[] getIngOrderStates() {
		Integer[] ing = { 0, 2, 4, 5, 7, 8, 10, 12, 13, 14, 15, 16, 17, 19, 20,
				21, 22 };
		return ing;
	}

	public static Integer[] getSuccessOrderStates() {
		Integer[] suc = { 9 };
		return suc;
	}

	public static int xihuaPower(int power, int state) {
		boolean isXZ = false;
		boolean isQY = false;
		boolean isJW = false;

		switch (state) {
		case 3:
			isXZ = true;
			isQY = true;
			break;
		case 4:
			isJW = true;
			break;
		case 5:
			isXZ = true;
			isQY = true;
			break;

		case 6:
			isXZ = true;
			isQY = true;
			break;
		case 7:
			isXZ = true;
			isQY = true;
			break;
		case 8:
			isXZ = true;
			isQY = true;
			break;
		case 9:
			isJW = true;
			break;
		case 10:
			isJW = true;
			break;
		case 12:
			isXZ = true;
			break;
		case 14:
			isXZ = true;
			break;

		case 16:
			isQY = true;
			break;
		case 17:
			isQY = true;
			break;
		case 18:
			isXZ = true;
			break;
		case 19:
			isXZ = true;
			break;
		case 20:
			isQY = true;
			break;
		case 21:
			isQY = true;
			break;
		case 22:
			isJW = true;
			break;
		case 23:
			isXZ = true;
			isQY = true;
			break;
		case 24:
			isXZ = true;
			isQY = true;
			break;

		}
		int xhpower = power;
		switch (power) {
		case 12:
			if (isQY)
				xhpower = 3;
			else if (isXZ)
				xhpower = 2;
			break;
		case 13:
			if (isQY)
				xhpower = 3;
			if (isJW)
				xhpower = 4;
			break;
		case 14:
			if (isXZ || isQY)
				xhpower = 2;
			if (isJW)
				xhpower = 4;
		case 15:
			if (isQY)
				xhpower = 3;
			else if (isXZ)
				xhpower = 2;
			else if (isJW)
				xhpower = 4;

			break;

		default:
			break;
		}
		return xhpower;

	}

	public static boolean isUpdateTowerLevel(String time) {
		if(time==null||time.equals(""))
			return false;
		long t = Timestamp.valueOf(time).getTime();
		long n = System.currentTimeMillis();
		if (n - t >= Config.unordertowerday * 24 * 60 * 60 * 1000) {
			return true;
		}
		

		return false;

	}
	
	
	
	private static boolean checkWeiXinTokenIndate(){
		if(WeiXinConfig.getWxtoken()==null||WeiXinConfig.getWxtoken().equals("")){
			return false;
		}
		if(WeiXinConfig.getWxtokentime()==0||System.currentTimeMillis()-WeiXinConfig.getWxtokentime()>=1.5*60*60*1000){
			return false;
		}
		return true;
	}
	/**
	 * 微信TOKEN  注意失效  注意访问次数
	 * @return
	 */
	public static String getWeiXinToken(){
		Token token=null;
		if(!checkWeiXinTokenIndate()){
			token=WXCommon.getToken();
			if(token!=null)
			WeiXinConfig.setWxtoken(token.getAccessToken());
			WeiXinConfig.setWxtokentime(System.currentTimeMillis());
			return token.getAccessToken();
		}
		return WeiXinConfig.getWxtoken();
	}
	
}
