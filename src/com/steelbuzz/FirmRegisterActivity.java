package com.steelbuzz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.steelbuzz.bean.CountryInfo;

public class FirmRegisterActivity extends BaseActivity {
	
	private ArrayList<CountryInfo> list = new ArrayList<CountryInfo>();
	private String[] countryName ;
	private Spinner spCountryList;
	private EditText etCountryCode;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_register_firm);
		
		spCountryList = (Spinner)findViewById(R.id.spCountryList);
		etCountryCode = (EditText)findViewById(R.id.etCountryCode);
		etCountryCode.setInputType(0);
		
		new Countries().getAllCountryList();
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				FirmRegisterActivity.this,
                android.R.layout.simple_dropdown_item_1line, countryName);
		spCountryList.setAdapter(adapter);
		
		spCountryList.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				String countryCode = list.get(arg2).getCountryCode();
				String phonePrefix = new CountryToPhonePrefix().prefixFor(countryCode);
				
				System.out.println("!!code phonePrefix:"+phonePrefix);
				etCountryCode.setText(phonePrefix);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
	}

	public class Countries {

		public Countries(){
			
		}
	    public void getAllCountryList() {
	    	String[] locales = Locale.getISOCountries();
	        ArrayList<String> countries = new ArrayList<String>();
	        list.add(new CountryInfo("Select Country", "00"));
	        for (String countryCode : locales) {
	        	Locale obj = new Locale("", countryCode);
	            String countryName = obj.getDisplayCountry();
	            String countryCode1 = obj.getCountry();
	            if (countryName.trim().length()>0 && !countries.contains(countryName)) {
	                countries.add(countryName);
	                list.add(new CountryInfo(countryName, countryCode1));
	                System.out.println("!!code:"+countryCode1);
	            }
	        }
	        Collections.sort(countries);
	        countryName = new String[countries.size()+1];
	        //countryName[0] = "Select Country";
	        for (int i = 0 ; i < list.size(); i++) {
	            countryName[i] = list.get(i).getCountryName();
	        }
	        System.out.println( "# countries found: " + countries.size());
	    }
	}
}
