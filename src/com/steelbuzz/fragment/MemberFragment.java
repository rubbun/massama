package com.steelbuzz.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ninehetz.pulltorefreshlistlib.PullToRefreshListView;
import org.ninehetz.pulltorefreshlistlib.PullToRefreshListView.OnRefreshListener;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.steelbuzz.BaseActivity;
import com.steelbuzz.R;
import com.steelbuzz.adapter.MemberAdapter;
import com.steelbuzz.adapter.SelectedMemberAdapter;
import com.steelbuzz.adapter.SelectedMemberAdapter.OnSelectedMemberClickListener;
import com.steelbuzz.bean.Member;
import com.steelbuzz.constant.Constants;
import com.steelbuzz.network.HttpClientGet;
import com.steelbuzz.subfragment.CompanyInfoFragment;
import com.steelbuzz.subfragment.MailInfoFragment;
import com.steelbuzz.subfragment.PhoneInfoFragment;
import com.steelbuzz.subfragment.ProductInfoFragment;

public class MemberFragment extends BaseFragment implements OnItemClickListener, OnClickListener, OnSelectedMemberClickListener {
	private PullToRefreshListView lv_members;
	private MemberAdapter memberAdapter;
	private SelectedMemberAdapter selectedmemberAdapter;
	private EditText et_search;
	private AutoCompleteTextView ll_dialog_search;
	private TextView tv_membername, tv_address;
	private LinearLayout ll_member_detail, ll_member_list;
	private Fragment fragment = null;
	private BaseActivity base;
	private Member memberBean;
	private Dialog dialog;
	private boolean isSearchEnable = false;
	private boolean isRefresh = false;
	public ArrayList<Member> memberList = new ArrayList<Member>();
	private ArrayList<Member> selectedtempArr = new ArrayList<Member>();
	private LinearLayout ll_conpany_info, ll_phone, ll_mail, ll_products;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		base = (BaseActivity) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_member, container, false);
		lv_members = (PullToRefreshListView) v.findViewById(R.id.lv_members);
		et_search = (EditText) v.findViewById(R.id.et_search);
		ll_member_list = (LinearLayout) v.findViewById(R.id.ll_member_list);
		ll_member_detail = (LinearLayout) v.findViewById(R.id.ll_member_detail);
		ll_member_list.setVisibility(View.VISIBLE);
		ll_member_detail.setVisibility(View.GONE);
		tv_membername = (TextView) v.findViewById(R.id.tv_membername);
		tv_address = (TextView) v.findViewById(R.id.tv_address);

		ll_conpany_info = (LinearLayout) v.findViewById(R.id.ll_conpany_info);
		ll_conpany_info.setOnClickListener(this);

		ll_phone = (LinearLayout) v.findViewById(R.id.ll_phone);
		ll_phone.setOnClickListener(this);

		ll_mail = (LinearLayout) v.findViewById(R.id.ll_mail);
		ll_mail.setOnClickListener(this);

		ll_products = (LinearLayout) v.findViewById(R.id.ll_products);
		ll_products.setOnClickListener(this);

		lv_members.setOnItemClickListener(this);

		et_search.setOnClickListener(this);

		new GetMemberList().execute();
		
		lv_members.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				if (base.hasConnection()) {
					isRefresh = true;
					new GetMemberList().execute();
				} else {
					lv_members.onRefreshComplete();
				}
			}
		});
		return v;
	}

	public class GetMemberList extends AsyncTask<String, Member, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (base.hasConnection() && (isRefresh == false)) {
				showLoading();
			}
		}

		@Override
		protected Void doInBackground(String... arg0) {

			try {
				String response;
				JSONArray jArr = null;
				if (base.hasConnection()) {
					response = HttpClientGet.SendHttpGet(Constants.ALL_MEMBER_LIST);
					if (response != null) {
						JSONObject jObject = new JSONObject(response);
						jArr = jObject.getJSONArray("members");
						memberList.clear();
						//Constants.memberArr.clear();
						base.mDb.insertMemberListValue(jArr.toString());
					}
				} else {
					jArr = base.mDb.fetchMemberListValue();
				}

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

					Member member1 = new Member(id, name, address, contactParson, tata, mobile, fax, residential, email, web, hughes_no);
					publishProgress(member1);
				}

			} catch (JSONException e) {
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Member... values) {
			super.onProgressUpdate(values);
			memberList.add(values[0]);
			//Constants.memberArr.add(values[0]);
			Collections.sort(Constants.memberArr, new Comparator<Member>() {
				@Override
				public int compare(Member arg0, Member arg1) {
					return arg0.getName().compareTo(arg1.getName());
				}
			});
			if (base.hasConnection() && (isRefresh == false)) {
				dismissLoading();
			}
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			isRefresh = true;
			updateUi();
		}
	}

	private void updateUi() {
		Constants.memberArr.clear();
		Constants.memberArr = memberList;
		memberAdapter = new MemberAdapter(getActivity(), R.layout.row_member, Constants.memberArr);
		lv_members.setAdapter(memberAdapter);
		lv_members.setFastScrollEnabled(true);
		lv_members.onRefreshComplete();
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (dialog != null) {
			isSearchEnable = false;
			dialog.cancel();
		}
		ll_member_list.setVisibility(View.GONE);
		ll_member_detail.setVisibility(View.VISIBLE);
		tv_membername.setText("" + Constants.memberArr.get(arg2).getName());
		tv_address.setText("" + Constants.memberArr.get(arg2).getAddress());
		memberBean = Constants.memberArr.get(arg2);
		displaySubView(0, memberBean);
	}

	public void displaySubView(int position, Member memberBean) {
		if (dialog != null) {
			dialog.hide();
		}
		hideKeyBoard(ll_dialog_search);
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
			
		case R.id.et_search:
			openSearchpage();
			isSearchEnable = true;
			break;
		}
	}

	public void OnbackPress() {
		if (ll_member_detail.getVisibility() == View.VISIBLE) {
			if(isSearchEnable){
				dialog.show();
			}
			ll_member_detail.setVisibility(View.GONE);
			ll_member_list.setVisibility(View.VISIBLE);
		} else {
			isSearchEnable = false;
			getActivity().finish();
		}
	}

	@Override
	public void onSelectedMemberClick(Member member) {
		ll_member_list.setVisibility(View.GONE);
		ll_member_detail.setVisibility(View.VISIBLE);
		tv_membername.setText("" + member.getName());
		tv_address.setText("" + member.getAddress());
		memberBean = member;
		displaySubView(0, memberBean);
	}

	public void openSearchpage() {
		dialog = new Dialog(base);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog.setContentView(R.layout.dialog_member);
		dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

		final ListView lv_dialog_members = (ListView) dialog.findViewById(R.id.lv_dialog_members);
		ll_dialog_search = (AutoCompleteTextView) dialog.findViewById(R.id.ll_dialog_search);

		showKeyBoard();
		ll_dialog_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

				// search(cs.toString());
				String searchString = ll_dialog_search.getText().toString();
				if (searchString.length() > 0) {
					int textLength = searchString.length();
					selectedtempArr.clear();
					for (int i = 0; i < Constants.memberArr.size(); i++) {
						String retailerName = Constants.memberArr.get(i).getName();
						if (textLength <= retailerName.length()) {
							if (retailerName.toLowerCase().contains(searchString.toLowerCase())) {
								selectedtempArr.add(new Member(Constants.memberArr.get(i).getName(), Constants.memberArr.get(i).getName(), Constants.memberArr.get(i).getAddress(), Constants.memberArr.get(i).getContactParson(), Constants.memberArr.get(i).getTata(), Constants.memberArr.get(i).getMobile(), Constants.memberArr.get(i).getFax(), Constants.memberArr.get(i).getResidential(), Constants.memberArr.get(i).getEmail(), Constants.memberArr.get(i).getWeb(), Constants.memberArr.get(i).getHughes_no()));								
							}
						}
					}
					selectedmemberAdapter = new SelectedMemberAdapter(base, MemberFragment.this, R.layout.row_member, selectedtempArr);
					lv_dialog_members.setAdapter(selectedmemberAdapter);
					lv_dialog_members.setFastScrollEnabled(false);
				} else {
					lv_dialog_members.setAdapter(null);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});

		lv_dialog_members.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				ll_member_list.setVisibility(View.GONE);
				ll_member_detail.setVisibility(View.VISIBLE);
				tv_membername.setText("" + Constants.memberArr.get(arg2).getName());
				tv_address.setText("" + Constants.memberArr.get(arg2).getAddress());
				memberBean = Constants.memberArr.get(arg2);
				
				displaySubView(0, memberBean);
			}
		});
		dialog.show();
	}
	
	public void resetUiVisiabilty() {
		ll_member_list.setVisibility(View.VISIBLE);
		ll_member_detail.setVisibility(View.GONE);
	}
}
