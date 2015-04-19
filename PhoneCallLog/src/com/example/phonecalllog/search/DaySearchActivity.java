package com.example.phonecalllog.search;

import java.net.URI;
import java.util.ArrayList;

import com.example.phonecalllog.HelpActivity;
import com.example.phonecalllog.MainActivity;
import com.example.phonecalllog.R;
import com.example.phonecalllog.search.Change_profileHttpRequest;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class DaySearchActivity extends Activity implements Change_profileHttpRequest{
	
//    private static final String TAG = "Victor-Manage_Clique";
	Bundle bndanimation_to_child,bndanimation_to_parent;
	
    private Context context;
	ListView listView;
	EditText name;
	private Button send;

	private ArrayList<Call_Info> my_log = new ArrayList<Call_Info>();
	private Spinner  start_day, end_day, s_month, s_year;

	String from_day, to_day, month, year;
	String na;
	private String[] days=new String[32];
	private String[] years=new String[200];
	private String _start_day, _end_day, _month, _year;
	private URI url;
	private Change_profileHttpRequest c=this;

	private String TAG;
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState); 
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.calllog_search);
			
		setTitle("To Main");
		bndanimation_to_child = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation,R.anim.animation2).toBundle();
		bndanimation_to_parent = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation3,R.anim.animation4).toBundle();
		// get the action bar
		ActionBar actionBar = getActionBar();
		// Enabling Back navigation on Action Bar icon
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		context = this;
		
		listView = (ListView)findViewById(R.id.result);
		send = (Button)findViewById(R.id.btnsearch);
		name = (EditText)this.findViewById(R.id.get_name);
		
		
		start_day=(Spinner)this.findViewById(R.id.start_day_spinner);
		end_day=(Spinner)this.findViewById(R.id.end_day_spinner);
		s_month=(Spinner)this.findViewById(R.id.call_month_spinner);		
		s_year=(Spinner)this.findViewById(R.id.call_year_spinner);
		
		for (int i = 1; i <= 31; i++) {
			days[0]="-";
			days[i]=String.valueOf(i);
		}
        for (int i = 0; i <100 ; i++) {
			years[i]=String.valueOf(i+2000);
		}
        
        ArrayAdapter<String> start_dayadapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,days);
        start_dayadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        start_day.setAdapter(start_dayadapter);
        ArrayAdapter<String> end_dayadapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,days);
        end_dayadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        end_day.setAdapter(end_dayadapter);
        
        ArrayAdapter<String> yearadapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,years);
        yearadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_year.setAdapter(yearadapter);   
        
        start_day.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				_start_day=parent.getItemAtPosition(position).toString();	
//				_start_day=String.valueOf(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
				// TODO Auto-generated method stub
				_start_day="1";
			}
		});
        
        
        end_day.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				_end_day=parent.getItemAtPosition(position).toString();	
//				_end_day=String.valueOf(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				_end_day="1";
			}
		});        
        
        
        s_month.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				_month=String.valueOf(position);	
//				Log.d("Birth_month", _month);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				_month="January";
			}
		});
        s_year.setSelection(15);
        s_year.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				_year=parent.getItemAtPosition(position).toString();
