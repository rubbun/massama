package com.steelbuzz.subfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.steelbuzz.R;
import com.steelbuzz.bean.Member;
import com.steelbuzz.fragment.BaseFragment;

public class ProductInfoFragment extends BaseFragment{
	private Member member;
	private TextView tv_address,tv_contact_person,tv_header_name;
	public ProductInfoFragment(Member bean){
		this.member = bean;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.subfragment_contact_info, container, false);
		tv_address = (TextView)v.findViewById(R.id.tv_address);
		tv_contact_person = (TextView)v.findViewById(R.id.tv_contact_person);
		return v;
	}
}
