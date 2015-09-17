package com.steelbuzz.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.steelbuzz.AboutActivity;
import com.steelbuzz.BaseActivity;
import com.steelbuzz.ContactUs;
import com.steelbuzz.FAQActivity;
import com.steelbuzz.FirmRegisterActivity;
import com.steelbuzz.R;
import com.steelbuzz.SignInScreen;
import com.steelbuzz.constant.Constants;
import com.steelbuzz.network.HttpClient;

public class Supportfragment extends BaseFragment implements OnClickListener {

	public BaseActivity activity;
	private ProgressDialog dialog;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = (BaseActivity) activity;
	}
	
	private RelativeLayout rl_about,rl_share,rl_eaq,rl_logout, rl_contactus, rl_review, rl_reg_firm;
	private RelativeLayout rl_follow_twitter,rl_likeus_fb,rl_help;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_support, container, false);

		rl_about = (RelativeLayout) v.findViewById(R.id.rl_about);
		rl_about.setOnClickListener(this);
		
		rl_reg_firm = (RelativeLayout) v.findViewById(R.id.rl_reg_firm);
		rl_reg_firm.setOnClickListener(this);
		
		rl_eaq = (RelativeLayout) v.findViewById(R.id.rl_eaq);
		rl_eaq.setOnClickListener(this);

		rl_help = (RelativeLayout) v.findViewById(R.id.rl_help);
		rl_help.setOnClickListener(this);
		
		rl_share = (RelativeLayout) v.findViewById(R.id.rl_share);
		rl_share.setOnClickListener(this);

		//rl_review = (RelativeLayout) v.findViewById(R.id.rl_review);
		//rl_review.setOnClickListener(this);
		
		rl_logout = (RelativeLayout)v.findViewById(R.id.rl_logout);
		rl_logout.setOnClickListener(this);
		
		rl_likeus_fb = (RelativeLayout)v.findViewById(R.id.rl_likeus_fb);
		rl_likeus_fb.setOnClickListener(this);
		
		rl_follow_twitter = (RelativeLayout)v.findViewById(R.id.rl_follow_twitter);
		rl_follow_twitter.setOnClickListener(this);

		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_about:
			
			startActivity(new Intent(getActivity(), AboutActivity.class));

			break;
		case R.id.rl_eaq:
			startActivity(new Intent(getActivity(), FAQActivity.class));
			break;
			
		case R.id.rl_reg_firm:
			startActivity(new Intent(getActivity(), FirmRegisterActivity.class));
			break;
			
		case R.id.rl_follow_twitter:
			Intent browserIntent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/steel_buzz"));
			startActivity(browserIntent1);
			break;
			
		case R.id.rl_likeus_fb:
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/realsteelbuzz"));
			startActivity(browserIntent);
			break;
		case R.id.rl_help:
			Intent i = new Intent(activity,ContactUs.class);
			startActivity(i);
			break;

		/*case R.id.rl_review:
			final String appPackageName = getActivity().getPackageName(); // getPackageName() from Context or Activity object
			try {
			    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
			} catch (android.content.ActivityNotFoundException anfe) {
			    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
			}
			break;*/

		/*case R.id.rl_version:
			final Dialog dialog = new Dialog(getActivity());
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.dialog_version);
			
			TextView tv_device_name = (TextView)dialog.findViewById(R.id.tv_device_name);
			String str = android.os.Build.MODEL;
			tv_device_name.setText(str);
			
			TextView tv_android_version = (TextView)dialog.findViewById(R.id.tv_version_name);
			int sdkVersion = android.os.Build.VERSION.SDK_INT;
			tv_android_version.setText(""+sdkVersion);
			
			TextView tv_app_name = (TextView)dialog.findViewById(R.id.tv_app_name);
			tv_app_name.setText("Masama");
			
			TextView tv_app_version = (TextView)dialog.findViewById(R.id.tv_app_version);
			PackageInfo pInfo;
			try {
				pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
				tv_app_version.setText(pInfo.versionName);
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
			
			Button btn_ok = (Button)dialog.findViewById(R.id.btn_ok);
			btn_ok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialog.cancel();
				}
			});
			
			dialog.show();
			break;
			*/
			
		case R.id.rl_share:
			Intent shareIntent = new Intent();
			shareIntent.setAction(Intent.ACTION_SEND);
			shareIntent.setType("text/plain");
			shareIntent.putExtra(Intent.EXTRA_TEXT, "Hi, I am using SteelBuzz.Connecting you to Steel Industry.");
			startActivity(Intent.createChooser(shareIntent, "Share your thoughts"));
			break;
		case R.id.rl_logout:
			
			if (activity.app.getUserinfo().getLogin_type().equalsIgnoreCase("social")) {
				new doLogout().execute();
			}else{
				activity.app.getUserinfo().setSession(false);
				Intent i1 = new Intent(activity,SignInScreen.class);
				startActivity(i1);
				getActivity().finish();
			}
		}
	}

	public class doLogout extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			doShowLoading();
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				JSONObject ob = new JSONObject();
				ob.put("id", activity.app.getUserinfo().getUser_id());
				String response = HttpClient.SendHttpPost(Constants.SOCIAL_LOGOUT, ob.toString());
				if(response != null){
					JSONObject obj = new JSONObject(response);
					if(obj.getBoolean("status")){
						return true;
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
				activity.app.getUserinfo().setSession(false);
				Intent i = new Intent(activity,SignInScreen.class);
				startActivity(i);
				getActivity().finish();
			}else{
				Toast.makeText(activity, "Some error occured.Please try again..", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	public void doShowLoading() {
		dialog = new ProgressDialog(activity);
		dialog.setMessage("Logging out...");
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setIndeterminate(true);
		dialog.setCancelable(false);
		dialog.show();
	}
	
	public void doRemoveLoading() {
		dialog.dismiss();
	}
}
