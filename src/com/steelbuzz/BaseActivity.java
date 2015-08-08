package com.steelbuzz;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.steelbuzz.application.Appsettings;
import com.steelbuzz.application.UserInfo;
import com.steelbuzz.db.TestDbAdapter;

public class BaseActivity extends FragmentActivity {

	public TestDbAdapter mDb;
	private ProgressDialog dialog;
	public Appsettings app = null;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mDb = TestDbAdapter.createInstance(getApplicationContext());
		app = (Appsettings)getApplication();
		if(!app.init){
			app.init = true;
			app.setUserinfo(new UserInfo(BaseActivity.this));
		}
	}

	public boolean hasConnection() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiNetwork != null && wifiNetwork.isConnected()) {
			return true;
		}

		NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mobileNetwork != null && mobileNetwork.isConnected()) {
			return true;
		}

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) {
			return true;
		}
		return false;
	}
	
	public void doShowLoading() {
		dialog = new ProgressDialog(BaseActivity.this);
		dialog.setMessage("Please wait..........");
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setIndeterminate(true);
		dialog.setCancelable(false);
		dialog.show();

	}

	public void doRemoveLoading() {
		dialog.dismiss();
	}
	
	public Typeface getRegularTypeFace(){
		 Typeface face= Typeface.createFromAsset(getAssets(), "fonts/OpenSans_Regular.ttf");
		 return face;
	}
	
	public Typeface getBoldTypeFace(){
		 Typeface face= Typeface.createFromAsset(getAssets(), "fonts/OpenSans_Bold.ttf");
		 return face;
	}
}
