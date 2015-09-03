package com.steelbuzz;

import com.steelbuzz.constant.Constants;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MyGestureListener extends SimpleOnGestureListener {

   private static final int MIN_DISTANCE = 50;
   private static final String TAG = "MyGestureListener";
   private RelativeLayout backLayout;
   private LinearLayout frontLayout;
   private Animation inFromRight,outToRight,outToLeft,inFromLeft;
   private Context context;
   private Intent mIntent;

   public MyGestureListener(Context ctx,View convertView) {
      backLayout = (RelativeLayout) convertView.findViewById(R.id.layout_back);
      frontLayout = (LinearLayout) convertView.findViewById(R.id.layout_front);
      inFromRight = AnimationUtils.loadAnimation(ctx, R.anim.in_from_right);
      outToRight = AnimationUtils.loadAnimation(ctx, R.anim.out_to_right);
      outToLeft = AnimationUtils.loadAnimation(ctx, R.anim.out_to_left);
      inFromLeft = AnimationUtils.loadAnimation(ctx, R.anim.in_from_left);
      context = ctx;
   }

   @Override
   public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
         float velocityY) {
      float diffX = e2.getX() - e1.getX();
      float diffY = e2.getY() - e1.getY();
      if (Math.abs(diffX) > Math.abs(diffY)) {
         if (Math.abs(diffX) > MIN_DISTANCE) {
            if(diffX<0){
               Log.v(TAG, "Swipe Right to Left");
               if(backLayout.getVisibility()==View.GONE){
                  frontLayout.startAnimation(outToLeft);
                  backLayout.setVisibility(View.VISIBLE);
                  backLayout.startAnimation(inFromRight);
                  frontLayout.setVisibility(View.GONE);
                  
                  outToLeft.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation arg0) {
						
					}
					
					@Override
					public void onAnimationRepeat(Animation arg0) {
						
					}
					
					@Override
					public void onAnimationEnd(Animation arg0) {
						if (Constants.TAB_NAME.equalsIgnoreCase("mail")) {
							mIntent = new Intent(Intent.ACTION_SEND);
							mIntent.setType("plain/text");
							mIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ Constants.EMAIL_ID });
							mIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
							mIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
							context.startActivity(Intent.createChooser(mIntent, "Send email"));
						}else if (Constants.TAB_NAME.equalsIgnoreCase("phone")) {
							mIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Constants.PHONE_NO));
							context.startActivity(mIntent);
						}
						
						new Handler().postDelayed(new Runnable() {
							
							@Override
							public void run() {
								if (backLayout.getVisibility() != View.GONE) {
									backLayout.setVisibility(View.GONE);
					                frontLayout.setVisibility(View.VISIBLE);
								}
							}
						}, 500);
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				});
               }
            }else{
               Log.v(TAG, "Swipe Left to Right");
               /*if(backLayout.getVisibility()!=View.GONE){
                  backLayout.startAnimation(outToRight);
                  backLayout.setVisibility(View.GONE);
                  frontLayout.setVisibility(View.VISIBLE);
                  frontLayout.startAnimation(inFromLeft);
                  
                  outToRight.setAnimationListener(new AnimationListener() {
  					
  					@Override
  					public void onAnimationStart(Animation arg0) {
  						
  					}
  					
  					@Override
  					public void onAnimationRepeat(Animation arg0) {
  						
  					}
  					
  					@Override
  					public void onAnimationEnd(Animation arg0) {
  						mIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Constants.PHONE_NO));
  						context.startActivity(mIntent);
  					}
  				});
               }*/
            }
         }
      }

      return true;
   }
   
}