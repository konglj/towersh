package com.tower.service;

import java.util.List;
import java.util.Map;

import com.tower.common.bean.TxPage;
import com.tower.common.bean.TxRecord;
import com.tower.common.bean.TxSource;

public interface TxService {
	
	public List<TxRecord> getTxs(TxPage txPage);
	
	public TxRecord getTx(TxPage txPage);
	
	public TxSource getTxSources(TxPage txPage);

	int shTx(Map map);

	public int plShTx(Map map);
	
	public  int dkTx(Map map);
	
	public List<Map> getDcTxs(TxPage txPage);
	
	public List<Map> getDcTxApplys(TxPage txPage);
	
	public int dkTx(List<Map> list,int adminid);
	
	public List<TxRecord> getAutoTxs();

	public int updateAutoTx(TxRecord txRecord);
}
