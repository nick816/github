package com.javacodegeeks.android.facebookloginexample;

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
		
		//Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.splash);
		Thread background = new Thread(){
			public void run(){
				try{
					//Thread will sleep for 5 seconds
					sleep(3000);
					//After 5 seconds redirect to another intent
					Intent start = new Intent (getBaseContext(), LoginActivity.class);
					startActivity(start);
					
					//Remove activity
					finish();
				} catch (Exception e){
					
				}
			}
		};
		
		//start thread
		background.start();
		
	}
	
}
