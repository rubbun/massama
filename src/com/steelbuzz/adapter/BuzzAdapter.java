package com.steelbuzz.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.steelbuzz.R;
import com.steelbuzz.bean.BuzzBean;

public class BuzzAdapter extends ArrayAdapter<BuzzBean> {

	private Context context;
	private ViewHolder mHolder;
	private ArrayList<BuzzBean> list = new ArrayList<BuzzBean>();

	public BuzzAdapter(Context context, int textViewResourceId, ArrayList<BuzzBean> list) {
		super(context, textViewResourceId);
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
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
			v = vi.inflate(R.layout.buzz_row, null);
			mHolder = new ViewHolder();
			v.setTag(mHolder);

			mHolder.tvTitle = (TextView) v.findViewById(R.id.tvTitle);
			mHolder.tv_date = (TextView) v.findViewById(R.id.tv_date);

		} else {
			mHolder = (ViewHolder) v.getTag();
		}
		final BuzzBean member = list.get(position);
		mHolder.tvTitle.setText(member.getTitle());
		mHolder.tv_date.setText(member.getDate());
		return v;
	}

	class ViewHolder {
		public TextView tvTitle,tv_date;
	}
}
