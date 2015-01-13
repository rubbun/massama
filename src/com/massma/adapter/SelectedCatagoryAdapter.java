package com.massma.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.massma.BaseActivity;
import com.massma.R;
import com.massma.bean.Catagory;
import com.massma.bean.SelectedCatagory;

public class SelectedCatagoryAdapter extends ArrayAdapter<SelectedCatagory> {

	public interface OnSelecedCatagoryClickListener {
		public void onSelectedCatagoryClick(int position);
	}

	private BaseActivity activity;
	private ViewHolder mHolder;
	public ArrayList<SelectedCatagory> item = new ArrayList<SelectedCatagory>();
	private OnSelecedCatagoryClickListener listener;

	public SelectedCatagoryAdapter(BaseActivity activity, int textViewResourceId, ArrayList<SelectedCatagory> items) {
		super(activity, textViewResourceId, items);
		this.item = items;
		this.activity = activity;
		listener = (OnSelecedCatagoryClickListener) activity;
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
			v = vi.inflate(R.layout.row_catagory, null);
			mHolder = new ViewHolder();
			v.setTag(mHolder);

			mHolder.tv_name = (TextView) v.findViewById(R.id.tv_name);
			mHolder.ll_member = (LinearLayout) v.findViewById(R.id.ll_member);

		} else {
			mHolder = (ViewHolder) v.getTag();
		}

		final SelectedCatagory catagory = item.get(position);

		if (catagory != null) {
			mHolder.tv_name.setText(catagory.getName());
		}

		mHolder.ll_member.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				listener.onSelectedCatagoryClick(position);
			}
		});
		return v;
	}

	class ViewHolder {
		public TextView tv_name;
		public LinearLayout ll_member;
	}
}
