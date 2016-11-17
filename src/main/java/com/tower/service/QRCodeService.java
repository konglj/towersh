package com.tower.service;

import java.io.OutputStream;

public interface QRCodeService {

	/**
	 * 生成图像业务接口
	 * @param imgPath     要生成图像的路径
	 * @param imgFormat   要生成图像的格式
	 * @param imgShowData 要生成图像的数据
	 * @param imgSize     要生成图像的像素
	 */
	public void QRCodeEncoder(String imgPath,String imgFormat,String imgShowData,int imgSize) throws Exception;
	
	/**
	 * 生成图像业务接口
	 * @param OS          要生成图像的流
	 * @param imgFormat   要生成图像的格式
	 * @param imgShowData 要生成图像的数据
	 * @param imgSize     要生成图像的像素
	 */
	public void QRCodeEncoder(OutputStream OS,String imgFormat,String imgShowData,int imgSize) throws Exception;	

}
