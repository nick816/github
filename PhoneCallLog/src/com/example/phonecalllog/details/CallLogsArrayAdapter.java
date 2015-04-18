package com.example.phonecalllog.details;

import java.util.List;

import com.example.phonecalllog.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CallLogsArrayAdapter extends ArrayAdapter<CallLogModel> {
	private LayoutInflater inflater;

	/**
	 * @purpose Dynamic binding of xml
	 * @param context
	 * @param contactList
	 */

	/* Constructor */
	public CallLogsArrayAdapter(Context context, List<CallLogModel> callLogsList) {
		super(context, R.layout.list_item, R.id.nameTV, callLogsList);
		// Cache the LayoutInflate to avoid asking for a new one each time.
		inflater = LayoutInflater.from(context);
	}

	/*
	 * Function name: getType Parameters: none Return: int
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// Contact to display
		final CallLogModel callLogModel = this.getItem(position);

		TextView nameTV;		
		TextView numberTV;
		TextView dateTV;		
		TextView durationTV;

		//Code is kept simple and not optimized
		convertView = inflater.inflate(R.layout.list_item, null);

		// Find the child views.
		nameTV = (TextView) convertView.findViewById(R.id.nameTV);		
		numberTV = (TextView) convertView.findViewById(R.id.numberTV);
		dateTV = (TextView) convertView.findViewById(R.id.dateTV);		
		durationTV = (TextView) convertView.findViewById(R.id.durationTV);

		nameTV.setText(callLogModel.getName());
		nameTV.setTextColor(Color.argb(255, 0, 100, 255));
		numberTV.setText(callLogModel.getNumber());
		numberTV.setTextColor(Color.argb(255, 0, 100, 255));
		dateTV.setText(callLogModel.getDate());
		dateTV.setTextColor(Color.argb(255, 0, 100, 255));
		durationTV.setText(callLogModel.getDuration()+" secs");
		durationTV.setTextColor(Color.argb(255, 0, 100, 255));
		return convertView;
	}

}
