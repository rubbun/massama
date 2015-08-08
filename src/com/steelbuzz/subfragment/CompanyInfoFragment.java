package com.steelbuzz.subfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.steelbuzz.R;
import com.steelbuzz.bean.Member;
import com.steelbuzz.fragment.BaseFragment;

public class CompanyInfoFragment extends BaseFragment{
	private Member member;
	private LinearLayout ll_mobile_container,ll_huges_container;
	private TextView tv_address,tv_contact_person,tv_header_second_name,tv_header_name;
	public CompanyInfoFragment(Member bean){
		this.member = bean;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.subfragment_contact_info, container, false);
		tv_address = (TextView)v.findViewById(R.id.tv_address);
		tv_contact_person = (TextView)v.findViewById(R.id.tv_contact_person);
		
		tv_header_second_name = (TextView)v.findViewById(R.id.tv_header_second_name);
		tv_header_name = (TextView)v.findViewById(R.id.tv_header_name);
		
		tv_header_name.setText("Address");
		tv_header_second_name.setText("Contact Person");
		
		tv_address.setText(""+member.getAddress());
		tv_address.setVisibility(View.VISIBLE);
		tv_contact_person.setVisibility(View.GONE);
		
		ll_mobile_container = (LinearLayout)v.findViewById(R.id.ll_mobile_container);
		ll_huges_container = (LinearLayout)v.findViewById(R.id.ll_huges_container);
		ll_mobile_container.setVisibility(View.GONE);
		
		if(member.getContactParson().contains("/"))
		{
		    String parts[] = member.getContactParson().split("/");
		    getChildView(parts);
		   
		}else{
			
			String parts[] = new String[1];
			parts[0] = member.getContactParson();
			getChildView(parts);
		}
		
		return v;
	}
	
	public void getChildView(String[] parts){
		for(int i = 0; i <parts.length ; i++){
			View v = View.inflate(getActivity(), R.layout.detail_page_row, null);
			
			ImageView iv_call = (ImageView)v.findViewById(R.id.iv_call);
			iv_call.setVisibility(View.GONE);
			final TextView tv_contact_person_name = (TextView)v.findViewById(R.id.tv_mobile_no);
			tv_contact_person_name.setText(parts[i]);
			ll_huges_container.addView(v);
		}
	}
}
