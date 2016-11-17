package com.tower.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tower.common.bean.Notice;
import com.tower.common.bean.NoticePage;
import com.tower.mapper.NoticeMapper;
import com.tower.service.NoticeService;

@Service
public class NoticeServiceImpl implements NoticeService{
	
	@Autowired
	private NoticeMapper noticeMapper;

	@Override
	@Transactional
	public List<Notice> getNotices(NoticePage NoticePage) {
		return noticeMapper.getNotices(NoticePage);
	}

	@Override
	@Transactional
	public int getNoticesCount(NoticePage NoticePage) {
		return noticeMapper.getNoticesCount(NoticePage);
	}

	@Override
	@Transactional
	public Notice getNotice(int ID) {
		return noticeMapper.getNotice(ID);
	}

	@Override
	@Transactional
	public int deleteNotice(Map map) {
		int count = 0;
		count = noticeMapper.delNotice(map);
		if (count == 0)
			throw new RuntimeException();
		return 1;
	}

	@Override
	public int changeNotice(Map map) {
		int count = 0;
		count = noticeMapper.changeNotice(map);
		if (count == 0)
			throw new RuntimeException();
		return 1;
	}

	@Override
	public int addNotice(Map map) {
		int count = 0;
		count = noticeMapper.addNotice(map);
		if (count == 0)
			throw new RuntimeException();
		return 1;
	}

}
