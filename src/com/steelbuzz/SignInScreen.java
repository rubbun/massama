package com.steelbuzz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AppEventsLogger;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;
import com.flurry.android.FlurryAgent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.steelbuzz.constant.Constants;
import com.steelbuzz.network.HttpClient;

public class SignInScreen extends BaseActivity implements OnClickListener,ConnectionCallbacks, OnConnectionFailedListener{

	private Intent mIntent;
	private LinearLayout lblSignin, lblSignup;
	private String email,password = "";
	private EditText etEmail,etPassword;
	public InputMethodManager imm;
	private String socialLogintype = "gplus";
	private TextView txtSignUp,txtSignIn,txtOr,txtDonttHave;
	
	private String firstName,lastName,userId;
	
	public String user_id, profile_image, profile_url, name = null;
	
	private static final int RC_SIGN_IN = 0;
    // Logcat tag
    private static final String TAG = "MainActivity";
    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;
 
    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
    private ConnectionResult mConnectionResult;
    private SignInButton btnSignIn;
    
    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    
    private ImageView iv_gplus,iv_twitter;
    
    // For Twitter
    
    /* Shared preference keys */
	private static final String PREF_NAME = "sample_twitter_pref";
	private static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
	private static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
	private static final String PREF_KEY_TWITTER_LOGIN = "is_twitter_loggedin";
	private static final String PREF_USER_NAME = "twitter_user_name";
	
	/* Any number for uniquely distinguish your request */
	public static final int WEBVIEW_REQUEST_CODE = 100;

	private ProgressDialog pDialog;

	private static Twitter twitter;
	private static RequestToken requestToken;
	
	private static SharedPreferences mSharedPreferences;

	private String consumerKey = null;
	private String consumerSecret = null;
	private String callbackUrl = null;
	private String oAuthVerifier = null;
	Session.StatusCallback statusCallback = new SessionStatusCallback();
	private static List<String> permissions;
    
	//For facebook
	private UiLifecycleHelper uiHelper;
	private final String PENDING_ACTION_BUNDLE_KEY = "com.facebook.samples.hellofacebook:PendingAction";
	private PendingAction pendingAction = PendingAction.NONE;
	private GraphUser user;
	private boolean isUiUpdateCall = false; 
	private LoginButton loginButton;
	private TextView txtForgotPassword;
	
	private enum PendingAction {
        NONE,
        POST_PHOTO,
        POST_STATUS_UPDATE
    }
	
