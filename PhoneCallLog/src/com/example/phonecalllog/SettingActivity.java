package com.example.phonecalllog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.Toast;

public class SettingActivity<ViewHolder> extends Activity{

	ProgressDialog progressBar;
	private int progressBarStatus = 0;
	private Handler progressBarHandler = new Handler();
	RadioButton radio_e, radio_a;
	private long fileSize = 0;

	ViewHolder holder;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		
		radio_e = (RadioButton)this.findViewById(R.id.radio_en);
		radio_a = (RadioButton)this.findViewById(R.id.radio_ar);	
		
		
		radio_e.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
				progressBar = new ProgressDialog(view.getContext());
				progressBar.setCancelable(true);
				progressBar.setMessage("Language Setting ...");
				progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				progressBar.setProgress(0);
				progressBar.setMax(100);
				progressBar.show();
				
				progressBarStatus = 0;
				fileSize = 0;
				new Thread(new Runnable(){
					public void run(){
						while(progressBarStatus < 100){
							progressBarStatus = languageSetting();
							try{
								Thread.sleep(1000);
							}catch(InterruptedException e){
								e.printStackTrace();
							}
							
							progressBarHandler.post(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									progressBar.setProgress(progressBarStatus);
								}
							});
						}
						
						if(progressBarStatus >= 100){
							try{
								Thread.sleep(2000);
							}catch(InterruptedException e){
								e.printStackTrace();
							}
							progressBar.dismiss();
						}
						
					}
					
					
				}).start();
				
				radio_a.setChecked(false);
				radio_e.setChecked(true);		
				
			}
		});
		
		radio_a.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
				progressBar = new ProgressDialog(view.getContext());
				progressBar.setCancelable(true);
				progressBar.setMessage("Language Setting ...");
				progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				progressBar.setProgress(0);
				progressBar.setMax(100);
				progressBar.show();
				
				progressBarStatus = 0;
				fileSize = 0;
				new Thread(new Runnable(){
					public void run(){
						while(progressBarStatus < 100){
							progressBarStatus = languageSetting();
							try{
								Thread.sleep(1000);
							}catch(InterruptedException e){
								e.printStackTrace();
							}
							
							progressBarHandler.post(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									progressBar.setProgress(progressBarStatus);
								}
							});
						}
						
						if(progressBarStatus >= 100){
							try{
								Thread.sleep(2000);
							}catch(InterruptedException e){
								e.printStackTrace();
							}
							progressBar.dismiss();
						}
						
					}
					
					
				}).start();
				
				radio_a.setChecked(true);
				radio_e.setChecked(false);
			}
		});
		
//		Toast.makeText(SettingActivity.this, "Setting Success!", Toast.LENGTH_SHORT).show();
	}
	
	private int languageSetting() {
		// TODO Auto-generated method stub
		 while (fileSize <= 5000000) {
			             fileSize++;
			             if (fileSize == 100000) {
			                 return 10;
			             } else if (fileSize == 100000) {
			                 return 15;
			             } else if (fileSize == 150000) {
			                 return 20;
			             } else if (fileSize == 200000) {
			                 return 25;
			             } else if (fileSize == 250000) {
			                 return 30;
			             }else if (fileSize == 300000) {
			                 return 35;
			             }else if (fileSize == 350000) {
			                 return 40;
			             }else if (fileSize == 400000) {
			                 return 45;
			             }else if (fileSize == 450000) {
			                 return 50;
			             }else if (fileSize == 500000) {
			                 return 55;
			             }else if (fileSize == 550000) {
			                 return 60;
			             }else if (fileSize == 600000) {
			                 return 65;
			             }else if (fileSize == 650000) {
			                 return 70;
			             }else if (fileSize == 700000) {
			                 return 75;
			             }else if (fileSize == 750000) {
			                 return 80;
			             }else if (fileSize == 800000) {
			                 return 85;
			             }else if (fileSize == 850000) {
			                 return 90;
			             }else if (fileSize == 900000) {
			                 return 95;
			             }
			             
			         }
			         return 100;			         
	}
}
