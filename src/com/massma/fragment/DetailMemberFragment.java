package com.massma.fragment;

import android.content.Intent;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.massma.BaseActivity;
import com.massma.R;

public class DetailMemberFragment extends Fragment implements OnClickListener{
	
	private BaseActivity base;
	private String name, address, contactParson, tata, mobile, fax, residential, email, web; 
	private TextView tv_name, tv_address, tv_contact_person, tv_tata, tv_mobile, tv_fax, tv_residential, tv_email, tv_web;
	private ImageView iv_call;
	private LinearLayout ll_mobile_second,ll_mobile_third,ll_email,ll_webaddress;
	private TextView tv_mobile_second,tv_mobile_third,tv_hughes_no,tv_hughes_no_two,tv_hughes_no_three;
	private ImageView iv_call_second,iv_call_third,iv_hughes_call_one,iv_hughes_call_two,iv_hughes_call_three;
	
	public DetailMemberFragment(BaseActivity base,String name, String address, String contactParson, String tata, String mobile, String fax, String residential, String email, String web){
		this.base = base;
		this.name = name;
		this.address = address;
		this.contactParson = contactParson;
		this.tata = tata;
		this.mobile = mobile;
		this.fax = fax;
		this.residential = residential;
		this.email = email;
		this.web = web;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_detail_member, container, false);
		
		ll_mobile_second = (LinearLayout)v.findViewById(R.id.ll_mobile_second);
		ll_mobile_second.setVisibility(View.GONE);
		ll_mobile_third = (LinearLayout)v.findViewById(R.id.ll_mobile_third);
		ll_mobile_third.setVisibility(View.GONE);
		
		ll_email = (LinearLayout)v.findViewById(R.id.ll_email);
		ll_email.setOnClickListener(this);
		
		ll_webaddress = (LinearLayout)v.findViewById(R.id.ll_webaddress);
		ll_webaddress.setOnClickListener(this);
		
		tv_mobile_second = (TextView)v.findViewById(R.id.tv_mobile_second);
		tv_mobile_third = (TextView)v.findViewById(R.id.tv_mobile_third);
		
		iv_call_second = (ImageView)v.findViewById(R.id.iv_call_second);
		iv_call_third = (ImageView)v.findViewById(R.id.iv_call_third);
		
		tv_name = (TextView)v.findViewById(R.id.tv_name);
		tv_address = (TextView)v.findViewById(R.id.tv_address);
		tv_contact_person = (TextView)v.findViewById(R.id.tv_contact_person);
		tv_tata = (TextView)v.findViewById(R.id.tv_tata);
		tv_mobile = (TextView)v.findViewById(R.id.tv_mobile);
		tv_fax = (TextView)v.findViewById(R.id.tv_fax);
		tv_residential = (TextView)v.findViewById(R.id.tv_residential);
		tv_email = (TextView)v.findViewById(R.id.tv_email);
		tv_web = (TextView)v.findViewById(R.id.tv_web);
		iv_call = (ImageView)v.findViewById(R.id.iv_call);
		
		tv_hughes_no = (TextView)v.findViewById(R.id.tv_hughes_no);
		tv_hughes_no.setVisibility(View.GONE);
		
		tv_hughes_no_two = (TextView)v.findViewById(R.id.tv_hughes_no_two);
		tv_hughes_no_two.setVisibility(View.GONE);
		
		tv_hughes_no_three = (TextView)v.findViewById(R.id.tv_hughes_no_three);
		tv_hughes_no_three.setVisibility(View.GONE);
		
		tv_name.setText(name);
		tv_address.setText(address);
		tv_contact_person.setText(contactParson);
		tv_tata.setText(tata);
		
		if(mobile.contains("/"))
		{
		    String parts[] = mobile.split("/");
		    tv_mobile.setText(parts[0]);
		    tv_mobile_second.setText(parts[1]);
		    ll_mobile_second.setVisibility(View.VISIBLE);
		    iv_call_second.setOnClickListener(this);
		}else{
			tv_mobile.setText(mobile);
		}
		
		tv_fax.setText(fax);
		tv_residential.setText(residential);
		tv_email.setText(email);
		tv_web.setText(web);
		iv_call.setOnClickListener(this);
		
		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_call:
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobile));
			startActivity(intent);
			break;
			
		case R.id.iv_call_second:
			Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tv_mobile_second.getText().toString().trim()));
			startActivity(intent1);
			break;
			
		case R.id.ll_webaddress:
			if(!web.contains("--")){
				String url = web;
				if (!url.startsWith("http://") && !url.startsWith("https://"))
					   url = "http://" + url;
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(browserIntent);
			}
			break;
			
		case R.id.ll_email:
			if(!email.contains("--")){
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("plain/text");
				i.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ email });
				i.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
				i.putExtra(android.content.Intent.EXTRA_TEXT, "");
				startActivity(Intent.createChooser(i, "Send email"));
			}
		}
	}
}
