package com.steelbuzz.subfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.steelbuzz.BaseActivity;
import com.steelbuzz.R;
import com.steelbuzz.bean.Member;
import com.steelbuzz.fragment.BaseFragment;

public class ProductInfoFragment extends BaseFragment{
	private Member member;
	private BaseActivity base;
	public ProductInfoFragment(Member bean, BaseActivity base){
		this.member = bean;
		this.base = base;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.subfragment_product, container, false);
				
		return v;
	}
}
