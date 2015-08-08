package com.steelbuzz.bean;

public class BuzzBean {
	
	String id,title,date,desc,image;

	public BuzzBean(String id, String title, String date, String desc, String image) {
		super();
		this.id = id;
		this.title = title;
		this.date = date;
		this.desc = desc;
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	
}
