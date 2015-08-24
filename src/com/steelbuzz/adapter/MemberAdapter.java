package com.steelbuzz.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.steelbuzz.R;
import com.steelbuzz.StringMatcher;
import com.steelbuzz.bean.Member;

public class MemberAdapter extends ArrayAdapter<Member> implements SectionIndexer{

	private Activity activity;
	private ViewHolder mHolder;
	public ArrayList<Member> item = new ArrayList<Member>();
	private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	String[] sections;

	public MemberAdapter(Activity activity, int textViewResourceId, ArrayList<Member> items) {
		super(activity, textViewResourceId, items);
		this.item = items;
		this.activity = activity;
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
			mHolder.ivFeature = (ImageView) v.findViewById(R.id.ivFeature);
			mHolder.tvFeature = (TextView) v.findViewById(R.id.tvFeature);
			mHolder.llArrow = (LinearLayout) v.findViewById(R.id.llArrow);
			mHolder.line = (View) v.findViewById(R.id.line);

		} else {
			mHolder = (ViewHolder) v.getTag();
		}

		mHolder.ivFeature.setVisibility(View.INVISIBLE);
		mHolder.tvFeature.setVisibility(View.INVISIBLE);
		mHolder.llArrow.setVisibility(View.VISIBLE);
		mHolder.line.setBackgroundColor(Color.parseColor("#999999"));
		mHolder.ll_member.setBackgroundColor(Color.WHITE);

		final Member member = item.get(position);

		if (member != null) {
			mHolder.tv_name.setText(member.getName());
		}
		
		sections = new String[item.size()];
		return v;
	}

	class ViewHolder {
		public TextView tv_name;
		public LinearLayout ll_member;
		public ImageView ivFeature;
		public TextView tvFeature;
		public LinearLayout llArrow;
		public View line;
	}
	
	@Override
	public int getPositionForSection(int section) {
		// If there is no item for current section, previous section will be
		// selected
		for (int i = section; i >= 0; i--) {
			for (int j = 0; j < getCount(); j++) {
				if (i == 0) {
					// For numeric section
					for (int k = 0; k <= 9; k++) {
						if (StringMatcher.match(String.valueOf(item.get(j).getName().charAt(0)), String.valueOf(k)))
							return j;
					}
				} else {
					if (StringMatcher.match(String.valueOf(item.get(j).getName().charAt(0)), String.valueOf(mSections.charAt(i))))
						return j;
				}
			}
		}
		return 0;
	}

	@Override
	public int getSectionForPosition(int position) {
		return 0;
	}

	@Override
	public Object[] getSections() {
		String[] sections = new String[mSections.length()];
		for (int i = 0; i < mSections.length(); i++)
			sections[i] = String.valueOf(mSections.charAt(i));
		return sections;
	}

}

