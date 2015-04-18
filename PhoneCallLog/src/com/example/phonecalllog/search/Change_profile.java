package com.example.phonecalllog.search;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.phonecalllog.search.Change_profileHttpRequest;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.phonecalllog.AsyncHttpRequestSample;
import com.example.phonecalllog.HttpConstants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

public class Change_profile extends AsyncHttpRequestSample {
	
	private static final String LOG_TAG = "Call Log";
	String from_day;
	String to_day;
	String month;	
	String year;
	String na;
	Change_profileHttpRequest change_profile_HttpRequest;
	protected Context context;
	
	public Change_profile(Context context,String from_day, String to_day, String month, String year, String na, Change_profileHttpRequest c) {		
		super(context);
		
		this.from_day = from_day;
		this.to_day = to_day;
		this.month = month;
		this.year = year;
		this.na = na;
		
		this.change_profile_HttpRequest = c;
	}

	@Override
	public ResponseHandlerInterface getResponseHandler() {
		return new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
            	isRunning = false;
            	String json = new String(response);
            	if ( isDebuggable == true ) {
	                debugHeaders(LOG_TAG, headers);
	                debugStatusCode(LOG_TAG, statusCode);
	                debugResponse(LOG_TAG, json);
            	}
            	try {
            		JSONObject obj = new JSONObject(json);
					JSONArray data = null;
					if (obj != null && !obj.isNull("data") ) {
						data = obj.getJSONArray("data");
					}
            		if(data != null && data.length() > 0){
            			
            			ArrayList<Call_Info> search_result = new ArrayList<Call_Info>();
            			for(int i = 0; i < data.length(); i++){
            				JSONObject json_temp = data.getJSONObject(i);
            				Call_Info temp = new Call_Info();
            				temp.set_number(json_temp.getString("fr_number"));
            				temp.set_data(json_temp.getString("dt"));
            				temp.set_type(json_temp.getString("type"));
            				temp.set_duration(json_temp.getString("duration"));
            				search_result.add(temp);
            				
            			}
    					if ( change_profile_HttpRequest != null ){
    						change_profile_HttpRequest.change_profile_requestSuccess(search_result);
    					}
            		}
            		else if(data != null && data.length() == 0){
						if ( change_profile_HttpRequest != null )
							change_profile_HttpRequest.change_profile_requestFailure("");
						if ( !obj.isNull("error_description") ) {
							
						}						
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
            	if ( change_profile_HttpRequest != null )
            		change_profile_HttpRequest.change_profile_requestFailure("");
            	isRunning = false;
                debugHeaders(LOG_TAG, headers);
                debugStatusCode(LOG_TAG, statusCode);
                debugThrowable(LOG_TAG, e);
                if (errorResponse != null) {
                    debugResponse(LOG_TAG, new String(errorResponse));
                }
            }

            @Override
            public void onRetry(int retryNo) {
                Toast.makeText(_context,
                        String.format("Request is retried, retry no. %d", retryNo),
                        Toast.LENGTH_SHORT)
                        .show();
            }
        };
	}

	@Override
	public String getDefaultURL() {
		return HttpConstants.CALLLOG;
	}

	@Override
	public void getDefaultValue() {

	}

	@Override
	public boolean isRequestHeadersAllowed() {
		return true;
	}

	@Override
	public boolean isRequestBodyAllowed() {
		return true;
	}

	@Override
	public RequestHandle executeSample(AsyncHttpClient client, String URL,
			Header[] headers, HttpEntity entity,
			ResponseHandlerInterface responseHandler) {
//		RequestParams params = new RequestParams();
		JSONObject params = new JSONObject();
		try{
			params.put("from_day", from_day);
			params.put("to_day", to_day);
			params.put("month", month);
			params.put("year", year);
			params.put("fr_name", na);
		}catch(JSONException e){
			e.printStackTrace();
		}
		StringEntity stringEntity = null;
		try{
			stringEntity = new StringEntity(params.toString());
			stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
		return client.post(_context, URL, headers, stringEntity, RequestParams.APPLICATION_JSON, responseHandler);  
	}
	    
	
	public List<Header> getRequestHeadersList() {
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        headers.add(new BasicHeader("Data-Type", "json"));
        headers.add(new BasicHeader("Accept", "application/json"));
        return headers;
    }
}
