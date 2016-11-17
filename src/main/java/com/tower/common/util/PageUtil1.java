package com.tower.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * 分页信息
 */
public class PageUtil1 {

	// 默认页数大小
	public final static int DEFAULT_PAGE_SIZE = 10;
	// 每组显示的页数
	public final static int DEFAULT_PAGES_PER_GROUP = 10;
	// 页面大小
	private int pageSize = DEFAULT_PAGE_SIZE;
	// 当前页
	private int pageNo = 1;
	// 记录总条数
	private int total = 0;
	// 总页数
	private int totalPage;
	// 查询Url
	private String url;
	// 查询参数
	private String params;
	// form 条件
	private String formStr;
	// priva
	private String pageInfo;
	
	
	
	/**
	 * 
	 * @param request
	 * @param pageNo
	 *            当前页
	 * @param total
	 *            记录总数
	 */
	public PageUtil1(HttpServletRequest request,int pageSize, int total) {
		this.pageSize = pageSize;
		this.total = total;
		//this.pageNo = pageNo;
		this.url = request.getRequestURI();
		intQueryParams(url, request);
		createPageInfo();
	}

	/**
	 * 
	 */
	public PageUtil1(int total, int pageNo) {

	}
	/**
	 * 
	 * @param request
	 * @param pageNo2
	 * @param pageSize2
	 * @param tatol
	 */
	public PageUtil1(HttpServletRequest request, int pageNo, int pageSize,
			int total) {
		this.pageSize = pageSize;
		this.total = total;
		//this.pageNo = pageNo;
		this.url = request.getRequestURI();
		intQueryParams(url, request);
	}

	/**
	 * 总页数
	 * 
	 * @return
	 */
	public int getTotalPage() {
		int tp = this.total / this.pageSize;
		if((this.total % this.pageSize)!=0){
			tp+=1;
		} 
		return tp;
	}

	/**
	 * 
	 * 是否还有下一页
	 * 
	 * @return
	 */
	public boolean hasNextPage() {
		return this.pageNo < this.getTotalPage();
	}

	/**
	 * 
	 * 是否有上一页
	 * 
	 * @return
	 */
	public boolean hasPrevPage() {
		return this.pageNo > 1;
	}

