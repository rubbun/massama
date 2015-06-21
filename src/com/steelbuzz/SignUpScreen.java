package com.steelbuzz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class SignUpScreen extends BaseActivity implements OnClickListener{

	private Intent mIntent;
	private LinearLayout lblSignup;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.signup);
		
		lblSignup = (LinearLayout)findViewById(R.id.lblSignup);
		lblSignup.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.lblSignup:
			mIntent = new Intent(SignUpScreen.this,MainActivity.class);
			startActivity(mIntent);
			finish();
			break;
		}
	}
}
