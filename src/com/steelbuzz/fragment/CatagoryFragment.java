package com.steelbuzz.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.steelbuzz.BaseActivity;
import com.steelbuzz.R;
import com.steelbuzz.adapter.MemberAdapter;
import com.steelbuzz.adapter.SelectedCatagoryAdapter;
import com.steelbuzz.adapter.SelectedCatagoryAdapter.OnSelecedCatagoryClickListener;
import com.steelbuzz.bean.Member;
import com.steelbuzz.constant.Constants;
import com.steelbuzz.network.HttpClient;
import com.steelbuzz.network.HttpClientGet;
import com.steelbuzz.subfragment.CompanyInfoFragment;
import com.steelbuzz.subfragment.MailInfoFragment;
import com.steelbuzz.subfragment.PhoneInfoFragment;
import com.steelbuzz.subfragment.ProductInfoFragment;

public class CatagoryFragment extends BaseFragment implements OnItemClickListener, OnClickListener,OnSelecedCatagoryClickListener {

	public LinearLayout ll_body, ll_member, ll_member_detail;
	public View view;
	private ArrayList<Member> memberArr = new ArrayList<Member>();
	private SelectedCatagoryAdapter selectedCatagoryAdapter;
	private ArrayList<Member> selectedtempArr = new ArrayList<Member>();

