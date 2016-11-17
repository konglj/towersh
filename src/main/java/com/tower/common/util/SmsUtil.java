package com.tower.common.util;

import java.io.UnsupportedEncodingException;

public class SmsUtil {

	public static boolean sendSms(String phone,String content) {

		// 获取验证码
		String url = "http://manager.wxtxsms.cn/smsport/sendPost.aspx";
		String uid ="sdbc";
		String upwd = "824d9deb03b11a4f918b0a68fff96557";
		content="山东百传提醒您，平台将要到期，到期日是"+content;
		String Msg = "";
		String sign = "山东百传";
		
		try {
			Msg = java.net.URLEncoder.encode(content, "utf-8");
			sign = java.net.URLEncoder.encode(sign, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String param = String.format(
				"uid=%s&upsd=%s&sendtele=%s&Msg=%s&sign=%s", uid, upwd, phone,
				Msg, sign);
		String result = "";
		try {
			result = HttpRequst.sendPost(url, param);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("result= "+result);

		if (result.startsWith("success"))
			return true;
		return false;
	}

}
