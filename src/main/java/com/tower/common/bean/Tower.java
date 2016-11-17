package com.tower.common.bean;

import java.util.List;

public class Tower {

	private int id;

	private String towerid;

	private String towername;

	// 运营商需求 移动
	private String towerwhoyd;

	// 运营商需求 移动
	private String towerwhodx;

	// 运营商需求 移动
	private String towerwholt;

	private int towerarea;

	private String toweraddress;

	private int towerlevel;

	private String towerlevelname;

	private int towerfee;

	private int towerstate;

	private String towerstatename;

	private String towertype;

	private String towertypename;

	private String towerlarge;

	private String towerj;

	private String towerw;

	private String towerjbaidu;

	private String towerwbaidu;
	
	private String towerfirstjbaidu="0";

	private String towerfirstwbaidu="0";
	
	private String towersecondjbaidu="0";

	private String towersecondwbaidu="0";

	private String towermanager;

	private String towermanagername;

	private String towerinfo;

	private int towervisible;

	private String towerorderid;

	private String towerimg;

	private String areaname;

	private String cityname;

	private int cityid;

	private int areaid;

	private int isfavourite;

	private String toweridefined;

	private String toweradddate;

	private String towervisibletime;

	private int towerrentfee;

	private int adminpower;

	private int orderstate;

	private int pagetype = 0;// 0 tower 1 order

	private int towerstore;

	private String towerstorename;

	// 经度
	private String towerfirstj;

	// 纬度
	private String towerfirstw;

	// 经度
	private String towersecondj;

	// 纬度
	private String towersecondw;

	private int towersource;

	private List<MapPoint> towerMapPoint;
	
	private int towercreatetype;
	
	private String towercreatetypename;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTowerid() {
		return towerid;
	}

	public void setTowerid(String towerid) {
		this.towerid = towerid;
	}

	public String getTowername() {
		return towername;
	}

	public void setTowername(String towername) {
		this.towername = towername;
	}

	public String getTowerwhoyd() {
		return towerwhoyd;
	}

	public void setTowerwhoyd(String towerwhoyd) {
		this.towerwhoyd = towerwhoyd;
	}

	public String getTowerwhodx() {
		return towerwhodx;
	}

	public void setTowerwhodx(String towerwhodx) {
		this.towerwhodx = towerwhodx;
	}

	public String getTowerwholt() {
		return towerwholt;
	}

	public void setTowerwholt(String towerwholt) {
		this.towerwholt = towerwholt;
	}

	public int getTowerarea() {
		return towerarea;
	}

	public void setTowerarea(int towerarea) {
		this.towerarea = towerarea;
	}

	public String getToweraddress() {
		return toweraddress;
	}

	public void setToweraddress(String toweraddress) {
		this.toweraddress = toweraddress;
	}

	public int getTowerlevel() {
		return towerlevel;
	}

	public void setTowerlevel(int towerlevel) {
		this.towerlevel = towerlevel;
	}

	public int getTowerfee() {
		return towerfee;
	}

	public void setTowerfee(int towerfee) {
		this.towerfee = towerfee;
	}

	public int getTowerstate() {
		return towerstate;
	}

	public void setTowerstate(int towerstate) {
		this.towerstate = towerstate;
	}

	public String getTowertype() {
		return towertype;
	}

	public void setTowertype(String towertype) {
		this.towertype = towertype;
	}

	public String getTowerlarge() {
		return towerlarge;
	}

	public void setTowerlarge(String towerlarge) {
		this.towerlarge = towerlarge;
	}

	public String getTowerj() {
		return towerj;
	}

	public void setTowerj(String towerj) {
		this.towerj = towerj;
	}

	public String getTowerw() {
		return towerw;
	}

	public void setTowerw(String towerw) {
		this.towerw = towerw;
	}

	public String getTowermanager() {
		return towermanager;
	}

	public void setTowermanager(String towermanager) {
		this.towermanager = towermanager;
	}

	public String getTowermanagername() {
		return towermanagername;
	}

	public void setTowermanagername(String towermanagername) {
		this.towermanagername = towermanagername;
	}

	public String getTowerinfo() {
		return towerinfo;
	}

	public void setTowerinfo(String towerinfo) {
		this.towerinfo = towerinfo;
	}

	public int getTowervisible() {
		return towervisible;
	}

	public void setTowervisible(int towervisible) {
		this.towervisible = towervisible;
	}

	public String getTowerorderid() {
		return towerorderid;
	}

	public void setTowerorderid(String towerorderid) {
		this.towerorderid = towerorderid;
	}

	public String getTowerimg() {
		return towerimg;
	}

	public void setTowerimg(String towerimg) {
		this.towerimg = towerimg;
	}

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public int getIsfavourite() {
		return isfavourite;
	}

	public void setIsfavourite(int isfavourite) {
		this.isfavourite = isfavourite;
	}

