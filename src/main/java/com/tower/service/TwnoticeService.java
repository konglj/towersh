package com.tower.service;


import java.util.List;

import com.tower.common.bean.SendNoticeParameter;
import com.tower.common.bean.TwNotice;
import com.tower.common.bean.TwNoticePage;
import com.tower.common.bean.TwNoticeSend;

public interface TwnoticeService {
	
	public void getTwNotices(TwNoticePage twNoticePage);
	
	public TwNotice getTwNotice(int id);
	
	public TwNotice getTwNoticeInfo(int id);
	
	public int insertTwNotice(TwNotice twNotice);
	
	public int updateTwNotice(TwNotice twNotice);

	public int delTwNotice(int id);
	
	public int  sendNotice(SendNoticeParameter parameter);
	
	public List<TwNoticeSend>  getTwNoticeSends(int noticeid);
	
}
