package com.tower.common.bean;

public class DataTowerParameter {
	
	private String towername;
	
	private int towerarea;
	
	private int towercity;
	
	private int towerlevel = -1;

	public String getTowername() {
		return towername;
	}

	public void setTowername(String towername) {
		this.towername = towername;
	}

	public int getTowerarea() {
		return towerarea;
	}

	public void setTowerarea(int towerarea) {
		this.towerarea = towerarea;
	}

	public int getTowercity() {
		return towercity;
	}

	public void setTowercity(int towercity) {
		this.towercity = towercity;
	}

	public int getTowerlevel() {
		return towerlevel;
	}

	public void setTowerlevel(int towerlevel) {
		this.towerlevel = towerlevel;
	}
	
}
