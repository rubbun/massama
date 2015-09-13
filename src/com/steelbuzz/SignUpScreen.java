package com.steelbuzz;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.steelbuzz.ResetPasword.PasswordValidator;
import com.steelbuzz.constant.Constants;
import com.steelbuzz.network.HttpClient;

public class SignUpScreen extends BaseActivity implements OnClickListener{

	private Intent mIntent;
	private TextView textView3,tvTerms;
	private LinearLayout lblSignup;
	private EditText etFname,etLname,etEmail,etPassword;
	private String fname,lname,email,password = "";
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private CheckBox checkbox;
	
	private PasswordValidator validator;
	
	private static final String PASSWORD_PATTERN = 
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.signup);
		
		lblSignup = (LinearLayout)findViewById(R.id.lblSignup);
		textView3 = (TextView)findViewById(R.id.textView3);
		
		checkbox = (CheckBox)findViewById(R.id.checkBox1);
		tvTerms = (TextView)findViewById(R.id.tvTerms);
		
		SpannableString content = new SpannableString("I accept Terms and Conditions.");
        content.setSpan(new UnderlineSpan(), 9, content.length(), 0);
        content.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFF00")), 9, content.length(), 0);
        tvTerms.setText(content);
        
        tvTerms.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(SignUpScreen.this,TermsAndConditions.class);
				startActivity(i);
			}
		});
		
		lblSignup.setOnClickListener(this);
		
		etPassword = (EditText)findViewById(R.id.etPassword);
		etEmail = (EditText)findViewById(R.id.etEmail);
		etLname = (EditText)findViewById(R.id.etLname);
		etFname = (EditText)findViewById(R.id.etFname);
		
		etFname.setTypeface(getRegularTypeFace());
		etEmail.setTypeface(getRegularTypeFace());
		etLname.setTypeface(getRegularTypeFace());
		etPassword.setTypeface(getRegularTypeFace());
		textView3.setTypeface(getRegularTypeFace());
		
		validator = new PasswordValidator();
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
			etPassword.setError("Please enter your password.");
			flag = false;
		}else if (!validator.validate(password)) {
			etPassword.setError("Password should be a 6 to 20 characters string with at least one digit, one upper case letter, one lower case letter and one special symbol.");
			flag = false;
		}else if (!checkbox.isChecked()) {
			
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
	
	public class PasswordValidator{
		
		  private Pattern pattern;
		  private Matcher matcher;
		  private String Password = "";
	 
		  private static final String PASSWORD_PATTERN = 
	              "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
		        
		  public PasswordValidator(){
			  pattern = Pattern.compile(PASSWORD_PATTERN);
		  }
		  
		  /**
		   * Validate password with regular expression
		   * @param password password for validation
		   * @return true valid password, false invalid password
		   */
		  public boolean validate(final String password){
			  this.Password = password;
			  matcher = pattern.matcher(Password);
			  return matcher.matches();    
		  }
	}
}
