package com.massma;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.massma.Enum.Tabs;
import com.massma.fragment.CatagoryFragment;
import com.massma.fragment.MemberFragment;

public class MainActivity extends BaseActivity {

	private static String CURRENT_TAB;
	private Fragment fragment = null;
	private String TAG_MEMBER = "com.massma.fragment.MemberFragment";
	private String TAG_CATEGORY = "com.massma.fragment.CatagoryFragment";
	private String TAG_DIELER = "com.massma.fragment.Dialerfragment";
	private String TAG_SUPPORT = "com.massma.fragment.Supportfragment";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		((LinearLayout) findViewById(R.id.ll_member)).setOnClickListener(new TabSelectListener());
		((LinearLayout) findViewById(R.id.ll_catagory)).setOnClickListener(new TabSelectListener());
		((LinearLayout) findViewById(R.id.ll_dialer)).setOnClickListener(new TabSelectListener());
		((LinearLayout) findViewById(R.id.ll_support)).setOnClickListener(new TabSelectListener());

		((LinearLayout) findViewById(R.id.ll_member)).setTag(TAG_MEMBER);
		((LinearLayout) findViewById(R.id.ll_catagory)).setTag(TAG_CATEGORY);
		((LinearLayout) findViewById(R.id.ll_dialer)).setTag(TAG_DIELER);
		((LinearLayout) findViewById(R.id.ll_support)).setTag(TAG_SUPPORT);
		fragment = new MemberFragment();

		getSupportFragmentManager().beginTransaction().add(R.id.frame_container, fragment, TAG_MEMBER).commit();

		CURRENT_TAB = TAG_MEMBER;

		// displayView(Tabs.MEMBER);
	}

	private void displayView(Tabs tab, View v) {
		if (v.getTag() == CURRENT_TAB) {
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
		}
		ft.commit();
		CURRENT_TAB = v.getTag().toString();
		/*
		 * 
		 * switch (tab) { case MEMBER: CURRENT_TAB = Tabs.MEMBER.name();
		 * fragment = new MemberFragment(this); break; case CATEGORY:
		 * CURRENT_TAB = Tabs.CATEGORY.name(); fragment = new
		 * CatagoryFragment(); break; case DIALER: CURRENT_TAB =
		 * Tabs.DIALER.name(); fragment = new Dialerfragment(this); break; case
		 * SUPPORT: CURRENT_TAB = Tabs.SUPPORT.name(); fragment = new
		 * Supportfragment(this); break; }
		 * 
		 * if (fragment != null) { FragmentManager fragmentManager =
		 * getSupportFragmentManager();
		 * fragmentManager.beginTransaction().replace(R.id.frame_container,
		 * fragment).commit();
		 * 
		 * } else { Log.e("MainActivity", "Error in creating fragment"); }
		 */}

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
		}
	}
}
