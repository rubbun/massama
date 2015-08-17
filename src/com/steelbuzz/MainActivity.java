package com.steelbuzz;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.steelbuzz.Enum.Tabs;
import com.steelbuzz.constant.Constants;
import com.steelbuzz.fragment.BuzzFragment;
import com.steelbuzz.fragment.CatagoryFragment;
import com.steelbuzz.fragment.MemberFragment;

public class MainActivity extends BaseActivity {

	private static String CURRENT_TAB;
	private Fragment fragment = null;
	private String TAG_MEMBER = "com.steelbuzz.fragment.MemberFragment";
	private String TAG_CATEGORY = "com.steelbuzz.fragment.CatagoryFragment";
	private String TAG_DIELER = "com.steelbuzz.fragment.Dialerfragment";
	private String TAG_SUPPORT = "com.steelbuzz.fragment.Supportfragment";
	private String TAG_BUZZ = "com.steelbuzz.fragment.BuzzFragment";
	
	private RelativeLayout ll_member,ll_catagory,ll_dialer,ll_support,ll_buzz;
	private TextView tv_member,tvProduct,tvDialer,tvBuzz,tvMore;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv_member = (TextView)findViewById(R.id.tv_member);
		tvProduct = (TextView)findViewById(R.id.tvProduct);
		tvDialer = (TextView)findViewById(R.id.tvDialer);
		tvBuzz = (TextView)findViewById(R.id.tvBuzz);
		tvMore = (TextView)findViewById(R.id.tvMore);
		
		tv_member.setTypeface(getRegularTypeFace());
		tvProduct.setTypeface(getRegularTypeFace());
		tvDialer.setTypeface(getRegularTypeFace());
		tvBuzz.setTypeface(getRegularTypeFace());
		tvMore.setTypeface(getRegularTypeFace());

		ll_member = (RelativeLayout) findViewById(R.id.ll_member);
		ll_catagory = (RelativeLayout) findViewById(R.id.ll_catagory);
		ll_dialer = (RelativeLayout) findViewById(R.id.ll_dialer);
		ll_support = (RelativeLayout) findViewById(R.id.ll_support);
		ll_buzz = (RelativeLayout) findViewById(R.id.ll_buzz);
		
		ll_member.setOnClickListener(new TabSelectListener());
		ll_catagory.setOnClickListener(new TabSelectListener());
		ll_dialer.setOnClickListener(new TabSelectListener());
		ll_support.setOnClickListener(new TabSelectListener());
		ll_buzz.setOnClickListener(new TabSelectListener());
		
		ll_member.setTag(TAG_MEMBER);
		ll_catagory.setTag(TAG_CATEGORY);
		ll_dialer.setTag(TAG_DIELER);
		ll_support.setTag(TAG_SUPPORT);
		ll_buzz.setTag(TAG_BUZZ);
		fragment = new MemberFragment();

		getSupportFragmentManager().beginTransaction().add(R.id.frame_container, fragment, TAG_MEMBER).commit();

		CURRENT_TAB = TAG_MEMBER;

		
		try {

			   PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);

			   for (Signature signature : info.signatures) 
			   {
			    MessageDigest md = MessageDigest.getInstance("SHA");
			    md.update(signature.toByteArray());
			    Log.e("!!KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
			   }

			  } catch (NameNotFoundException e) {
			   Log.e("name not found", e.toString());
			  } catch (NoSuchAlgorithmException e) {
			   Log.e("no such an algorithm", e.toString());
			  }
		
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

		@SuppressWarnings("deprecation")
		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.ll_member:
				ll_member.setBackground(getResources().getDrawable(R.drawable.tab_bg));
				ll_catagory.setBackground(getResources().getDrawable(R.drawable.tab_unselect));
				ll_dialer.setBackground(getResources().getDrawable(R.drawable.tab_big_unselect));
				ll_support.setBackground(getResources().getDrawable(R.drawable.tab_unselect));
				ll_buzz.setBackground(getResources().getDrawable(R.drawable.tab_unselect));				
				displayView(Tabs.MEMBER, v);
				break;

			case R.id.ll_catagory:
				ll_member.setBackground(getResources().getDrawable(R.drawable.tab_unselect));
				ll_catagory.setBackground(getResources().getDrawable(R.drawable.tab_bg));
				ll_dialer.setBackground(getResources().getDrawable(R.drawable.tab_big_unselect));
				ll_support.setBackground(getResources().getDrawable(R.drawable.tab_unselect));
				ll_buzz.setBackground(getResources().getDrawable(R.drawable.tab_unselect));				
				
				displayView(Tabs.CATEGORY, v);
				break;

			case R.id.ll_dialer:
				ll_member.setBackground(getResources().getDrawable(R.drawable.tab_unselect));
				ll_catagory.setBackground(getResources().getDrawable(R.drawable.tab_unselect));
				ll_dialer.setBackground(getResources().getDrawable(R.drawable.tab_bg_big));
				ll_support.setBackground(getResources().getDrawable(R.drawable.tab_unselect));
				ll_buzz.setBackground(getResources().getDrawable(R.drawable.tab_unselect));				
				
				displayView(Tabs.DIALER, v);
				break;

			case R.id.ll_support:
				ll_member.setBackground(getResources().getDrawable(R.drawable.tab_unselect));
				ll_catagory.setBackground(getResources().getDrawable(R.drawable.tab_unselect));
				ll_dialer.setBackground(getResources().getDrawable(R.drawable.tab_big_unselect));
				ll_support.setBackground(getResources().getDrawable(R.drawable.tab_bg));
				ll_buzz.setBackground(getResources().getDrawable(R.drawable.tab_unselect));				
				
				displayView(Tabs.SUPPORT, v);
				break;
				
			case R.id.ll_buzz:
				ll_member.setBackground(getResources().getDrawable(R.drawable.tab_unselect));
				ll_catagory.setBackground(getResources().getDrawable(R.drawable.tab_unselect));
				ll_dialer.setBackground(getResources().getDrawable(R.drawable.tab_big_unselect));
				ll_support.setBackground(getResources().getDrawable(R.drawable.tab_unselect));
				ll_buzz.setBackground(getResources().getDrawable(R.drawable.tab_bg));				
				
				displayView(Tabs.BUZZ, v);
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
		}else if (CURRENT_TAB == TAG_BUZZ) {
			Constants.memberArr.clear();
			BuzzFragment buzzFragment = (BuzzFragment) fragment;
			buzzFragment.OnbackPress();
		}
	}
}
