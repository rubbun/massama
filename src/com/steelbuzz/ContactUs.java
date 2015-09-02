package com.steelbuzz;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ContactUs extends BaseActivity{
	
	private EditText etEmail,etDesc;
	private Spinner spOptionList;
	private TextView tvHeaderText;
	private String[] arr;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_contactus);
		
		etEmail = (EditText)findViewById(R.id.etEmail);
		etDesc = (EditText)findViewById(R.id.etDesc);
		
		spOptionList = (Spinner)findViewById(R.id.spOptionList);
		tvHeaderText = (TextView)findViewById(R.id.tvHeaderText);
		
		arr = getResources().getStringArray(R.array.desc_array);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
			    this, android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.help_array) );

			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spOptionList.setAdapter(adapter);
		
	   spOptionList.setOnItemSelectedListener(new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
			
			if (pos != 0) {
				etDesc.setText(arr[pos]);
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			
		}
	   });
	}
}
