package com.steelbuzz.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.steelbuzz.BaseActivity;
import com.steelbuzz.R;
import com.steelbuzz.bean.SelectedSubCatagory;

public class SelectedSubCatagoryAdapter extends ArrayAdapter<SelectedSubCatagory> {
	
	public interface OnSelectedSubCatagoryClickListener{
		public void onSelectedSubCatagoryClick(String name, String address, String contactParson, String tata, String mobile, String fax, String residential, String email, String web);
	}

	private BaseActivity activity;
	private ViewHolder mHolder;
	public ArrayList<SelectedSubCatagory> item = new ArrayList<SelectedSubCatagory>();
	private OnSelectedSubCatagoryClickListener listener;

	public SelectedSubCatagoryAdapter(BaseActivity activity, int textViewResourceId, ArrayList<SelectedSubCatagory> items) {
		super(activity, textViewResourceId, items);
		this.item = items;
		this.activity = activity;
		listener = (OnSelectedSubCatagoryClickListener) activity;
	}

	@Override
	public int getCount() {
		return item.size();
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.row_member, null);
			mHolder = new ViewHolder();
			v.setTag(mHolder);

			mHolder.tv_name = (TextView) v.findViewById(R.id.tv_name);
			mHolder.ll_member = (LinearLayout) v.findViewById(R.id.ll_member);

		} else {
			mHolder = (ViewHolder) v.getTag();
		}

		final SelectedSubCatagory member = item.get(position);

		if (member != null) {
			mHolder.tv_name.setText(member.getName());
		}
		
		mHolder.ll_member.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				listener.onSelectedSubCatagoryClick(item.get(position).getName(),item.get(position).getAddress(),item.get(position).getContactParson(),item.get(position).getTata(),item.get(position).getMobile(),item.get(position).getFax(), item.get(position).getResidential(), item.get(position).getEmail(), item.get(position).getWeb());
			}
		});
		return v;
	}

	class ViewHolder {
		public TextView tv_name;
		public LinearLayout ll_member;
	}
}
