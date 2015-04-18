package com.mobappwiz.freakingmatch;


import com.mobappwiz.freakingmatch.R;
//import com.myvideo.loveweclass.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class SplashActivity extends Activity{
	
	ImageView mImageViewLogo;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(1);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        startAnimation();
	}

	private void startAnimation() {
		
		mImageViewLogo = (ImageView)findViewById(R.id.imageViewLogo);
				
				AlphaAnimation alpha = new AlphaAnimation(1.5f, 1.0f);
				alpha.setDuration(1000);
				alpha.setFillAfter(true);
				alpha.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationEnd(Animation animation) {
						Intent loginIntent = new Intent(getBaseContext(), MyActivity.class);
						startActivity(loginIntent);
						finish();
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
						
					}
					@Override
					public void onAnimationStart(Animation animation) {
						
					}
				});
				mImageViewLogo.setAnimation(alpha);
				
			}

}
