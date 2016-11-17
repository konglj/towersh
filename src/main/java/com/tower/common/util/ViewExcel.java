package com.tower.common.util;

import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class ViewExcel extends AbstractExcelView {

	@Override
	public void buildExcelDocument(Map obj,  
            HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		  String filename = obj.get("filename").toString();//设置下载时客户端Excel的名称       
	        response.setContentType("application/vnd.ms-excel");       
	        response.setHeader("Content-disposition", "attachment;filename=" + filename);       
	        OutputStream ouputStream = response.getOutputStream();       
	        workbook.write(ouputStream);       
	        ouputStream.flush();       
	        ouputStream.close();   
		
	}  

}
