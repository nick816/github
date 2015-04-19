package com.abma.texttimer.schedule;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.abma.texttimer.FragmentCompose;
import com.abma.texttimer.MainActivity;
import com.abma.texttimer.R;
import com.abma.texttimer.adapter.ListScheduleAdapter;
import com.abma.texttimer.additional.ChangeFragmentListener;
import com.abma.texttimer.schedule.db.TextLaterDBAdapter;

public class ListViewSchedule extends ListView {

	protected ArrayList<Schedule> scheduleList = null;
	protected ListScheduleAdapter scheduleAdapter = null;
	
	TextLaterDBAdapter dbAdapter = null;
	
	public ListViewSchedule(Context context) {
		super(context);
	}

	public ListViewSchedule(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ListViewSchedule(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void init()
	{
		this.setDivider(new ColorDrawable(this.getResources().getColor(android.R.color.transparent)));
		this.setSelector(android.R.color.transparent);
		
		initScheduleList();
		if ( dbAdapter != null )
			scheduleList = dbAdapter.selectAll();
		if ( scheduleList == null )
			scheduleList = new ArrayList<Schedule>();
		scheduleAdapter = new ListScheduleAdapter(getContext(), R.layout.layout_list_row, scheduleList);
		setAdapter(scheduleAdapter);
		setOnItemClickListener(new ListSelection());
		
		setUpdate();
	}
	
	public void setUpdate() {
		
	}
	
	public void setDBAdapter(TextLaterDBAdapter dbAdapter) {
		this.dbAdapter = dbAdapter;
	}

	public void initScheduleList() {
	}
	
	public ArrayList<Schedule> getScheduelList() {
		return scheduleList;
	}
	
	public void addSchedule(Schedule schedule, boolean isUpdate) {
		if ( scheduleList == null )
			scheduleList = new ArrayList<Schedule>();
		if ( isUpdate == true && dbAdapter != null ) {
			String insertedid = dbAdapter.insertData(schedule);
			schedule.setScheduleId(insertedid);
		}
		scheduleList.add(schedule);
	}
	public void updateSchedule(Schedule schedule, boolean isUpdate) {
		if ( scheduleList == null )
			return;
		if ( isUpdate == true && dbAdapter != null )
			dbAdapter.updateData(schedule);
	}
	public void removeSchedule(Schedule schedule, boolean isUpdate) {
		if ( scheduleList != null )
			scheduleList.remove(schedule);
		if ( isUpdate == true&&  dbAdapter != null )
			dbAdapter.removeData(Integer.parseInt(schedule.getScheduleId()));
	}
	
	public void refresh() {
	}
	  
	 protected class ListSelection implements OnItemClickListener
	 {
	 
	  @Override
	  public void onItemClick(AdapterView<?> parent, View view, int position,
	    long id)
	  {
		  FragmentCompose.setState(scheduleList.get(position));
		  ((ChangeFragmentListener)getContext()).changeFragment(MainActivity.COMPOSE);
	  }
	   
	 }
}
