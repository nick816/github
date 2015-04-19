package com.example.phonecalllog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity{
//	private static int SPLASH_TIME_OUT = 3000;

	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
                
        Thread background = new Thread() {
            public void run() {
                 
                try {
                    // Thread will sleep for 5 seconds
                    sleep(2000);                     
                    // After 5 seconds redirect to another intent
                    Intent i=new Intent(SplashActivity.this, LogInActivity.class);
//                    Intent i=new Intent(SplashActivity.this, LogInActivity.class);
                    startActivity(i);                     
                    //Remove activity
                    finish();
                     
                } catch (Exception e) {
                 
                }
            }
        };
         
        // start thread
        background.start();
        
    }
}