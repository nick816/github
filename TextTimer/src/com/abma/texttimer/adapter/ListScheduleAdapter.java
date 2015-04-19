package com.abma.texttimer.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abma.texttimer.R;
import com.abma.texttimer.schedule.Schedule;

public class ListScheduleAdapter extends ArrayAdapter<Schedule> {
	
	protected LayoutInflater inflater = null;
	
	public ListScheduleAdapter(Context c, int resourceid, ArrayList<Schedule> arrays) {
		super(c, resourceid, arrays);
		inflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return super.getCount();
	}

	public Schedule getItem(int position) {
		return (Schedule) super.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		return super.getItemId(position);
	}

	@Override
	public View getView(final int position, View convertview, ViewGroup parent) {
		View v = convertview;
		ViewHolder viewHolder = null;
		
		if (convertview == null) {
			viewHolder = new ViewHolder();
			v = inflater.inflate(R.layout.layout_list_row, null);
			viewHolder.tvRecipient = (TextView)v.findViewById(R.id.tvRecipient);
			viewHolder.tvMessage = (TextView)v.findViewById(R.id.tvMessage);
			viewHolder.tvRemainingDateTime = (TextView)v.findViewById(R.id.tvRemainingDateTime);
			viewHolder.tvReservationDateTime = (TextView)v.findViewById(R.id.tvReservationDateTime);
			viewHolder.layoutRemainingTime = (LinearLayout)v.findViewById(R.id.layoutRemainingTime);
			
			v.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) v.getTag();
		}
				
		Schedule schedule = getItem(position);
		
		viewHolder.tvRecipient.setText(schedule.getRecipientName().toString());
		viewHolder.tvMessage.setText(schedule.getMessage().toString());
		String remainingTime = schedule.getRemainingDateTime().toString();
		if ( remainingTime.isEmpty() ) {
			viewHolder.layoutRemainingTime.setVisibility(View.GONE);
		}
		else {
			viewHolder.layoutRemainingTime.setVisibility(View.VISIBLE);
			viewHolder.tvRemainingDateTime.setText(remainingTime);
		}
		viewHolder.tvReservationDateTime.setText(schedule.getReservationDateTime().toString());
		
		return v;
	}
	
	/*
	 * ViewHolder
	 */
	class ViewHolder{
		public TextView tvRecipient  = null;
		public TextView tvMessage = null;
		public TextView tvRemainingDateTime = null;
		public TextView tvReservationDateTime = null;
		public LinearLayout layoutRemainingTime = null;
	}

	@Override
	protected void finalize() throws Throwable {
		free();
		super.finalize();
	}
	
	protected void free(){
		inflater = null;
	}
}
