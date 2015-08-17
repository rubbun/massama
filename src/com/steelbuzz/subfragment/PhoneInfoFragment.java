package com.steelbuzz.subfragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.steelbuzz.BaseActivity;
import com.steelbuzz.MyGestureListener;
import com.steelbuzz.R;
import com.steelbuzz.bean.Member;
import com.steelbuzz.fragment.BaseFragment;

public class PhoneInfoFragment extends BaseFragment{
	
	GestureDetectorCompat mDetector;
	private Member member;
	private BaseActivity base;
	private LinearLayout ll_mobile_container,ll_huges_container;
	private TextView tv_address,tv_contact_person,tv_header_second_name,tv_header_name;
	public PhoneInfoFragment(Member bean, BaseActivity base){
		this.member = bean;
		this.base = base;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.subfragment_contact_info, container, false);
		tv_address = (TextView)v.findViewById(R.id.tv_address);
		tv_contact_person = (TextView)v.findViewById(R.id.tv_contact_person);
		
		tv_header_second_name = (TextView)v.findViewById(R.id.tv_header_second_name);
		tv_header_name = (TextView)v.findViewById(R.id.tv_header_name);
		
		tv_header_second_name.setTypeface(base.getSemiBoldTypeFace());
		tv_header_name.setTypeface(base.getSemiBoldTypeFace());
		tv_address.setTypeface(base.getRegularTypeFace());
		
		tv_header_name.setText("Mobile");
		tv_header_second_name.setText("Hughes:");
		
		tv_address.setText(""+member.getMobile());
		tv_address.setVisibility(View.GONE);
		tv_contact_person.setText(""+member.getResidential());
		tv_contact_person.setVisibility(View.GONE);
		
		ll_mobile_container = (LinearLayout)v.findViewById(R.id.ll_mobile_container);
		ll_huges_container = (LinearLayout)v.findViewById(R.id.ll_huges_container);
		
		if(member.getMobile().contains("/"))
		{
		    String parts[] = member.getMobile().split("/");
		    getChildViewHughes(parts);
		   
		}else{
			
			String parts[] = new String[1];
			parts[0] = member.getMobile();
			getChildViewHughes(parts);
		}
		
		if(member.getHughes_no().contains("/"))
		{
		    String parts[] = member.getHughes_no().split("/");
		    getChildView(parts);
		   
		}else if(member.getHughes_no().length() > 0){
			
			String parts[] = new String[1];
			parts[0] = member.getHughes_no();
			getChildView(parts);
		}
		
		return v;
	}
	
	public void getChildView(String[] parts){
		for(int i = 0; i <parts.length ; i++){
			View v = View.inflate(getActivity(), R.layout.detail_page_row, null);
			
			final TextView tv_mobile_no = (TextView)v.findViewById(R.id.tv_mobile_no);
			tv_mobile_no.setText(parts[i]);
			tv_mobile_no.setTypeface(base.getRegularTypeFace());
			
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
	public void getChildViewHughes(String[] parts){
		for(int i = 0; i <parts.length ; i++){
			View v = View.inflate(getActivity(), R.layout.phone_info_row, null);
			
			mDetector = new GestureDetectorCompat(getActivity(),
                    new MyGestureListener(getActivity(), v));
			final LinearLayout ll_mobile_second = (LinearLayout)v.findViewById(R.id.ll_mobile_second);
			
			final TextView tv_mobile_no = (TextView)v.findViewById(R.id.tv_mobile_no);
			tv_mobile_no.setText("+91"+parts[i]);
			tv_mobile_no.setTypeface(base.getRegularTypeFace());
			/*ImageView iv_call = (ImageView)v.findViewById(R.id.iv_call);
			iv_call.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tv_mobile_no.getText().toString().trim()));
					startActivity(intent1);
				}
			});*/
			ll_mobile_container.addView(v);
			
			ll_mobile_second.setOnTouchListener(new OnTouchListener() {

		            @Override
		            public boolean onTouch(View v, MotionEvent event) {
		                mDetector.onTouchEvent(event);
		                return true;
		            }
		        });
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
