package com.tower.common.listener;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class TowerListener implements ServletContextListener  {

	//每一分钟查询一次数据库判断是否自动释放
	private Timer timer_cancel;
	private TaskCancel task_cancel;
	
	

	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	//	timer_cancel.cancel();
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
	//	timer_cancel=new Timer(true);
	//	task_cancel=new TaskCancel(event.getServletContext());
	//	timer_cancel.schedule(task_cancel, 0,1000);
		
		
		
	}

}
