package com.example.Adapter;

import com.example.starnote.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
 
public class SingleItemView extends Activity {
	// Declare Variables
	String title;
	String description;
	String photo_url;
	String position;
	ImageLoader imageLoader = new ImageLoader(this);
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from singleitemview.xml
		setContentView(R.layout.main_item);
 
		Intent i = getIntent();
		// Get the result of rank
		title = i.getStringExtra("title");
		// Get the result of country
		description = i.getStringExtra("description");
		// Get the result of flag
		photo_url = i.getStringExtra("photo_url");
 
		// Locate the TextViews in singleitemview.xml
		TextView txttitle = (TextView) findViewById(R.id.item_name);
		TextView txtcontent = (TextView) findViewById(R.id.item_content);
		 
		// Locate the ImageView in singleitemview.xml
		ImageView imgView = (ImageView) findViewById(R.id.item_img);
 
		// Set results to the TextViews
		txttitle.setText(title);
		txtcontent.setText(description);
 
		// Capture position and set results to the ImageView
		// Passes flag images URL into ImageLoader.class
		imageLoader.DisplayImage(photo_url, imgView);
	}
}
