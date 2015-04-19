package com.javacodegeeks.android.audiocapturetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.splash);
		Thread backgorund = new Thread(){
			public void run(){
				try{
					sleep(2000);
					
					Intent i = new Intent(getBaseContext(), AudioActivity.class);
					startActivity(i);
					
				} catch (Exception e){
					
				}
			}
		};
		
		backgorund.start();
	}

}
