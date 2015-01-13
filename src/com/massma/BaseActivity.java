package com.massma;

import com.massma.adapter.SelectedCatagoryAdapter.OnSelecedCatagoryClickListener;
import com.massma.adapter.SelectedSubCatagoryAdapter.OnSelectedSubCatagoryClickListener;
import com.massma.adapter.SubCatagoryAdapter.OnSubCatagoryClickListener;
import com.massma.adapter.CatagoryAdapter.OnCatagoryClickListener;
import com.massma.adapter.MemberAdapter.OnMemberClickListener;
import com.massma.adapter.SelectedMemberAdapter.OnSelectedMemberClickListener;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity implements OnMemberClickListener,OnSelectedMemberClickListener,OnCatagoryClickListener,OnSelecedCatagoryClickListener,OnSubCatagoryClickListener,OnSelectedSubCatagoryClickListener{

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
	}

	public void onMemberClick(String name, String address, String contactParson, String tata, String mobile, String fax, String residential, String email, String web) {
	}

	@Override
	public void onSelectedMemberClick(String name, String address, String contactParson, String tata, String mobile, String fax, String residential, String email, String web) {
		
	}

	@Override
	public void onSubCatagoryClick(String name, String address, String contactParson, String tata, String mobile, String fax, String residential, String email, String web) {
		
	}

	@Override
	public void onCatagoryClick(int position) {
		
	}

	@Override
	public void onSelectedCatagoryClick(int position) {
		
	}

	@Override
	public void onSelectedSubCatagoryClick(String name, String address,
			String contactParson, String tata, String mobile, String fax,
			String residential, String email, String web) {
		
	}
}
