package com.mobappwiz.freakingmatch;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.*;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobappwiz.freakingmatch.R;
import com.mobappwiz.freakingmatch.R.drawable;
import com.mobappwiz.freakingmatch.UIUtils;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.mobappwiz.freakingmatch.BaseApplication;
import com.mobappwiz.freakingmatch.PrefStore;
import com.startapp.android.publish.StartAppAd;
import com.mobappwiz.freakingmatch.GameObject;
import com.mobappwiz.freakingmatch.Helper;
import com.mobappwiz.freakingmatch.ResizeAnimation;

import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings("unused")
public class MyActivity extends Activity {
	private AdView adView;

    boolean resultOfGame;
    int highScore = 0;
    ImageView firstTxt;
    ImageView secondTxt;
    ImageView resultTxt;
    ImageView operatorTxt;
    TextView highScoreTxt;
    RelativeLayout parentLayout;
    AtomicBoolean isEnd;

    int[] number_image={R.drawable.n0,R.drawable.n1,R.drawable.n2,R.drawable.n3,R.drawable.n4,R.drawable.n5,R.drawable.n6,
    		R.drawable.n7,R.drawable.n8,R.drawable.n9,R.drawable.n10,R.drawable.n11,R.drawable.n12,
    		R.drawable.n13,R.drawable.n14,R.drawable.n15,R.drawable.n16,R.drawable.n17,R.drawable.n18,R.drawable.n19,R.drawable.n20,R.drawable.n21,
    		R.drawable.n22,R.drawable.n23,R.drawable.n24,R.drawable.n25,R.drawable.n26,R.drawable.n27,R.drawable.n28,R.drawable.n29,
    		R.drawable.n30,R.drawable.n31,R.drawable.n32,R.drawable.n33,R.drawable.n34,R.drawable.n35,R.drawable.n36};
    
    int equal=R.drawable.equals;
    /** StartApp ads **/
    public StartAppAd startAppAd = new StartAppAd(this);
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        UIUtils.setOrientationLockPortrait(this);

        setContentView(R.layout.main);
				
        isEnd = new AtomicBoolean(false);

        /** StartApp **/
        StartAppAd.init(this, "101007880", "204108768");


        firstTxt = (ImageView) findViewById(R.id.first);
        secondTxt = (ImageView) findViewById(R.id.second);
        resultTxt = (ImageView) findViewById(R.id.result);
        operatorTxt = (ImageView) findViewById(R.id.operator);
        highScoreTxt = (TextView) findViewById(R.id.highscore);
        parentLayout = (RelativeLayout) findViewById(R.id.parentLayout);

        parentLayout.setBackgroundColor(Color.parseColor(Helper.getRandomNiceColor()));
        highScoreTxt.setText("0");

        final ImageView progressBar = (ImageView) findViewById(R.id.progressbar);
        progressBar.setBackgroundColor(Color.parseColor("#4788f9"));

        final ImageButton trueImg = (ImageButton) findViewById(R.id.true_btn);
        final ImageButton falseImg = (ImageButton) findViewById(R.id.false_btn);

        trueImg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if(action == MotionEvent.ACTION_DOWN) {
                    trueImg.setBackgroundResource(R.drawable.right_press);
                } else if (action == MotionEvent.ACTION_UP) {
                    trueImg.setBackgroundResource(R.drawable.right);
                }
                return false;
            }
        });


        falseImg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if(action == MotionEvent.ACTION_DOWN) {
                    falseImg.setBackgroundResource(R.drawable.wrong_press);
                } else if (action == MotionEvent.ACTION_UP) {
                    falseImg.setBackgroundResource(R.drawable.wrong);
                }
                return false;
            }
        });

        final int width = Helper.getWidth();
        final ResizeAnimation animation = new ResizeAnimation(progressBar, width, 7, 0, 7, 1200, isEnd);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (progressBar.getWidth() == 0) {
                    looseGame();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        animation.cancel();
        progressBar.setAnimation(animation);

        trueImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progressBar.getWidth() == 0) isEnd.set(true);
                if (isEnd.get()) {
                    return;
                }

                if (!animation.hasEnded()) {
                    animation.cancel();
                }

                if (resultOfGame) {
                    BaseApplication.soundWhenGuessTrue();
                    // reset game
                    progressBar.getLayoutParams().width = width;
                    setGameNumber();
                    animation.start();
                    highScore++;
                    highScoreTxt.setText(highScore + "");
                } else {
                    looseGame();
                }
            }
        });


        falseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progressBar.getWidth() == 0) isEnd.set(true);
                if (isEnd.get()) {
                    return;
                }

                if (!animation.hasEnded()) {
                    animation.cancel();
                }

                if (!resultOfGame) {
                    BaseApplication.soundWhenGuessTrue();
                    // reset game
                    progressBar.getLayoutParams().width = width;
                    setGameNumber();
                    animation.start();
                    highScore++;
                    highScoreTxt.setText(highScore + "");
                } else {
                    looseGame();
                }
            }
        });
        Helper.resetSetting();
        setGameNumber();
        
        String id = "ca-app-pub-5757354627664623/6970426996";
		
		adView = (AdView)this.findViewById(R.id.AdMob);
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
 				LayoutParams.WRAP_CONTENT,
 				LayoutParams.WRAP_CONTENT);
 		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
 		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
    }

    private void setGameNumber() {
        GameObject o = Helper.randomGame();
        resultOfGame = o.isTrue;
        firstTxt.setImageResource(number_image[o.first]);
        secondTxt.setImageResource(number_image[o.second]);
        resultTxt.setImageResource(number_image[o.res]);

        if (o.isPlusOperate) operatorTxt.setImageResource(drawable.plus);
        else operatorTxt.setImageResource(drawable.minus);
        parentLayout.setBackgroundColor(Color.parseColor(Helper.getRandomNiceColor()));
    }

    private void looseGame() {
        isEnd.set(true);
        BaseApplication.soundWhenGuessWrong();
        if (highScore > PrefStore.getMaxScore()) {
            PrefStore.setHighScore(highScore);
        }

        Helper.resetSetting();
        Intent intent = new Intent(this, GameOver.class);
        intent.putExtra("score", highScore);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
       /* startAppAd.onBackPressed();
        super.onBackPressed();*/
    }

    @Override
    public void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);  // Add this method.
    }

    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);  // Add this method.
    }
}
