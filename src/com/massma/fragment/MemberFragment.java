package com.massma.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;

import com.massma.BaseActivity;
import com.massma.IndexableListView;
import com.massma.R;
import com.massma.adapter.MemberAdapter;
import com.massma.adapter.SelectedMemberAdapter;
import com.massma.bean.Member;
import com.massma.bean.SelectedMember;

public class MemberFragment extends Fragment {

	private BaseActivity base;
	private ArrayList<Member> memberArr;
	private IndexableListView lv_members;
	private MemberAdapter memberAdapter;
	private SelectedMemberAdapter selectedmemberAdapter;
	private AutoCompleteTextView et_search = null;
	private ArrayList<Member> tempArr = new ArrayList<Member>();
	private ArrayList<SelectedMember> selectedtempArr = new ArrayList<SelectedMember>();

	public MemberFragment(BaseActivity base, ArrayList<Member> memberArr) {
		this.base = base;
		this.memberArr = memberArr;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_member, container, false);
		lv_members = (IndexableListView) v.findViewById(R.id.lv_members);
		et_search = (AutoCompleteTextView) v.findViewById(R.id.et_search);
		memberAdapter = new MemberAdapter(base, R.layout.row_member, memberArr);
		lv_members.setAdapter(memberAdapter);
		lv_members.setFastScrollEnabled(true);
		et_search.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				et_search.setFocusableInTouchMode(true);
				base.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				return false;
			}
		});
		
		et_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

				//search(cs.toString());
				String searchString = et_search.getText().toString();
				if(searchString.length()>0){
					int textLength = searchString.length();
					selectedtempArr.clear();
					for (int i = 0; i < memberArr.size(); i++) {
						String retailerName = memberArr.get(i).getName();			
						if (textLength <= retailerName.length()) {
							if(retailerName.contains(" ")){									
								String[] arr = retailerName.split("\\s+");
								for(int j = 0;j<arr.length;j++){
									if(arr[j].toLowerCase().contains(searchString.toLowerCase())){
										selectedtempArr.add(new SelectedMember(memberArr.get(i).getName(), memberArr.get(i).getAddress(),memberArr.get(i).getContactParson(),memberArr.get(i).getTata(),memberArr.get(i).getMobile(),memberArr.get(i).getFax(),memberArr.get(i).getResidential(),memberArr.get(i).getEmail(), memberArr.get(i).getWeb(), memberArr.get(i).getHughes_no()));											
										break;
									}
								}
							}else if(retailerName.toLowerCase().contains(searchString.toLowerCase())){
								selectedtempArr.add(new SelectedMember(memberArr.get(i).getName(), memberArr.get(i).getAddress(),memberArr.get(i).getContactParson(),memberArr.get(i).getTata(),memberArr.get(i).getMobile(),memberArr.get(i).getFax(),memberArr.get(i).getResidential(),memberArr.get(i).getEmail(), memberArr.get(i).getWeb(),memberArr.get(i).getHughes_no()));											
								}
							}
						}
	
				selectedmemberAdapter = new SelectedMemberAdapter(base, R.layout.row_member, selectedtempArr);
				lv_members.setAdapter(selectedmemberAdapter);
				lv_members.setFastScrollEnabled(true);
				}else{
					lv_members.setAdapter(memberAdapter);
					lv_members.setFastScrollEnabled(true);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});
		return v;
	}

	public void search(String st) {
		tempArr.clear();
		for (int i = 0; i < memberArr.size(); i++) {
			if (memberArr.get(i).getName().toUpperCase().startsWith(st.toUpperCase())) {
				String name = memberArr.get(i).getName();
				String address = memberArr.get(i).getAddress();
				String contactParson = memberArr.get(i).getContactParson();
				String tata = memberArr.get(i).getTata();
				String mobile = memberArr.get(i).getMobile();
				String fax = memberArr.get(i).getFax();
				String residential = memberArr.get(i).getResidential();
				String email = memberArr.get(i).getEmail();
				String web = memberArr.get(i).getWeb();
				String hughes_no = memberArr.get(i).getHughes_no();
				tempArr.add(new Member(name, address, contactParson, tata, mobile, fax, residential, email, web,hughes_no));
			}
		}

		memberAdapter = new MemberAdapter(base, R.layout.row_member, tempArr);
		lv_members.setAdapter(memberAdapter);
		lv_members.setFastScrollEnabled(true);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		InputMethodManager imm = (InputMethodManager)base.getSystemService(
			      Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(et_search.getWindowToken(), 0);
	}
}
