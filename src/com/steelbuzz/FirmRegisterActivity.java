package com.steelbuzz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.steelbuzz.bean.CountryInfo;

public class FirmRegisterActivity extends BaseActivity {

	private ArrayList<CountryInfo> list = new ArrayList<CountryInfo>();
	private String[] countryName;
	private Spinner spCountryList;
	private EditText etCountryCode;
	private LinearLayout llPhoneContainer, llHughesContainer,llEmailContainer;
	private LayoutInflater inflater;
	private View promotionMonthRow;
	private String phonePrefixCode = "";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_register_firm);

		llPhoneContainer = (LinearLayout) findViewById(R.id.llPhoneContainer);
		llHughesContainer = (LinearLayout) findViewById(R.id.llHughesContainer);
		llEmailContainer = (LinearLayout) findViewById(R.id.llEmailContainer);

		spCountryList = (Spinner) findViewById(R.id.spCountryList);
		// etCountryCode = (EditText)findViewById(R.id.etCountryCode);
		// etCountryCode.setInputType(0);

		new Countries().getAllCountryList();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(FirmRegisterActivity.this, android.R.layout.simple_dropdown_item_1line, countryName);
		spCountryList.setAdapter(adapter);

		spCountryList.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				String countryCode = list.get(arg2).getCountryCode();
				String phonePrefix = new CountryToPhonePrefix().prefixFor(countryCode);

				etCountryCode.setText(phonePrefix);
				phonePrefixCode = phonePrefix;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		addDynamicViewForPhone();
		addDynamicViewForHughes();
		addDynamicViewForEmail();
	}

	public class Countries {

		public Countries() {

		}

		public void getAllCountryList() {
			String[] locales = Locale.getISOCountries();
			ArrayList<String> countries = new ArrayList<String>();
			list.add(new CountryInfo("Select Country", "00"));
			for (String countryCode : locales) {
				Locale obj = new Locale("", countryCode);
				String countryName = obj.getDisplayCountry();
				String countryCode1 = obj.getCountry();
				if (countryName.trim().length() > 0 && !countries.contains(countryName)) {
					countries.add(countryName);
					list.add(new CountryInfo(countryName, countryCode1));
					System.out.println("!!code:" + countryCode1);
				}
			}
			Collections.sort(countries);
			countryName = new String[countries.size() + 1];
			// countryName[0] = "Select Country";
			for (int i = 0; i < list.size(); i++) {
				countryName[i] = list.get(i).getCountryName();
			}
			System.out.println("# countries found: " + countries.size());
		}
	}

	public void addDynamicViewForPhone() {
		inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		promotionMonthRow = (View) inflater.inflate(R.layout.enter_phone_row, null);

		final EditText etPhoneNo = (EditText) promotionMonthRow.findViewById(R.id.etPhoneNo);
		etCountryCode = (EditText) promotionMonthRow.findViewById(R.id.etCountryCode);

		final ImageView img = (ImageView) promotionMonthRow.findViewById(R.id.ivPlus);
		if (llPhoneContainer.getChildCount() > 1) {
			img.setVisibility(View.GONE);
		}
		if (phonePrefixCode.length() > 0) {
			etCountryCode.setText(phonePrefixCode);
		}

		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (etCountryCode.getText().toString().trim().length() > 0) {
					if (etPhoneNo.getText().toString().trim().length() > 0) {
						img.setVisibility(View.GONE);
						addDynamicViewForPhone();
					} else {
						Toast.makeText(FirmRegisterActivity.this, "Please enter your mobile no.", Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(FirmRegisterActivity.this, "Please choose a country first.", Toast.LENGTH_LONG).show();
				}
			}
		});

		llPhoneContainer.addView(promotionMonthRow);
	}

	public void addDynamicViewForHughes() {
		inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		promotionMonthRow = (View) inflater.inflate(R.layout.enter_hughes_row, null);

		final EditText etHughesNo = (EditText) promotionMonthRow.findViewById(R.id.etHughesNo);

		final ImageView img = (ImageView) promotionMonthRow.findViewById(R.id.ivPlus);
		if (llHughesContainer.getChildCount() > 1) {
			img.setVisibility(View.GONE);
		}

		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (etHughesNo.getText().toString().trim().length() > 0) {
					img.setVisibility(View.GONE);
					addDynamicViewForHughes();
				} else {
					Toast.makeText(FirmRegisterActivity.this, "Please enter your Hughes no.", Toast.LENGTH_LONG).show();
				}

			}
		});
		llHughesContainer.addView(promotionMonthRow);
	}
	
	public void addDynamicViewForEmail() {
		inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		promotionMonthRow = (View) inflater.inflate(R.layout.enter_email_row, null);

		final EditText etEmail = (EditText) promotionMonthRow.findViewById(R.id.etEmail);

		final ImageView img = (ImageView) promotionMonthRow.findViewById(R.id.ivPlus);
		if (llEmailContainer.getChildCount() > 1) {
			img.setVisibility(View.GONE);
		}

		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (etEmail.getText().toString().trim().length() > 0) {
					img.setVisibility(View.GONE);
					addDynamicViewForEmail();
				} else {
					Toast.makeText(FirmRegisterActivity.this, "Please enter your email address.", Toast.LENGTH_LONG).show();
				}

			}
		});
		llEmailContainer.addView(promotionMonthRow);
	}
}
