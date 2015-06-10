package com.steelbuzz.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.steelbuzz.R;
import com.steelbuzz.bean.Catagory;

public class CatagoryAdapter extends ArrayAdapter<Catagory> {

	private Context context;
	private ViewHolder mHolder;
	public JSONArray item;

	public CatagoryAdapter(Context context, int textViewResourceId, JSONArray items) {
		super(context, textViewResourceId);
		this.item = items;
		this.context = context;

	}

	@Override
	public int getCount() {
		return item.length();
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.row_catagory, null);
			mHolder = new ViewHolder();
			v.setTag(mHolder);

			mHolder.textViewName = (TextView) v.findViewById(R.id.textViewName);
			mHolder.llSubCategory = (LinearLayout) v.findViewById(R.id.llSubCategory);

		} else {
			mHolder = (ViewHolder) v.getTag();
		}
		try {
			mHolder.textViewName.setText(item.getJSONObject(position).getString("name"));

		} catch (JSONException e) {
			e.printStackTrace();
		}

		mHolder.textViewName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				JSONObject jsonObject;
				try {
					jsonObject = item.getJSONObject(position);
					if (jsonObject.has("root")) {
						mHolder.llSubCategory.removeAllViews();
						JSONArray jsonArray = jsonObject.getJSONArray("root");
						System.out.println(jsonArray.toString());
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject subJsonObject = jsonArray.getJSONObject(i);
							mHolder.llSubCategory.addView(SubView(subJsonObject));
							notifyDataSetChanged();
						}
					} else {
						Toast.makeText(context, "END", Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		});
		return v;
	}

	class ViewHolder {
		public TextView textViewName;
		public LinearLayout llSubCategory;

	}

	public View view;

	public View SubView(final JSONObject jsonObject) {
		try {
			view = View.inflate(context, R.layout.row_catagory, null);
			String name = jsonObject.getString("name");
			String id = jsonObject.getString("id");
			TextView textViewName = (TextView) view.findViewById(R.id.textViewName);
			textViewName.setText(name);
			System.out.println("inner");
			final LinearLayout llSubCategory = (LinearLayout) view.findViewById(R.id.llSubCategory);
			LinearLayout llBody = (LinearLayout) view.findViewById(R.id.llBody);
			if (jsonObject.has("root")) {
				
			}else{
				
			}
			textViewName.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					try {
						if (jsonObject.has("root")) {
							JSONArray jsonArray = jsonObject.getJSONArray("root");
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject subJsonObject = jsonArray.getJSONObject(i);
								llSubCategory.addView(SubView(subJsonObject));
							}
						} else {
							Toast.makeText(context, "END", Toast.LENGTH_LONG).show();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			return view;
		} catch (Exception e) {
			System.out.println("exceptiojn");
			e.printStackTrace();
			return null;
		}
	}
}
