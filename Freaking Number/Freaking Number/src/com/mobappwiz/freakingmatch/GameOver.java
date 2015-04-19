package com.mobappwiz.freakingmatch;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class GameOver extends Activity {
    ImageView newGameBtn, img;
    Button button;
    
  //<!-- Admob Ads Using Google Play Services SDK -->
  	@SuppressWarnings("unused")
	private static final String AD_UNIT_ID = "ca-app-pub-5757354627664623/5493693793";
  	@SuppressWarnings("unused")
	private static final String AD_INTERSTITIAL_UNIT_ID = "ca-app-pub-5757354627664623/6970426996";
  	
  	/** The Admob ad. */
  	@SuppressWarnings("unused")
	private InterstitialAd interstitialAd = null;
  	public AdView adView = null;
  	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        UIUtils.setOrientationLockPortrait(this);

        setContentView(R.layout.gameover);
        
      
        final ImageView img = (ImageView)findViewById(R.id.play_btn);
        img.setEnabled(false);
        img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				adView.setVisibility(View.INVISIBLE);
				button.setVisibility(View.INVISIBLE);
			}
		});
        
        final Button button = (Button)findViewById(R.id.back);
        button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				adView.setVisibility(View.INVISIBLE);				
				button.setVisibility(View.INVISIBLE);
				img.setEnabled(true);
			}
		});
        

        int highScore = getIntent().getIntExtra("score", 0);

        TextView currentScoreTxt = (TextView) findViewById(R.id.curent_score);
        TextView bestScoreTxt  = (TextView) findViewById(R.id.best_score);
        newGameBtn = (ImageView) findViewById(R.id.play_btn);

        currentScoreTxt.setText(highScore + "");
        bestScoreTxt.setText(PrefStore.getMaxScore() + "");
        if (highScore == PrefStore.getMaxScore()) bestScoreTxt.setTextColor(Color.YELLOW);

        newGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseApplication.soundWhenPlay();
                Intent intent = new Intent(GameOver.this, MyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        newGameBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    newGameBtn.setBackgroundResource(R.drawable.play_press);
                } else if (action == MotionEvent.ACTION_UP) {
                    newGameBtn.setBackgroundResource(R.drawable.play);
                }
                return false;
            }
        });

       		//<!-- Ads Using Google Play Services SDK -->
       		adView = (AdView)this.findViewById(R.id.AdMob);
    		AdRequest adRequest = new AdRequest.Builder().build();
    		adView.loadAd(adRequest);
       		
       		
    }

    @Override
    public void onPause()
    {
    	if (adView != null) {
		      adView.pause();
		    }
    	
        super.onPause();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        
        if (adView != null) {
	        adView.resume();
	}
    }

    @Override
    public void onDestroy()
    {
    	// Destroy the AdView.
	    if (adView != null) {
	      adView.destroy();
	    }
	    
        super.onDestroy();
    }
    
    @Override
    public void onBackPressed() {
    	
    }
}