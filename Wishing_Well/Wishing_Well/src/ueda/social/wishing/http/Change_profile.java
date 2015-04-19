package ueda.social.wishing.http;

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

import ueda.social.wishing.http_request.Change_profileHttpRequest;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

public class Change_profile extends AsyncHttpRequestSample {
	
	private static final String LOG_TAG = "Change profile";
	String user_id;
	String password;	
	String filePath;
	String first_name;
	String last_name;
	int gender;
	String birthday;
	String country;
	String zip_code;
	String email;
	String phone_number;
	String username;	
	String tick;
	
	Change_profileHttpRequest change_profile_HttpRequest;
	
	public Change_profile(Context context,String user_id, String first_name,String last_name,
			int gender,String birthday,String country,String zip_code,String email, 
			String phone_number,String username, String password,String filePath,String tick, Change_profileHttpRequest c) {
		
		super(context);
		this.user_id=user_id;
		this.password = password;
		this.filePath=filePath;
		this.first_name=first_name;
		this.last_name=last_name;
		this.gender=gender;
		this.birthday=birthday;
		this.country=country;
		this.zip_code=zip_code;
		this.email = email;
		this.phone_number=phone_number;
		this.username = username;
		this.tick=tick;
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
					int result = 0;
					if (obj != null && !obj.isNull("data") ) {
						result = Integer.parseInt(obj.getString("data"));
					}
//					{"data":1,"result":[{"id":"1","username":"test1","password":"12345678","firstname":"Berkfh",
//					"lastname":"Alfh","gender":"0","birthday":"05-05-1991","country":"Romania","zipcode":"123",
//					"city":"","address":"","tick":"Tufhfjn","email":"gtest1@hotmail.com","phonenumber":"6N1582254512",
//					"avatar":"uploads\/1\/avatar.png","usergrade":"0","token":null}]}
//					int str_id = 0;
            		String str_username="";
            		String str_first_name="";
            		String str_last_name="";
            		int str_gender=0;
            		String str_birthday="";
            		String str_country="";
            		String str_zip_code="";
            		String str_email="";
            		String str_phone_number="";            		
            		String str_password="";
            		String str_avatar="";
            		String str_tick="";
					if ( result>0 ) {
						JSONObject data=obj.getJSONArray("result").getJSONObject(0);
						Log.d("Change profile Request Response", "Success");
						str_username=data.getString("username");
						str_password=data.getString("password");
						str_first_name=data.getString("firstname");
						str_last_name=data.getString("lastname");
						str_gender=Integer.parseInt(data.getString("gender"));
						str_birthday=data.getString("birthday");
						str_country=data.getString("country");
						str_zip_code=data.getString("zipcode");
						str_email=data.getString("email");
						str_phone_number=data.getString("phonenumber");
						str_avatar=data.getString("avatar");
						str_tick=data.getString("tick");
						
						if ( change_profile_HttpRequest != null )
							change_profile_HttpRequest.change_profile_requestSuccess();
						SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
						SharedPreferences.Editor editor = sharedPreferences.edit();		
//						editor.putInt("current_userid", str_id);
						editor.putString("username", str_username);
						editor.putString("first_name", str_first_name);
						editor.putString("last_name", str_last_name);
						editor.putString("birthday", str_birthday);
						editor.putInt("gender", str_gender);
						editor.putString("country", str_country);
						editor.putString("zipcode", str_zip_code);
						editor.putString("email",str_email);
						editor.putString("phonenumber", str_phone_number);
						editor.putString("password", str_password);
						editor.putString("avatar_image", str_avatar);
						editor.putString("tick", str_tick);
						editor.commit();						
					}					
					else {
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
		return HttpConstants.CHANGE_PROFILE;
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
		RequestParams params = new RequestParams();

		params.put("firstname", first_name);
		params.put("lastname", last_name);
		params.put("gender", gender);
		params.put("birthday", birthday);
		params.put("country", country);
		params.put("zipcode", zip_code);
		params.put("email", email);
		params.put("username", username);
		params.put("phonenumber", phone_number);		
		params.put("user_id", user_id);
		params.put("password", password);	
		params.put("tick", tick);
		if ( filePath.equals("") ) {
			params.put("avatar_img", "");
		}
		else{
			File photo_200 = new File(filePath);
		    try {
		        params.put("avatar_img", photo_200);
		    } catch(FileNotFoundException e) {
		    }
		}
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
