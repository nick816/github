package com.abma.texttimer.schedule;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;

import com.abma.texttimer.FragmentSchedule;
import com.abma.texttimer.MainActivity;
import com.abma.texttimer.schedule.db.ScheduledDBAdapter;

public class ListViewScheduled extends ListViewSchedule {

	public ListViewScheduled(Context context) {
		super(context);
	}

	public ListViewScheduled(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ListViewScheduled(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void initScheduleList() {
		setDBAdapter(new ScheduledDBAdapter(getContext()));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void setUpdate() {
		final Handler handler = new Handler();
		Runnable runnable = new Runnable() {
            public void run() {
            	if ( MainActivity.isBackground == false )
            		updateList();
                handler.postDelayed(this, 60 * 1000);
            }
        };        
        Date date = new Date();
        long curDatetime = date.getTime();
        date.setMinutes(date.getMinutes() + 1);
        date.setSeconds(1);
        
        handler.postDelayed(runnable, date.getTime() - curDatetime);
	}
	private void updateList() {
		long now = (new Date()).getTime() / 60 / 1000;
		ListViewScheduleSent sentSchedule = FragmentSchedule._mSentListView;
		for ( int i = scheduleList.size() - 1; i >= 0 ; i-- ) {
			Schedule schedule = scheduleList.get(i);
			if ( now >= (long)(schedule.getReservationTime() / 60 / 1000) ) {
				scheduleList.remove(i);
				schedule.setState(true);
				sentSchedule.addSchedule(schedule, false);
			}
		}
		refresh();
		sentSchedule.refresh();
		FragmentSchedule._mAllListView.refresh();
	}
	
	@Override
	public void refresh() {
		for ( int i = 0; i < scheduleList.size(); i++ ) {
			System.out.println(scheduleList.get(i).getReservationTimeForCompare());
		}
		if ( scheduleList != null ) 
			Collections.sort(scheduleList, comparator);
		scheduleAdapter.notifyDataSetChanged();
		this.invalidate();
	}
	private final static Comparator<Schedule> comparator= new Comparator<Schedule>() {
		private final Collator collator = Collator.getInstance();
		@Override
		public int compare(Schedule object1, Schedule object2) {
			return collator.compare(object1.getReservationTimeForCompare(), object2.getReservationTimeForCompare());
		}
	};
}
