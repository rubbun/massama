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
import android.widget.ListView;

import com.massma.BaseActivity;
import com.massma.R;
import com.massma.adapter.CatagoryAdapter;
import com.massma.adapter.SelectedCatagoryAdapter;
import com.massma.bean.Catagory;
import com.massma.bean.SelectedCatagory;

public class CatagoryFragment extends Fragment {

	private BaseActivity base;
	private ListView lv_members;
	private CatagoryAdapter catagoryAdapter;
	private SelectedCatagoryAdapter selectedCatagoryAdapter;
	private AutoCompleteTextView et_search = null;
	private ArrayList<Catagory> catagorypList = new ArrayList<Catagory>();
	private ArrayList<SelectedCatagory> selectedtempArr = new ArrayList<SelectedCatagory>();

	public CatagoryFragment(BaseActivity base, ArrayList<Catagory> catagorypArr) {
		this.base = base;
		this.catagorypList = catagorypArr;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_smae_catagory, container, false);
		lv_members = (ListView) v.findViewById(R.id.lv_members);
		et_search = (AutoCompleteTextView) v.findViewById(R.id.et_search);
		catagoryAdapter = new CatagoryAdapter(base, R.layout.row_catagory, catagorypList);
		lv_members.setAdapter(catagoryAdapter);
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
					for (int i = 0; i < catagorypList.size(); i++) {
						String retailerName = catagorypList.get(i).getName();			
						if (textLength <= retailerName.length()) {
							if(retailerName.contains(" ")){									
								String[] arr = retailerName.split("\\s+");
								for(int j = 0;j<arr.length;j++){
									if(arr[j].toLowerCase().contains(searchString.toLowerCase())){
										selectedtempArr.add(new SelectedCatagory(catagorypList.get(i).getName()));											
										break;
									}
								}
							}else if(retailerName.toLowerCase().contains(searchString.toLowerCase())){
								selectedtempArr.add(new SelectedCatagory(catagorypList.get(i).getName()));									
								}
							}
						}
	
				selectedCatagoryAdapter = new SelectedCatagoryAdapter(base, R.layout.row_catagory, selectedtempArr);
				lv_members.setAdapter(selectedCatagoryAdapter);
				lv_members.setFastScrollEnabled(true);
				}else{
					lv_members.setAdapter(catagoryAdapter);
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
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		InputMethodManager imm = (InputMethodManager)base.getSystemService(
			      Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(et_search.getWindowToken(), 0);
	}
}
