package com.steelbuzz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends BaseActivity{
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.spalsh);
		/*if (app.getUserinfo().isTrialPeriodStatus()) {
			setContentView(R.layout.spalsh);
					
			goToNextScreen(true);
			
		}else{
			
			if(app.getUserinfo().getStartTime() == 00){
				setContentView(R.layout.spalsh_welcome);
				app.getUserinfo().setStartTime(System.currentTimeMillis());
				//edit.putLong("ACCESS_START_TIME", System.currentTimeMillis());
				goToNextScreen(false);
				
			}else{
				long start_time = app.getUserinfo().getStartTime();
				long millis = System.currentTimeMillis() - start_time;
				millis = (5 * 60 * 1000) - millis;
				if(millis>0){
					setContentView(R.layout.spalsh_welcome);
					goToNextScreen(false);
					//edit.putLong("ACCESS_START_TIME", System.currentTimeMillis());
					
				}else{
					setContentView(R.layout.spalsh);
					app.getUserinfo().setTrialPeriodStatus(true);
					goToNextScreen(true);
					//edit.putLong("ACCESS_START_TIME", System.currentTimeMillis());					
				}
			}
		}*/
		
		if (app.getUserinfo().isSession()) {
			Intent i = new Intent(SplashScreen.this,MainActivity.class);
			startActivity(i);
			finish();
		}else{
			goToNextScreen(true);
		}
		
	}
	
	public void goToNextScreen(final boolean b){
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				if(!b){
					Intent i = new Intent(SplashScreen.this,MainActivity.class);
					startActivity(i);
					finish();	
				}else{
					Intent i = new Intent(SplashScreen.this,SignInScreen.class);
					startActivity(i);
					finish();
				}
			}
		}, 5000);
	}
}
