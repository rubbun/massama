package com.steelbuzz.subfragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.steelbuzz.BaseActivity;
import com.steelbuzz.R;
import com.steelbuzz.bean.Member;
import com.steelbuzz.fragment.BaseFragment;

public class MailInfoFragment extends BaseFragment{
	private Member member;
	private BaseActivity base;
	private LinearLayout ll_email_container,ll_huges_container,ll_website_container;
	private TextView tv_address,tv_header_second_name,tv_contact_person,tv_third_header,tv_header_name,tv_fax;
	public MailInfoFragment(Member bean, BaseActivity base){
		this.member = bean;
		this.base = base;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.subfragment_mail_info, container, false);
		
		tv_address = (TextView)v.findViewById(R.id.tv_address);
		tv_contact_person = (TextView)v.findViewById(R.id.tv_contact_person);
		tv_fax = (TextView)v.findViewById(R.id.tv_fax);
		
		tv_third_header = (TextView)v.findViewById(R.id.tv_third_header);
		tv_header_second_name = (TextView)v.findViewById(R.id.tv_header_second_name);
		tv_header_name = (TextView)v.findViewById(R.id.tv_header_name);
		
		tv_header_second_name.setTypeface(base.getSemiBoldTypeFace());
		tv_header_name.setTypeface(base.getSemiBoldTypeFace());
		tv_address.setTypeface(base.getRegularTypeFace());
		tv_third_header.setTypeface(base.getSemiBoldTypeFace());
		
		ll_email_container = (LinearLayout)v.findViewById(R.id.ll_email_container);
		ll_huges_container = (LinearLayout)v.findViewById(R.id.ll_huges_container);
		ll_website_container = (LinearLayout)v.findViewById(R.id.ll_website_container);
		
		ll_email_container.setVisibility(View.GONE);
		ll_huges_container.setVisibility(View.GONE);
		ll_website_container.setVisibility(View.GONE);
		
		tv_header_name.setText("Mail");
		tv_header_second_name.setText("Website");
		tv_third_header.setText("Fax");
		
		if(member.getEmail().contains("/"))
		{
			tv_address.setVisibility(View.GONE);
			ll_email_container.setVisibility(View.VISIBLE);
		    String parts[] = member.getEmail().split("/");
		    getChildView(parts);
		   
		}else{
			tv_address.setVisibility(View.VISIBLE);
			ll_email_container.setVisibility(View.GONE);
			tv_address.setText("------");
		}
		if(member.getWeb().equalsIgnoreCase("")){
			tv_contact_person.setText("------");
			
		}else{
			String text = "<a href="+member.getWeb()+"> "+member.getWeb()+" </a>";
			tv_contact_person.setText(Html.fromHtml(text));
		}
		tv_contact_person.setVisibility(View.VISIBLE);
		tv_third_header.setTypeface(base.getSemiBoldTypeFace());
		
		if(member.getFax().equalsIgnoreCase("")){
			tv_fax.setText("------");
		}else{
			tv_fax.setText(""+member.getFax());
		}
		tv_fax.setVisibility(View.VISIBLE);
		
		tv_contact_person.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(!tv_contact_person.getText().toString().trim().contains("--")){
					String url = tv_contact_person.getText().toString();
					if (!url.startsWith("http://") && !url.startsWith("https://"))
						   url = "http://" + url;
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					startActivity(browserIntent);
				}
			}
		});
		
		tv_address.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(!member.getEmail().contains("--")){
					Intent i = new Intent(Intent.ACTION_SEND);
					i.setType("plain/text");
					i.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ member.getEmail() });
					i.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
					i.putExtra(android.content.Intent.EXTRA_TEXT, "");
					startActivity(Intent.createChooser(i, "Send email"));
				}
			}
		});
		
		return v;
	}
	
	public void getChildView(String[] parts){
		for(int i = 0; i <parts.length ; i++){
			View v = View.inflate(getActivity(), R.layout.detail_page_row, null);
			
			final TextView tv_mobile_no = (TextView)v.findViewById(R.id.tv_mobile_no);
			tv_mobile_no.setText(parts[i].trim());
			tv_mobile_no.setTypeface(base.getRegularTypeFace());
			
			ImageView iv_call = (ImageView)v.findViewById(R.id.iv_call);
			iv_call.setVisibility(View.GONE);
			tv_mobile_no.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i = new Intent(Intent.ACTION_SEND);
					i.setType("plain/text");
					i.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ member.getEmail() });
					i.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
					i.putExtra(android.content.Intent.EXTRA_TEXT, "");
					startActivity(Intent.createChooser(i, "Send email"));
				}
			});
			ll_email_container.addView(v);
		}
	}
}
