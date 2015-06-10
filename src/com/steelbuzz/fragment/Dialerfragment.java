package com.steelbuzz.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.steelbuzz.R;

public class Dialerfragment extends Fragment implements OnClickListener{
	
	//private BaseActivity base;
	private LinearLayout ll_one,ll_two,ll_three,ll_four,ll_five,ll_six,ll_seven,ll_eight,ll_nine,ll_zero,ll_call;
	private TextView tv_dial_pad,tv_show_message;
	private Button btn_clear;
	
	String last_four_digit = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_dialer, container, false);
		
		ll_one = (LinearLayout)v.findViewById(R.id.ll_one);
		ll_one.setOnClickListener(this);
		
		ll_two = (LinearLayout)v.findViewById(R.id.ll_two);
		ll_two.setOnClickListener(this);
		
		ll_three = (LinearLayout)v.findViewById(R.id.ll_three);
		ll_three.setOnClickListener(this);
		
		ll_four = (LinearLayout)v.findViewById(R.id.ll_four);
		ll_four.setOnClickListener(this);
		
		ll_five = (LinearLayout)v.findViewById(R.id.ll_five);
		ll_five.setOnClickListener(this);
		
		ll_six = (LinearLayout)v.findViewById(R.id.ll_six);
		ll_six.setOnClickListener(this);
		
		ll_seven = (LinearLayout)v.findViewById(R.id.ll_seven);
		ll_seven.setOnClickListener(this);
		
		ll_eight = (LinearLayout)v.findViewById(R.id.ll_eight);
		ll_eight.setOnClickListener(this);
		
		ll_nine = (LinearLayout)v.findViewById(R.id.ll_nine);
		ll_nine.setOnClickListener(this);
		
		ll_zero = (LinearLayout)v.findViewById(R.id.ll_zero);
		ll_zero.setOnClickListener(this);
		
		ll_call = (LinearLayout)v.findViewById(R.id.ll_call);
		ll_call.setOnClickListener(this);
		
		tv_dial_pad = (TextView)v.findViewById(R.id.tv_dial_pad);
		tv_show_message = (TextView)v.findViewById(R.id.tv_show_message);
		
		btn_clear = (Button)v.findViewById(R.id.btn_clear);
		btn_clear.setOnClickListener(this);
		
		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_one:
			last_four_digit = tv_dial_pad.getText().toString();
			setNumberToDialPad("1");
			break;
		case R.id.ll_two:
			setNumberToDialPad("2");
			break;

		case R.id.ll_three:
			setNumberToDialPad("3");
			break;

		case R.id.ll_four:
			setNumberToDialPad("4");
			break;

		case R.id.ll_five:
			setNumberToDialPad("5");
			break;

		case R.id.ll_six:
			setNumberToDialPad("6");
			break;

		case R.id.ll_seven:
			setNumberToDialPad("7");
			break;

		case R.id.ll_eight:
			setNumberToDialPad("8");
			break;

		case R.id.ll_nine:
			setNumberToDialPad("9");
			break;

		case R.id.ll_zero:
			setNumberToDialPad("0");
			break;

		case R.id.btn_clear:
			tv_dial_pad.setText("");
			break;
			
		case R.id.ll_call:
			last_four_digit = tv_dial_pad.getText().toString();
			if(last_four_digit.length() > 0){
				String first_four_digit = Integer.toString(getInitialNos(Integer.parseInt(last_four_digit)));
				Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "022" + first_four_digit + last_four_digit));
				startActivity(intent1);
			}else{
				Toast.makeText(getActivity(), "Please dial last four digit", 6000).show();
			}
		}
	}
	
	public int getInitialNos(int val){
		int p=0;
		if(val>=1000 && val<=1099){
			p = 6645;
		}else if(val>=1100 && val<=1299){
			p = 6752;
		}else if(val>=1300 && val<=1499){
			p = 6615;
		}else if(val>=1500 && val<=1679){
			p = 6658;
		}else if(val>=1680 && val<=1999){
			p = 6615;
		}else if(val>=2000 && val<=3499){
			p = 6636;
		}else if(val>=3500 && val<=4999){
			p = 6639;
		}else if(val>=5000 && val<=5999){
			p = 6659;
		}else if(val>=6000 && val<=6399){
			p = 6749;
		}else if(val>=6400 && val<=6999){
			p = 6743;
		}else if(val>=7000 && val<=7099){
			p = 6615;
		}else if(val>=7100 && val<=7999){
			p = 6743;
		}else if(val>=8000 && val<=8099){
			p = 6615;
		}else if(val>=8100 && val<=8499){
			p = 6743;
		}else if(val>=8500 && val<=8999){
			p = 6651;
		}else if(val>=9000 && val<=9099){
			p = 6615;
		}else if(val>=9100 && val<=9199){
			p = 6743;
		}else if(val>=9200 && val<=9999){
			p = 6610;
		}
		return p;
	}

	private void setNumberToDialPad(String number) {
		last_four_digit = tv_dial_pad.getText().toString();
		tv_dial_pad.setText(last_four_digit+number);
	}
}
