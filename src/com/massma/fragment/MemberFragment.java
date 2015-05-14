package com.massma.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.massma.BaseActivity;
import com.massma.IndexableListView;
import com.massma.R;
import com.massma.adapter.MemberAdapter;
import com.massma.adapter.SelectedMemberAdapter;
import com.massma.bean.Member;
import com.massma.bean.SelectedMember;
import com.massma.constant.Constants;
import com.massma.fragment.CatagoryFragment.GetMemberList;
import com.massma.network.HttpClient;
import com.massma.subfragment.CompanyInfoFragment;
import com.massma.subfragment.MailInfoFragment;
import com.massma.subfragment.PhoneInfoFragment;
import com.massma.subfragment.ProductInfoFragment;

public class MemberFragment extends BaseFragment implements OnItemClickListener, OnClickListener {
	private ArrayList<Member> memberArr = new ArrayList<Member>();
	private IndexableListView lv_members;
	private MemberAdapter memberAdapter;
	private AutoCompleteTextView et_search = null;
	private TextView tv_membername, tv_address;
	private LinearLayout ll_member_detail, ll_member_list;
	private Fragment fragment = null;
	private BaseActivity base;
	private Member memberBean;
	
	private LinearLayout ll_conpany_info,ll_phone,ll_mail,ll_products;

	public MemberFragment(BaseActivity base) {
		this.base = base;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_member, container, false);
		lv_members = (IndexableListView) v.findViewById(R.id.lv_members);
		et_search = (AutoCompleteTextView) v.findViewById(R.id.et_search);
		ll_member_list = (LinearLayout) v.findViewById(R.id.ll_member_list);
		ll_member_detail = (LinearLayout) v.findViewById(R.id.ll_member_detail);
		ll_member_list.setVisibility(View.VISIBLE);
		ll_member_detail.setVisibility(View.GONE);
		tv_membername = (TextView) v.findViewById(R.id.tv_membername);
		tv_address = (TextView) v.findViewById(R.id.tv_address);

		ll_conpany_info = (LinearLayout)v.findViewById(R.id.ll_conpany_info);
		ll_conpany_info.setOnClickListener(this);
		
		ll_phone = (LinearLayout)v.findViewById(R.id.ll_phone);
		ll_phone.setOnClickListener(this);
		
		ll_mail = (LinearLayout)v.findViewById(R.id.ll_mail);
		ll_mail.setOnClickListener(this);
		
		ll_products = (LinearLayout)v.findViewById(R.id.ll_products);
		ll_products.setOnClickListener(this);
		
		lv_members.setOnItemClickListener(this);

		new GetMemberList().execute("1");

		return v;

	}

	public class GetMemberList extends AsyncTask<String, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showLoading();
		}

		@Override
		protected Void doInBackground(String... arg0) {

			try {

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("category_id", 1);
				String response = HttpClient.SendHttpPost(Constants.CATEGORY_MEMBER_LIST, jsonObject.toString());
				if (response != null) {
					JSONObject jObject = new JSONObject(response);
					JSONArray jArr = jObject.getJSONArray("details");
					for (int i = 0; i < jArr.length(); i++) {
						JSONObject c = jArr.getJSONObject(i);
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
						memberArr.add(new Member(name, address, contactParson, tata, mobile, fax, residential, email, web, hughes_no));

					}
					Collections.sort(memberArr, new Comparator<Member>() {

						@Override
						public int compare(Member arg0, Member arg1) {
							return arg0.getName().compareTo(arg1.getName());
						}
					});
				}
			} catch (JSONException e) {

			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			dismissLoading();
			memberAdapter = new MemberAdapter(getActivity(), R.layout.row_member, memberArr);
			lv_members.setAdapter(memberAdapter);
			lv_members.setFastScrollEnabled(true);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		ll_member_list.setVisibility(View.GONE);
		ll_member_detail.setVisibility(View.VISIBLE);
		tv_membername.setText("" + memberArr.get(arg2).getName());
		tv_address.setText("" + memberArr.get(arg2).getAddress());
		memberBean = memberArr.get(arg2);
		displaySubView(0,memberBean);
	}

	public void displaySubView(int position,Member memberBean) {
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
			displaySubView(0,memberBean);
			break;
		case R.id.ll_phone:
			ll_conpany_info.setBackgroundColor(Color.parseColor("#0069A7"));
			ll_phone.setBackgroundColor(Color.parseColor("#414141"));
			ll_mail.setBackgroundColor(Color.parseColor("#0069A7"));
			ll_products.setBackgroundColor(Color.parseColor("#0069A7"));
			displaySubView(1,memberBean);
			break;
		case R.id.ll_mail:
			ll_conpany_info.setBackgroundColor(Color.parseColor("#0069A7"));
			ll_phone.setBackgroundColor(Color.parseColor("#0069A7"));
			ll_mail.setBackgroundColor(Color.parseColor("#414141"));
			ll_products.setBackgroundColor(Color.parseColor("#0069A7"));
			displaySubView(2,memberBean);
			break;
		case R.id.ll_products:
			ll_conpany_info.setBackgroundColor(Color.parseColor("#0069A7"));
			ll_phone.setBackgroundColor(Color.parseColor("#0069A7"));
			ll_mail.setBackgroundColor(Color.parseColor("#0069A7"));
			ll_products.setBackgroundColor(Color.parseColor("#414141"));
			displaySubView(3,memberBean);
			break;
		}
	}

}
