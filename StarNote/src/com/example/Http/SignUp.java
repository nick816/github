package com.example.Http;

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

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.HttpRequest.SignupHttpRequest;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

public class SignUp extends AsyncHttpRequestSample {
	
	private static final String LOG_TAG = "Register";	
	String username;
	String password;
	
	SignupHttpRequest interfaceHttpRequest;
	
	public SignUp(Context context, String username, String password, SignupHttpRequest i) {
		super(context);
		
		this.username=username;
		this.password=password;
		this.interfaceHttpRequest = i;
		
	}

	@Override
	public ResponseHandlerInterface getResponseHandler() {
		return new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
            }

            // when success
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
            	isRunning = false;
            	String json = new String(response);
            	if ( isDebuggable == true ) {
	                debugHeaders(LOG_TAG, headers);
	                debugStatusCode(LOG_TAG, statusCode);
	                debugResponse(LOG_TAG, json);
            	}
				if ( json.equals("1") ) {
					Log.d("Http Request Response", "User was successfully registered");
					
					if ( interfaceHttpRequest != null ){
						Log.d("aaaaaaaa", json);
						interfaceHttpRequest.requestSuccess();
					}						
				}
				else if (json.equals("-1")) {
					AlertUtil.messageAlert(_context, "Signup Error", "Username already exist!");
				}
				
				else {
					if ( interfaceHttpRequest != null )
						interfaceHttpRequest.requestFailure("");				
				}
            }

            // when failed
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

	// to sing up URL
	@Override
	public String getDefaultURL() {
		return HttpConstants.SIGNUP;
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
			params.put("username", username);
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
		return client.post(_context, URL, headers, stringEntity, RequestParams.APPLICATION_JSON, responseHandler);	
	}
	//JSON-type
	public List<Header> getRequestHeadersList() {
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        headers.add(new BasicHeader("Data-Type", "json"));
        headers.add(new BasicHeader("Accept", "application/json"));
        return headers;
    }
}
