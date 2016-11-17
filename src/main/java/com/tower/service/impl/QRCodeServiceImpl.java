package com.tower.service.impl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.tower.service.QRCodeService;
import com.swetake.util.Qrcode;
@Service
public class QRCodeServiceImpl implements QRCodeService{

	@Override
	public void QRCodeEncoder(String imgPath, String imgFormat,
			String imgShowData, int imgSize) throws Exception {

		Qrcode qrcode = new Qrcode();
		qrcode.setQrcodeErrorCorrect('M');
		qrcode.setQrcodeEncodeMode('B');
		qrcode.setQrcodeVersion(7);
		StringBuffer sb = new StringBuffer(imgShowData);
		String result = sb.toString();
		byte[] bt = result.getBytes("utf-8");
		BufferedImage bi = new BufferedImage(imgSize, imgSize,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D gh2D = bi.createGraphics();
		gh2D.setBackground(Color.WHITE);
		gh2D.clearRect(0, 0, imgSize, imgSize);
		gh2D.setColor(Color.BLACK);
		if (bt.length > 0) {
			boolean[][] s = qrcode.calQrcode(bt);
			for (int i = 0; i < s.length; i++) {
				for (int j = 0; j < s.length; j++) {
					if (s[j][i]) {
						gh2D.fillRect(j * 3 + 2, i * 3 + 2, 3, 3);
					}
				}
			}
		}
		gh2D.dispose();
		bi.flush();
		File file = new File(imgPath);
		  if (!file.exists()) {
			  file.mkdirs();
			}
		ImageIO.write(bi, imgFormat, file);

		System.out.println("doned!");
	}

	@Override
	public void QRCodeEncoder(OutputStream OS, String imgFormat,
			String imgShowData, int imgSize) throws Exception {

		Qrcode qrcode = new Qrcode();
		qrcode.setQrcodeErrorCorrect('M');
		qrcode.setQrcodeEncodeMode('B');
		qrcode.setQrcodeVersion(7);
		StringBuffer sb = new StringBuffer(imgShowData);
		String result = sb.toString();
		byte[] bt = result.getBytes("utf-8");
		BufferedImage bi = new BufferedImage(imgSize, imgSize,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D gh2D = bi.createGraphics();
		gh2D.setBackground(Color.WHITE);
		gh2D.clearRect(0, 0, imgSize, imgSize);
		gh2D.setColor(Color.BLACK);
		if (bt.length > 0) {
			boolean[][] s = qrcode.calQrcode(bt);
			for (int i = 0; i < s.length; i++) {
				for (int j = 0; j < s.length; j++) {
					if (s[j][i]) {
						gh2D.fillRect(j * 3 + 2, i * 3 + 2, 3, 3);
					}
				}
			}
		}
		gh2D.dispose();
		bi.flush();
		ImageIO.write(bi, imgFormat, OS);

		System.out.println("doned!");
	}
}
