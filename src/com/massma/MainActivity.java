package com.massma;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
import android.widget.TextView;

import com.massma.bean.Catagory;
import com.massma.bean.Member;
import com.massma.bean.SubCatagory;
import com.massma.fragment.CatagoryFragment;
import com.massma.fragment.DetailMemberFragment;
import com.massma.fragment.Dialerfragment;
import com.massma.fragment.MemberFragment;
import com.massma.fragment.SubCategoryFragment;
import com.massma.fragment.Supportfragment;

public class MainActivity extends BaseActivity implements OnClickListener {

	private ArrayList<Member> memberArr = new ArrayList<Member>();
	private ArrayList<SubCatagory> subCatagoryList = new ArrayList<SubCatagory>();
	private ArrayList<Catagory> catagoryList = new ArrayList<Catagory>();
	private LinearLayout ll_member, ll_catagory, ll_schedule,ll_support;
	private TextView tv_member;

	private String name, address, contactParson, tata, mobile, fax, residential, email, web, hughes_no = "";

	private static final String TAB_MEMBER = "member";
	private static final String TAB_CATEGORY = "category";
	private static final String TAB_CATEGORY_LEVEL_1 = "category_level_1";
	private static final String TAB_THIRD = "third";
	private static final String DETAIL_MEMBER = "member_detail";
	private static final String TAB_SUBCATAGORY = "sub_catagory";
	private static final String TAB_SPPORT = "support";

	private String current_tab, previous_tab = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ll_member = (LinearLayout) findViewById(R.id.ll_member);
		ll_member.setOnClickListener(this);

		ll_catagory = (LinearLayout) findViewById(R.id.ll_catagory);
		ll_catagory.setOnClickListener(this);

		ll_schedule = (LinearLayout) findViewById(R.id.ll_schedule);
		ll_schedule.setOnClickListener(this);
		
		ll_support = (LinearLayout)findViewById(R.id.ll_support);
		ll_support.setOnClickListener(this);
		
		tv_member = (TextView)findViewById(R.id.tv_member);

