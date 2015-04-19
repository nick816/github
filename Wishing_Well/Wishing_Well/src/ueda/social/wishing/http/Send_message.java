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

import ueda.social.wishing.http_request.Send_message_Request;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

public class Send_message extends AsyncHttpRequestSample {
	
	private static final String LOG_TAG = "Send_Message";
	String user_id;
	String friend_id;
	String content;
	String msgtype;
	String notifytype;
	
	Send_message_Request send_message_request;
	
	public Send_message(Context context, String user_id,String friend_id, String content,String msgtype,String notifytype, Send_message_Request sm) {
		super(context);
		this.user_id=user_id;
		this.friend_id = friend_id;
		this.content = content;
		this.msgtype = msgtype;
		this.notifytype=notifytype;
		this.send_message_request=sm;
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
					if ( result>0 ) {
						Log.d("Send Message", "Success!");
						if ( send_message_request != null )
							send_message_request.send_message_requestSuccess();						
					}
					else {
						if ( send_message_request != null )
							send_message_request.send_message_requestFailure("");
						if ( !obj.isNull("error_description") ) {
						}						
					}
				} catch (JSONException e) {
					e.printStackTrace();
					Log.d("Send Message", "Failed!");
				}
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
            	if ( send_message_request != null )
            		send_message_request.send_message_requestFailure("");
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
		return HttpConstants.SEND_MESSAGE;
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
			params.put("friend_id", friend_id);
			params.put("content", content);
			params.put("msgtype", msgtype);
			params.put("notifytype", notifytype);
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