	private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };
	
    private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
        @Override
        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
            Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
        }

        @Override
        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
            Log.d("HelloFacebook", "Success!");
        }
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin);
		
		if (app.getUserinfo().isSession()) {
			mIntent = new Intent(SignInScreen.this,MainActivity.class);
			startActivity(mIntent);
			finish();
		}

		lblSignin = (LinearLayout) findViewById(R.id.lblSignin);
		lblSignin.setOnClickListener(this);

		lblSignup = (LinearLayout) findViewById(R.id.lblSignup);
		lblSignup.setOnClickListener(this);
		
		txtOr = (TextView) findViewById(R.id.txtOr);
		txtOr.setTypeface(getRegularTypeFace());
		
		txtDonttHave = (TextView) findViewById(R.id.txtDonttHave);
		txtDonttHave.setTypeface(getRegularTypeFace());
		
		txtSignIn = (TextView) findViewById(R.id.txtSignIn);
		txtSignIn.setTypeface(getRegularTypeFace());
		
		txtForgotPassword = (TextView) findViewById(R.id.txtForgotPassword);
		txtForgotPassword.setTypeface(getRegularTypeFace());
		
		txtSignUp = (TextView) findViewById(R.id.txtSignUp);
		txtSignUp.setTypeface(getLightTypeFace());
		
		etEmail = (EditText)findViewById(R.id.etEmail);
		etPassword = (EditText)findViewById(R.id.etPassword);
		
		etEmail.setTypeface(getRegularTypeFace());
		etPassword.setTypeface(getRegularTypeFace());
		
		btnSignIn = (SignInButton) findViewById(R.id.btn_gplus_signin);
        btnSignIn.setOnClickListener(this);
        
        iv_gplus = (ImageView)findViewById(R.id.iv_gplus);
		iv_gplus.setOnClickListener(this);
		
		iv_twitter = (ImageView)findViewById(R.id.iv_twitter);
		iv_twitter.setOnClickListener(this);
		
		permissions = new ArrayList<String>();
		permissions.add("email"); 
		
		Session session = Session.getActiveSession();
		if (session == null) {
			if (savedInstanceState != null) {
				session = Session.restoreSession(this, null, statusCallback,
						savedInstanceState);
			}
			if (session == null) {
				session = new Session(this);
			}
			Session.setActiveSession(session);
			session.addCallback(statusCallback);
			if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
				session.openForRead(new Session.OpenRequest(this).setCallback(
						statusCallback).setPermissions(permissions));
			}
		}
		
		if( Build.VERSION.SDK_INT >= 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy); 
		}
		
		uiHelper = new UiLifecycleHelper(this, callback);
	    uiHelper.onCreate(savedInstanceState);
	    if (savedInstanceState != null) {
            String name = savedInstanceState.getString(PENDING_ACTION_BUNDLE_KEY);
            pendingAction = PendingAction.valueOf(name);
        }
	    
	    loginButton = (LoginButton) findViewById(R.id.login_button);
		loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
	        @Override
	        public void onUserInfoFetched(GraphUser user) {
	        	SignInScreen.this.user = user;	        	
	        	isUiUpdateCall = true;
	        	loginButton.setReadPermissions(Arrays.asList("public_profile, email"));
	        	updateFacebookUI();
	            handlePendingAction();
	        }
	    });
		
		  mGoogleApiClient = new GoogleApiClient.Builder(this,this,this)
	        .addConnectionCallbacks(this)
	        .addOnConnectionFailedListener(this)
	        .addApi(Plus.API)
	        .addScope(Plus.SCOPE_PLUS_LOGIN)
	        .build();
		  
		  /* initializing twitter parameters from string.xml */
			initTwitterConfigs();

			/* Enabling strict mode */
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			
			/* Check if required twitter keys are set */
			if (TextUtils.isEmpty(consumerKey) || TextUtils.isEmpty(consumerSecret)) {
				Toast.makeText(this, "Twitter key and secret not configured",
						Toast.LENGTH_SHORT).show();
				return;
			}
			
			/* Initialize application preferences */
			mSharedPreferences = getSharedPreferences(PREF_NAME, 0);
			
			 Map<String, String> articleParams = new HashMap<String, String>();
			 FlurryAgent.init(this, "2MXJMWHWXCK25DFSW75R");
			 FlurryAgent.logEvent("LoginActivity", articleParams, true);
			 FlurryAgent.endTimedEvent("LoginActivity");
	
			 
			 txtForgotPassword = (TextView)findViewById(R.id.txtForgotPassword);
			 txtForgotPassword.setOnClickListener(this);
	}
	
	private boolean isValid() {
		boolean flag = true;
		email = etEmail.getText().toString().trim();
		password = etPassword.getText().toString().trim();
		if(email.length() == 0){
			etEmail.setError("Please enter your email id");
			flag = false;
		}else if(password.length() == 0){
			etPassword.setError("Please enter your password");
			flag = false;
		}
		return flag;
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.lblSignin:
			if(isValid()){
				sendValueToServer();
			}
			break;

		case R.id.lblSignup:
			mIntent = new Intent(SignInScreen.this, SignUpScreen.class);
			startActivity(mIntent);
			
			break;
			
		case R.id.iv_gplus:
			socialLogintype = "gplus";
			int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
			if(status == ConnectionResult.SUCCESS) {
				signInWithGplus();
			}else{
				Toast.makeText(getApplicationContext(), "Please install google play service first...", Toast.LENGTH_LONG).show();
			}
			break;
			
		case R.id.btn_gplus_signin:
			break;
			
		case R.id.iv_twitter:
			socialLogintype = "twitter";
			boolean isLoggedIn = mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
			 // if already logged in, then hide login layout and show share layout 
			if (isLoggedIn) {
				
				//showTwitterShareDialog();
				
			} else {			
				loginToTwitter();
			}
			break;
			
		case R.id.txtForgotPassword:
			Intent i = new Intent(SignInScreen.this,ForgotPassword.class);
			startActivity(i);
			
			break;
		}
	}
	
	/* Reading twitter essential configuration parameters from strings.xml */
	private void initTwitterConfigs() {
		consumerKey = getString(R.string.twitter_consumer_key);
		consumerSecret = getString(R.string.twitter_consumer_secret);
		callbackUrl = getString(R.string.twitter_callback);
		oAuthVerifier = getString(R.string.twitter_oauth_verifier);
	}
	
	private void loginToTwitter() {
		//boolean isLoggedIn = mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
		
		//if (!isLoggedIn) {
			final ConfigurationBuilder builder = new ConfigurationBuilder();
			builder.setOAuthConsumerKey(consumerKey);
			builder.setOAuthConsumerSecret(consumerSecret);

			final Configuration configuration = builder.build();
			final TwitterFactory factory = new TwitterFactory(configuration);
			twitter = factory.getInstance();

			try {
				requestToken = twitter.getOAuthRequestToken(callbackUrl);

				/**
				 *  Loading twitter login page on webview for authorization 
				 *  Once authorized, results are received at onActivityResult
				 *  */
				final Intent intent = new Intent(this, TwitterWebViewActivity.class);
				intent.putExtra(TwitterWebViewActivity.EXTRA_URL, requestToken.getAuthenticationURL());
				startActivityForResult(intent, WEBVIEW_REQUEST_CODE);
				
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		//} 
	}
	
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (!result.hasResolution()) {
	        GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
	                0).show();
	        return;
	    }
	 
	    if (!mIntentInProgress) {
	        // Store the ConnectionResult for later usage
	        mConnectionResult = result;
	 
	        if (mSignInClicked) {
	            // The user has already clicked 'sign-in' so we attempt to
	            // resolve all
	            // errors until the user is signed in, or they cancel.
	            resolveSignInError();
	        }
	    }
	}
	
	 @Override
	    public void onConnectionSuspended(int arg0) {
	        mGoogleApiClient.connect();
	        updateUI(false);
	    }
	
	 private void signInWithGplus() {
	        if (!mGoogleApiClient.isConnecting()) {
	            mSignInClicked = true;
	            resolveSignInError();
	        }
	    }
	 
	 protected void onStart() {
	        super.onStart();
	        mGoogleApiClient.connect();
	        FlurryAgent.onStartSession(this, "2MXJMWHWXCK25DFSW75R");
	  	  	FlurryAgent.setLogEnabled(true);
	  	  	FlurryAgent.setLogEvents(true);
	  	  	FlurryAgent.setLogLevel(Log.VERBOSE);
	    }
	 
	    protected void onStop() {
	        super.onStop();
	        if (mGoogleApiClient.isConnected()) {
	            mGoogleApiClient.disconnect();
	        }
	        FlurryAgent.onEndSession(this);
	    }
	 
	 /**
	     * Method to resolve any signin errors
	     * */
	    private void resolveSignInError() {
	        if (mConnectionResult.hasResolution()) {
	            try {
	                mIntentInProgress = true;
	                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
	            } catch (SendIntentException e) {
	                mIntentInProgress = false;
	                mGoogleApiClient.connect();
	            }
	        }
	    }
	
	private void sendValueToServer() {
		new CallServerForLogin().execute();
	}

	public class CallServerForLogin extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			doShowLoading();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			try {
			JSONObject req = new JSONObject();
				req.put("email", email);
				req.put("pass", password);

				String response = HttpClient.SendHttpPost(Constants.LOGIN, req.toString());
				if (response != null) {
					try {
						JSONObject ob = new JSONObject(response);
						if (ob.getBoolean("status")) {
							JSONObject obj = ob.getJSONObject("user");
							app.getUserinfo().SetUserInfo(obj.getString("firstname"),
									obj.getString("lastname"),
									obj.getString("email"),
									obj.getString("id"),
									true,"normal");
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
				Map<String, String> articleParams = new HashMap<String, String>();
				FlurryAgent.init(SignInScreen.this, "2MXJMWHWXCK25DFSW75R");
				FlurryAgent.logEvent("LoginActivity", articleParams, true);
				FlurryAgent.endTimedEvent("LoginActivity");
		
				Toast.makeText(getApplicationContext(), "Welcome to SteelBuzz!!!", Toast.LENGTH_LONG).show();
				mIntent = new Intent(SignInScreen.this,MainActivity.class);
				startActivity(mIntent);
				hideKeyBoard(etEmail);
				finish();
			}else{
				Toast.makeText(getApplicationContext(), "Invalid email id or password..", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	public void hideKeyBoard(EditText et) {
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
	}
	
	private void onSessionStateChange(Session session, SessionState state,
			Exception exception)
	{
		if (pendingAction != PendingAction.NONE
				&& (exception instanceof FacebookOperationCanceledException || exception instanceof FacebookAuthorizationException))
		{
			new AlertDialog.Builder(SignInScreen.this)
					.setTitle(R.string.cancelled)
					.setMessage(R.string.permission_not_granted)
					.setPositiveButton(R.string.ok, null).show();
			pendingAction = PendingAction.NONE;
		}
		else if (state == SessionState.OPENED_TOKEN_UPDATED)
		{
			handlePendingAction();
		}
	}
	
	@SuppressWarnings("incomplete-switch")
	private void handlePendingAction()
	{
		PendingAction previouslyPendingAction = pendingAction;
		pendingAction = PendingAction.NONE;

		switch (previouslyPendingAction)
		{
			case POST_PHOTO:
				break;
			case POST_STATUS_UPDATE:
				break;
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);

		outState.putString(PENDING_ACTION_BUNDLE_KEY, pendingAction.name());
	}
	
	private void updateFacebookUI()
	{
		if (isUiUpdateCall)
		{
			isUiUpdateCall = false;
			Session session = Session.getActiveSession();
			boolean enableButtons = (session != null && session.isOpened());

			if (enableButtons && user != null)
			{				
					user_id = user.getId();
					name = user.getName();										
					//email = user.getProperty("email").toString();
					profile_image = "https://graph.facebook.com/"+user.getId()+"/picture";
					profile_url = "https://graph.facebook.com/"+user.getId();
					
					SendDetailsToserver();
			}
		}
	}
	
	public void SendDetailsToserver(){
		
		Thread t = new Thread()
		{
			public void run()
			{
				JSONObject obj = new JSONObject();
				try {
					obj.put("email", "");
					email = "";
					obj.put("username", user_id);

					if(name.contains(" ")){
						String[] splited = name.split("\\s+");
						obj.put("firstname", splited[0]);
						obj.put("lastname", splited[1]);
						firstName = splited[0];
						lastName = splited[1];
					}else{
						obj.put("firstname", name);
						firstName = name;
						lastName = "";
					}
					obj.put("pass", "00");
					String response = HttpClient.SendHttpPost(Constants.REGISTER, obj.toString());
					if(response != null){
						
						JSONObject ob = new JSONObject(response);
						if(ob.getBoolean("status")){
							Map<String, String> articleParams = new HashMap<String, String>();
							FlurryAgent.init(SignInScreen.this, "2MXJMWHWXCK25DFSW75R");
							FlurryAgent.logEvent("LoginActivity", articleParams, true);
							FlurryAgent.endTimedEvent("LoginActivity");
							
							app.getUserinfo().SetUserInfo(firstName,
									lastName,
									email,
									user_id,
									true,"social");
					
							/*String first_name = ob.getString("first_name");
							String last_name = ob.getString("last_name");
							String user_id = ob.getString("user_id");
							String reservation_type = ob.getString("reservation_type");
							boolean checkin_status = ob.getBoolean("checkin_status");
							String rev_id = null,reservation_code = null;
							JSONArray object = ob.getJSONArray("reservation_detail");
							for(int i = 0;i<object.length();i++){
								rev_id = object.getJSONObject(i).getString("reservation_id");
								reservation_code = object.getJSONObject(i).getString("reservation_code");
							}*/
														
							/*app.getUserInfo().SetUserInfo(first_name,
									last_name,
									user_id,
									rev_id,
									reservation_code,
									reservation_type,
									checkin_status);
							
							app.getLogininfo().setLoginInfo(true);*/
						}
						UpdateUiResult(true);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}							
			}
		};
	t.start();
}
	
	private void UpdateUiResult(boolean b) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
				if(Session.getActiveSession() != null){
					Session.getActiveSession().closeAndClearTokenInformation();
					Session.setActiveSession(null);
				}
				
				if(mGoogleApiClient.isConnected()){
					signOutFromGplus();
				}
				
				mIntent = new Intent(SignInScreen.this,MainActivity.class);
				startActivity(mIntent);
				finish();
			}
		});
	}
	
	 /**
     * Sign-out from google
     * */
    private void signOutFromGplus() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
            updateUI(false);
        }
    }
    
    /**
     * Revoking access from google
     * */
    private void revokeGplusAccess() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status arg0) {
                            Log.e(TAG, "User access revoked!");
                            mGoogleApiClient.connect();
                            updateUI(false);
                        }
             });
        }
    }
    
    @Override
    public void onConnected(Bundle arg0) {
        mSignInClicked = false;
        // Get user's information
        getProfileInformation();
 
        // Update the UI after signin
        //updateUI(true);
    }
    
    /**
     * Updating the UI, showing/hiding buttons and profile layout
     * */
    private void updateUI(boolean isSignedIn) {
    	//goNextScreen(false);
    }
	
	 @Override
	    protected void onActivityResult(int requestCode, int responseCode,Intent data) {
	    	super.onActivityResult(requestCode, responseCode, data);
	    	if (requestCode == RC_SIGN_IN) {
	            if (responseCode != RESULT_OK) {
	                mSignInClicked = false;
	            }
	            mIntentInProgress = false;
	 
	            if (!mGoogleApiClient.isConnecting()) {
	                mGoogleApiClient.connect();
	            }
	        }else if(data != null){
				if(socialLogintype.equalsIgnoreCase("twitter")){
					String verifier = data.getExtras().getString(oAuthVerifier);
					try {
						AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);

						long userID = accessToken.getUserId();
						final User user = twitter.showUser(userID);
						String username = user.getName();
						
						saveTwitterInfo(accessToken);
						
					} catch (Exception e) {
						Log.e("Twitter Login Failed", e.getMessage());
					}
				
				}else{
					uiHelper.onActivityResult(requestCode, responseCode, data, dialogCallback);
				}	
			}
	    	
	    	else{
	        	uiHelper.onActivityResult(requestCode, responseCode, data, dialogCallback);
	        }
	    }
	 
	 /**
		 * Saving user information, after user is authenticated for the first time.
		 * You don't need to show user to login, until user has a valid access toen
		 */
		private void saveTwitterInfo(AccessToken accessToken) {
			
			long userID = accessToken.getUserId();
			
			User user;
			try {
				user = twitter.showUser(userID);
			
				String username = user.getName();

				/* Storing oAuth tokens to shared preferences */
				Editor e = mSharedPreferences.edit();
				e.putString(PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
				e.putString(PREF_KEY_OAUTH_SECRET, accessToken.getTokenSecret());
				e.putBoolean(PREF_KEY_TWITTER_LOGIN, true);
				e.putString(PREF_USER_NAME, username);
				e.commit();

			} catch (TwitterException e1) {
				e1.printStackTrace();
			}
		}
	 
	 /**
	     * Fetching user's information name, email, profile pic
	     * */
	    private void getProfileInformation() {
	        try {
	            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
	                Person currentPerson = Plus.PeopleApi
	                        .getCurrentPerson(mGoogleApiClient);
	                String personName = currentPerson.getDisplayName();
	                String personPhotoUrl = null;
	                if(currentPerson.getImage().getUrl() != null){
	                	personPhotoUrl = currentPerson.getImage().getUrl();
	                }else{
	                	personPhotoUrl = "";
	                }
	                String personGooglePlusProfile = currentPerson.getUrl();
	                String gplusemail = Plus.AccountApi.getAccountName(mGoogleApiClient);
	 
	                Log.e(TAG, "Name: " + personName + ", plusProfile: "
	                        + personGooglePlusProfile + ", email: " + email
	                        + ", Image: " + personPhotoUrl);
	                
	                user_id = currentPerson.getId();
	                profile_image = personPhotoUrl;
	                profile_url = personGooglePlusProfile;
	                
	                name = personName;
	                this.email = gplusemail;
	                
	               /* txtName.setText(personName);
	                txtEmail.setText(email);*/
	 
	                // by default the profile url gives 50x50 px image only
	                // we can replace the value with whatever dimension we want by
	                // replacing sz=X
	                personPhotoUrl = personPhotoUrl.substring(0,
	                        personPhotoUrl.length() - 2)
	                        + PROFILE_PIC_SIZE;
	 
	               // new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);
	                
	                Thread t = new Thread()
					{
						public void run()
						{
							JSONObject obj = new JSONObject();
							try {
								obj.put("uid", user_id);
								obj.put("email", email);
								obj.put("profile_url", profile_url);
								if(name.contains(" ")){
									String[] splited = name.split("\\s+");
									obj.put("firstname", splited[0]);
									obj.put("lastname", splited[1]);
									
									firstName = splited[0];
									lastName = splited[1];
								}else{
									obj.put("firstname", name);
									firstName = name;
									lastName = "";
								}
								obj.put("profile_image", profile_image);
								obj.put("type", "google");
								obj.put("device_type", "android");
								
								String response = HttpClient.SendHttpPost(Constants.REGISTER, obj.toString());
								if(response != null){
									JSONObject ob = new JSONObject(response);
									if(ob.getBoolean("status")){
										
										Map<String, String> articleParams = new HashMap<String, String>();
										FlurryAgent.init(SignInScreen.this, "2MXJMWHWXCK25DFSW75R");
										FlurryAgent.logEvent("LoginActivity", articleParams, true);
										FlurryAgent.endTimedEvent("LoginActivity");
								
										
										app.getUserinfo().SetUserInfo(firstName,
												lastName,
												email,
												user_id,
												true,"social");
										/*String first_name = ob.getString("first_name");
										String last_name = ob.getString("last_name");
										String user_id = ob.getString("user_id");
										String reservation_type = ob.getString("reservation_type");
										boolean checkin_status = ob.getBoolean("checkin_status");
										
										String rev_id = null,reservation_code = null;
										JSONArray object = ob.getJSONArray("reservation_detail");
										for(int i = 0;i<object.length();i++){
											rev_id = object.getJSONObject(i).getString("reservation_id");
											reservation_code = object.getJSONObject(i).getString("reservation_code");
										}
										
										app.getUserInfo().SetUserInfo(first_name,
												last_name,
												user_id,
												rev_id,
												reservation_code,
												reservation_type,
												checkin_status);
										
										app.getLogininfo().setLoginInfo(true);*/
										UpdateUiResult(true);
									}
									
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}							
						}
					};
				t.start();
	                
	            } else {
	                Toast.makeText(getApplicationContext(),
	                        "Person information is null", Toast.LENGTH_LONG).show();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    private class SessionStatusCallback implements Session.StatusCallback {

			@Override
			public void call(Session session, SessionState state,
					Exception exception) {
				//Check if Session is Opened or not
				processSessionStatus(session, state, exception);
			}
		}
	    
	    @SuppressWarnings("deprecation")
		public void processSessionStatus(Session session, SessionState state,
				Exception exception) {
			if (session != null && session.isOpened()) {
				if (session.getPermissions().contains("email")) {
					//Show Progress Dialog
					//dialog = new ProgressDialog(Connect.this);
					//dialog.setMessage("Loggin in..");
					//dialog.show();
					Request.executeMeRequestAsync(session,
							new Request.GraphUserCallback() {

								@Override
								public void onCompleted(GraphUser user,
										Response response) {
									
									if(user != null){
										
										System.out.println("!!name"+user.getFirstName());
										
										//publishFeedDialog();
									}
								}
							});
				} else {
					session.requestNewReadPermissions(new Session.NewPermissionsRequest(
							SignInScreen.this, permissions));
				}
			}
		}
	 
	 @Override
		protected void onResume()
		{
			super.onResume();
			uiHelper.onResume();
			AppEventsLogger.activateApp(this);
		}
	    
	    @Override
		public void onPause()
		{
			super.onPause();
			uiHelper.onPause();
		}

		@Override
		public void onDestroy()
		{
			super.onDestroy();
			uiHelper.onDestroy();
		}	
}
