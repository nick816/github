package com.abma.texttimer.schedule;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.util.AttributeSet;

import com.abma.texttimer.schedule.db.SentDBAdapter;

public class ListViewScheduleSent extends ListViewSchedule {

	public ListViewScheduleSent(Context context) {
		super(context);
	}

	public ListViewScheduleSent(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ListViewScheduleSent(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void initScheduleList() {
		setDBAdapter(new SentDBAdapter(getContext()));
	}
	
	@Override
	public void refresh() {
		
		if ( scheduleList != null ) 
			Collections.sort(scheduleList, comparator);
		scheduleAdapter.notifyDataSetChanged();
		this.invalidate();
	}
	private final static Comparator<Schedule> comparator= new Comparator<Schedule>() {
		private final Collator collator = Collator.getInstance();
		@Override
		public int compare(Schedule object1, Schedule object2) {
			return collator.compare(object2.getReservationTimeForCompare(), object1.getReservationTimeForCompare());
		}
	};
}
