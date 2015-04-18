package com.example.phonecalllog.details;

import com.example.phonecalllog.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CallLogs extends Activity {
	private ListView mainListView;
	private ArrayAdapter<CallLogModel> listAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		mainListView = (ListView) findViewById(R.id.listView);
		mainListView.setSmoothScrollbarEnabled(true);

		Bundle extras = getIntent().getExtras();
		int callType = extras.getInt(CallLogsActivity.CALL_TYPE);
		if (callType == CallLogsActivity.OUTGOING_CALLS)
			listAdapter = new CallLogsArrayAdapter(this,
					CallLogsActivity.outgoingList);
		else if (callType == CallLogsActivity.INCOMING_CALLS)
			listAdapter = new CallLogsArrayAdapter(this,
					CallLogsActivity.incomingList);
		else if (callType == CallLogsActivity.MISSED_CALLS)
			listAdapter = new CallLogsArrayAdapter(this,
					CallLogsActivity.missedcallList);
		mainListView.setAdapter(listAdapter);

	}

	public void initElements() {
		listAdapter.notifyDataSetChanged();
	}
}
