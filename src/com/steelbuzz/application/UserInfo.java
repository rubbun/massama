package com.steelbuzz.application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.steelbuzz.constant.Constants;

public class UserInfo {
	public String fname = "";
	public String lname = "";
	public String email = "";
	public String user_id = "";
	public String login_type = "";
	public boolean session = false;
	public boolean trialPeriodStatus = false;
	public long startTime = 00;
	
	public SharedPreferences preference;
	
	public UserInfo(Context ctx){
		
		preference = ctx.getSharedPreferences(Constants.values.USRINFO.name(), Context.MODE_PRIVATE);
		fname = preference.getString(Constants.values.FIRSTNAME.name(), "");
		lname = preference.getString(Constants.values.FIRSTNAME.name(), "");
		email = preference.getString(Constants.values.EMAIL.name(), "");
		user_id = preference.getString(Constants.values.USERID.name(), "");		
		session =  preference.getBoolean(Constants.values.SESSION.name(), false);
		login_type = preference.getString(Constants.values.LOGINTYPE.name(), "");
		trialPeriodStatus =  preference.getBoolean(Constants.values.TRIALPERIODSTATUS.name(), false);
		startTime = preference.getLong(Constants.values.STARTTIME.name(), 00);
	}

	public void SetUserInfo(String fname,String lname, String email,String user_id,boolean session,String login_type) {
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.user_id = user_id;
		this.login_type = login_type;
		this.session = session;
		
		Editor edit = preference.edit();
		edit.putString(Constants.values.FIRSTNAME.name(), fname);
		edit.putString(Constants.values.LASTNAME.name(), fname);
		edit.putString(Constants.values.EMAIL.name(), email);
		edit.putString(Constants.values.USERID.name(), user_id);
		edit.putBoolean(Constants.values.SESSION.name(), session);
		edit.putString(Constants.values.LOGINTYPE.name(), login_type);
		edit.commit();
	}
	
	public void setSession(boolean session) {
		this.session = session;
		Editor edit = preference.edit();
		edit.putBoolean(Constants.values.SESSION.name(), session);		
		edit.commit();
	}

	public String getUser_id() {
		return preference.getString(Constants.values.USERID.name(), "");
	}

	public void setUser_id(String user_id) {
		Editor edit = preference.edit();
		edit.putString(Constants.values.USERID.name(), user_id);		
		edit.commit();
	}

	public String getFirstName() {
		return preference.getString(Constants.values.FIRSTNAME.name(), "");
	}

	public void setFirstName(String fname) {
		this.fname = fname;
		Editor edit = preference.edit();
		edit.putString(Constants.values.FIRSTNAME.name(), fname);		
		edit.commit();
	}
	
	public String getLastName() {
		return preference.getString(Constants.values.FIRSTNAME.name(), "");
	}

	public void setLastName(String lname) {
		this.lname = lname;
		Editor edit = preference.edit();
		edit.putString(Constants.values.FIRSTNAME.name(), lname);		
		edit.commit();
	}

	public String getEmail() {
		return preference.getString(Constants.values.EMAIL.name(), "");
	}

	public void setEmail(String email) {
		this.email = email;
		Editor edit = preference.edit();
		edit.putString(Constants.values.EMAIL.name(), email);		
		edit.commit();
	}
	public boolean isSession() {
		return preference.getBoolean(Constants.values.SESSION.name(), false);
	}

	public String getLogin_type() {
		return preference.getString(Constants.values.LOGINTYPE.name(), "");
	}

	public void setLogin_type(String login_type) {
		this.login_type = login_type;
		Editor edit = preference.edit();
		edit.putString(Constants.values.LOGINTYPE.name(), login_type);		
		edit.commit();
	}

	public boolean isTrialPeriodStatus() {
		return preference.getBoolean(Constants.values.TRIALPERIODSTATUS.name(), false);
	}

	public void setTrialPeriodStatus(boolean trialPeriodStatus) {
		this.trialPeriodStatus = trialPeriodStatus;
		Editor edit = preference.edit();
		edit.putBoolean(Constants.values.TRIALPERIODSTATUS.name(), trialPeriodStatus);		
		edit.commit();
	}

	public long getStartTime() {
		return preference.getLong(Constants.values.STARTTIME.name(), 00);
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
		Editor edit = preference.edit();
		edit.putLong(Constants.values.STARTTIME.name(), startTime);		
		edit.commit();
	}
}
