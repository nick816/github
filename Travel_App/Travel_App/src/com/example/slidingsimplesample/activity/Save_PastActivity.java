package com.example.slidingsimplesample.activity;

import com.example.slidingsimplesample.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class Save_PastActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.save_past);
		
		LinearLayout btnFrom = (LinearLayout)this.findViewById(R.id.save_past_layout1);
		LinearLayout btnTo = (LinearLayout)this.findViewById(R.id.save_past_layout2);
		btnFrom.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent from = new Intent(Save_PastActivity.this, BrowseActivity.class);
				startActivity(from);
			}
		});
		
		btnTo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent to = new Intent(Save_PastActivity.this, BrowseActivity.class);
				startActivity(to);
			}
		});
	}
}
