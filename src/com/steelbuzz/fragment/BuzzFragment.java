package com.steelbuzz.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.steelbuzz.BaseActivity;
import com.steelbuzz.R;
import com.steelbuzz.adapter.BuzzAdapter;
import com.steelbuzz.bean.BuzzBean;
import com.steelbuzz.constant.Constants;
import com.steelbuzz.network.HttpClientGet;
import com.steelbuzz.util.ImageLoader;

public class BuzzFragment extends BaseFragment /*implements OnItemClickListener, OnClickListener, OnSelectedMemberClickListener */ implements OnItemClickListener{
	private BuzzAdapter buzzAdapter;
	
	private LinearLayout ll_buzz_list, ll_buzz_detail;
	private BaseActivity base;
	private ListView lv_buzz;
	private boolean isRefresh = false;
	public ImageLoader imageLoader;
	public ArrayList<BuzzBean> buzzList = new ArrayList<BuzzBean>();
	
	private TextView tv_date,tvTitle,tv_desc;
	private ImageView ivBuzz;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		base = (BaseActivity) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_buzz, container, false);
		//lv_members = (PullToRefreshListView) v.findViewById(R.id.lv_members);
		ll_buzz_list = (LinearLayout) v.findViewById(R.id.ll_buzz_list);
		ll_buzz_detail = (LinearLayout) v.findViewById(R.id.ll_buzz_detail);
		ll_buzz_list.setVisibility(View.VISIBLE);
		ll_buzz_detail.setVisibility(View.GONE);
		
		lv_buzz = (ListView)v.findViewById(R.id.lv_buzz);
		lv_buzz.setOnItemClickListener(this);
		
		tv_date = (TextView) v.findViewById(R.id.tv_date);
		tvTitle = (TextView) v.findViewById(R.id.tvTitle);
		tv_desc = (TextView) v.findViewById(R.id.tv_desc);
		ivBuzz = (ImageView) v.findViewById(R.id.ivBuzz);
		
		imageLoader=new ImageLoader(getActivity());
		
		new GetBuzzList().execute();
		return v;
	}

	public class GetBuzzList extends AsyncTask<String, BuzzBean, Void> {
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
					response = HttpClientGet.SendHttpGet(Constants.BUZZ_LIST);
					if (response != null) {
						JSONObject jObject = new JSONObject(response);
						jArr = jObject.getJSONArray("buzzsettings");
						buzzList.clear();
						//Constants.memberArr.clear();
						//base.mDb.insertMemberListValue(jArr.toString());
					}
				} else {
					jArr = base.mDb.fetchMemberListValue();
				}

				for (int i = 0; i < jArr.length(); i++) {
					JSONObject c = jArr.getJSONObject(i);
					String id = c.getString("id");
					String title = c.getString("buzz_title");
					String date = c.getString("buzz_date");
					String buzz_image = c.getString("buzz_image");
					String buzz_description = c.getString("buzz_description");
					

					BuzzBean buzz = new BuzzBean(id, title, date, buzz_description, buzz_image);
					publishProgress(buzz);
				}

			} catch (JSONException e) {
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(BuzzBean... values) {
			super.onProgressUpdate(values);
			buzzList.add(values[0]);
			
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
		buzzAdapter = new BuzzAdapter(getActivity(), R.layout.buzz_row, buzzList);
		lv_buzz.setAdapter(buzzAdapter);
		
	}

	public void openSearchpage(BuzzBean buzzBean) {
		
		/*final TextView tv_date = (TextView) dialog.findViewById(R.id.tv_date);
		final TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
		final TextView tv_desc = (TextView) dialog.findViewById(R.id.tv_desc);
		final TextView tv_catagory = (TextView) dialog.findViewById(R.id.tv_catagory);
		final ImageView ivBuzz = (ImageView) dialog.findViewById(R.id.ivBuzz);*/
		
		tv_date.setText(buzzBean.getDate());
		tvTitle.setText(buzzBean.getTitle());
		tv_desc.setText(Html.fromHtml(buzzBean.getDesc()));
		if(buzzBean.getImage().length()> 0){
			ivBuzz.setVisibility(View.VISIBLE);
			System.out.println("!!iamge link:"+Constants.IMAGE_LINK + ""+buzzBean.getImage());
			imageLoader.DisplayImage(Constants.IMAGE_LINK + ""+buzzBean.getImage(), ivBuzz);
		}else{
			ivBuzz.setVisibility(View.GONE);
		}
		tv_date.setText(buzzBean.getDate());
		//dialog.show();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		ll_buzz_list.setVisibility(View.GONE);
		ll_buzz_detail.setVisibility(View.VISIBLE);
		openSearchpage(buzzList.get(arg2));
	}
	
	public void OnbackPress() {
		if (ll_buzz_list.getVisibility() == View.VISIBLE) {
			getActivity().finish();
		} else if (ll_buzz_detail.getVisibility() == View.VISIBLE) {
			ll_buzz_list.setVisibility(View.VISIBLE);
			ll_buzz_detail.setVisibility(View.GONE);
		}else{
			Toast.makeText(getActivity(), "AAA", Toast.LENGTH_LONG).show();
		}
	}
}
