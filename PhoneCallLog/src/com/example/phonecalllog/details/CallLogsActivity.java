package com.example.phonecalllog.details;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.phonecalllog.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CallLog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CallLogsActivity extends Activity {

	private Context context;
	public static ArrayList<CallLogModel> outgoingList;
	public static ArrayList<CallLogModel> incomingList;
	public static ArrayList<CallLogModel> missedcallList;

	public static final int OUTGOING_CALLS = 1;
	public static final int INCOMING_CALLS = 2;
	public static final int MISSED_CALLS = 3;

	public static final String CALL_TYPE = "TYPE";

	private Button inBtn, outBtn, missedBtn;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);
		outBtn = (Button) findViewById(R.id.button1);
		inBtn = (Button) findViewById(R.id.button2);
		missedBtn = (Button) findViewById(R.id.button3);

		inBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showIncomingList();
			}
		});
		outBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showOutGoingList();
			}
		});
		missedBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showMissedList();
			}
		});
		outgoingList = new ArrayList<CallLogModel>();
		incomingList = new ArrayList<CallLogModel>();
		missedcallList = new ArrayList<CallLogModel>();

		new ReadLogs().execute();
	}

	private void showIncomingList() {
		Intent intent = new Intent(this, CallLogs.class);
		intent.putExtra(CALL_TYPE, INCOMING_CALLS);
		startActivity(intent);
	}

	private void showMissedList() {
		Intent intent = new Intent(this, CallLogs.class);
		intent.putExtra(CALL_TYPE, MISSED_CALLS);
		startActivity(intent);
	}

	private void showOutGoingList() {
		Intent intent = new Intent(this, CallLogs.class);
		intent.putExtra(CALL_TYPE, OUTGOING_CALLS);
		startActivity(intent);
	}

	private String getDuration(long milliseconds) {
		int seconds = (int) (milliseconds / 1000) % 60;
		int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
		int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
		if (hours < 1)
			return minutes + ":" + seconds;
		return hours + ":" + minutes + ":" + seconds;
	}

	private String getDateTime(long milliseconds) {
		Date date = new Date(milliseconds);
		//return DateFormat.getDateTimeInstance().format(new Date());

		return date.toLocaleString();
	}

	private void readCallLogs() {

		missedcallList.clear();
		incomingList.clear();
		outgoingList.clear();

		Cursor callLogCursor = getContentResolver().query(
				android.provider.CallLog.Calls.CONTENT_URI, null, null, null,
				android.provider.CallLog.Calls.DEFAULT_SORT_ORDER);
		if (callLogCursor != null) {
			while (callLogCursor.moveToNext()) {
				
				String id = callLogCursor.getString(callLogCursor
						.getColumnIndex(CallLog.Calls._ID));
				String name = callLogCursor.getString(callLogCursor
						.getColumnIndex(CallLog.Calls.CACHED_NAME));
				String cacheNumber = callLogCursor.getString(callLogCursor
						.getColumnIndex(CallLog.Calls.CACHED_NUMBER_LABEL));
				String number = callLogCursor.getString(callLogCursor
						.getColumnIndex(CallLog.Calls.NUMBER));
				long dateTimeMillis = callLogCursor.getLong(callLogCursor
						.getColumnIndex(CallLog.Calls.DATE));
				long durationMillis = callLogCursor.getLong(callLogCursor
						.getColumnIndex(CallLog.Calls.DURATION));
				int callType = callLogCursor.getInt(callLogCursor
						.getColumnIndex(CallLog.Calls.TYPE));

				String duration = getDuration(durationMillis * 1000);

				String dateString = getDateTime(dateTimeMillis);

				if (cacheNumber == null)
					cacheNumber = number;
				if (name == null)
					name = "No Name";

				CallLogModel callLogModel = new CallLogModel(name, cacheNumber,
						duration, dateString);
				if (callType == CallLog.Calls.OUTGOING_TYPE) {
					outgoingList.add(callLogModel);

				} else if (callType == CallLog.Calls.INCOMING_TYPE) {
					incomingList.add(callLogModel);

				} else if (callType == CallLog.Calls.MISSED_TYPE) {
					missedcallList.add(callLogModel);

				}

			}
			callLogCursor.close();
		}
	}

	private class ReadLogs extends AsyncTask<Void, Void, Void> {

		/* Object */
		ProgressDialog dialog;

		/*
		 * Function name: onPreExecute Parameters: Void params Return: void
		 */
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = ProgressDialog.show(CallLogsActivity.this,
					"Reading Call Logs...", "Please wait...", true);
		}

		/*
		 * Function name: doInBackground Parameters: Void params Return: void
		 */
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			readCallLogs();
			return null;
		}

		/*
		 * Function name: onPostExecute Parameters: Void result Return: void
		 */
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
		}
	}
}