package com.example.slidingsimplesample.activity;

import com.example.slidingsimplesample.R;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

public class FriendsActivity extends Activity{

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_friends);
		
		ActionBar bar = getActionBar();
		//for color
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00C4CD")));
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setIcon(new Drawable() {
			
			@Override
			public void setColorFilter(ColorFilter cf) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setAlpha(int alpha) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public int getOpacity() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public void draw(Canvas canvas) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
}