	public String createPageInfo() {
		StringBuffer html = new StringBuffer();
		html.append("<form id=\"pageFrm\" action=\"" + url + "\" method=\"post\" onsubmit =\"submitPage()\" >");
		html.append("<div style=\"position: absolute; right: 20px;\">");
		
		//跳转
		html.append("<div style=\"float: right; position: relative; margin-left: 5px;\">");
		html.append("<script> function submitPage(){" +
					"if($('#pageNo').val() >"+this.getTotalPage()+"){" +
					"$('#pageNo').val("+this.getTotalPage()+")" +
					"" +
					"}" +
				"} </script>");
		html.append(this.formStr);
		html.append("&nbsp;共<em class=\"value\">"+this.getTotalPage()+"</em>页&nbsp;&nbsp;");
		html.append("<span class=\"pager-jump\"><span>到</span><input type=\"text\" size=\"2\" id='pageNo' name=\"pageNo\" class=\"input\" value=\"\" " +
				" onkeyup=\"this.value=this.value.replace(/\\D/g,'')\" onafterpaste=\"this.value=this.value.replace(/\\D/g,'')\" ><span>页</span></span>");
		html.append("<button type=\"submit\" style=\"border-color: rgb(111, 179, 224); background-color: rgb(79, 153, 198);\">确定</button>");
		
		html.append("</div>");
		html.append("<div style=\"float: right; position: relative; margin-left: 5px;\"><ul class=\"pagination\">");
		// 上一页
		if (this.hasPrevPage()) {
			html.append("<li><a href=\"").append(url);
			html.append("?");
			if (!"".equals(this.params)) {
				html.append(this.params);
				html.append("&");
			}
			html.append("&pageNo=" + (this.pageNo - 1));
			html.append("\"><i class=\"icon-double-angle-left\"></i></a></li>");
		}else{
			 
			html.append("<li class=\"disabled\"><a href=\"#\"> <i class=\"icon-double-angle-left\"></i> </a></li>");
		}
		//第一页
		if(this.pageNo==1){
			html.append("<li class=\"active\"><a href=\"#\">1</a></li>");
		}else{
			html.append("<li><a href=\"").append(url);
			html.append("?");
			if (!"".equals(this.params)) {
				html.append(this.params);
				html.append("&");
			}
			html.append("pageNo=1");
			html.append("\">1</a></li>");
		}
		// 页面
		if (this.pageNo >= 6) {
			html.append("<li class=\"disabled\">...</li>");
			int n = 1;
			int tmp = this.pageNo-4;	
			while (n <= 8 && (tmp < this.getTotalPage())) {
				n++;
				tmp++;
				if(this.pageNo==tmp){
					html.append("<li class=\"active\"><a href=\"#\">"+tmp+"</a></li>");
				}else{
					html.append("<li><a href=\"").append(url);
					html.append("?");
					if (!"".equals(this.params)) {
						html.append(this.params);
						html.append("&");
					}
					html.append("pageNo=" + tmp);
					html.append("\">" + tmp + "</a></li>");
				}
			}
			
		} else {
			int n = 1;
			while (n <= 8 && (n < this.getTotalPage())) {
				n++;
				if(this.pageNo==n){
					html.append("<li class=\"active\"><a href=\"#\">"+n+"</a></li>");
				}else{
					html.append("<li><a href=\"").append(url);
					html.append("?");
					if (!"".equals(this.params)) {
						html.append(this.params);
						html.append("&");
					}
					html.append("pageNo=" + n);
					html.append("\">" + n + "</a></li>");
				}
			}
		}
		//当前页后 的页数 大于4页
		if( (this.getTotalPage()-this.pageNo)>4){
			html.append("<li class=\"disabled\">...</li>");
		}
		// 下一页
		if (this.hasNextPage()) {
			html.append("<li><a href=\"").append(url);
			html.append("?");
			if (!"".equals(this.params)) {
				html.append(this.params);
				html.append("&");
			}
			html.append("&pageNo=" + (this.pageNo + 1));
			html.append("\"><i class=\"icon-double-angle-right\"></i></a></li>");
		}else{
			html.append("<li class=\"disabled\"><a href=\"#\"> <i class=\"icon-double-angle-right\"></i> </a></li>");
		}
		html.append("</ul>");
		html.append("</div>");
		html.append("</div>");
		html.append("</form>");
		return  html.toString();
	}
		
