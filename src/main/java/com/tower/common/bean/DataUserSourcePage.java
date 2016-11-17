package com.tower.common.bean;

import java.util.List;
import java.util.Map;

public class DataUserSourcePage {
	
	//下辖的管理员
		
		
		//检索页No
		private int pageNo =1;
		
		//总数据条数
		private int pagerowtotal;
		
		/**
		 * 界面表示用的  Page信息Map
		 */
		private Map  pageinfo;
		
		//管理地市
		private List<Area> adminareas;
		
	    private List<City> adminCitys;
	    
	    private List<UserSourceAnalysis> userSources;
	    
	    private int sourceid;
	    
	    private int infoid;

		public int getPageNo() {
			return pageNo;
		}

		public void setPageNo(int pageNo) {
			this.pageNo = pageNo;
		}

		public int getPagerowtotal() {
			return pagerowtotal;
		}

		public void setPagerowtotal(int pagerowtotal) {
			this.pagerowtotal = pagerowtotal;
		}

		public Map getPageinfo() {
			return pageinfo;
		}

		public void setPageinfo(Map pageinfo) {
			this.pageinfo = pageinfo;
		}

		public List<Area> getAdminareas() {
			return adminareas;
		}

		public void setAdminareas(List<Area> adminareas) {
			this.adminareas = adminareas;
		}

		public List<City> getAdminCitys() {
			return adminCitys;
		}

		public void setAdminCitys(List<City> adminCitys) {
			this.adminCitys = adminCitys;
		}

		public List<UserSourceAnalysis> getUserSources() {
			return userSources;
		}

		public void setUserSources(List<UserSourceAnalysis> userSources) {
			this.userSources = userSources;
		}

		public int getSourceid() {
			return sourceid;
		}

		public void setSourceid(int sourceid) {
			this.sourceid = sourceid;
		}

		public int getInfoid() {
			return infoid;
		}

		public void setInfoid(int infoid) {
			this.infoid = infoid;
		}
	    
	    
	    
	    

}
