package com.abma.texttimer.schedule;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;

import com.abma.texttimer.R;
import com.abma.texttimer.adapter.ListScheduleAllAdapter;

public class ListViewScheduleAll extends ListViewSchedule {

	protected ListScheduleAllAdapter scheduleAdapter = null;
	
	public ListViewScheduleAll(Context context) {
		super(context);
	}

	public ListViewScheduleAll(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ListViewScheduleAll(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public void init()
	{
		this.setDivider(new ColorDrawable(this.getResources().getColor(android.R.color.transparent)));
		this.setSelector(android.R.color.transparent);
		
		initScheduleList();
//		if ( dbAdapter != null )
//			scheduleList = dbAdapter.selectAll();
		if ( scheduleList == null )
			scheduleList = new ArrayList<Schedule>();
		scheduleAdapter = new ListScheduleAllAdapter(getContext(), R.layout.layout_list_row, scheduleList);
		setAdapter(scheduleAdapter);
		setOnItemClickListener(new ListSelection());
		
		setUpdate();
	}
	
	public void refresh() {
		scheduleAdapter.notifyDataSetChanged();
		this.invalidate();
	}
	
	@Override
	public void initScheduleList() {

	}

}
