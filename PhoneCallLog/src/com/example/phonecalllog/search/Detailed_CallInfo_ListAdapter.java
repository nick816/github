package com.example.phonecalllog.search;

import java.util.ArrayList;

import com.example.phonecalllog.R;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Detailed_CallInfo_ListAdapter extends BaseAdapter{

	private static final String TAG = null;
	private Context context;
	ArrayList<Call_Info> log;
	
	public Detailed_CallInfo_ListAdapter(Context context, ArrayList<Call_Info> my_log) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.log = my_log;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return log.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return log.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null){
			  LayoutInflater mInflater = (LayoutInflater)
	                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	            convertView = mInflater.inflate(R.layout.list_item_result, null);
		}
		TextView number = (TextView)convertView.findViewById(R.id.number_result);
		TextView date = (TextView)convertView.findViewById(R.id.date_result);
		TextView type = (TextView)convertView.findViewById(R.id.type_result);
		TextView duration = (TextView)convertView.findViewById(R.id.duration_result);
		
		Log.e(TAG, "My Call Log View");
		
		number.setText(log.get(position).get_number());
		date.setText(log.get(position).get_data());
		type.setText(log.get(position).get_type());
		duration.setText(log.get(position).get_duration());
		return convertView;
	}

}
