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
import com.steelbuzz.bean.Member;
import com.steelbuzz.fragment.CatagoryFragment;

public class SelectedCatagoryAdapter extends ArrayAdapter<Member> {

	public interface OnSelecedCatagoryClickListener {
		public void onSelectedCatagoryClick(Member catagory);
	}

	private BaseActivity activity;
	private ViewHolder mHolder;
	public ArrayList<Member> item = new ArrayList<Member>();
	private OnSelecedCatagoryClickListener listener;
	private CatagoryFragment fragment;

	public SelectedCatagoryAdapter(BaseActivity activity,CatagoryFragment fragment, int textViewResourceId, ArrayList<Member> items) {
		super(activity, textViewResourceId, items);
		this.item = items;
		this.activity = activity;
		this.fragment = fragment;
		listener = (OnSelecedCatagoryClickListener) fragment;
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

		final Member catagory = item.get(position);

		if (catagory != null) {
			mHolder.tv_name.setText(catagory.getName());
		}

		mHolder.ll_member.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				listener.onSelectedCatagoryClick(catagory);
			}
		});
		return v;
	}

	class ViewHolder {
		public TextView tv_name;
		public LinearLayout ll_member;
	}
}
