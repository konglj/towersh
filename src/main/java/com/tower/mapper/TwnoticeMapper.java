package com.tower.mapper;

import java.util.List;

import com.jcraft.jsch.UserInfo;
import com.tower.common.bean.SendNoticeParameter;
import com.tower.common.bean.TwNotice;
import com.tower.common.bean.TwNoticePage;
import com.tower.common.bean.TwNoticeSend;

public interface TwnoticeMapper {

	public List<TwNotice> getTwNotices(TwNoticePage twNoticePage);
	
	public int getTwNoticesCount(TwNoticePage twNoticePage);
	
	public TwNotice getTwNotice(int id);
	
	public TwNotice getTwNoticeInfo(int id);
	

	public int insertTwNotice(TwNotice twNotice);
	
	public int updateTwNotice(TwNotice twNotice);
	
	public int delTwNotice(int id);
	
	public List<TwNoticeSend> getNoticeSends(int noticeid);
	
	public int insertNoticeSend(TwNoticeSend noticeSend);
	
	

}
