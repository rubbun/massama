package com.steelbuzz;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.steelbuzz.db.TestDbAdapter;

public class BaseActivity extends FragmentActivity{

	public TestDbAdapter mDb;
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mDb = TestDbAdapter.createInstance(getApplicationContext());
	}
	
	 public boolean hasConnection() {
		 ConnectivityManager cm =
			        (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
			
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
}
