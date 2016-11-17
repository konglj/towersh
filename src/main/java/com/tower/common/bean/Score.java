package com.tower.common.bean;

public class Score {

	private int id;

	private String wxid;

	private int scorecount;

	private String scorereason;
	
	private String scoretime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWxid() {
		return wxid;
	}

	public void setWxid(String wxid) {
		this.wxid = wxid;
	}

	public int getScorecount() {
		return scorecount;
	}

	public void setScorecount(int scorecount) {
		this.scorecount = scorecount;
	}

	public String getScorereason() {
		return scorereason;
	}

	public void setScorereason(String scorereason) {
		this.scorereason = scorereason;
	}

	public String getScoretime() {
		return scoretime;
	}

	public void setScoretime(String scoretime) {
		this.scoretime = scoretime;
	}
}
