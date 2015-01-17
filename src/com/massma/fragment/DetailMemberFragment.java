package com.massma.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.massma.BaseActivity;
import com.massma.R;

public class DetailMemberFragment extends Fragment implements OnClickListener{
	
	private BaseActivity base;
	private String name, address, contactParson, tata, mobile, fax, residential, email, web,hughes_no; 
	private TextView tv_name, tv_address, tv_contact_person, tv_tata, tv_fax, tv_residential, tv_email, tv_web;
	private LinearLayout ll_mobile_container,ll_huges_container,ll_email,ll_webaddress;
	
	public DetailMemberFragment(BaseActivity base,String name, String address, String contactParson, String tata, String mobile, String fax, String residential, String email, String web, String hughes_no){
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
		this.hughes_no = hughes_no;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_detail_member, container, false);
		
		ll_mobile_container = (LinearLayout)v.findViewById(R.id.ll_mobile_container);
		ll_huges_container = (LinearLayout)v.findViewById(R.id.ll_huges_container);
		
		if(hughes_no.length() > 0){
			System.out.println("!!i am here");
			ll_huges_container.setVisibility(View.VISIBLE);
		}else{
			ll_huges_container.setVisibility(View.GONE);
		}
		
		ll_email = (LinearLayout)v.findViewById(R.id.ll_email);
		ll_email.setOnClickListener(this);
		
		ll_webaddress = (LinearLayout)v.findViewById(R.id.ll_webaddress);
		ll_webaddress.setOnClickListener(this);
		
		tv_name = (TextView)v.findViewById(R.id.tv_name);
		tv_address = (TextView)v.findViewById(R.id.tv_address);
		tv_contact_person = (TextView)v.findViewById(R.id.tv_contact_person);
		tv_tata = (TextView)v.findViewById(R.id.tv_tata);
		tv_fax = (TextView)v.findViewById(R.id.tv_fax);
		tv_residential = (TextView)v.findViewById(R.id.tv_residential);
		tv_email = (TextView)v.findViewById(R.id.tv_email);
		tv_web = (TextView)v.findViewById(R.id.tv_web);
		
		tv_name.setText(name);
		tv_address.setText(address);
		tv_contact_person.setText(contactParson);
		tv_tata.setText(tata);
		
		if(mobile.contains("/"))
		{
		    String parts[] = mobile.split("/");
		    getChildView(parts);
		   
		}else{
			
			String parts[] = new String[1];
			parts[0] = mobile;
		    getChildView(parts);
		}
		
		if(hughes_no.contains("/"))
		{
		    String parts[] = hughes_no.split("/");
		    getChildViewHughes(parts);
		   
		}else if(hughes_no.length() > 0){
			
			String parts[] = new String[1];
			parts[0] = hughes_no;
			getChildViewHughes(parts);
		}
		
		tv_fax.setText(fax);
		tv_residential.setText(residential);
		tv_email.setText(email);
		tv_web.setText(web);
		
		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
						
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
	
	public void getChildView(String[] parts){
		for(int i = 0; i <parts.length ; i++){
			View v = View.inflate(base, R.layout.detail_page_row, null);
			
			final TextView tv_mobile_no = (TextView)v.findViewById(R.id.tv_mobile_no);
			tv_mobile_no.setText(parts[i]);
			
			ImageView iv_call = (ImageView)v.findViewById(R.id.iv_call);
			iv_call.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String first_four_digit = Integer.toString(getInitialNos(Integer.parseInt(tv_mobile_no.getText().toString().trim())));
					Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "022" +first_four_digit + tv_mobile_no.getText().toString().trim()));
					startActivity(intent1);
				}
			});
			ll_mobile_container.addView(v);
		}
	}
	
	public void getChildViewHughes(String[] parts){
		for(int i = 0; i <parts.length ; i++){
			View v = View.inflate(base, R.layout.detail_hughes_page_row, null);
			
			final TextView tv_mobile_no = (TextView)v.findViewById(R.id.tv_mobile_no);
			tv_mobile_no.setText(parts[i]);
			
			ImageView iv_call = (ImageView)v.findViewById(R.id.iv_call);
			iv_call.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String first_four_digit = Integer.toString(getInitialNos(Integer.parseInt(tv_mobile_no.getText().toString().trim())));
					Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "022" +first_four_digit + tv_mobile_no.getText().toString().trim()));
					startActivity(intent1);
				}
			});
			ll_huges_container.addView(v);
		}
	}
	
	public int getInitialNos(int val){
		int p=0;
		if(val>=1000 && val<=1099){
			p = 6645;
		}else if(val>=1100 && val<=1299){
			p = 6752;
		}else if(val>=1300 && val<=1499){
			p = 6615;
		}else if(val>=1500 && val<=1679){
			p = 6658;
		}else if(val>=1680 && val<=1999){
			p = 6615;
		}else if(val>=2000 && val<=3499){
			p = 6636;
		}else if(val>=3500 && val<=4999){
			p = 6639;
		}else if(val>=5000 && val<=5999){
			p = 6659;
		}else if(val>=6000 && val<=6399){
			p = 6749;
		}else if(val>=6400 && val<=6999){
			p = 6743;
		}else if(val>=7000 && val<=7099){
			p = 6615;
		}else if(val>=7100 && val<=7999){
			p = 6743;
		}else if(val>=8000 && val<=8099){
			p = 6615;
		}else if(val>=8100 && val<=8499){
			p = 6743;
		}else if(val>=8500 && val<=8999){
			p = 6651;
		}else if(val>=9000 && val<=9099){
			p = 6615;
		}else if(val>=9100 && val<=9199){
			p = 6743;
		}else if(val>=9200 && val<=9999){
			p = 6610;
		}
		return p;
	}
}
