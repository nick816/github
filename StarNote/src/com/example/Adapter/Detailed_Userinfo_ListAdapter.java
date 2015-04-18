package com.example.Adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Model.User_Info;
import com.example.starnote.R;

public class Detailed_Userinfo_ListAdapter extends BaseAdapter {
	
	@SuppressWarnings("unused")
	private static final String TAG = null;
	private Context context;	
	ArrayList<User_Info> users;
	
	//get arraydata from database. 
	public Detailed_Userinfo_ListAdapter(Context context, ArrayList<User_Info> users){
		this.context = context;
		this.users=users;
	}

	@Override
	public int getCount() {
		return users.size();
	}

	@Override
	public Object getItem(int position) {		
		return users.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.main_item, null);
        }
		
		//show images and texts to get from SQLite.
        ImageView user=(ImageView)convertView.findViewById(R.id.item_img);
        TextView  username = (TextView) convertView.findViewById(R.id.item_name);        
        TextView  usercontent=(TextView)convertView.findViewById(R.id.item_content);
        if (users.get(position).get_photo().equals("")) {
			
        	//if there isn't image,
        	user.setImageResource(R.drawable.empty_user);
						
		}
        else{
        	
        	//when there is image, show an image to added by user.
        	user.setImageURI(Uri.parse(users.get(position).get_photo()));
        }
        //show theme and content to added by user.
        username.setText(users.get(position).get_name());
        usercontent.setText(users.get(position).get_content());
        
        return convertView;
	}

}
