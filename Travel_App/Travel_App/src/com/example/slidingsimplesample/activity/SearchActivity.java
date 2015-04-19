package com.example.slidingsimplesample.activity;

import com.example.slidingsimplesample.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class SearchActivity extends Activity{

	ImageView invite;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		
		invite = (ImageView)this.findViewById(R.id.invite_friends);
		invite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(SearchActivity.this, InviteActivity.class);
				startActivity(i);
			}
		});
	}

}
