package com.example.Http;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.HttpRequest.ShareRequest;
import com.example.Model.User_Info;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

public class Share extends AsyncHttpRequestSample {
	
	private static final String LOG_TAG = "Login";
	User_Info user_info;
	ShareRequest inviteHttpRequest;
	
	public Share(Context context,User_Info temp, ShareRequest i) {
		super(context);		
		this.user_info=temp;
		this.inviteHttpRequest = i;
	}

	@Override
	public ResponseHandlerInterface getResponseHandler() {
		return new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
            }

            //when success request,
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
					int result = 0;
					if (obj != null && !obj.isNull("data") ) {
						result = Integer.parseInt(obj.getString("data"));
					}
					if ( result>0 ) {
						Log.d("Invite state", "Success!");
						if ( inviteHttpRequest != null )
							inviteHttpRequest.share_requestSuccess();						
					}
					else if (result==-1) {
						AlertUtil.messageAlert(_context, "Invite Error", "User not exist!");
					}
					else if (result==-2) {
						AlertUtil.messageAlert(_context, "Invite Error", "Already your friend!");
					}
					else if (result==-3){
						AlertUtil.messageAlert(_context, "Invite Error", "It's you!");
					}
					else {
						if ( inviteHttpRequest != null )
							inviteHttpRequest.share_requestFailure("");
						if ( !obj.isNull("error_description") ) {
						}						
					}
				} catch (JSONException e) {
					e.printStackTrace();
					Log.d("Invite state", "Failed!");
				}
            }
            
            // when failed,
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
            	if ( inviteHttpRequest != null )
					inviteHttpRequest.share_requestFailure("");
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
		return HttpConstants.SHARE;
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

	// send data(image, text, audio etc) to server,
	@Override
	public RequestHandle executeSample(AsyncHttpClient client, String URL,
			Header[] headers, HttpEntity entity,
			ResponseHandlerInterface responseHandler) {
		RequestParams params = new RequestParams();
		
		String photo_path=user_info.get_photo();
		String audio_path=user_info.get_audio();
		String name = user_info.get_name();
		String content = user_info.get_content();
		Log.d("PARAM", photo_path);
		Log.d("PARAM", audio_path);
		if ( photo_path.equals("") ) {
			params.put("photo", "");
		}
		else {
			File photo = new File(photo_path);
			if(photo.isFile()){				
				Log.d("photo", photo_path);
			}else {
				Log.d("454545", photo_path);
			}
		    try {
		        params.put("photo", photo);
		    } catch(FileNotFoundException e) {
		    	e.printStackTrace();
		    }			
		}
		if ( audio_path.equals("") ) {
			params.put("audio", "");
		}
		else{
			File audio = new File(audio_path);
		    try {
		        params.put("audio", audio);
		    } catch(FileNotFoundException e) {
		    }
		}
		params.put("title", name);
		params.put("description", content);
		return client.post(_context, URL, params, responseHandler);
	}
	    
	
	public List<Header> getRequestHeadersList() {
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        headers.add(new BasicHeader("Data-Type", "json"));
        headers.add(new BasicHeader("Accept", "application/json"));
        return headers;
    }

}
