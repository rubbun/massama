package com.massma.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.massma.IndexableListView;
import com.massma.R;
import com.massma.adapter.MemberAdapter;
import com.massma.adapter.SelectedMemberAdapter;
import com.massma.bean.Member;
import com.massma.bean.SelectedMember;
import com.massma.constant.Constants;
import com.massma.network.HttpClient;

public class CatagoryFragment extends BaseFragment {

	public LinearLayout ll_body, ll_member;
	public View view;
	private ArrayList<Member> memberArr = new ArrayList<Member>();
	private IndexableListView lv_members;
	private MemberAdapter memberAdapter;
	private SelectedMemberAdapter selectedmemberAdapter;
	private AutoCompleteTextView et_search = null;
	private ArrayList<Member> tempArr = new ArrayList<Member>();
	private ArrayList<SelectedMember> selectedtempArr = new ArrayList<SelectedMember>();

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_category, container, false);
		ll_body = (LinearLayout) v.findViewById(R.id.ll_body);
		ll_member = (LinearLayout) v.findViewById(R.id.ll_member);
		ll_member.setVisibility(View.GONE);
		ll_body.setVisibility(View.VISIBLE);
		lv_members = (IndexableListView) v.findViewById(R.id.lv_members);
		et_search = (AutoCompleteTextView) v.findViewById(R.id.et_search);
		// listCategory = (ListView) v.findViewById(R.id.listCategory);
		new CategoryAsynctask().execute();

		return v;
	}

	public class CategoryAsynctask extends AsyncTask<Void, Void, JSONArray> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showLoading();
		}

		@Override
		protected JSONArray doInBackground(Void... arg0) {
			try {
				/*
				 * JSONObject response = new
				 * JSONObject(readXMLinString("category.json"));
				 */
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("category", 1);
				String response = HttpClient.SendHttpPost(Constants.CATEGORY_LIST, jsonObject.toString());
				if (response != null) {
					JSONObject jObject = new JSONObject(response);
					return jObject.getJSONArray("root");
				}
			} catch (JSONException e) {

			}

			return null;
		}

		@Override
		protected void onPostExecute(JSONArray result) {
			super.onPostExecute(result);
			dismissLoading();
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
					for(int i=0; i<jArr.length(); i++){
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
			ll_member.setVisibility(View.VISIBLE);
			ll_body.setVisibility(View.GONE);
			memberAdapter = new MemberAdapter(getActivity(), R.layout.row_member, memberArr);
			lv_members.setAdapter(memberAdapter);
			lv_members.setFastScrollEnabled(true);
			
		}

	}

}