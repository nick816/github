package com.example.phonecalllog;

import java.util.ArrayList;

//import com.example.phonecalllog.details.CallLogsActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HistoryActivity extends Activity{
	
	private ArrayList<String> phones=new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
      	
		getCallDetails();		
		
//        String[] items={"itme1","item2","itme1","item2","itme1","item2","itme1","item2","itme1","item2","itme1","item2"};
		
        for (int i = 0; i <phones.size(); i++) {
			adapter.add(phones.get(i));
		}
        ListView listView = (ListView) findViewById(R.id.logview);

        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	
            	Intent i = new Intent(HistoryActivity.this, DetailsInfoActivity.class);
            	startActivity(i);
//                ListView listView = (ListView) parent;
//                String item = (String) listView.getItemAtPosition(position);
//                Toast.makeText(CallListView.this, item, Toast.LENGTH_LONG).show();
                
            }
        });

//    listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//        @Override
//        public void onItemSelected(AdapterView<?> parent, View view,
//                int position, long id) {
//            ListView listView = (ListView) parent;
//            String item = (String) listView.getSelectedItem();
//            Toast.makeText(CallListView.this, item, Toast.LENGTH_LONG).show();
//        }
//        @Override
//        public void onNothingSelected(AdapterView<?> parent) {
//        }
//        });
    }
	public void getCallDetails() { 
		StringBuffer sb = new StringBuffer(); 
		Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, null); 
		int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER); 
		sb.append(" Call Log Information : ");
		
		while (managedCursor.moveToNext()) { 
			String phNumber = managedCursor.getString(number);
			phones.add(phNumber);
		}
		managedCursor.close();
	}	
}
