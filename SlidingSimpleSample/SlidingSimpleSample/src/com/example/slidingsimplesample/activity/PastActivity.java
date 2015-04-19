package com.example.slidingsimplesample.activity;

import com.example.slidingsimplesample.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class PastActivity extends Activity{

	final Context context = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.past);
		
		ImageButton btn = (ImageButton)this.findViewById(R.id.plus);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(context);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.detail_two);

				Window dialogWindow = dialog.getWindow();
				dialogWindow.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				
				ImageView img_cancel = (ImageView)dialog.findViewById(R.id.btn_cancel);
				ImageView img_save = (ImageView)dialog.findViewById(R.id.btn_save);
				
				img_cancel.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				
				img_save.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent save = new Intent(PastActivity.this, SaveTabActivity.class);
						startActivity(save);
					}
				});
				
				dialog.show();
			}
		});
	}
}
