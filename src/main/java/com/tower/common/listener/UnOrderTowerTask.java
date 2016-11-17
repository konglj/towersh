package com.tower.common.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tower.common.Config;
import com.tower.common.util.ParamerUtil;
import com.tower.service.OrderService;
import com.tower.service.TowerService;
import com.tower.service.TxService;


@Component
public class UnOrderTowerTask {

	@Autowired
	private OrderService orderService;

	@Autowired
	private TowerService towerService;
	
	@Autowired
	private TxService txService;

	@Scheduled(cron = "0 0 12 * * *")
	public void getUnOrderTower() {
		System.out.println("2222 start");
		try {

			List<Map> towers = towerService.getUnOrderTower();
			if (towers != null && towers.size() > 0) {
				for (Map map : towers) {

					
					String time="";
					if (map.get("lastordertime") == null) {
						String visitime = map.get("towervisibletime")==null?null:map.get("towervisibletime").toString();
						// 判读日志
						time=visitime;
						

					} else {
						time=map.get("lastordertime").toString();

					}
					if(ParamerUtil.isUpdateTowerLevel(time)){
					   Map mapLeve=new HashMap();
					   mapLeve.put("towerid", map.get("towerid"));
					   mapLeve.put("towerlevel", Config.jjtowerlevel);
					   int count=towerService.updateTowerLevel(mapLeve);
					}
				
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("22");
	}
	
}
