package com.example.Adapter;

import java.util.ArrayList;
import java.util.HashMap;
 



import com.example.starnote.MainActivity;
import com.example.starnote.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class ListViewAdapter extends BaseAdapter {
 
	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	ImageLoader imageLoader;
	HashMap<String, String> resultp = new HashMap<String, String>();
 
	public ListViewAdapter(Context context,
			ArrayList<HashMap<String, String>> arraylist) {
		this.context = context;
		data = arraylist;
		imageLoader = new ImageLoader(context);
	}
 
	@Override
	public int getCount() {
		return data.size();
	}
 
	@Override
	public Object getItem(int position) {
		return null;
	}
 
	@Override
	public long getItemId(int position) {
		return 0;
	}
 
	
	@SuppressLint("ViewHolder")
	public View getView(final int position, View convertView, ViewGroup parent) {
		// Declare Variables
		TextView title;
		TextView content;
		ImageView photo_url;
 
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View itemView = inflater.inflate(R.layout.main_item, parent, false);
		// Get the position
		resultp = data.get(position);
 
		// Locate the TextViews in listview_item.xml
		title = (TextView) itemView.findViewById(R.id.item_name);
		content = (TextView) itemView.findViewById(R.id.item_content);
		 
		// Locate the ImageView in listview_item.xml
		photo_url = (ImageView) itemView.findViewById(R.id.item_img);
 
		// Capture position and set results to the TextViews
		title.setText(resultp.get(MainActivity.TITLE));
		content.setText(resultp.get(MainActivity.DESCRIPTION));
		// Capture position and set results to the ImageView
		// Passes flag images URL into ImageLoader.class
		imageLoader.DisplayImage(resultp.get(MainActivity.IMAGE), photo_url);
		
		return itemView;
	}
}