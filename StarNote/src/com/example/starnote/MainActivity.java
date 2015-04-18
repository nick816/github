package com.example.starnote;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.Adapter.Detailed_Userinfo_ListAdapter;
import com.example.Adapter.JSONfunctions;
import com.example.Adapter.ListViewAdapter;
import com.example.Http.Share;
import com.example.HttpRequest.ShareRequest;
import com.example.Model.User_Info;
import com.example.Model.Userdata_DB_Helper;

public class MainActivity extends Activity implements OnItemClickListener ,ShareRequest{

	private ListView datas, sharedata;
	private Userdata_DB_Helper db;
	private ArrayList<User_Info> users=new ArrayList<User_Info>();
	private ShareRequest _share;
	
	JSONObject jsonobject;
	JSONArray jsonarray;
	
	ListViewAdapter adapter;
	public static String TITLE = "title";
	public static String DESCRIPTION = "description";	
	public static String IMAGE = "photo_url";
	
	ProgressDialog mProgressDialog;
	ArrayList<HashMap<String, String>> arraylist;	
	final Context context = this;
	TextView no, yes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new DownloadJSON().execute();
		
		datas=(ListView)this.findViewById(R.id.listview_first);				
		sharedata=(ListView)this.findViewById(R.id.listview_second);
		ImageView myData = (ImageView)findViewById(R.id.btn_mydata);
		ImageView shareData = (ImageView)findViewById(R.id.btn_sharedata);
		myData.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				datas.setVisibility(View.VISIBLE);
				sharedata.setVisibility(View.GONE);
			}
		});
		
		shareData.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				datas.setVisibility(View.GONE);
				sharedata.setVisibility(View.VISIBLE);
			}
		});
		
		db=new Userdata_DB_Helper(MainActivity.this);
		users=db.getAll_users();
		
		datas.setAdapter(new Detailed_Userinfo_ListAdapter(MainActivity.this, users));
		datas.setOnItemClickListener(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch(id){
		case R.id.plus:
			Intent content = new Intent(MainActivity.this, AddNewActivity.class);			
			startActivity(content);
			overridePendingTransition(R.anim.fade, R.anim.hold);
			finish();
			break;
		case R.id.drive:
			Intent google = new Intent(MainActivity.this, GoogleActivity.class);
			startActivity(google);
			overridePendingTransition(R.anim.fade, R.anim.hold);
			finish();
			break;
		case R.id.about_app:
			Intent about = new Intent(MainActivity.this, AboutActivity.class);
			startActivity(about);
			overridePendingTransition(R.anim.fade, R.anim.hold);
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
		// TODO Auto-generated method stub
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);		 
		// set title
		alertDialogBuilder.setTitle("Confirm!");		 
		// set dialog message
		alertDialogBuilder
			.setMessage("Share this data?")						
			.setPositiveButton("No",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, close
					// current activity
					dialog.cancel();
				}
			  })
			.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, just close
					// the dialog box and do nothing
					Share upload=new Share(MainActivity.this, users.get(arg2), _share);
					upload.onRun();
				}
			});
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		// show it
		alertDialog.show();
		
	}
	
	
	
	// DownloadJSON AsyncTask
		private class DownloadJSON extends AsyncTask<Void, Void, Void> {
	 
			protected void onPreExecute() {
				super.onPreExecute();
				// Create a progress dialog
				mProgressDialog = new ProgressDialog(MainActivity.this);
				// Set progress dialog title
				mProgressDialog.setTitle("Loading DATA.");
				// Set progress dialog message
				mProgressDialog.setMessage("Loading...");
				mProgressDialog.setIndeterminate(false);
				// Show progress dialog
				mProgressDialog.show();
			}
	 
			protected Void doInBackground(Void... params) {
				// Create an array
				arraylist = new ArrayList<HashMap<String, String>>();
				// Retrieve JSON Objects from the given URL address
				jsonobject = JSONfunctions
						.getJSONfromURL("http://zuumbli.com/notestar/index.php/api/getJsonMedias");
				
				try {
					// Locate the array name in JSON
					jsonarray = jsonobject.getJSONArray("data");
	 
					for (int i = 0; i < jsonarray.length(); i++) {
						HashMap<String, String> map = new HashMap<String, String>();
						jsonobject = jsonarray.getJSONObject(i);
						// Retrieve JSON Objects
						map.put("title", jsonobject.getString("title"));
						map.put("description", jsonobject.getString("description"));
						map.put("photo_url", jsonobject.getString("photo_url"));
						// Set the JSON Objects into the array
						arraylist.add(map);
					}
				} catch (JSONException e) {
					Log.e("Error", e.getMessage());
					e.printStackTrace();
				}
				return null;
			}
	 
			@Override
			protected void onPostExecute(Void args) {
				// Locate the listview in listview_main.xml
				sharedata = (ListView) findViewById(R.id.listview_second);
				// Pass the results into ListViewAdapter.java
				adapter = new ListViewAdapter(MainActivity.this, arraylist);
				// Set the adapter to the ListView
				sharedata.setAdapter(adapter);
				// Close the progressdialog
				mProgressDialog.dismiss();
			}
		}
		
	@Override
	public void share_requestFailure(String errMsg) {
		
	}

	@Override
	public void share_requestSuccess() {
		
	}
}
