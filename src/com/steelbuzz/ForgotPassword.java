package com.steelbuzz;

import org.json.JSONException;
import org.json.JSONObject;

import com.steelbuzz.constant.Constants;
import com.steelbuzz.network.HttpClient;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ForgotPassword extends BaseActivity implements OnClickListener{
	
	private EditText etEmail;
	private LinearLayout lblCancel,lblSubmit;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.forgot_password);
		
		etEmail = (EditText)findViewById(R.id.etEmail);
		
		lblSubmit = (LinearLayout)findViewById(R.id.lblSubmit);
		lblCancel = (LinearLayout)findViewById(R.id.lblCancel);
		
		lblCancel.setOnClickListener(this);
		lblSubmit.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.lblCancel:
			finish();
			break;

		case R.id.lblSubmit:
			verifyEmail();
			break;
		}
	}

	private void verifyEmail() {
		if(etEmail.getText().toString().toString().trim().length() == 0){
			etEmail.setError("Please enter your Email id.");
		}else{
			new sendEmailForVerification().execute();
		}
	}
	
	public class sendEmailForVerification extends AsyncTask<Void, Void, Boolean>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			doShowLoading();
		}
		@Override
		protected Boolean doInBackground(Void... params) {
			JSONObject obj = new JSONObject();
			try {
				obj.put("email", etEmail.getText().toString().toString().trim());
				String response = HttpClient.SendHttpPost(Constants.FORGOTPASSWORD_EMAIL_VERIFICATION, obj.toString());
				if(response != null){
					JSONObject ob = new JSONObject(response);
					if(ob.getBoolean("status")){
						return true;
					}else{
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
			if(result){
				Intent i = new Intent(ForgotPassword.this,ResetPasword.class);
				i.putExtra("email", etEmail.getText().toString().trim());
				startActivity(i);;
				finish();
			}else{
				Toast.makeText(ForgotPassword.this, "Email id does no0t find.", Toast.LENGTH_LONG).show();
			}
		}
	}
}
