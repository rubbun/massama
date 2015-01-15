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
import com.massma.adapter.SelectedSubCatagoryAdapter;
import com.massma.adapter.SubCatagoryAdapter;
import com.massma.bean.SelectedSubCatagory;
import com.massma.bean.SubCatagory;

public class SubCategoryFragment extends Fragment{
	
	private BaseActivity base;
	private SubCatagoryAdapter adapter;
	private SelectedSubCatagoryAdapter selecetedAdapter;
	private ArrayList<SubCatagory> catagoryList = new ArrayList<SubCatagory>();
	private ArrayList<SelectedSubCatagory> selectedCatagoryList = new ArrayList<SelectedSubCatagory>();
	private IndexableListView lv_catagories;
	private AutoCompleteTextView et_search;
	
	public SubCategoryFragment(BaseActivity base, ArrayList<SubCatagory> catagoryList2){
		this.base = base;
		this.catagoryList = catagoryList2;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_sub_category, container, false);
		
		lv_catagories = (IndexableListView)v.findViewById(R.id.lv_catagories);
		adapter = new SubCatagoryAdapter(base, R.layout.row_member, catagoryList);
		lv_catagories.setAdapter(adapter);
		lv_catagories.setFastScrollEnabled(true);
		
		et_search = (AutoCompleteTextView)v.findViewById(R.id.et_search);
		et_search.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String searchString = et_search.getText().toString();
				if(searchString.length()>0){
					int textLength = searchString.length();
					selectedCatagoryList.clear();
					for (int i = 0; i < catagoryList.size(); i++) {
						String retailerName = catagoryList.get(i).getName();			
						if (textLength <= retailerName.length()) {
							if(retailerName.contains(" ")){									
								String[] arr = retailerName.split("\\s+");
								for(int j = 0;j<arr.length;j++){
									if(arr[j].toLowerCase().contains(searchString.toLowerCase())){
										selectedCatagoryList.add(new SelectedSubCatagory(catagoryList.get(i).getName(), catagoryList.get(i).getAddress(),catagoryList.get(i).getContactParson(),catagoryList.get(i).getTata(),catagoryList.get(i).getMobile(),catagoryList.get(i).getFax(),catagoryList.get(i).getResidential(),catagoryList.get(i).getEmail(), catagoryList.get(i).getWeb()));											
										break;
									}
								}
							}else if(retailerName.toLowerCase().contains(searchString.toLowerCase())){
								selectedCatagoryList.add(new SelectedSubCatagory(catagoryList.get(i).getName(), catagoryList.get(i).getAddress(),catagoryList.get(i).getContactParson(),catagoryList.get(i).getTata(),catagoryList.get(i).getMobile(),catagoryList.get(i).getFax(),catagoryList.get(i).getResidential(),catagoryList.get(i).getEmail(), catagoryList.get(i).getWeb()));											
								}
							}
						}
	
					selecetedAdapter = new SelectedSubCatagoryAdapter(base, R.layout.row_member, selectedCatagoryList);
					lv_catagories.setAdapter(selecetedAdapter);
				}else{
					lv_catagories.setAdapter(adapter);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
		et_search = (AutoCompleteTextView)v.findViewById(R.id.et_search);
		et_search.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				et_search.setFocusableInTouchMode(true);
				base.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				return false;
			}
		});
		return v;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		InputMethodManager im = (InputMethodManager)base.getSystemService(Context.INPUT_METHOD_SERVICE);
		im.hideSoftInputFromInputMethod(et_search.getWindowToken(), 0);
	}
}