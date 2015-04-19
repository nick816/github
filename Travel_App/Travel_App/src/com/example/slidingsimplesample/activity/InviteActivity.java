package com.example.slidingsimplesample.activity;

import com.example.slidingsimplesample.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class InviteActivity extends Activity{

	ImageView invite;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.invite_friend);
	}
}
