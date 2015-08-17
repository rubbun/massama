package com.steelbuzz;

import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.steelbuzz.constant.Constants;
import com.steelbuzz.network.HttpClient;

public class SignUpScreen extends BaseActivity implements OnClickListener{

	private Intent mIntent;
	private LinearLayout lblSignup;
	private EditText etFname,etLname,etEmail,etPassword;
	private String fname,lname,email,password = "";
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.signup);
		
		lblSignup.setOnClickListener(this);
		
		etPassword = (EditText)findViewById(R.id.etPassword);
		etEmail = (EditText)findViewById(R.id.etEmail);
		etLname = (EditText)findViewById(R.id.etLname);
		etFname = (EditText)findViewById(R.id.etFname);
		
		etFname.setTypeface(getRegularTypeFace());
		etEmail.setTypeface(getRegularTypeFace());
		etLname.setTypeface(getRegularTypeFace());
		etPassword.setTypeface(getRegularTypeFace());
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.lblSignup:
			if(isValid()){
				sendValueToServer();
			}
			break;
		}
	}

	private boolean isValid() {
		boolean flag = true;
		fname = etFname.getText().toString().trim();
		lname = etLname.getText().toString().trim();
		email = etEmail.getText().toString().trim();
		password = etPassword.getText().toString().trim();
		if(fname.length() == 0){
			etFname.setError("Please enter your first name");
			flag = false;
		}else if(lname.length() == 0){
			etLname.setError("Please enter your last name");
			flag = false;
		}else if(email.length() == 0){
			etEmail.setError("Please enter your last name");
			flag = false;
		}else if (!isvalidMailid(email)) {
			etEmail.setError("Please enter valid Email id.");
			flag = false;
		}else if(password.length() == 0){
			etEmail.setError("Please enter your mail name");
			flag = false;
		}
		return flag;
	}
	public boolean isvalidMailid(String mail) {
		return Pattern.compile(EMAIL_PATTERN).matcher(mail).matches();
	}
	
	private void sendValueToServer() {
		new CallServerForRegistration().execute();
	}

	public class CallServerForRegistration extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			doShowLoading();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				JSONObject req = new JSONObject();
				req.put("firstname", fname);
				req.put("lastname", lname);
				req.put("email", email);
				req.put("pass", password);

				String response = HttpClient.SendHttpPost(Constants.REGISTER, req.toString());
				if (response != null) {
			   		try {
						JSONObject ob = new JSONObject(response);
						if (ob.getBoolean("status")) {
							return true;
						}else{
							return false;
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			doRemoveLoading();
			if(result){
				Toast.makeText(getApplicationContext(), "Registration Successful!!!", Toast.LENGTH_LONG).show();
				mIntent = new Intent(SignUpScreen.this,SignInScreen.class);
				startActivity(mIntent);
				finish();
			}else{
				Toast.makeText(getApplicationContext(), "Some error occured.Please try again..", Toast.LENGTH_LONG).show();
			}
		}
	}
}
