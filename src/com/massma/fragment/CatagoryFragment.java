package com.massma.fragment;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.massma.BaseActivity;
import com.massma.R;

public class CatagoryFragment extends BaseFragment {

	private BaseActivity base;
	public LinearLayout ll_body,ll_member;
	public View view;
	public JSONArray jsonArray0,jsonArray1,jsonArray2;
	public int level = 0;

	public CatagoryFragment(BaseActivity base) {
		this.base = base;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_category, container, false);
		ll_body = (LinearLayout) v.findViewById(R.id.ll_body);
		ll_member = (LinearLayout) v.findViewById(R.id.ll_member);
		new CategoryAsynctask().execute();

		return v;
	}

	public View CategoryLeve(final JSONObject jsonObject) {
		view = View.inflate(getActivity(), R.layout.view_category, null);
		try {
			((TextView) view.findViewById(R.id.lblName)).setText(jsonObject.getString("Name"));
			((TextView) view.findViewById(R.id.lblName)).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (jsonObject.has("root")) {
						try {
							level++;
							ll_body.removeAllViews();
							JSONArray jarr = jsonObject.getJSONArray("root");
							if(level == 1){
								jsonArray1 = jarr;
							}else if(level == 2){
								jsonArray2 = jarr;
							}
							for (int i = 0; i < jarr.length(); i++) {
								JSONObject c = jarr.getJSONObject(i);
								ll_body.addView(CategoryLeve(c));

							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
						

					}else{
						ll_body.setVisibility(View.GONE);
						ll_member.setVisibility(View.VISIBLE);
					}

				}
			});
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return view;

	}
	
	
	public class CategoryAsynctask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showLoading();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				JSONObject response = new JSONObject(readXMLinString("category.json"));
				if (response != null) {
					jsonArray0 = response.getJSONArray("root");
				}
			} catch (JSONException e) {

			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			dismissLoading();
			ll_body.removeAllViews();
			for (int i = 0; i < jsonArray0.length(); i++) {
				try {
					JSONObject c = jsonArray0.getJSONObject(i);
					ll_body.addView(CategoryLeve(c));

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}

		public String readXMLinString(String fileName) {
			try {
				InputStream is = getActivity().getAssets().open(fileName);
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

	}
	
	public class CategoryListAsynctask extends AsyncTask<Void, Void, Void>{
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showLoading();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			dismissLoading();
		}
		
	}
	
	public void ToggleView(){
		
	}

}