package com.steelbuzz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class SignInScreen extends BaseActivity implements OnClickListener {

	private Intent mIntent;
	private LinearLayout lblSignin, lblSignup;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.signin);

		lblSignin = (LinearLayout) findViewById(R.id.lblSignin);
		lblSignin.setOnClickListener(this);

		lblSignup = (LinearLayout) findViewById(R.id.lblSignup);
		lblSignup.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.lblSignin:
			mIntent = new Intent(SignInScreen.this, MainActivity.class);
			startActivity(mIntent);
			finish();
			break;

		case R.id.lblSignup:
			mIntent = new Intent(SignInScreen.this, SignUpScreen.class);
			startActivity(mIntent);
			finish();
			break;
		}
	}
}
