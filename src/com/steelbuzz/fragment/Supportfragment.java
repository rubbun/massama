package com.steelbuzz.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.steelbuzz.R;

public class Supportfragment extends Fragment implements OnClickListener {

	
	private RelativeLayout rl_about, rl_contactus, rl_review, rl_version;

	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_support, container, false);

		rl_about = (RelativeLayout) v.findViewById(R.id.rl_about);
		rl_about.setOnClickListener(this);

		rl_contactus = (RelativeLayout) v.findViewById(R.id.rl_contactus);
		rl_contactus.setOnClickListener(this);

		rl_review = (RelativeLayout) v.findViewById(R.id.rl_review);
		rl_review.setOnClickListener(this);

		rl_version = (RelativeLayout) v.findViewById(R.id.rl_version);
		rl_version.setOnClickListener(this);

		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_about:

			break;
		case R.id.rl_contactus:
			Intent i = new Intent(Intent.ACTION_SEND);
			i.setType("plain/text");
			i.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ "udit@yahoo.com" });
			i.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
			i.putExtra(android.content.Intent.EXTRA_TEXT, "");
			startActivity(Intent.createChooser(i, "Send email"));
			break;

		case R.id.rl_review:
			final String appPackageName = getActivity().getPackageName(); // getPackageName() from Context or Activity object
			try {
			    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
			} catch (android.content.ActivityNotFoundException anfe) {
			    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
			}
			break;

		case R.id.rl_version:
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

		}
	}

}
