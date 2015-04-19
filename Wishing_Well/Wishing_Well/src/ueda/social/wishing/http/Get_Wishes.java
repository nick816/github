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

import ueda.social.wishing.http_request.Get_Wish_Result_Request;
import ueda.social.wishing.model.Wish_Info;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

public class Get_Wishes extends AsyncHttpRequestSample {
	
	private static final String LOG_TAG = "Get_wishes";
	String user_id;	
	String picked;
	String deleted;
	Get_Wish_Result_Request get_wish_result_request;
	public Get_Wishes(Context context,String user_id,String picked,String deleted, Get_Wish_Result_Request gw) {
		super(context);
		
		this.user_id=user_id;	
		this.picked=picked;
		this.deleted=deleted;
		
		this.get_wish_result_request= gw;
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
					if (obj != null && !obj.isNull("result") ) {
						data = obj.getJSONArray("result");
					}
					if ( data != null && data.length() >= 0 ) {
						
						Log.d("Get wish state", String.valueOf(data.length())+"found!");
						ArrayList<Wish_Info> search_result=new ArrayList<Wish_Info>();						
						for (int i = 0; i < data.length(); i++) {
							JSONObject json_temp = data.getJSONObject(i);
							Wish_Info temp=new Wish_Info();
							temp.set_deleted(json_temp.getString("deleted"));
							temp.set_description(json_temp.getString("descript"));
							temp.set_event(json_temp.getString("wish_option"));
							temp.set_img(json_temp.getString("img_name"));
							temp.set_picked(json_temp.getString("picked"));
							temp.set_price(json_temp.getString("price"));
							temp.set_product_code(json_temp.getString("product_code"));
							temp.set_time(json_temp.getString("event_time"));
							temp.set_title(json_temp.getString("title"));
							temp.set_user_id(json_temp.getString("user_id"));
							temp.set_wish_id(json_temp.getString("wish_id"));
							temp.set_visible_all(json_temp.getString("visable_all"));
							temp.set_visible_friend_ids(json_temp.getString("visable_friend_ids"));
							temp.set_wish_date(json_temp.getString("event_date"));
							search_result.add(temp);
						}
					
						if (get_wish_result_request != null ){
							Log.d(LOG_TAG, "Success!");
							get_wish_result_request.get_wish_requestSuccess(search_result);
						}
					}					
					else {
						if (get_wish_result_request != null )
							get_wish_result_request.get_wish_requestFailure("");
						if ( !obj.isNull("error_description") ) {
						}						
					}
				} catch (JSONException e) {
					e.printStackTrace();
					Log.d("Get wish state", "Failed!");
				}
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
            	if ( get_wish_result_request != null )
					get_wish_result_request.get_wish_requestFailure("");
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
		return HttpConstants.GET_WISH;
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
			params.put("picked", picked);
			params.put("deleted", deleted);
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
