package com.steelbuzz;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
	   
	   
	   ClickableSpan clickable = new ClickableSpan() {
	          public void onClick(View view) {
	        	  System.out.println("!!reach here");
	        	  Toast.makeText(ContactUs.this, "Hello", Toast.LENGTH_LONG).show();
	          }
	    };
	   String s = getResources().getString(R.string.contactus_text);
	   SpannableStringBuilder sb = new SpannableStringBuilder(s);
				//normal font for 1st 9 chars
	   sb.setSpan(new ForegroundColorSpan(Color.parseColor("#8CD1F3")), 17, 20, 0);
	   //sb.setSpan(clickable, 17, 20, 0);
	   tvHeaderText.setText(sb);
	   
	  /* ((Spannable)tvHeaderText.getText()).setSpan(new ClickableSpan(){
	     @Override public void onClick(View widget){
	    	 //Toast.makeText(ContactUs.this, "Hello", Toast.LENGTH_LONG).show();
	     }
	   },17,20,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);*/
	   
	   //SpannableStringBuilder strBuilder = new SpannableStringBuilder(s);
	   
	    
	    //strBuilder.removeSpan(span);
	}
}