	/**
	 *  搜索分页
	 * @return
	 */
	public String createPageInfoSeach() {
		StringBuffer html = new StringBuffer();
		html.append("<form id=\"pageFrm\" action=\"" + url + "\" method=\"post\" >");
		html.append("<div style=\"position: absolute; right: 20px;\">");
		html.append("<div style=\"float: right; position: relative; margin-left: 5px;\">");
		
		//跳转
		html.append(this.formStr);
		html.append("&nbsp;&nbsp;共<em class=\"value\">"+this.getTotalPage()+"</em>页&nbsp;&nbsp;");
		html.append("到 <input type=\"text\" name=\"pageNo\" size=\"2\" class=\"sm-filter-textElem\" value=\"\" style=\"width:50px;\"> 页&nbsp;");
		html.append("<button type=\"submit\" style=\"border-color: rgb(111, 179, 224); background-color: rgb(79, 153, 198);\"> 确定 </button>");
		
		html.append("</div><div style=\"float: right; position: relative; margin-left: 5px;\"><ul class=\"pagination\">");
		
		// 上一页
		if (this.hasPrevPage()) {
			html.append("<li><a href=\"").append(url);
			html.append("?");
			if (!"".equals(this.params)) {
				html.append(this.params);
				html.append("&");
			}
			html.append("&pageNo=" + (this.pageNo - 1));
			html.append("\"><i class=\"icon-double-angle-left\"></i></a>");
		}else{
			html.append("<li class=\"disabled\"><a href=\"#\"> <i class=\"icon-double-angle-left\"></i> </a></li>");
		}
		//第一页
		if(this.pageNo==1){
			html.append("<li class=\"active\"><a href=\"#\">1</a></li>");
		}else{
			html.append("<li><a href=\"").append(url);
			html.append("?");
			if (!"".equals(this.params)) {
				html.append(this.params);
				html.append("&");
			}
			html.append("pageNo=1");
			html.append("\">1</a></li>");
		}
		// 页面
		if (this.pageNo >= 6) {
			html.append("<li class=\"disabled\">...</li>");
			int n = 1;
			int tmp = this.pageNo-4;	
			while (n <= 8 && (tmp < this.getTotalPage())) {
				n++;
				tmp++;
				if(this.pageNo==tmp){
					html.append("<li class=\"active\"><a href=\"#\">"+tmp+"</a></li>");
				}else{
					html.append("<li><a href=\"").append(url);
					html.append("?");
					if (!"".equals(this.params)) {
						html.append(this.params);
						html.append("&");
					}
					html.append("pageNo=" + tmp);
					html.append("\">" + tmp + "</a></li>");
				}
			}
			
		} else {
			int n = 1;
			while (n <= 8 && (n < this.getTotalPage())) {
				n++;
				if(this.pageNo==n){
					html.append("<li class=\"active\"><a href=\"#\">"+n+"</a></li>");
				}else{
					html.append("<li><a href=\"").append(url);
					html.append("?");
					if (!"".equals(this.params)) {
						html.append(this.params);
						html.append("&");
					}
					html.append("pageNo=" + n);
					html.append("\">" + n + "</a></li>");
				}
			}
		}
		//当前页后 的页数 大于4页
		if( (this.getTotalPage()-this.pageNo)>4){
			html.append("<li class=\"disabled\">...</li>");
		}
		// 下一页
		if (this.hasNextPage()) {
			html.append("<li><a href=\"").append(url);
			html.append("?");
			if (!"".equals(this.params)) {
				html.append(this.params);
				html.append("&");
			}
			html.append("pageNo=" + (this.pageNo + 1));
			html.append("\"><i class=\"icon-double-angle-right\"></i></a></li>");
		}else{
			html.append("<li><a href=\"#\"> <i class=\"icon-double-angle-right\"></i> </a></li>");
		}
		
		
		html.append("</ul></div></div>");
		html.append("</form>");
		return  html.toString();
	}
	/**
	 * 初始化参数
	 * 
	 * @param url
	 * @param request
	 */
	public void intQueryParams(String url, HttpServletRequest request) {
		this.params = "";
		this.formStr = "";
		Map paramMap = request.getParameterMap();
		HashMap<String, String> stringMap = new HashMap();
		for (Iterator iter = paramMap.entrySet().iterator(); iter.hasNext();) {
			Map.Entry element = (Map.Entry) iter.next();
			Object strKey = element.getKey();
			String[] valueArr = (String[]) element.getValue();
			if("pageNo".equals(strKey)){
				try{
					this.pageNo = Integer.parseInt(valueArr[0]);
					if(this.pageNo > this.getTotalPage()){
						this.pageNo = this.getTotalPage();
					}
					if(this.pageNo <=0){
						this.pageNo = 1;
					}
				}catch(Exception e){
					this.pageNo =1;
				}
				continue;
			}
			this.params += "&" + strKey + "=" + valueArr[0];
			this.formStr += "<input type=\"hidden\" name=\"" + strKey+ "\" value=\"" + valueArr[0] + "\" />";
//					+ "\" value=\"" + valueArr[i] + "\" />";
//			for (int i = 0; i < valueArr.length; i++) {
//				this.params += "&" + strKey + "=" + valueArr[i];
//				this.formStr += "<input type=\"hidden\" name=\"" + strKey
//						+ "\" value=\"" + valueArr[i] + "\" />";
//			}
		}
		if (this.params.startsWith("&")) {
			this.params = this.params.substring(1, this.params.length());
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public void setPageInfo(String pageInfo) {
		this.pageInfo = pageInfo;
	}
	/**
	 * 默认分页
	 * @return
	 */
	public String getPageInfo() {
		return createPageInfo();
	}
	/**
	 * 搜索分页
	 * @return
	 */
	public String getPageInfoSeach() {
		return createPageInfoSeach();
	}

}
