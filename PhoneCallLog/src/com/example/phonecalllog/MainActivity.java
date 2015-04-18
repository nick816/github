package com.example.phonecalllog;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.example.phonecalllog.details.CallLogsActivity;
import com.example.phonecalllog.search.DaySearchActivity;
//import com.example.phonecalllog.search.ContactSearchActivity;

public class MainActivity extends TabActivity 
{
	public static boolean isRunning = false;
	public static MainActivity _mainActivity = null;

	ArrayList<User_Info> friends=new ArrayList<User_Info>();
	private Bundle bndanimation_to_child,bndanimation_to_parent;
	
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState)
    {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            
            isRunning = true;

            // create the TabHost that will contain the Tabs
            TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);

            TabSpec tab1 = tabHost.newTabSpec("First Tab");
            TabSpec tab2 = tabHost.newTabSpec("Second Tab");
            TabSpec tab3 = tabHost.newTabSpec("Third tab");
//          TabSpec tab4 = tabHost.newTabSpec("forth tab");

           // Set the Tab name and Activity
           // that will be opened when particular Tab will be selected
            tab1.setIndicator("Call Logs");
            tab1.setContent(new Intent(this,CallLogsActivity.class));
            
            tab2.setIndicator("Contacts");
            tab2.setContent(new Intent(this,ContactListActivity.class));

            tab3.setIndicator("Settings");
            tab3.setContent(new Intent(this,SettingActivity.class));
            
//            tab4.setIndicator("Search");
//            tab4.setContent(new Intent(this,SettingActivity.class));
            
            /** Add the tabs  to the TabHost to display. */
            tabHost.addTab(tab1);
            tabHost.addTab(tab2);
            tabHost.addTab(tab3);

    }
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
    
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		switch (id) {
		
//		case R.id.address:
//			Intent address=new Intent(MainActivity.this, ContactSearchActivity.class);
//			startActivity(address);
//			finish();
//			break;
		case R.id.search:
			Intent details=new Intent(MainActivity.this, DaySearchActivity.class);
			startActivity(details);
			
			break;
		case R.id.help:
			Intent help=new Intent(MainActivity.this,HelpActivity.class);
			startActivity(help);
			
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void requestFailure(String errMsg) {
		// TODO Auto-generated method stub
		
	}
	
    public void onDestroy(){
    	isRunning = false;
    	_mainActivity = null;
    	super.onDestroy();
    }
} 
