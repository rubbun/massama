package com.massma;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.massma.Enum.Tabs;
import com.massma.fragment.CatagoryFragment;
import com.massma.fragment.Dialerfragment;
import com.massma.fragment.MemberFragment;
import com.massma.fragment.Supportfragment;

public class MainActivity extends BaseActivity {

	private static String CURRENT_TAB;
	private Fragment fragment = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		((LinearLayout) findViewById(R.id.ll_member)).setOnClickListener(new TabSelectListener());
		((LinearLayout) findViewById(R.id.ll_catagory)).setOnClickListener(new TabSelectListener());
		((LinearLayout) findViewById(R.id.ll_dialer)).setOnClickListener(new TabSelectListener());
		((LinearLayout) findViewById(R.id.ll_support)).setOnClickListener(new TabSelectListener());

		displayView(Tabs.MEMBER);
	}

	private void displayView(Tabs tab) {

		switch (tab) {
		case MEMBER:
			CURRENT_TAB = Tabs.MEMBER.name();
			fragment = new MemberFragment(this);
			break;
		case CATEGORY:
			CURRENT_TAB = Tabs.CATEGORY.name();
			fragment = new CatagoryFragment(this);
			break;
		case DIALER:
			CURRENT_TAB = Tabs.DIALER.name();
			fragment = new Dialerfragment(this);
			break;
		case SUPPORT:
			CURRENT_TAB = Tabs.SUPPORT.name();
			fragment = new Supportfragment(this);
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

		} else {
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	public class TabSelectListener implements OnClickListener {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.ll_member:
				displayView(Tabs.MEMBER);
				break;

			case R.id.ll_catagory:
				displayView(Tabs.CATEGORY);
				break;

			case R.id.ll_dialer:
				displayView(Tabs.DIALER);
				break;

			case R.id.ll_support:
				displayView(Tabs.SUPPORT);
				break;
			}

		}

	}

	@Override
	public void onBackPressed() {
		if (CURRENT_TAB.equalsIgnoreCase(Tabs.CATEGORY.name())) {
			JSONArray jsrr = null;
			CatagoryFragment catagoryFragment = (CatagoryFragment) fragment;
			if (catagoryFragment.level == 0) {
				finish();
				return;
			} else if (catagoryFragment.level == 1) {
				jsrr = catagoryFragment.jsonArray0;
			} else if (catagoryFragment.level == 2) {
				jsrr = catagoryFragment.jsonArray1;
			} else if (catagoryFragment.level == 3) {
				jsrr = catagoryFragment.jsonArray2;
			}
			catagoryFragment.level--;
			catagoryFragment.ll_body.removeAllViews();
			for (int i = 0; i < jsrr.length(); i++) {
				try {
					JSONObject c = jsrr.getJSONObject(i);
					catagoryFragment.ll_body.addView(catagoryFragment.CategoryLeve(c));

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
