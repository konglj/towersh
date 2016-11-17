package com.tower.common.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.taglibs.standard.tag.common.xml.WhenTag;

public class PageUtil {

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

	/**
	 * 
	 * @param request
	 * @param pageNo2
	 * @param pageSize2
	 * @param tatol
	 */
	public PageUtil(int pageNo, int total) {
		this.total = total;
		this.pageNo = pageNo;
		if(total==0)
			this.pageNo=0;
		this.totalPage=this.getTotalPage();

	}

	/**
	 * 
	 * @param request
	 * @param pageNo2
	 * @param pageSize2
	 * @param tatol
	 */
	public PageUtil(int pageNo,int pageSize, int total) {
		this.total = total;
		this.pageNo = pageNo;
		this.pageSize=pageSize;
		if(total==0)
			this.pageNo=0;
		this.totalPage=this.getTotalPage();

	}

	
	public Map getPage() {
		Map map = new HashMap();
		map.put("pagecount", this.getPageInfo());
		if(total>0){
	     	map.put("page", this.getCountInfo());
		}else{
			
		}
	    map.put("jumppage", this.getGoPage());

		return map;
	}

	private int getTotalPage() {
		int tp = this.total / this.pageSize;
		if ((this.total % this.pageSize) != 0) {
			tp += 1;
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

	
	private String getGoPage(){
		StringBuilder html = new StringBuilder();
		if(total/pageSize>=1&&total>pageSize){
			
			html.append("<span style=\"padding: 5px 10px 0px 10px\">");
			html.append("跳转到&nbsp;&nbsp;");
			html.append("<input  type=\"text\" id=\"jumppage\"  class=\"dfinput\" style=\"width: 20px;height: 20px;margin-right: 0px;text-indent:2px;padding-bottom: 4px;\" >");
			html.append("&nbsp;&nbsp;页&nbsp;&nbsp;");
			html.append("<input type=\"button\" onclick=\"jump_page()\" class=\"sure\" style=\"width: 20px;height: 20px;margin-right: 30px;font-size:6px;font-weight:normal;line-height:0px\" value=\"GO\" />");
			html.append("</span>");
		}
		return html.toString();
		
	}
	
	private String getCountInfo() {
		StringBuilder html = new StringBuilder();
		
		

		
		if (this.hasPrevPage()) {
			html.append("<li class=\"paginItem\">");
			
			html.append(" <a href=\"javascript:;\"");
			html.append(" onclick=go_page("+(pageNo-1)+")");
			html.append(">");
			html.append("   <span class=\"pagepre1\"></span>");
			html.append(" </a>");
			html.append("</li>");
		} else {
			html.append("<li class=\"paginItem\"");
			html.append(">");
			html.append(" <a href=\"javascript:#;\">");
			html.append("   <span class=\"pagepre\"></span>");
			html.append(" </a>");
			html.append("</li>");
		}
		int pz = (pageNo-1) / 10;
		int page=0;
			page=pz*10+1;			
		while (page<=(pz+1)*10&&page<=totalPage) {
			if (pageNo == page) {
				html.append("<li class=\"paginItem current\">");
				html.append(" <a href=\"javascript:;\"");
				html.append(">");
			} else {
				html.append("<li class=\"paginItem\">");
				html.append(" <a href=\"javascript:;\"");
				html.append(" onclick=go_page("+page+")");
				html.append(">");
				
			}
		
			html.append(page);
			html.append(" </a>");
			html.append("</li>");
			page++;
			
		}
		if (this.hasNextPage()) {
			html.append("<li class=\"paginItem\">");
			
			html.append(" <a href=\"javascript:;\"");
			html.append(" onclick=go_page("+(pageNo+1)+")");
			html.append(">");
			html.append("   <span class=\"pagenxt1\"></span>");
			html.append(" </a>");
			html.append("</li>");
		} else {

            html.append("<li class=\"paginItem\">");
			
			html.append(" <a href=\"javascript:;\"");
			html.append(">");
			html.append("<span class=\"pagenxt\"></span>");
			html.append(" </a>");
			html.append("</li>");
			
			
			
			
		}
		
		return html.toString();
	}

	private String getPageInfo() {
		String info = String.format("当前第%d/%d页 共%d条记录 ", pageNo,
				this.getTotalPage(), total, pageSize);
		return info;
	}

}
