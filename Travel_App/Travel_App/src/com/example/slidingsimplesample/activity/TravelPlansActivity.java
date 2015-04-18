package com.example.slidingsimplesample.activity;

import com.example.slidingsimplesample.R;

import android.app.Dialog;
import android.app.TabActivity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class TravelPlansActivity extends TabActivity{
	
	final Context context = this;
	public static boolean isRunning = false;
	TabHost tabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.travel_plans);
		
		isRunning = true;
		
		// create the TabHost that will contain the Tabs
		tabHost = (TabHost)findViewById(android.R.id.tabhost);
		
		TabSpec tab1 = tabHost.newTabSpec("First Tab");
		TabSpec tab2 = tabHost.newTabSpec("Second Tab");
		
		// Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
		tab1.setIndicator("Current");
		tab1.setContent(new Intent(this, CurrentActivity.class));
		
		tab2.setIndicator("Past");
		tab2.setContent(new Intent(this, PastActivity.class));
		
		
		/** Add the tabs  to the TabHost to display. */
		tabHost.addTab(tab1);
		tabHost.addTab(tab2);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.travel_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.add) {
			final Dialog dialog = new Dialog(context);				
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.detail_one);
			
			Window dialogWindow = dialog.getWindow();
			dialogWindow.setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			
			tabHost.getCurrentTab();
			
			ImageView img_cancel = (ImageView)dialog.findViewById(R.id.image_cancel);
			ImageView img_save = (ImageView)dialog.findViewById(R.id.image_save);
			if(id == 0){
				img_cancel.setOnClickListener(new OnClickListener() {
									
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				img_save.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent save = new Intent(TravelPlansActivity.this, Save_CurrentActivity.class);
						startActivity(save);
					}
				});
				dialog.show();
			}else if(id == 1){
				img_cancel.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				img_save.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent past = new Intent(TravelPlansActivity.this, Save_PastActivity.class);
						startActivity(past);
					}
				});
				dialog.show();
			}
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
