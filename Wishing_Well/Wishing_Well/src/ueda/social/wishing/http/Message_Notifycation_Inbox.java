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

import ueda.social.wishing.http_request.Inbox_Result_Request;
import ueda.social.wishing.model.Message;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

public class Message_Notifycation_Inbox extends AsyncHttpRequestSample {
	
	private static final String LOG_TAG = "Message_Notification_Inbox";
	Message search_key;	
	Inbox_Result_Request inbox_result_request;
	
	public Message_Notifycation_Inbox(Context context,Message key,Inbox_Result_Request m) {
		super(context);
		
		this.search_key=key;		
		this.inbox_result_request= m;
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
					if ( data != null && data.length() > 0 ) {
						
						Log.d(LOG_TAG, String.valueOf(data.length())+"found!");
						ArrayList<Message> search_result=new ArrayList<Message>();						
						for (int i = 0; i < data.length(); i++) {
							JSONObject json_temp = data.getJSONObject(i);
							Message temp=new Message();
							temp.set_msg_id(json_temp.getString("msg_id"));
							temp.set_date(json_temp.getString("mdate"));
							temp.set_time(json_temp.getString("mtime"));
							temp.set_user_id(json_temp.getString("user_id"));
							temp.set_msg_type(json_temp.getString("msgtype"));
							temp.set_notify_type(json_temp.getString("notifytype"));
							temp.set_state(json_temp.getString("state"));
							temp.set_friend_id(json_temp.getString("friend_id"));
							temp.set_username(json_temp.getString("username"));
							temp.set_content(json_temp.getString("content"));
							search_result.add(temp);
						}
						if (inbox_result_request != null ){							
							inbox_result_request.requestSuccess(search_result);
						}
					}
					else if (data.length()==0) {
					}
					else {
						if ( inbox_result_request != null )
							inbox_result_request.requestFailure("");
						if ( !obj.isNull("error_description") ) {
						}						
					}
				} catch (JSONException e) {
					e.printStackTrace();
					Log.d(LOG_TAG, "Failed!");
				}
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
            	if ( inbox_result_request != null )
            		inbox_result_request.requestFailure("");
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
		return HttpConstants.INBOX;
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
			params.put("user_id", search_key.get_user_id());			
			params.put("msgtype", search_key.get_msg_type());
			params.put("notifytype", search_key.get_notify_type());
			params.put("state", search_key.get_state());
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
