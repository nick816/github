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

import ueda.social.wishing.http_request.Friend_GroupingHttpRequest;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

public class Friend_Grouping extends AsyncHttpRequestSample {
	
	private static final String LOG_TAG = "Friend_grouping";
	String user_id;
	String friend_id;
	String category_id;
	
	Friend_GroupingHttpRequest friend_groupingHttpRequest;
	
	public Friend_Grouping(Context context, String user_id,String friend_id,String category_id,Friend_GroupingHttpRequest fg) {
		super(context);
		this.user_id=user_id;
		this.friend_id=friend_id;
		this.category_id=category_id;
		this.friend_groupingHttpRequest=fg;
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
						Log.d(LOG_TAG, "User was successfully registered");
						if ( friend_groupingHttpRequest != null ){
							friend_groupingHttpRequest.friend_grouping_requestSuccess();
						}						
					}
					else if (result==-1) {						
					}
					else {
						if ( friend_groupingHttpRequest != null )
							friend_groupingHttpRequest.friend_grouping_requestFailure("");
						if ( !obj.isNull("error_description") ) {
						}						
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
            	if ( friend_groupingHttpRequest != null )
            		friend_groupingHttpRequest.friend_grouping_requestFailure("");
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
		return HttpConstants.FRIEND_GROUPING;
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
			params.put("category_id", category_id);						
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
