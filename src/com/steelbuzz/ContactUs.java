package com.steelbuzz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.BufferType;

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
				//etDesc.setText(arr[pos-1]);
				etDesc.setHint(arr[pos-1]);
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			
		}
	   });
	   
	   String s = getResources().getString(R.string.contactus_text);
	   
	   tvHeaderText.setMovementMethod(LinkMovementMethod.getInstance());
	   tvHeaderText.setText(addClickablePart1(s), BufferType.SPANNABLE);
	}
	
	private SpannableStringBuilder addClickablePart1(String str) {
    	SpannableStringBuilder ssb = new SpannableStringBuilder(str);
            final String clickString = str.substring(8, 11);
            ssb.setSpan(new ClickableSpan() {

                @Override
                public void onClick(View widget) {
                    //Toast.makeText(ContactUs.this, clickString,Toast.LENGTH_SHORT).show();
                	Intent intent = new Intent(ContactUs.this,FAQActivity.class);
                	startActivity(intent);
                }
            }, 17, 20, 0);
            
            ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#179CDF")), 17, 20, 0);
        return ssb;
    }
}
