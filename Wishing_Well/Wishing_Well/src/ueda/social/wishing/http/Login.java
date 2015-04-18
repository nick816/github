package ueda.social.wishing.http;

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

import ueda.social.wishing.http_request.InterfaceHttpRequest;
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
	String name;
	String password;
	
	InterfaceHttpRequest interfaceHttpRequest;
	
	public Login(Context context, String email, String password, InterfaceHttpRequest i) {
		super(context);
		this.name = email;
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
            		int id = 0;
            		String username="";
            		String first_name="";
            		String last_name="";
            		int gender=0;
            		String birthday="";
            		String country="";
            		String zip_code="";
            		String email="";
            		String phone_number="";            		
            		String password="";
            		String avatar="";
            		String tick="";
					JSONObject data=null;
					if (obj != null && !obj.isNull("data") ) {
						result = Integer.parseInt(obj.getString("data"));
					}
//            		{"result":{"id":"1","username":"test1","password":"12345678","firstname":"Berk",
//            		"lastname":"Al","gender":"0","birthday":"05-05-1991","country":"romania","zipcode":"123",
//            		"email":"test1@hotmail.com","phonenumber":"1582254512","avatar":"uploads\/1\/avatar.png","usergrade":"1",
//            		"token":null},"data":1}
					if ( result>0 ) {
						data=obj.getJSONObject("result");
		
						id=Integer.parseInt(data.getString("id"));
						username=data.getString("username");
						password=data.getString("password");
						first_name=data.getString("firstname");
						last_name=data.getString("lastname");
						gender=Integer.parseInt(data.getString("gender"));
						birthday=data.getString("birthday");
						country=data.getString("country");
						zip_code=data.getString("zipcode");
						email=data.getString("email");
						phone_number=data.getString("phonenumber");
						avatar=data.getString("avatar");
						tick=data.getString("tick");
						
						if ( interfaceHttpRequest != null )
							interfaceHttpRequest.requestSuccess();
						SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
						SharedPreferences.Editor editor = sharedPreferences.edit();		
						editor.putInt("current_userid", id);
						editor.putString("username", username);
						editor.putString("first_name", first_name);
						editor.putString("last_name", last_name);
						editor.putString("birthday", birthday);
						editor.putInt("gender", gender);
						editor.putString("country", country);
						editor.putString("zipcode", zip_code);
						editor.putString("email",email);
						editor.putString("phonenumber", phone_number);
						editor.putString("password", password);
						editor.putString("avatar_image", avatar);
						editor.putString("tick", tick);
						editor.commit();				
					}
					else if (result==-1) {
						AlertUtil.messageAlert(_context, "Login In Error", "Username incorrect!");
					}
					else if (result==-2) {
						AlertUtil.messageAlert(_context, "Login In Error", "Password incorrect!");
					}
					else {
						if ( interfaceHttpRequest != null )
							interfaceHttpRequest.requestFailure("");
						if ( !obj.isNull("error_description") ) {
						}						
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
			params.put("username", name);
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
