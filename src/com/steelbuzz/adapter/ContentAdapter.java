package com.steelbuzz.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.steelbuzz.BaseActivity;
import com.steelbuzz.R;
import com.steelbuzz.StringMatcher;
import com.steelbuzz.bean.Member;
import com.steelbuzz.bean.SelectedMember;


public class ContentAdapter extends ArrayAdapter<Member> implements SectionIndexer {
	private ViewHolder mHolder;
	private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private ArrayList<Member> mItems;
	private BaseActivity base;
	
	public ContentAdapter(BaseActivity context, int textViewResourceId,
			ArrayList<Member> objects) {
		super(context, textViewResourceId, objects);
		this.mItems = objects;
		base  = context;
	}

	@Override
	public int getPositionForSection(int section) {
		// If there is no item for current section, previous section will be selected
		for (int i = section; i >= 0; i--) {
			for (int j = 0; j < getCount(); j++) {
				if (i == 0) {
					// For numeric section
					for (int k = 0; k <= 9; k++) {
						if (StringMatcher.match(String.valueOf(mItems.get(j).getName()), String.valueOf(k)))
							return j;
					}
				} else {
					if (StringMatcher.match(String.valueOf(mItems.get(j).getName()), String.valueOf(mSections.charAt(i))))
						return j;
				}
			}
		}
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) base.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.row_member, null);
			mHolder = new ViewHolder();
			v.setTag(mHolder);

			mHolder.tv_name = (TextView) v.findViewById(R.id.tv_name);
		

		} else {
			mHolder = (ViewHolder) v.getTag();
		}

		final Member member = mItems.get(position);

		if (member != null) {
			mHolder.tv_name.setText(member.getName());
			
		}
		
		mHolder.tv_name.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Toast.makeText(base, "ggg", Toast.LENGTH_LONG).show();
				
			}
		});
		return v;
	}

	@Override
	public Object[] getSections() {
		String[] sections = new String[mSections.length()];
		for (int i = 0; i < mSections.length(); i++)
			sections[i] = String.valueOf(mSections.charAt(i));
		return sections;
	}
	
	class ViewHolder {
		public TextView tv_name;
		
	}

	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public int getSectionForPosition(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
}