//				_year=String.valueOf(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				_year="2015";
			}
		});
        
        
		
        send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				from_day = String.valueOf(_start_day);
				to_day = String.valueOf(_end_day);
				month = String.valueOf(_month);
				year = _year;
				
				if(name == null){
					na = name.getText().toString();
					if(String.valueOf(_start_day).equals("-") && String.valueOf(_end_day).equals("-")){
							if(String.valueOf(month).equals("-")){
								if(_year != null)
								from_day = "0";
								to_day ="0";
								month = "0";
								na = "0";
								Change_profile change_profile=new Change_profile(context, from_day, to_day, month, year, na, c);
								change_profile.onRun();
							}if(!String.valueOf(month).equals("-")){
								if(_year != null)
								from_day = "0";
								to_day ="0";
								na = "0";
								Change_profile change_profile=new Change_profile(context, from_day, to_day, month, year, na, c);
								change_profile.onRun();
							}
					}
					
									
					
					if(!String.valueOf(_start_day).equals("-") && String.valueOf(_end_day).equals("-")){
						if(String.valueOf(month).equals("-")){
							if(_year != null)
							Toast.makeText(DaySearchActivity.this, "Select month!", Toast.LENGTH_SHORT).show();
						}
						if(!String.valueOf(month).equals("-")){
							if(_year != null)
							to_day ="0";
							na = "0";
							Change_profile change_profile=new Change_profile(context, from_day, to_day, month, year, na, c);
							change_profile.onRun();
						}
					}
						
						
					if(String.valueOf(_start_day).equals("-") && !String.valueOf(_end_day).equals("-")){
						if(String.valueOf(month).equals("-")){
							if(_year != null)
							Toast.makeText(DaySearchActivity.this, "Select month!", Toast.LENGTH_SHORT).show();
						}if(!String.valueOf(month).equals("-")){
							if(_year != null)
							from_day ="0";
							na = "0";
							Change_profile change_profile=new Change_profile(context, from_day, to_day, month, year, na, c);
							change_profile.onRun();
						}
					}
					
					
					
					
					if(!String.valueOf(_start_day).equals("-") && !String.valueOf(_end_day).equals("-")){
						if(Integer.parseInt(_start_day) <= Integer.parseInt(_end_day)){
							if(String.valueOf(month).equals("-")){
								if(_year != null)
								Toast.makeText(DaySearchActivity.this, "Select month!", Toast.LENGTH_SHORT).show();
							}if(!String.valueOf(month).equals("-")){
								if(_year != null){
									na = "0";
									Change_profile change_profile=new Change_profile(context, from_day, to_day, month, year, na, c);
									change_profile.onRun();
								}
							}
						}else if(Integer.parseInt(_start_day) >= Integer.parseInt(_end_day)){
							if(String.valueOf(month).equals("-")){
								if(_year != null)
								Toast.makeText(DaySearchActivity.this, "Enter valid date!", Toast.LENGTH_SHORT).show();
							}else if(!String.valueOf(month).equals("-")){
								if(_year != null)
								Toast.makeText(DaySearchActivity.this, "Enter valid date!", Toast.LENGTH_SHORT).show();
							}
						}
					}
					
					
					
					if(String.valueOf(month).equals("-")){
						if(String.valueOf(_start_day).equals("-") && String.valueOf(_end_day).equals("-")){
							if(_year != null)
							from_day = "0";
							to_day ="0";
							month = "0";
							na = "0";
							Change_profile change_profile=new Change_profile(context, from_day, to_day, month, year, na, c);
							change_profile.onRun();
						}if(!String.valueOf(_start_day).equals("-") && !String.valueOf(_end_day).equals("-")){
							if(_year != null)
							Toast.makeText(DaySearchActivity.this, "Select month!", Toast.LENGTH_SHORT).show();
						}if(!String.valueOf(_start_day).equals("-") && String.valueOf(_end_day).equals("-") || String.valueOf(_start_day).equals("-") && !String.valueOf(_end_day).equals("-")){
							if(_year != null)
							Toast.makeText(DaySearchActivity.this, "Select month!", Toast.LENGTH_SHORT).show();
						}
					}
					
					if(!String.valueOf(month).equals("-")){
						if(String.valueOf(_start_day).equals("-") && String.valueOf(_end_day).equals("-")){
							if(_year != null)
							from_day = "0";
							to_day ="0";
							na = "0";
							Change_profile change_profile=new Change_profile(context, from_day, to_day, month, year, na, c);
							change_profile.onRun();
						}if(!String.valueOf(_start_day).equals("-") && !String.valueOf(_end_day).equals("-")){
							if(Integer.parseInt(_start_day) <= Integer.parseInt(_end_day)){
								if(_year != null){
									na = "0";
								Change_profile change_profile=new Change_profile(context, from_day, to_day, month, year, na, c);
								change_profile.onRun();
								}
							}else if(Integer.parseInt(_start_day) > Integer.parseInt(_end_day)){
								if(_year != null)
								Toast.makeText(DaySearchActivity.this, "Enter valid date!", Toast.LENGTH_SHORT).show();
							}
						}if(!String.valueOf(_start_day).equals("-") && String.valueOf(_end_day).equals("-")){
							if(_year != null)
							to_day = "0";
							na = "0";
							Change_profile change_profile=new Change_profile(context, from_day, to_day, month, year, na, c);
							change_profile.onRun();
						}if(String.valueOf(_start_day).equals("-") && !String.valueOf(_end_day).equals("-")){
							if(_year != null)
							from_day = "0";
							na = "0";
							Change_profile change_profile=new Change_profile(context, from_day, to_day, month, year, na, c);
							change_profile.onRun();
						}
					}
				}
				
				
				
				
				
				
				if(name != null){
					na = name.getText().toString();
						if(String.valueOf(_start_day).equals("-") && String.valueOf(_end_day).equals("-")){
								if(String.valueOf(month).equals("-")){
									if(_year != null)
									from_day = "0";
									to_day ="0";
									month = "0";
									Change_profile change_profile=new Change_profile(context, from_day, to_day, month, year, na, c);
									change_profile.onRun();
								}if(!String.valueOf(month).equals("-")){
									if(_year != null)
									from_day = "0";
									to_day ="0";							
									Change_profile change_profile=new Change_profile(context, from_day, to_day, month, year, na, c);
									change_profile.onRun();
								}
						}
						
										
						
						if(!String.valueOf(_start_day).equals("-") && String.valueOf(_end_day).equals("-")){
							if(String.valueOf(month).equals("-")){
								if(_year != null)
								Toast.makeText(DaySearchActivity.this, "Select month!", Toast.LENGTH_SHORT).show();
							}
							if(!String.valueOf(month).equals("-")){
								if(_year != null)
								to_day ="0";
								Change_profile change_profile=new Change_profile(context, from_day, to_day, month, year, na, c);
								change_profile.onRun();
							}
						}
							
							
						if(String.valueOf(_start_day).equals("-") && !String.valueOf(_end_day).equals("-")){
							if(String.valueOf(month).equals("-")){
								if(_year != null)
								Toast.makeText(DaySearchActivity.this, "Select month!", Toast.LENGTH_SHORT).show();
							}if(!String.valueOf(month).equals("-")){
								if(_year != null)
								from_day ="0";
								Change_profile change_profile=new Change_profile(context, from_day, to_day, month, year, na, c);
								change_profile.onRun();
							}
						}
						
						
						
						
						if(!String.valueOf(_start_day).equals("-") && !String.valueOf(_end_day).equals("-")){
							if(Integer.parseInt(_start_day) <= Integer.parseInt(_end_day)){
								if(String.valueOf(month).equals("-")){
									if(_year != null)
									Toast.makeText(DaySearchActivity.this, "Select month!", Toast.LENGTH_SHORT).show();
								}if(!String.valueOf(month).equals("-")){
									if(_year != null){
									Change_profile change_profile=new Change_profile(context, from_day, to_day, month, year, na, c);
									change_profile.onRun();
									}
								}
							}else if(Integer.parseInt(_start_day) >= Integer.parseInt(_end_day)){
								if(String.valueOf(month).equals("-")){
									if(_year != null)
									Toast.makeText(DaySearchActivity.this, "Enter valid date!", Toast.LENGTH_SHORT).show();
								}else if(!String.valueOf(month).equals("-")){
									if(_year != null)
									Toast.makeText(DaySearchActivity.this, "Enter valid date!", Toast.LENGTH_SHORT).show();
								}
							}
						}
						
						
						
						if(String.valueOf(month).equals("-")){
							if(String.valueOf(_start_day).equals("-") && String.valueOf(_end_day).equals("-")){
								if(_year != null)
								from_day = "0";
								to_day ="0";
								month = "0";
								Change_profile change_profile=new Change_profile(context, from_day, to_day, month, year, na, c);
								change_profile.onRun();
							}if(!String.valueOf(_start_day).equals("-") && !String.valueOf(_end_day).equals("-")){
								if(_year != null)
								Toast.makeText(DaySearchActivity.this, "Select month!", Toast.LENGTH_SHORT).show();
							}if(!String.valueOf(_start_day).equals("-") && String.valueOf(_end_day).equals("-") || String.valueOf(_start_day).equals("-") && !String.valueOf(_end_day).equals("-")){
								if(_year != null)
								Toast.makeText(DaySearchActivity.this, "Select month!", Toast.LENGTH_SHORT).show();
							}
						}
						
						if(!String.valueOf(month).equals("-")){
							if(String.valueOf(_start_day).equals("-") && String.valueOf(_end_day).equals("-")){
								if(_year != null)
								from_day = "0";
								to_day ="0";
								Change_profile change_profile=new Change_profile(context, from_day, to_day, month, year, na, c);
								change_profile.onRun();
							}if(!String.valueOf(_start_day).equals("-") && !String.valueOf(_end_day).equals("-")){
								if(Integer.parseInt(_start_day) <= Integer.parseInt(_end_day)){
									if(_year != null){
									Change_profile change_profile=new Change_profile(context, from_day, to_day, month, year, na, c);
									change_profile.onRun();
									}
								}else if(Integer.parseInt(_start_day) > Integer.parseInt(_end_day)){
									if(_year != null)
									Toast.makeText(DaySearchActivity.this, "Enter valid date!", Toast.LENGTH_SHORT).show();
								}
							}if(!String.valueOf(_start_day).equals("-") && String.valueOf(_end_day).equals("-")){
								if(_year != null)
								to_day = "0";
								Change_profile change_profile=new Change_profile(context, from_day, to_day, month, year, na, c);
								change_profile.onRun();
							}if(String.valueOf(_start_day).equals("-") && !String.valueOf(_end_day).equals("-")){
								if(_year != null)
								from_day = "0";
								Change_profile change_profile=new Change_profile(context, from_day, to_day, month, year, na, c);
								change_profile.onRun();
							}
						}
					}		
				
				
				
				
				
			}			
				
				
		});
        
           
	}
	
	public String getPath(Uri uri, Activity activity) {
        String[] projection = { MediaColumns.DATA };
        Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }	

	public String padding(int temp){
    	if (temp<10) {
			return "0"+String.valueOf(temp);
		}
    	else{
    		return String.valueOf(temp);    
    	}
    }
	@Override
	public void change_profile_requestFailure(String errMsg) {
		Toast.makeText(context, "No exist data!", Toast.LENGTH_SHORT).show();	
		my_log=new ArrayList<Call_Info>();
		listView.setAdapter(new Detailed_CallInfo_ListAdapter(DaySearchActivity.this, my_log));		
	}
	
	public void change_profile_requestSuccess(ArrayList<Call_Info> call_info) {
		
		my_log=new ArrayList<Call_Info>();
		for (int i = 0; i < call_info.size(); i++) {
				my_log.add(call_info.get(i));
		}		
		listView.setAdapter(new Detailed_CallInfo_ListAdapter(DaySearchActivity.this, my_log));		
	
	}
	
	@SuppressLint("NewApi")
	public boolean onNavigateUp() {
		Intent intent=new Intent(DaySearchActivity.this, MainActivity.class);
		startActivity(intent,bndanimation_to_parent);
		finish();
		return true;
	}
	
	@Override
	public void onBackPressed(){
		
	}
//	s_year.setSelection(15);
}