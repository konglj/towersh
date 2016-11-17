package com.tower.common.util;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class FileUpload {

	public static String uploadFile(HttpServletRequest request, String filepath,
			String inputfileid) {
		String filename=null;
		boolean result = false;
		// 获取上传文件
		// 转型为MultipartHttpRequest：
		// 保存
		String image = null;
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 获得文件：
			MultipartFile file = multipartRequest.getFile(inputfileid);
			if (file != null) {
				String path = request.getSession().getServletContext()
						.getRealPath("/");
		        if(path.endsWith("\\"))
		        	path=path.substring(0,path.length()-1);
				path=path.substring(0,path.lastIndexOf('\\'));
				path=path+"\\shanghai_towerfile\\"+filepath;

				String oldname = file.getOriginalFilename();
				String extname = oldname.substring(oldname.indexOf("."));
				// 获得文件名：
				 filename =UUID.randomUUID().toString().replace("-", "")+extname;
				System.out.println(filename);
				File targetFile = new File(path, filename);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}

				file.transferTo(targetFile);
				image ="\\shanghai_towerfile\\"+filepath + filename;
			}
		} catch (Exception e) {
			image=null;
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return image;
	}
	
	
	public static String uploadFile_excel(HttpServletRequest request, String filepath,
			String inputfileid) {
		String filename=null;
		boolean result = false;
		// 获取上传文件
		// 转型为MultipartHttpRequest：
		// 保存
		String image = null;
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 获得文件：
			MultipartFile file = multipartRequest.getFile(inputfileid);
			if (file != null) {
				String path = request.getSession().getServletContext()
						.getRealPath("/");
		        if(path.endsWith("\\"))
		        	path=path.substring(0,path.length()-1);
				path=path.substring(0,path.lastIndexOf('\\'));
				path=path+"\\towerfile\\"+filepath;

				String oldname = file.getOriginalFilename();
				String extname = oldname.substring(oldname.indexOf("."));
				// 获得文件名：
				 filename =UUID.randomUUID().toString().replace("-", "")+extname;
				System.out.println(filename);
				File targetFile = new File(path, filename);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}

				file.transferTo(targetFile);
				image=path+"\\"+filename;
			}
		} catch (Exception e) {
			image=null;
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return image;
	}
}
