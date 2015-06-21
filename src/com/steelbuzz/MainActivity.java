package com.steelbuzz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.RelativeLayout;

import com.steelbuzz.R;
import com.steelbuzz.Enum.Tabs;
import com.steelbuzz.constant.Constants;
import com.steelbuzz.fragment.CatagoryFragment;
import com.steelbuzz.fragment.MemberFragment;

public class MainActivity extends BaseActivity {

	private static String CURRENT_TAB;
	private Fragment fragment = null;
	private String TAG_MEMBER = "com.steelbuzz.fragment.MemberFragment";
	private String TAG_CATEGORY = "com.steelbuzz.fragment.CatagoryFragment";
	private String TAG_DIELER = "com.steelbuzz.fragment.Dialerfragment";
	private String TAG_SUPPORT = "com.steelbuzz.fragment.Supportfragment";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		((RelativeLayout) findViewById(R.id.ll_member)).setOnClickListener(new TabSelectListener());
		((RelativeLayout) findViewById(R.id.ll_catagory)).setOnClickListener(new TabSelectListener());
		((RelativeLayout) findViewById(R.id.ll_dialer)).setOnClickListener(new TabSelectListener());
		((RelativeLayout) findViewById(R.id.ll_support)).setOnClickListener(new TabSelectListener());

		((RelativeLayout) findViewById(R.id.ll_member)).setTag(TAG_MEMBER);
		((RelativeLayout) findViewById(R.id.ll_catagory)).setTag(TAG_CATEGORY);
		((RelativeLayout) findViewById(R.id.ll_dialer)).setTag(TAG_DIELER);
		((RelativeLayout) findViewById(R.id.ll_support)).setTag(TAG_SUPPORT);
		fragment = new MemberFragment();

		getSupportFragmentManager().beginTransaction().add(R.id.frame_container, fragment, TAG_MEMBER).commit();

		CURRENT_TAB = TAG_MEMBER;

		// displayView(Tabs.MEMBER);
	}

	private void displayView(Tabs tab, View v) {
		if (v.getTag() == CURRENT_TAB) {
			if(v.getTag().toString() == TAG_CATEGORY){
				CatagoryFragment catagoryFragment = (CatagoryFragment) fragment;
				catagoryFragment.resetUiVisiabilty();
			}else if(v.getTag().toString() == TAG_MEMBER){
				MemberFragment memberFragment = (MemberFragment) fragment;
				memberFragment.resetUiVisiabilty();
			}
			return;
		}

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.hide(getSupportFragmentManager().findFragmentByTag(CURRENT_TAB));

		fragment = getSupportFragmentManager().findFragmentByTag(v.getTag().toString());

		if (fragment == null) { 
			fragment = Fragment.instantiate(this, v.getTag().toString());
			ft.add(R.id.frame_container, fragment, v.getTag().toString());
		} else {
			ft.show(fragment);
			if(v.getTag().toString() == TAG_CATEGORY){
				CatagoryFragment catagoryFragment = (CatagoryFragment) fragment;
				catagoryFragment.resetUiVisiabilty();
			}else if(v.getTag().toString() == TAG_MEMBER){
				MemberFragment memberFragment = (MemberFragment) fragment;
				memberFragment.resetUiVisiabilty();
			}
		}
		ft.commit();
		CURRENT_TAB = v.getTag().toString();
	}

	public class TabSelectListener implements OnClickListener {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.ll_member:
				displayView(Tabs.MEMBER, v);
				break;

			case R.id.ll_catagory:
				displayView(Tabs.CATEGORY, v);
				break;

			case R.id.ll_dialer:
				displayView(Tabs.DIALER, v);
				break;

			case R.id.ll_support:
				displayView(Tabs.SUPPORT, v);
				break;
			}
		}
	}

	@Override
	public void onBackPressed() {
		if (CURRENT_TAB == TAG_MEMBER) {
			MemberFragment memberFragment = (MemberFragment) fragment;
			memberFragment.OnbackPress();
		} else if (CURRENT_TAB == TAG_CATEGORY) {
			CatagoryFragment catagoryFragment = (CatagoryFragment) fragment;
			catagoryFragment.OnbackPress();
		}else if (CURRENT_TAB == TAG_DIELER) {
			Constants.memberArr.clear();
			this.finish();			
		}else if (CURRENT_TAB == TAG_SUPPORT) {
			Constants.memberArr.clear();
			this.finish();
		}
	}
}