		parseJSONForMember();
		parseJSONForCtagory();
		displayView(0);
	}

	private void displayView(int position) {
		Fragment fragment = null;
		switch (position) {
		case 0:
			current_tab = TAB_MEMBER;
			fragment = new MemberFragment(this, memberArr);
			break;
		case 1:
			current_tab = TAB_CATEGORY;
			fragment = new CatagoryFragment(this, catagoryList);
			break;
		case 2:
			current_tab = TAB_THIRD;
			fragment = new Dialerfragment(this);
			break;
		case 3:
			current_tab = DETAIL_MEMBER;
			fragment = new DetailMemberFragment(this, name, address, contactParson, tata, mobile, fax, residential, email, web,hughes_no);
			break;

		case 4:
			current_tab = TAB_SUBCATAGORY;
			fragment = new SubCategoryFragment(this, subCatagoryList);
			break;
			
		case 5:
			current_tab = TAB_SPPORT;
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

	public String readXMLinString(String fileName) {
		try {
			InputStream is = getAssets().open(fileName);
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			String text = new String(buffer);

			return text;

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public void parseJSONForMember() {
		try {
			JSONArray jArr = new JSONArray(readXMLinString("member.json"));
			if (jArr != null) {
				for (int i = 0; i < jArr.length(); i++) {
					JSONObject c = jArr.getJSONObject(i);
					String name = c.getString("MEMBERS_NAME");
					String address = c.getString("ADDRESS");
					String contactParson = c.getString("CONT_PERSON");
					String tata = c.getString("PHONE_NO");
					String mobile = c.getString("MOBILE");
					String fax = c.getString("FAX");
					String residential = c.getString("RESIDENCE");
					String email = c.getString("EMAIL");
					String web = c.getString("WEB");
					String hughes_no = c.getString("HUGHES_NO");
					memberArr.add(new Member(name, address, contactParson, tata, mobile, fax, residential, email, web, hughes_no));
					Collections.sort(memberArr, new Comparator<Member>() {

						@Override
						public int compare(Member arg0, Member arg1) {
							return arg0.getName().compareTo(arg1.getName());
						}
					});
				}
				if(memberArr.size() > 0){
					tv_member.setText("Member"+"("+memberArr.size()+")");
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void parseJSONForCtagory() {
		try {
			JSONArray jArr = new JSONArray(readXMLinString("category.json"));
			if (jArr != null) {
				for (int i = 0; i < jArr.length(); i++) {
					JSONObject c = jArr.getJSONObject(i);
					String name = c.getString("CATAGORY_NAME");
					catagoryList.add(new Catagory(name));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void parseJSONForSubCtagory(String sub_catagory) {
		try {
			JSONArray jArr = new JSONArray(readXMLinString(sub_catagory));
			if (jArr != null) {
				for (int i = 0; i < jArr.length(); i++) {
					JSONObject c = jArr.getJSONObject(i);
					String name = c.getString("MEMBERS_NAME");
					String address = c.getString("ADDRESS");
					String contactParson = c.getString("CONT_PERSON");
					String tata = c.getString("TATA");
					String mobile = c.getString("MOBILE");
					String fax = c.getString("FAX");
					String residential = c.getString("RESI");
					String email = c.getString("EMAIL");
					String web = c.getString("WEB");
					subCatagoryList.add(new SubCatagory(name, address, contactParson, tata, mobile, fax, residential, email, web));
					Collections.sort(subCatagoryList, new Comparator<SubCatagory>() {

						@Override
						public int compare(SubCatagory arg0, SubCatagory arg1) {
							return arg0.getName().compareTo(arg1.getName());
						}
					});
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void onMemberClick(String name, String address, String contactParson, String tata, String mobile, String fax, String residential, String email, String web, String hughes_no) {
		this.name = name;
		this.address = address;
		this.contactParson = contactParson;
		this.tata = tata;
		this.mobile = mobile;
		this.fax = fax;
		this.residential = residential;
		this.email = email;
		this.web = web;
		this.hughes_no = hughes_no;
		previous_tab = TAB_MEMBER;
		displayView(3);
	}

	public void onCatagoryClick(int position) {
		previous_tab = TAB_CATEGORY;
		current_tab = TAB_SUBCATAGORY;
		if (position == 0) {
			subCatagoryList.clear();
			parseJSONForSubCtagory("Stainless_Steel_Seamless_Pipes_and_Tubes.json");
			displayView(4);
		} else if (position == 1) {
			subCatagoryList.clear();
			parseJSONForSubCtagory("Nickel_Alloys.json");
			displayView(4);
		} else if (position == 2) {
			subCatagoryList.clear();
			parseJSONForSubCtagory("Stainless_welded_pipes_tubes.json");
			displayView(4);
		} else if (position == 3) {
			subCatagoryList.clear();
			parseJSONForSubCtagory("coper_pipe.json");
			displayView(4);
		}
	}

	public void onSelectedCatagoryClick(int position) {
		previous_tab = TAB_CATEGORY;
		if (position == 0) {
			subCatagoryList.clear();
			parseJSONForSubCtagory("Stainless_Steel_Seamless_Pipes_and_Tubes.json");
			displayView(4);
		} else if (position == 1) {
			subCatagoryList.clear();
			parseJSONForSubCtagory("Nickel_Alloys.json");
			displayView(4);
		} else if (position == 2) {
			subCatagoryList.clear();
			parseJSONForSubCtagory("Stainless_welded_pipes_tubes.json");
			displayView(4);
		} else if (position == 3) {
			subCatagoryList.clear();
			parseJSONForSubCtagory("coper_pipe.json");
			displayView(4);
		}
	}

	public void onSelectedMemberClick(String name, String address, String contactParson, String tata, String mobile, String fax, String residential, String email, String web, String hughes_no) {
		this.name = name;
		this.address = address;
		this.contactParson = contactParson;
		this.tata = tata;
		this.mobile = mobile;
		this.fax = fax;
		this.residential = residential;
		this.email = email;
		this.web = web;
		this.hughes_no = hughes_no;
		previous_tab = TAB_MEMBER;
		displayView(3);
	}

	public void onSubCatagoryClick(String name, String address, String contactParson, String tata, String mobile, String fax, String residential, String email, String web) {
		this.name = name;
		this.address = address;
		this.contactParson = contactParson;
		this.tata = tata;
		this.mobile = mobile;
		this.fax = fax;
		this.residential = residential;
		this.email = email;
		this.web = web;
		previous_tab = TAB_SUBCATAGORY;
		displayView(3);
	}

	public void onSelectedSubCatagoryClick(String name, String address, String contactParson, String tata, String mobile, String fax, String residential, String email, String web) {
		this.name = name;
		this.address = address;
		this.contactParson = contactParson;
		this.tata = tata;
		this.mobile = mobile;
		this.fax = fax;
		this.residential = residential;
		this.email = email;
		this.web = web;
		previous_tab = TAB_SUBCATAGORY;
		displayView(3);
	}

	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {
		case R.id.ll_member:
			displayView(0);
			break;

		case R.id.ll_catagory:
			displayView(1);
			break;

		case R.id.ll_schedule:
			displayView(2);
			break;
			
		case R.id.ll_support:
			displayView(5);
			break;
		}
	}

	@Override
	public void onBackPressed() {

		if (current_tab.equalsIgnoreCase(DETAIL_MEMBER) && previous_tab.equalsIgnoreCase(TAB_MEMBER)) {
			displayView(0);
		} else if (current_tab.equalsIgnoreCase(DETAIL_MEMBER) && previous_tab.equalsIgnoreCase(TAB_SUBCATAGORY)) {
			displayView(4);
		} else if (current_tab.equalsIgnoreCase(TAB_SUBCATAGORY) && previous_tab.equalsIgnoreCase(TAB_CATEGORY)) {
			displayView(1);
		} else {
			MainActivity.this.finish();
		}
	}
}
