package ueda.social.wishing.http;

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

import ueda.social.wishing.http_request.Search_Result_Request;
import ueda.social.wishing.model.User_Info;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

public class Friend_search extends AsyncHttpRequestSample {
	
	private static final String LOG_TAG = "Username_Email_search";
	String user_id;
	String category;
	String state;
	Search_Result_Request search_result_request;
	public Friend_search(Context context,String user_id,String category,String state, Search_Result_Request s) {
		super(context);
		
		this.user_id=user_id;	
		this.category=category;
		this.state=state;
		this.search_result_request= s;
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
					ArrayList<User_Info> search_result=new ArrayList<User_Info>();	
					if ( data != null && data.length() > 0 ) {
						
						Log.d("Search result state", String.valueOf(data.length())+"found!");											
						for (int i = 0; i < data.length(); i++) {
							JSONObject json_temp = data.getJSONObject(i);
							User_Info temp=new User_Info();
							temp.set_birthday(json_temp.getString("birthday"));
							temp.set_username(json_temp.getString("username"));
							temp.set_email(json_temp.getString("email"));
							temp.set_user_id(json_temp.getString("id"));
							temp.set_country(json_temp.getString("country"));
							temp.set_firstname(json_temp.getString("firstname"));
							temp.set_gender(Integer.parseInt(json_temp.getString("gender")));
							temp.set_lastname(json_temp.getString("lastname"));
							temp.set_zipcode(json_temp.getString("zipcode"));
							temp.set_category(json_temp.getString("category_id"));
							temp.set_phonenumber(json_temp.getString("phonenumber"));
							temp.set_avarta(json_temp.getString("avatar"));
							search_result.add(temp);
						}
					
						if (search_result_request != null ){
							search_result_request.requestSuccess(search_result);
						}
					}
					else if (data.length()==0) {
						search_result_request.requestSuccess(search_result);
					}
					else {
						if (search_result_request != null )
							search_result_request.requestFailure("");
						if ( !obj.isNull("error_description") ) {
						}						
					}
				} catch (JSONException e) {
					e.printStackTrace();
					Log.d("Search result state", "Failed!");
				}
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
            	if ( search_result_request != null )
					search_result_request.requestFailure("");
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
		return HttpConstants.FRIEND_LIST;
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
			params.put("user_id", user_id);
			params.put("category_id",category);
			params.put("state", state);			
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
	
	public List<Header> getRequestHeadersList() {
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        headers.add(new BasicHeader("Data-Type", "json"));
        headers.add(new BasicHeader("Accept", "application/json"));
        return headers;
    }

}
