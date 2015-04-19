package com.abma.texttimer.schedule.db;

import android.content.Context;

public class ScheduledDBAdapter extends TextLaterDBAdapter {

    static ScheduledDBAdapter _mAdapter = null;

    public ScheduledDBAdapter(Context context) {
    	super(context);
    	
    	setTableName(tableName, false);
    }
    
    public static ScheduledDBAdapter getInastance(Context context) {
    	if ( _mAdapter == null ) {
    		_mAdapter = new ScheduledDBAdapter(context);
    	}
    	return _mAdapter;
    }
}
