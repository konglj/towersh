package com.tower.mapper;

import java.util.List;
import java.util.Map;

import com.tower.common.bean.TxDoinfo;
import com.tower.common.bean.TxPage;
import com.tower.common.bean.TxRecord;
import com.tower.common.bean.TxSource;

public interface TxMapper {
	
	public List<TxRecord> getTxs(TxPage txpage);
	
	public int getTxsCount(TxPage txpage);
	
	public TxRecord getTx(String txid);
	
	
	public List<TxSource> getTxSources(TxPage txPage);
	
	public List<TxDoinfo> getTxDoinfos(String txid);
	
	public int doinfo(Map map);
	
	public int addTxdoinfo(TxDoinfo txdoinfo);
	
	public int shnocharge(Map map);
	
	public int shnotxsource(Map map);
	
	public int dkyescharge(Map map);
	
	public int dkdoinfo(Map map);
	
	public List<Map> getDcTxs(TxPage txPage);
	
	public List<Map> getDcTxApplys(TxPage txPage);
	
	public TxRecord getTxRecord(String txid);
	
	public int plShTx(Map map);
	
	public TxRecord getTxRecordById(int id);
	
	public List<TxRecord> getAutoTxs();
	
	public int updateAutoTx(int id);
	
	

}
