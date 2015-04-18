package com.example.phonecalllog;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.phonecalllog.AlertUtil;
import com.example.phonecalllog.AsyncHttpRequestSample;
import com.example.phonecalllog.HttpConstants;
import com.example.phonecalllog.InterfaceHttpRequest;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

public class Register extends AsyncHttpRequestSample {
	
	private static final String LOG_TAG = "Register";
	
	String phone_number;
	String password;
	String confirm;
	
	InterfaceHttpRequest interfaceHttpRequest;

	private String TAG;
	
	public Register(Context context, String phone_number,String pass_word,String confirm_pass,InterfaceHttpRequest i) {
		super(context);
		
		this.password = pass_word;
		this.confirm=confirm_pass;
		this.phone_number = phone_number;
		this.interfaceHttpRequest = i;
	}

	@Override
	public ResponseHandlerInterface getResponseHandler() {
		return new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
//            	Log.e(TAG, "hello");
//            	isRunning = false;
            	String json = new String(response);
            	if ( isDebuggable == true ) {
	                debugHeaders(LOG_TAG, headers);
	                debugStatusCode(LOG_TAG, statusCode);
	                debugResponse(LOG_TAG, json);
            	}
            	try {
            		JSONObject obj = new JSONObject(json);
					int result = 0;
					if (obj != null && !obj.isNull("data") ) {
						result = Integer.parseInt(obj.getString("status"));
					}
					if ( result==0 ) {
						Log.d("Http Request Response", "User was successfully registered");
						if ( interfaceHttpRequest != null ){
							interfaceHttpRequest.requestSuccess();
						}						
					}
					else if (result==-1) {
						AlertUtil.messageAlert(_context, "Signup Error", "Username already exist!");
					}
					else if (result==-2) {
						AlertUtil.messageAlert(_context, "Signup Error", "E-mail address already exist!");
					}
					else {
						if ( interfaceHttpRequest != null )
							interfaceHttpRequest.requestFailure("");
						if ( !obj.isNull("error_description") ) {
						}						
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
            	if ( interfaceHttpRequest != null )
					interfaceHttpRequest.requestFailure("");
//            	isRunning = false;
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
		 
		return HttpConstants.REGISTER;  
	}

	@Override
	public void getDefaultValue() {

	}

	@Override
	public boolean isRequestHeadersAllowed() {
//		Log.e(TAG, phone_number); 
		return true;
	}

	@Override
	public boolean isRequestBodyAllowed() {
		return true;
	}

	@Override
	public RequestHandle executeSample(AsyncHttpClient client, String URL,
			Header[] headers, HttpEntity entity, ResponseHandlerInterface responseHandler) {
		
		JSONObject params = new JSONObject();
		
		try {
			
			params.put("phonenumber", phone_number);
			params.put("password", password);
//			Log.e(TAG, phone_number);  			
			
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		StringEntity stringEntity = null;
		try {
			stringEntity = new StringEntity(params.toString());
			
			stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
//			Log.e(TAG, "hello");
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
