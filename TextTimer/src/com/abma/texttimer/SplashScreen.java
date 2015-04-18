package com.abma.texttimer;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class SplashScreen extends Activity {

	// Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    
    ImageView ivSplashBackground = null;
    ImageView ivSplahsIcon = null;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        setSplashBackground();
        
        new Handler().postDelayed(new Runnable() {
 
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
 
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra(MainActivity.CURRENT_ACTION, MainActivity.SCHEDULE);
                i.putExtra(FragmentSchedule.CURRENT_TAB, FragmentSchedule.SENT);
                startActivity(i);
 
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private void setSplashBackground() {
    	if ( ivSplashBackground == null ) {
    		ivSplashBackground = (ImageView)findViewById(R.id.ivSplashBackground);
    	}
    	if ( ivSplahsIcon == null ) {
    		ivSplahsIcon = (ImageView)findViewById(R.id.ivSplashIcon);
    	}
    	if ( this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ) {
    		ivSplashBackground.setImageResource(R.drawable.splash);
    	}
    	else {
    		ivSplashBackground.setImageResource(R.drawable.splash_landscape);
    	}
    }
}
