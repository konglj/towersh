package com.tower.common.bean;

public class MapPoint {

	private int baidu;
	
	private String lat;// 纬度
	
	private String lng;// 经度

	
	public MapPoint() {
	}

	public MapPoint(String lng, String lat) {
		this.lng = lng;
		this.lat = lat;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MapPoint) {
			MapPoint bmapPoint = (MapPoint) obj;
			return (bmapPoint.getLng() == lng && bmapPoint.getLat() == lat) ? true
					: false;
		} else {
			return false;
		}
	}

	
	public int getBaidu() {
		return baidu;
	}

	public void setBaidu(int baidu) {
		this.baidu = baidu;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	@Override
	public String toString() {
		return "Point [lat=" + lat + ", lng=" + lng + "]";
	}
}
