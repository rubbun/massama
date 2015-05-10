package com.massma.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.massma.BaseActivity;
import com.massma.IndexableListView;
import com.massma.R;

public class MemberFragment extends Fragment {

	private BaseActivity base;
	
	private IndexableListView lv_members;
		public MemberFragment(BaseActivity base) {
		this.base = base;
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_member, container, false);
		lv_members = (IndexableListView) v.findViewById(R.id.lv_members);
		
		return v;
	}

	
}
