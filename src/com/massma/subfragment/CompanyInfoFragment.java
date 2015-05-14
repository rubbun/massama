package com.massma.subfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.massma.R;
import com.massma.bean.Member;
import com.massma.fragment.BaseFragment;

public class CompanyInfoFragment extends BaseFragment{
	private Member member;
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
		tv_contact_person.setText(""+member.getContactParson());
		
		return v;
	}
}
