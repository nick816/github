package ueda.social.wishing.adapter;

import java.util.ArrayList;

import ueda.social.wishing.R;
import ueda.social.wishing.image.SmartImageView;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Phone_contact_ListAdapter extends BaseAdapter {
	
	private Context context;	
	ArrayList<String> names;
	ArrayList<String> emails;
	ArrayList<String> numbers;
	ArrayList<String> images;
	
	public Phone_contact_ListAdapter(Context context,ArrayList<String> name,ArrayList<String> email,ArrayList<String> number,ArrayList<String> image){
		this.context = context;
		this.names=name;
		this.emails=email;
		this.numbers=number;
		this.images=image;
	}

	@Override
	public int getCount() {
		return names.size();
	}

	@Override
	public Object getItem(int position) {		
		return names.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.xml.contact_cell, null);
        }
        SmartImageView user=(SmartImageView)convertView.findViewById(R.id.phone_contact_avarta);
        TextView  username = (TextView) convertView.findViewById(R.id.phone_name);        
        TextView  useremail=(TextView)convertView.findViewById(R.id.phone_email);
        
        if (images.get(position).equals("")) {
			user.setImageResource(R.drawable.phone);
		}
        else{
        	user.setImageURI(Uri.parse(images.get(position)));
        }
        username.setText(names.get(position));        
        useremail.setText(emails.get(position));       
        return convertView;
	}

}
