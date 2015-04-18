package com.abma.texttimer.schedule.db;

import android.content.Context;

public class SentDBAdapter extends TextLaterDBAdapter {

	static SentDBAdapter _mAdapter = null;

    public SentDBAdapter(Context context) {
    	super(context);
    	setTableName(tableName, true);
    }
    
    public static SentDBAdapter getInastance(Context context) {
    	if ( _mAdapter == null ) {
    		_mAdapter = new SentDBAdapter(context);
    	}
    	return _mAdapter;
    }

}