	private ListView lv_members;
	private AutoCompleteTextView et_search;
	private MemberAdapter memberAdapter;
	private BaseActivity base;
	private TextView tv_membername, tv_address;
	private Member memberBean;
	private Fragment fragment = null;
	private LinearLayout ll_conpany_info, ll_phone, ll_mail, ll_products;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		base = (BaseActivity) activity;
	}
	
	public CatagoryFragment(){
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_category, container, false);
		ll_body = (LinearLayout) v.findViewById(R.id.ll_body);
		ll_member = (LinearLayout) v.findViewById(R.id.ll_member);
		ll_member_detail = (LinearLayout) v.findViewById(R.id.ll_member_detail);
		ll_member_detail.setVisibility(View.GONE);
		ll_member.setVisibility(View.GONE);
		ll_body.setVisibility(View.VISIBLE);

		et_search = (AutoCompleteTextView) v.findViewById(R.id.et_search);

		ll_conpany_info = (LinearLayout) v.findViewById(R.id.ll_conpany_info);
		ll_conpany_info.setOnClickListener(this);

		ll_phone = (LinearLayout) v.findViewById(R.id.ll_phone);
		ll_phone.setOnClickListener(this);

		ll_mail = (LinearLayout) v.findViewById(R.id.ll_mail);
		ll_mail.setOnClickListener(this);

		ll_products = (LinearLayout) v.findViewById(R.id.ll_products);
		ll_products.setOnClickListener(this);

		tv_membername = (TextView) v.findViewById(R.id.tv_membername);
		tv_address = (TextView) v.findViewById(R.id.tv_address);

		lv_members = (ListView) v.findViewById(R.id.lv_members);
		lv_members.setOnItemClickListener(this);

		et_search.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				et_search.setFocusableInTouchMode(true);
				base.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				return false;
			}
		});

		et_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

				// search(cs.toString());
				String searchString = et_search.getText().toString();
				if (searchString.length() > 0) {
					int textLength = searchString.length();
					selectedtempArr.clear();
					for (int i = 0; i < memberArr.size(); i++) {
						String retailerName = memberArr.get(i).getName();
						if (textLength <= retailerName.length()) {
							if (retailerName.contains(" ")) {
								String[] arr = retailerName.split("\\s+");
								for (int j = 0; j < arr.length; j++) {
									if (arr[j].toLowerCase().contains(searchString.toLowerCase())) {
										selectedtempArr.add(new Member(memberArr.get(i).getName(),memberArr.get(i).getName(), memberArr.get(i).getAddress(), memberArr.get(i).getContactParson(), memberArr.get(i).getTata(), memberArr.get(i).getMobile(), memberArr.get(i).getFax(), memberArr.get(i).getResidential(), memberArr.get(i).getEmail(), memberArr.get(i).getWeb(), memberArr.get(i).getHughes_no()));
										break;
									}
								}
							} else if (retailerName.toLowerCase().contains(searchString.toLowerCase())) {
								selectedtempArr.add(new Member(memberArr.get(i).getName(),memberArr.get(i).getName(), memberArr.get(i).getAddress(), memberArr.get(i).getContactParson(), memberArr.get(i).getTata(), memberArr.get(i).getMobile(), memberArr.get(i).getFax(), memberArr.get(i).getResidential(), memberArr.get(i).getEmail(), memberArr.get(i).getWeb(), memberArr.get(i).getHughes_no()));
							}
						}
					}

					selectedCatagoryAdapter = new SelectedCatagoryAdapter(base,CatagoryFragment.this, R.layout.row_catagory, selectedtempArr);
					lv_members.setAdapter(selectedCatagoryAdapter);
				} else {
					lv_members.setAdapter(memberAdapter);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});

		new CategoryAsynctask().execute();

		return v;
	}

	public class CategoryAsynctask extends AsyncTask<Void, Void, JSONArray> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (base.hasConnection()) {
				showLoading();
			}
		}

		@Override
		protected JSONArray doInBackground(Void... arg0) {
			try {

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("category", 1);
				if (base.hasConnection()) {
					String response = HttpClient.SendHttpPost(Constants.CATEGORY_LIST, jsonObject.toString());
					if (response != null) {
						JSONObject jObject = new JSONObject(response);
						base.mDb.insertCatagoryListValue(jObject.getJSONArray("root").toString());
						return jObject.getJSONArray("root");
					}
				} else {
					JSONArray arr = base.mDb.fetchCatagoryList();
					return arr;
				}

			} catch (JSONException e) {

			}
			return null;
		}

		@Override
		protected void onPostExecute(JSONArray result) {
			super.onPostExecute(result);
			if (base.hasConnection()) {
				dismissLoading();
			}
			fetchAllCatagoryDetailList();
			ll_body.removeAllViews();
			for (int i = 0; i < result.length(); i++) {
				try {
					JSONObject jsonObject = result.getJSONObject(i);
					ll_body.addView(SubView(jsonObject, 30));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public View SubView(final JSONObject jsonObject, int padding) {
		try {
			view = View.inflate(getActivity(), R.layout.row_catagory, null);
			String name = jsonObject.getString("name");
			final String id = jsonObject.getString("id");
			TextView textViewName = (TextView) view.findViewById(R.id.textViewName);
			textViewName.setText(name);
			final LinearLayout llSubCategory = (LinearLayout) view.findViewById(R.id.llSubCategory);
			LinearLayout llBody = (LinearLayout) view.findViewById(R.id.llBody);
			llBody.setPadding(padding, 0, 0, 0);
			textViewName.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					try {
						if (jsonObject.has("root")) {
							int childCount = llSubCategory.getChildCount();
							if (childCount > 0) {
								llSubCategory.removeAllViews();
							} else {
								JSONArray jsonArray = jsonObject.getJSONArray("root");
								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject subJsonObject = jsonArray.getJSONObject(i);
									llSubCategory.addView(SubView(subJsonObject, 70));
								}
							}

						} else {
							new GetMemberList().execute(id);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			return view;
		} catch (Exception e) {
			return null;
		}
	}

	public class GetMemberList extends AsyncTask<String, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			/*if (base.hasConnection()) {
				showLoading();
			}*/
		}

		@Override
		protected Void doInBackground(String... arg0) {

			try {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("category_id", arg0[0]);
				JSONArray jArr = new JSONArray();
				/*if (base.hasConnection()) {
					String response = HttpClient.SendHttpPost(Constants.CATEGORY_MEMBER_LIST, jsonObject.toString());
					if (response != null) {
						JSONObject jObject = new JSONObject(response);
						jArr = jObject.getJSONArray("details");
					}
				} else {*/
					JSONArray arr = base.mDb.fetchCatagoryDetailsList();
					for (int i = 0; i < arr.length(); i++) {
						JSONObject c = arr.getJSONObject(i);
						if (c.getString("category_id").equalsIgnoreCase(arg0[0])) {
							jArr.put(c);
						}
					}
				//}
				memberArr.clear();
				for (int i = 0; i < jArr.length(); i++) {
					JSONObject c = jArr.getJSONObject(i);
					String id = c.getString("id");
					String name = c.getString("members_name");
					String address = c.getString("address");
					String contactParson = c.getString("contact_person");
					String tata = c.getString("phone_no");
					String mobile = c.getString("mobile_no");
					String fax = c.getString("fax");
					String residential = c.getString("residence");
					String email = c.getString("email");
					String web = c.getString("web");
					String hughes_no = c.getString("hughes_no");
					memberArr.add(new Member(id, name, address, contactParson, tata, mobile, fax, residential, email, web, hughes_no));

				}
				Collections.sort(memberArr, new Comparator<Member>() {

					@Override
					public int compare(Member arg0, Member arg1) {
						return arg0.getName().compareTo(arg1.getName());
					}
				});

			} catch (JSONException e) {

			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (base.hasConnection()) {
				dismissLoading();
			}
			ll_member.setVisibility(View.VISIBLE);
			ll_body.setVisibility(View.GONE);
			memberAdapter = new MemberAdapter(getActivity(), R.layout.row_member, memberArr);
			lv_members.setAdapter(memberAdapter);
			lv_members.setFastScrollEnabled(true);
		}
	}

	public void OnbackPress() {
		if (ll_body.getVisibility() == View.VISIBLE) {
			getActivity().finish();
		} else if (ll_member.getVisibility() == View.VISIBLE) {
			ll_body.setVisibility(View.VISIBLE);
			ll_member.setVisibility(View.GONE);
			ll_member_detail.setVisibility(View.GONE);
		} else if (ll_member_detail.getVisibility() == View.VISIBLE) {
			ll_body.setVisibility(View.GONE);
			ll_member.setVisibility(View.VISIBLE);
			ll_member_detail.setVisibility(View.GONE);
		}
	}

	public void resetUiVisiabilty() {
		ll_body.setVisibility(View.VISIBLE);
		ll_member.setVisibility(View.GONE);
	}

	private void fetchAllCatagoryDetailList() {
		new FetchAllCatagoryDetailList().execute();
	}

	public class FetchAllCatagoryDetailList extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			String response = HttpClientGet.SendHttpGet(Constants.CATEGORY_MEMBER_DETAIL_LIST);
			if (response != null) {
				try {
					JSONObject obj = new JSONObject(response);
					JSONArray arr = obj.getJSONArray("details");
					base.mDb.insertCatagoryDetailListValue(arr.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		ll_member.setVisibility(View.GONE);
		ll_body.setVisibility(View.GONE);
		ll_member_detail.setVisibility(View.VISIBLE);
		tv_membername.setText("" + memberArr.get(arg2).getName());
		tv_address.setText("" + memberArr.get(arg2).getAddress());
		memberBean = memberArr.get(arg2);
		displaySubView(0, memberBean);
	}

	public void displaySubView(int position, Member memberBean) {
		hideKeyBoard(et_search);
		switch (position) {
		case 0:
			fragment = new CompanyInfoFragment(memberBean);
			break;
		case 1:
			fragment = new PhoneInfoFragment(memberBean);
			break;
		case 2:
			fragment = new MailInfoFragment(memberBean);
			break;
		case 3:
			fragment = new ProductInfoFragment(memberBean);
			break;
		}
		if (fragment != null) {
			FragmentManager fragmentManager = getChildFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.frame_detail_container, fragment).commit();
		} else {
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_conpany_info:
			ll_conpany_info.setBackgroundColor(Color.parseColor("#414141"));
			ll_phone.setBackgroundColor(Color.parseColor("#0069A7"));
			ll_mail.setBackgroundColor(Color.parseColor("#0069A7"));
			ll_products.setBackgroundColor(Color.parseColor("#0069A7"));
			displaySubView(0, memberBean);
			break;
		case R.id.ll_phone:
			ll_conpany_info.setBackgroundColor(Color.parseColor("#0069A7"));
			ll_phone.setBackgroundColor(Color.parseColor("#414141"));
			ll_mail.setBackgroundColor(Color.parseColor("#0069A7"));
			ll_products.setBackgroundColor(Color.parseColor("#0069A7"));
			displaySubView(1, memberBean);
			break;
		case R.id.ll_mail:
			ll_conpany_info.setBackgroundColor(Color.parseColor("#0069A7"));
			ll_phone.setBackgroundColor(Color.parseColor("#0069A7"));
			ll_mail.setBackgroundColor(Color.parseColor("#414141"));
			ll_products.setBackgroundColor(Color.parseColor("#0069A7"));
			displaySubView(2, memberBean);
			break;
		case R.id.ll_products:
			ll_conpany_info.setBackgroundColor(Color.parseColor("#0069A7"));
			ll_phone.setBackgroundColor(Color.parseColor("#0069A7"));
			ll_mail.setBackgroundColor(Color.parseColor("#0069A7"));
			ll_products.setBackgroundColor(Color.parseColor("#414141"));
			displaySubView(3, memberBean);
			break;
		}
	}
  
	@Override
	public void onSelectedCatagoryClick(Member catagory) {
		ll_member.setVisibility(View.GONE);
		ll_body.setVisibility(View.GONE);
		ll_member_detail.setVisibility(View.VISIBLE);
		tv_membername.setText("" + catagory.getName());
		tv_address.setText("" + catagory.getAddress());
		memberBean = catagory;
		displaySubView(0, memberBean);
	}
}