	public String getTowerlevelname() {
		return towerlevelname;
	}

	public void setTowerlevelname(String towerlevelname) {
		this.towerlevelname = towerlevelname;
	}

	public String getTowerstatename() {
		return towerstatename;
	}

	public void setTowerstatename(String towerstatename) {
		this.towerstatename = towerstatename;
	}

	public String getTowertypename() {
		return towertypename;
	}

	public void setTowertypename(String towertypename) {
		this.towertypename = towertypename;
	}

	public int getCityid() {
		return cityid;
	}

	public void setCityid(int cityid) {
		this.cityid = cityid;
	}

	public int getAreaid() {
		return areaid;
	}

	public void setAreaid(int areaid) {
		this.areaid = areaid;
	}

	public String getToweridefined() {
		return toweridefined;
	}

	public void setToweridefined(String toweridefined) {
		this.toweridefined = toweridefined;
	}

	public String getToweradddate() {
		return toweradddate;
	}

	public void setToweradddate(String toweradddate) {
		this.toweradddate = toweradddate;
	}

	public String getTowervisibletime() {
		return towervisibletime;
	}

	public void setTowervisibletime(String towervisibletime) {
		this.towervisibletime = towervisibletime;
	}

	public int getTowerrentfee() {
		return towerrentfee;
	}

	public void setTowerrentfee(int towerrentfee) {
		this.towerrentfee = towerrentfee;
	}

	public String getTowerjbaidu() {
		return towerjbaidu;
	}

	public void setTowerjbaidu(String towerjbaidu) {
		this.towerjbaidu = towerjbaidu;
	}

	public String getTowerwbaidu() {
		return towerwbaidu;
	}

	public void setTowerwbaidu(String towerwbaidu) {
		this.towerwbaidu = towerwbaidu;
	}

	public int getAdminpower() {
		return adminpower;
	}

	public void setAdminpower(int adminpower) {
		this.adminpower = adminpower;
	}

	public int getOrderstate() {
		return orderstate;
	}

	public void setOrderstate(int orderstate) {
		this.orderstate = orderstate;
	}

	public int getPagetype() {
		return pagetype;
	}

	public void setPagetype(int pagetype) {
		this.pagetype = pagetype;
	}

	public int getTowerstore() {
		return towerstore;
	}

	public void setTowerstore(int towerstore) {
		this.towerstore = towerstore;
	}

	public String getTowerstorename() {
		return towerstorename;
	}

	public void setTowerstorename(String towerstorename) {
		this.towerstorename = towerstorename;
	}

	public String getTowerfirstj() {
		return towerfirstj;
	}

	public void setTowerfirstj(String towerfirstj) {
		this.towerfirstj = towerfirstj;
	}

	public String getTowerfirstw() {
		return towerfirstw;
	}

	public void setTowerfirstw(String towerfirstw) {
		this.towerfirstw = towerfirstw;
	}

	public String getTowersecondj() {
		return towersecondj;
	}

	public void setTowersecondj(String towersecondj) {
		this.towersecondj = towersecondj;
	}

	public String getTowersecondw() {
		return towersecondw;
	}

	public void setTowersecondw(String towersecondw) {
		this.towersecondw = towersecondw;
	}

	public int getTowersource() {
		return towersource;
	}

	public void setTowersource(int towersource) {
		this.towersource = towersource;
	}

	public List<MapPoint> getTowerMapPoint() {
		return towerMapPoint;
	}

	public void setTowerMapPoint(List<MapPoint> towerMapPoint) {
		this.towerMapPoint = towerMapPoint;
	}

	public String getTowerfirstjbaidu() {
		return towerfirstjbaidu;
	}

	public void setTowerfirstjbaidu(String towerfirstjbaidu) {
		this.towerfirstjbaidu = towerfirstjbaidu;
	}

	public String getTowerfirstwbaidu() {
		return towerfirstwbaidu;
	}

	public void setTowerfirstwbaidu(String towerfirstwbaidu) {
		this.towerfirstwbaidu = towerfirstwbaidu;
	}

	public String getTowersecondjbaidu() {
		return towersecondjbaidu;
	}

	public void setTowersecondjbaidu(String towersecondjbaidu) {
		this.towersecondjbaidu = towersecondjbaidu;
	}

	public String getTowersecondwbaidu() {
		return towersecondwbaidu;
	}

	public void setTowersecondwbaidu(String towersecondwbaidu) {
		this.towersecondwbaidu = towersecondwbaidu;
	}

	public int getTowercreatetype() {
		return towercreatetype;
	}

	public void setTowercreatetype(int towercreatetype) {
		this.towercreatetype = towercreatetype;
	}

	public String getTowercreatetypename() {
		return towercreatetypename;
	}

	public void setTowercreatetypename(String towercreatetypename) {
		this.towercreatetypename = towercreatetypename;
	}
	
	
	

	
}
