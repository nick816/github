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

import ueda.social.wishing.http_request.ForgetHttpRequest;
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

public class Forget_password extends AsyncHttpRequestSample {
	
	private static final String LOG_TAG = "forget";
	String email;
	
	ForgetHttpRequest interfaceHttpRequest;
	
	public Forget_password(Context context, String email, ForgetHttpRequest i) {
		super(context);
		this.email = email;
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
            		String result = "";
            		int id = 0;
            		String password="";
            		String avatar="";
					JSONObject data=null;
					if (obj != null && !obj.isNull("data") ) {
						result = obj.getString("data");
//						data=obj.getJSONObject("result");
//						result=Integer.parseInt(data.getString("data"));
//						password=data.getString("password");						
					}
					if ( result.equals("true") ) {
//						data=obj.getJSONObject("result");
//						id=Integer.parseInt(data.getString("id"));
//						password=data.getString("password");	
//						avatar=data.getString("avatar");
						if ( interfaceHttpRequest != null )
							interfaceHttpRequest.requestForgetSuccess();
//						SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
//						SharedPreferences.Editor editor = sharedPreferences.edit();		
//						editor.putInt("current_userid", id);
//						editor.putString("password", password);
//						editor.putString("avatar_image", avatar);
//						editor.commit();				
					}
					else if(result.equals("-1")){
						AlertUtil.messageAlert(_context, "Forget Password", "Email address not exist!");
						if ( interfaceHttpRequest != null )
							interfaceHttpRequest.requestForgetFailure("");						
					}
					else {
						if ( interfaceHttpRequest != null )
							interfaceHttpRequest.requestForgetFailure("");
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
					interfaceHttpRequest.requestForgetFailure("");
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
		return HttpConstants.FORGET_PASSWORD;
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
			params.put("email", email);			
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
