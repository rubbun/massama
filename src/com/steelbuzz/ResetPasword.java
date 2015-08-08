package com.steelbuzz;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.steelbuzz.constant.Constants;
import com.steelbuzz.network.HttpClient;

public class ResetPasword extends BaseActivity implements OnClickListener {

	private EditText etPsw, etConfirmPsw;
	private LinearLayout lblSubmit, lblCancel;
	private String password, ResetPassword, eMail = "";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.reset_password);

		eMail = getIntent().getExtras().getString("email");

		etPsw = (EditText) findViewById(R.id.etPsw);
		etConfirmPsw = (EditText) findViewById(R.id.etConfirmPsw);

		lblCancel = (LinearLayout) findViewById(R.id.lblCancel);
		lblSubmit = (LinearLayout) findViewById(R.id.lblSubmit);

		lblSubmit.setOnClickListener(this);
		lblSubmit.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.lblSubmit:
			if (valid()) {
				new sendResetPassword().execute();
			}
			break;

		case R.id.lblCancel:
			finish();
			break;
		}
	}

	private boolean valid() {
		boolean flag = true;
		password = etPsw.getText().toString().trim().trim();
		ResetPassword = etConfirmPsw.getText().toString().trim().trim();
		if (password.length() == 0) {
			etPsw.setError("Plesae enter your password.");
			flag = false;
		} else if (ResetPassword.length() == 0) {
			etConfirmPsw.setError("Plesae Re-type your password.");
			flag = false;
		} else if (!password.equalsIgnoreCase(ResetPassword)) {
			Toast.makeText(ResetPasword.this, "Password mismatched!!", Toast.LENGTH_LONG).show();
			flag = false;
		}
		return flag;
	}

	public class sendResetPassword extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			doShowLoading();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			JSONObject obj = new JSONObject();
			try {
				obj.put("email", eMail);
				obj.put("pass", password);
				String response = HttpClient.SendHttpPost(Constants.RESET_PASSWORD, obj.toString());
				if (response != null) {
					JSONObject ob = new JSONObject(response);
					if (ob.getBoolean("status")) {
						return true;
					} else {
						return false;
					}
				}

			} catch (JSONException e) {
				e.printStackTrace();
				return false;
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			doRemoveLoading();
			if (result) {
				Toast.makeText(ResetPasword.this, "You have successfully reset password", Toast.LENGTH_LONG).show();
				finish();
			} else {
				Toast.makeText(ResetPasword.this, "Email id does not find.", Toast.LENGTH_LONG).show();
			}
		}
	}
}
