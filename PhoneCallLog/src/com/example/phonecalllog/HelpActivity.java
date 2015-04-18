package com.example.phonecalllog;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

public class HelpActivity extends Activity {

	Bundle bndanimation_to_child,bndanimation_to_parent;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		
		setTitle("To Main");
		bndanimation_to_child = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation,R.anim.animation2).toBundle();
		bndanimation_to_parent = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation3,R.anim.animation4).toBundle();
		// get the action bar
		ActionBar actionBar = getActionBar();
		// Enabling Back navigation on Action Bar icon
		actionBar.setDisplayHomeAsUpEnabled(true);		
		
	}	
	
	@SuppressLint("NewApi")
	public boolean onNavigateUp() {
		Intent intent=new Intent(HelpActivity.this,MainActivity.class);
		startActivity(intent,bndanimation_to_parent);
		finish();
		return true;
	}
	
	@Override
	public void onBackPressed(){
		
	}
}
