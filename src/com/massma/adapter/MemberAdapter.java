package com.massma.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.massma.BaseActivity;
import com.massma.R;
import com.massma.StringMatcher;
import com.massma.bean.Member;

public class MemberAdapter extends ArrayAdapter<Member> implements SectionIndexer{
	
	public interface OnMemberClickListener{
		public void onMemberClick(String name, String address, String contactParson, String tata, String mobile, String fax, String residential, String email, String web, String string);
	}
	private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private BaseActivity activity;
	private ViewHolder mHolder;
	public ArrayList<Member> item = new ArrayList<Member>();
	private OnMemberClickListener listener;

	public MemberAdapter(BaseActivity activity, int textViewResourceId, ArrayList<Member> items) {
		super(activity, textViewResourceId, items);
		this.item = items;
		this.activity = activity;
		listener = (OnMemberClickListener) activity;
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

		final Member member = item.get(position);

		if (member != null) {
			mHolder.tv_name.setText(member.getName());
			
		}
		
		mHolder.ll_member.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				listener.onMemberClick(item.get(position).getName(),item.get(position).getAddress(),item.get(position).getContactParson(),item.get(position).getTata(),item.get(position).getMobile(),item.get(position).getFax(), item.get(position).getResidential(), item.get(position).getEmail(), item.get(position).getWeb(),item.get(position).getHughes_no());
				
			}
		});
		return v;
	}

	class ViewHolder {
		public TextView tv_name;
		public LinearLayout ll_member;

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
