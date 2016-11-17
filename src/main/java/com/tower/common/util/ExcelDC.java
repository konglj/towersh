package com.tower.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class ExcelDC {
	
	private String path;
	public ExcelDC(HttpServletRequest request,String filename){
		 path = request.getSession().getServletContext().getRealPath("/")
				+ "excel_template\\"+filename;
	
	}
	
	/*
	 *path 模板路径、list数据源 
	 */
	
	
	public  HSSFWorkbook getTowers(List<Map> list){
	String[] headers = { "towerid", "toweridefined", "towername", "towerwhoyd","towerwholt","towerwhodx","cityname","areaname","toweraddress","towerlevelname",
			"towerfee","towerrentfee","towerstatename","towertypename","towerstorename","towercreatetypename","towerlarge","towerj","towerfirstj","towersecondj","towermanagername","towermanager","towerinfo","toweradddate"};
	HSSFWorkbook wb = ExcelHelper.exportExcel(path, headers,list);
	
	return wb;
	}
	public  HSSFWorkbook getOrders(List<Map> list){
		String[] headers = {"orderid", "username", "usertele", "userlevelname", "usercityname","usercompany","usertype","userbusiness","userwintype","userwintime","towername","toweridefined","towerwhoyd","towerwholt","towerwhodx","towercityname",
				"towerareaname","toweraddress","towerlevelname","towertypename","towerstorename","towercreatetypename","towerlarge","towerj","towerw","towermanagername","towermanager","towerinfo","towerstatename","towerfee","towerfactfee","towerrentfee",
				"towerfactrentfee","towertargetaddress","towerfactaddress","towerpowertype","towerpowerprice","towerpowert","toweryzname","toweryzphone","towerprojectno","towerprojectname","toweraddtime"};
		HSSFWorkbook wb = ExcelHelper.exportExcel(path, headers,list);
		
		return wb;
		}
	
	public  HSSFWorkbook getMoneys111(List<Map> list){
		String[] headers = { "username", "usertele", "levelname", "cityname","areaname","typename","charge","gettingnow","getnow",
				"allgeting","bankaccount","bankname","bankopen","bankcardid","fee","statename","bz","adddate"};
		HSSFWorkbook wb = ExcelHelper.exportExcel(path, headers,list);
		
		return wb;
		}
	
	public  HSSFWorkbook getTxApplys(List<Map> list){
		String[] headers = {"txid","username","usertele","towername","towerid","cityname","areaname","fee","towerprojectno","towerprojectname","adddate"};
		HSSFWorkbook wb = ExcelHelper.exportExcel(path, headers,list);
		
		return wb;
		}
	
	
	public  HSSFWorkbook getMoneys(List<Map> list){
		String[] headers = {"txid","bankaccount","bankname","bankopen","bankcardid","fee","cityname","bankbackid"};
		HSSFWorkbook wb = ExcelHelper.exportExcel(path, headers,list);
		
		return wb;
		}
	
	public  HSSFWorkbook getTxManagers(List<Map> list){
		String[] headers = {"txid","bankaccount","bankname","bankopen","bankcardid","fee","factfee","cityname","bankbackid"};
		HSSFWorkbook wb = ExcelHelper.exportExcel(path, headers,list);
		
		return wb;
		}
	
	

	public  HSSFWorkbook getUsers(List<Map> list){
		String[] headers = { "username","usersex" ,"usertele","wxname","cityname","usercompany","userwintype","userwintime","userbusiness","statename","levelname", "typename","teamtypename","verifystatename","userteamimg","addtime"};
		HSSFWorkbook wb = ExcelHelper.exportExcel(path, headers,list);
		
		return wb;
		}
	
	public  HSSFWorkbook getUsersFee(List<Map> list){
		String[] headers = { "username","usertele","cityname","levelname", "typename","addtime","getnow","gettingnow","allgeting","score","exp"};
		HSSFWorkbook wb = ExcelHelper.exportExcel(path, headers,list);
		
		return wb;
		}
	
	
	public  HSSFWorkbook getDataTowers(List<Map> list){
		String[] headers = { "towername","towerid","statename","towervisible","cityname", "areaname", "towerlevel","towerstorename","towercreatetypename","towerfee","towerrentfee","cancelcount","rejectcount","selecount",
				"viewcount","timecount"};
		HSSFWorkbook wb = ExcelHelper.exportExcel(path, headers,list);
		
		return wb;
		}
	
	public  HSSFWorkbook getDataTowerStates(List<Map> list){
		String[] headers = { "cityname","towervisisblecount","towerqdcount","towerunqdcount","ordertargetaddresscount", "orderjdcount", "ordershingcount","ordercompletecount","txsuccesscount"};
		HSSFWorkbook wb = ExcelHelper.exportExcel(path, headers,list);
		
		return wb;
		}
	

	public  HSSFWorkbook getDataUsers(List<Map> list){
		String[] headers = { "username","usertele","cityname", "gzcount","dqcount","succcount","rejectcount","cancelcount",
				"timecount"};
		HSSFWorkbook wb = ExcelHelper.exportExcel(path, headers,list);
		
		return wb;
		}
	public  HSSFWorkbook getDataUserScources(List<Map> list){
		String[] headers = { "source","info","count"};
		HSSFWorkbook wb = ExcelHelper.exportExcel(path, headers,list);
		
		return wb;
		}
	
	
	public  HSSFWorkbook getReportOrders(List<Map> list){
		String[] headers = {"orderid", "username", "usertele", "userlevelname", "usercityname","usercompany","userwintype","userwintime","towername","toweridefined","towerwhoyd","towerwholt","towerwhodx","towercityname",
				"towerareaname","toweraddress","towerlevelname","towertypename","towerstorename","towerlarge","towerj","towerw","towermanagername","towermanager","towerinfo","towerstatename","towerfee","towerfactfee","towerrentfee",
				"towerfactrentfee","towertargetaddress","towerfactaddress","towerpowertype","towerpowerprice","towerpowert","toweryzname","toweryzphone","orderiseffectname","toweraddtime","towerfactaddresstime",
				"towerfactaddressshtime","towerposttime","towerrenttime","towerthreetime","towerfktime","towercompletetime","towerfirstyq","towerfirstyqsh","towersecondyq","towersecondyqsh"
				};
		HSSFWorkbook wb = ExcelHelper.exportExcel(path, headers,list);
		
		return wb;
		}
	
	public  HSSFWorkbook getReportTxs(List<Map> list){
		String[] headers = {"txid", "username", "bankname", "bankopen", "bankcardid","bankbackid","orderid","towerid","towername",
				"towercityname","towerareaname","fee","adddate","statename"};
		HSSFWorkbook wb = ExcelHelper.exportExcel(path, headers,list);
		
		return wb;
		}
	
	public  HSSFWorkbook getReportUsers(List<Map> list){
		String[] headers = {"wxname", "username", "usersex", "usertele", "usercityname","usercompany","userwintype","userwintime","userlevel","usertype",
				"favouritecount","ordercount","succcount","failcount","cancelcount","ingcount","getnow","gettingnow","useraddtime"};
		HSSFWorkbook wb = ExcelHelper.exportExcel(path, headers,list);
		
		return wb;
		}
	
	
	public  HSSFWorkbook getTowerOrderHistory(List<Map> list){
		String[] headers = { "orderid", "username", "usertele", "usercityname","userareaname","towername","towerstatename","toweraddtime"};
		HSSFWorkbook wb = ExcelHelper.exportExcel(path, headers,list);
		
		return wb;
		}
}
