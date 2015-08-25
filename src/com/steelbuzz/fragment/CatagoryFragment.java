package com.steelbuzz.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.steelbuzz.BaseActivity;
import com.steelbuzz.MyCustomProgressDialog;
import com.steelbuzz.R;
import com.steelbuzz.adapter.CatagoryAdapter;
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

public class CatagoryFragment extends BaseFragment implements OnItemClickListener, OnClickListener, OnSelecedCatagoryClickListener {

	private ProgressDialog progressDialog;
	public LinearLayout ll_body, ll_member, ll_member_detail;
	public View view;
	private ArrayList<Member> memberArr = new ArrayList<Member>();
	private SelectedCatagoryAdapter selectedCatagoryAdapter;
	private ArrayList<Member> selectedtempArr = new ArrayList<Member>();
	private ImageView ivTick;
	private ImageView ivBack;
	private LinearLayout llBack;
	private ListView lv_members;
	private AutoCompleteTextView et_search;
	//private MemberAdapter memberAdapter;
	private CatagoryAdapter catagoryAdapter;
	private BaseActivity base;
	private TextView tv_membername, tv_address;
	private Member memberBean;
	private Fragment fragment = null;
	private int index = -1;
	private LinearLayout ll_conpany_info,llMain, ll_phone, ll_mail, ll_products;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		base = (BaseActivity) activity;
	}

	/*public CatagoryFragment() {

	}*/

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_category, container, false);
		ll_body = (LinearLayout) v.findViewById(R.id.ll_body);
		ll_member = (LinearLayout) v.findViewById(R.id.ll_member);
		ll_member_detail = (LinearLayout) v.findViewById(R.id.ll_member_detail);
		ll_member_detail.setVisibility(View.GONE);
		ll_member.setVisibility(View.GONE);
		ll_body.setVisibility(View.VISIBLE);
		ivBack = (ImageView)v.findViewById(R.id.ivBack);
		llBack = (LinearLayout)v.findViewById(R.id.llBack);
		llMain = (LinearLayout)v.findViewById(R.id.llMain);
		
		ivTick = (ImageView)v.findViewById(R.id.ivTick);

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
		
		ivBack.setOnClickListener(this);
		llBack.setOnClickListener(this);
		
		ivTick.setOnClickListener(this);

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
							if (retailerName.toLowerCase().contains(searchString.toLowerCase())) {

								selectedtempArr.add(new Member(memberArr.get(i).getName(), memberArr.get(i).getName(), memberArr.get(i).getAddress(), memberArr.get(i).getContactParson(), memberArr.get(i).getTata(), memberArr.get(i).getMobile(), memberArr.get(i).getFax(), memberArr.get(i).getResidential(), memberArr.get(i).getEmail(), memberArr.get(i).getWeb(), memberArr.get(i).getHughes_no(),memberArr.get(i).getCity(),memberArr.get(i).getCountry(),memberArr.get(i).getStatus()));
							}
						}
					}
					selectedCatagoryAdapter = new SelectedCatagoryAdapter(base, CatagoryFragment.this, R.layout.row_catagory, selectedtempArr);
					lv_members.setAdapter(selectedCatagoryAdapter);
				} else {
					lv_members.setAdapter(catagoryAdapter);
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
				progressDialog = MyCustomProgressDialog.ctor(getActivity());
				progressDialog.show();
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
				progressDialog.hide();
			}
			fetchAllCatagoryDetailList();
			ll_body.removeAllViews();
			for (int i = 0; i < result.length(); i++) {
				try {
					index = 0;
					JSONObject jsonObject = result.getJSONObject(i);
					ll_body.addView(SubView(jsonObject, 10,"level0"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public View SubView(final JSONObject jsonObject, int padding, String tag) {
		try {
			view = View.inflate(getActivity(), R.layout.row_catagory, null);
			String name = jsonObject.getString("name");
			final String id = jsonObject.getString("id");
			TextView textViewName = (TextView) view.findViewById(R.id.textViewName);
			textViewName.setText(name);
			final LinearLayout llSubCategory = (LinearLayout) view.findViewById(R.id.llSubCategory);
			LinearLayout llBody = (LinearLayout) view.findViewById(R.id.llBody);
			llBody.setTag(tag+System.currentTimeMillis());
			if(name.equals("Nickel Alloys")){
				textViewName.setBackgroundResource(R.drawable.nickel_alloys);
				textViewName.setTextColor(Color.WHITE);
			}else if(name.equals("Stainless Steel Pipes")){
				textViewName.setBackgroundResource(R.drawable.ss_pipes);
				textViewName.setTextColor(Color.WHITE);
			}else if(name.equals("Stainless Steel Sheets & Plates")){
				textViewName.setBackgroundResource(R.drawable.ss_sheets_plates);
				textViewName.setTextColor(Color.WHITE);
			}else if(name.equals("Stainless Steel Round Bar")){
				textViewName.setBackgroundResource(R.drawable.ss_bars);
				textViewName.setTextColor(Color.WHITE);
			}else if(name.equals("Fitings")){
				textViewName.setBackgroundResource(R.drawable.fittings);
				textViewName.setTextColor(Color.WHITE);
			}else if(name.equals("Flanges")){
				textViewName.setBackgroundResource(R.drawable.flanges);
				textViewName.setTextColor(Color.WHITE);
			}else if(name.equals("Stainless Steel Scrap")){
				textViewName.setBackgroundResource(R.drawable.ss_scrap);
				textViewName.setTextColor(Color.WHITE);
			}else if(name.equals("Copper and Brass")){
				textViewName.setBackgroundResource(R.drawable.copper_brass);
				textViewName.setTextColor(Color.WHITE);
			}else if(name.equals("Aluminium")){
				textViewName.setBackgroundResource(R.drawable.aluminium);
				textViewName.setTextColor(Color.WHITE);
			}
			
			if(tag.equalsIgnoreCase("level1")){
				textViewName.setBackgroundColor(Color.parseColor("#006AA8"));
				LayoutParams params = textViewName.getLayoutParams();
				params.height = 120;
				textViewName.setLayoutParams(params);
				textViewName.setTextColor(Color.WHITE);
			}else if(tag.equalsIgnoreCase("level2")){
				textViewName.setBackgroundColor(Color.parseColor("#7FB4D3"));
				LayoutParams params = textViewName.getLayoutParams();
				params.height = 100;
				textViewName.setLayoutParams(params);
				textViewName.setTextColor(Color.parseColor("#07669E"));
			}
			
			
			llBody.setPadding(padding, 0, 0, 0);
			textViewName.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					try {
						index = 1;
						if (jsonObject.has("root")) {
							int childCount = llSubCategory.getChildCount();
							if (childCount > 0) {
								llSubCategory.removeAllViews();
							} else {
								JSONArray jsonArray = jsonObject.getJSONArray("root");
								for (int i = 0; i < jsonArray.length(); i++) {
									index = 2;
									JSONObject subJsonObject = jsonArray.getJSONObject(i);
									if(((View)arg0.getParent()).getTag().toString().contains("level0")){
										llSubCategory.addView(SubView(subJsonObject, 40,"level1"));
									}else if(((View)arg0.getParent()).getTag().toString().contains("level1")){
										llSubCategory.addView(SubView(subJsonObject, 40,"level2"));	
									}
									
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
			/*
			 * if (base.hasConnection()) { showLoading(); }
			 */
		}

		@Override
		protected Void doInBackground(String... arg0) {

			try {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("category_id", arg0[0]);
				JSONArray jArr = new JSONArray();
				/*
				 * if (base.hasConnection()) { String response =
				 * HttpClient.SendHttpPost(Constants.CATEGORY_MEMBER_LIST,
				 * jsonObject.toString()); if (response != null) { JSONObject
				 * jObject = new JSONObject(response); jArr =
				 * jObject.getJSONArray("details"); } } else {
				 */
				JSONArray arr = base.mDb.fetchCatagoryDetailsList();
				for (int i = 0; i < arr.length(); i++) {
					JSONObject c = arr.getJSONObject(i);
					if (c.getString("category_id").equalsIgnoreCase(arg0[0])) {
						jArr.put(c);
					}
				}
				// }
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
					String city = c.getString("city");
					String country = c.getString("country");
					String status = c.getString("status");
					memberArr.add(new Member(id, name, address, contactParson, tata, mobile, fax, residential, email, web, hughes_no,city,country,status));
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
				//dismissLoading();
			}
			ll_member.setVisibility(View.VISIBLE);
			ll_body.setVisibility(View.GONE);
			catagoryAdapter = new CatagoryAdapter(getActivity(), R.layout.row_member, memberArr);
			lv_members.setAdapter(catagoryAdapter);
			//lv_members.setFastScrollEnabled(true);
			llMain.setVisibility(View.GONE);
		}
	}

	public void OnbackPress() {
		if (ll_body.getVisibility() == View.VISIBLE) {
			getActivity().finish();
		} else if (ll_member.getVisibility() == View.VISIBLE) {
			ll_body.setVisibility(View.VISIBLE);
			ll_member.setVisibility(View.GONE);
			ll_member_detail.setVisibility(View.GONE);
			llMain.setVisibility(View.VISIBLE);
		} else if (ll_member_detail.getVisibility() == View.VISIBLE) {
			ll_body.setVisibility(View.GONE);
			ll_member.setVisibility(View.VISIBLE);
			ll_member_detail.setVisibility(View.GONE);
		}
	}

	public void resetUiVisiabilty() {
		if((ll_member_detail.getVisibility() == View.VISIBLE) || (ll_member.getVisibility() == View.VISIBLE)){
			llMain.setVisibility(View.VISIBLE);
			ll_member_detail.setVisibility(View.GONE);
			ll_member.setVisibility(View.GONE);
			ll_body.setVisibility(View.VISIBLE);
		}
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
		if(selectedtempArr.size() > 0){
			ll_member.setVisibility(View.GONE);
			ll_body.setVisibility(View.GONE);
			
			ll_member_detail.setVisibility(View.VISIBLE);
			tv_membername.setText("" + selectedtempArr.get(arg2).getName());
			tv_address.setText("" + selectedtempArr.get(arg2).getCity()+ ", "+selectedtempArr.get(arg2).getCountry());
			memberBean = selectedtempArr.get(arg2);
			if(selectedtempArr.get(arg2).getStatus().equalsIgnoreCase("0")){
				ivTick.setVisibility(View.GONE);
			}else
				ivTick.setVisibility(View.VISIBLE);
			
			displaySubView(0, memberBean);
		}else{
			ll_member.setVisibility(View.GONE);
			ll_body.setVisibility(View.GONE);
			ll_member_detail.setVisibility(View.VISIBLE);
			tv_membername.setText("" + memberArr.get(arg2).getName());
			tv_address.setText("" + memberArr.get(arg2).getCity()+", "+memberArr.get(arg2).getCountry());
			memberBean = memberArr.get(arg2);
			if(memberArr.get(arg2).getStatus().equalsIgnoreCase("0")){
				ivTick.setVisibility(View.GONE);
			}else
				ivTick.setVisibility(View.VISIBLE);
			
			displaySubView(0, memberBean);
		}
		
	}

	public void displaySubView(int position, Member memberBean) {
		hideKeyBoard(et_search);
		switch (position) {
		case 0:
			fragment = new CompanyInfoFragment(memberBean,base);
			ll_conpany_info.setBackgroundColor(Color.parseColor("#FFFFFF"));
			ll_phone.setBackgroundColor(Color.parseColor("#ababab"));
			ll_mail.setBackgroundColor(Color.parseColor("#ababab"));
			ll_products.setBackgroundColor(Color.parseColor("#ababab"));
			break;
		case 1:
			fragment = new PhoneInfoFragment(memberBean,base);
			break;
		case 2:
			fragment = new MailInfoFragment(memberBean,base);
			break;
		case 3:
			fragment = new ProductInfoFragment(memberBean,base);
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
			ll_conpany_info.setBackgroundColor(Color.parseColor("#FFFFFF"));
			ll_phone.setBackgroundColor(Color.parseColor("#ababab"));
			ll_mail.setBackgroundColor(Color.parseColor("#ababab"));
			ll_products.setBackgroundColor(Color.parseColor("#ababab"));
			displaySubView(0, memberBean);
			break;
		case R.id.ll_phone:
			ll_conpany_info.setBackgroundColor(Color.parseColor("#ababab"));
			ll_phone.setBackgroundColor(Color.parseColor("#FFFFFF"));
			ll_mail.setBackgroundColor(Color.parseColor("#ababab"));
			ll_products.setBackgroundColor(Color.parseColor("#ababab"));
			displaySubView(1, memberBean);
			break;
		case R.id.ll_mail:
			ll_conpany_info.setBackgroundColor(Color.parseColor("#ababab"));
			ll_phone.setBackgroundColor(Color.parseColor("#ababab"));
			ll_mail.setBackgroundColor(Color.parseColor("#FFFFFF"));
			ll_products.setBackgroundColor(Color.parseColor("#ababab"));
			displaySubView(2, memberBean);
			break;
		case R.id.ll_products:
			ll_conpany_info.setBackgroundColor(Color.parseColor("#ababab"));
			ll_phone.setBackgroundColor(Color.parseColor("#ababab"));
			ll_mail.setBackgroundColor(Color.parseColor("#ababab"));
			ll_products.setBackgroundColor(Color.parseColor("#FFFFFF"));
			displaySubView(3, memberBean);
			break;
			
		case R.id.ivBack:
			if (ll_member.getVisibility() == View.VISIBLE) {
				ll_body.setVisibility(View.VISIBLE);
				ll_member.setVisibility(View.GONE);
				ll_member_detail.setVisibility(View.GONE);
			} 
			break;
			
		case R.id.llBack:
			if (ll_member_detail.getVisibility() == View.VISIBLE) {
				ll_body.setVisibility(View.GONE);
				ll_member.setVisibility(View.VISIBLE);
				ll_member_detail.setVisibility(View.GONE);
			} 
			break;
			
		case R.id.ivTick:
			displayPopupWindow(v);
			break;
		}
	}

	private void displayPopupWindow(View anchorView) {
        PopupWindow popup = new PopupWindow(getActivity());
        View layout = getActivity().getLayoutInflater().inflate(R.layout.popup_content, null);
        TextView tvCaption = (TextView)layout.findViewById(R.id.tvCaption);
        tvCaption.setText("Verified");
        popup.setContentView(layout);
        // Set content width and height
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        // Closes the popup window when touch outside of it - when looses focus
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        // Show anchored to button
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAsDropDown(anchorView);
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