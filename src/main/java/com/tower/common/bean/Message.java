package com.tower.common.bean;

public class Message {
	
	private int id;
	
	private String title;
	
	private String content;
	
	private int messager;
	
	private String messagername;
	
	private String messagetime;
	
	private String powername;
	
	private String url;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getMessager() {
		return messager;
	}

	public void setMessager(int messager) {
		this.messager = messager;
	}

	public String getMessagername() {
		return messagername;
	}

	public void setMessagername(String messagername) {
		this.messagername = messagername;
	}

	public String getMessagetime() {
		return messagetime;
	}

	public void setMessagetime(String messagetime) {
		this.messagetime = messagetime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPowername() {
		return powername;
	}

	public void setPowername(String powername) {
		this.powername = powername;
	}

}
