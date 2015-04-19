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
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

public class Login extends AsyncHttpRequestSample {
	
	private static final String LOG_TAG = "Login";
//	String name;
	String  password;
	
	InterfaceHttpRequest interfaceHttpRequest;
	protected JSONObject data;
	private String phonenumber;
	
	public Login(Context context, String password, String phonenumber, InterfaceHttpRequest i) {
		super(context);
//		this.name = email;
		this.phonenumber=phonenumber;
		this.password = password;
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
            		String phonenumber="";           		
            		String password="";
					if (obj != null && !obj.isNull("result") ) {
						result = Integer.parseInt(obj.getString("result"));
					}
					if ( result == 1 ) {
						
						if ( interfaceHttpRequest != null )
							interfaceHttpRequest.requestSuccess();
						SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
						SharedPreferences.Editor editor = sharedPreferences.edit();		
//						editor.putInt("current_userid", id);
						editor.putString("phonenumber", phonenumber);
						editor.putString("password", password);
					}
					else{
						AlertUtil.messageAlert(_context, "Login In Error", "Incorrect Login!");
					}
				} catch (JSONException e) {
					e.printStackTrace();
					Log.d("Log in state", "Failed!");
				}
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
            	if ( interfaceHttpRequest != null )
					interfaceHttpRequest.requestFailure("");
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
		return HttpConstants.LOGIN;
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
		JSONObject params = new JSONObject();
		try {
			params.put("phonenumber", phonenumber);
			params.put("password", password);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		StringEntity stringEntity = null;
		try {
			stringEntity = new StringEntity(params.toString());			
			stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
		Log.d("fdsnajkhdsajkhds", URL);
		Log.d("fhdsjkabhfdjsk", stringEntity.toString());
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
