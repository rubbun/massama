package com.steelbuzz.application;

import android.app.Application;

public class Appsettings extends Application{

	private UserInfo userinfo;	
	public boolean init = false;

	public UserInfo getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}
}
