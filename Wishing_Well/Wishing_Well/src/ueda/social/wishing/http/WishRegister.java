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
import ueda.social.wishing.http_request.WishRegistHttpRequest;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

public class WishRegister extends AsyncHttpRequestSample {
	///Descript:	 wish register 
	//Param: 	(user_id, wish_option, title, img_name, descript, product_code, price, event_date, event_time, visable_all, visable_friend_ids)
	//Return:	successful 1, failed 0;
	private static final String LOG_TAG = "Change profile";
	String user_id;
	String wish_option;
	String title;
	String descript;
	String product_code;
	String price;
	String event_date;
	String event_time;
	String visible_all;
	String visible_friend_ids;	
	String picked;
	String img_name;
	
	WishRegistHttpRequest wish_regist_HttpRequest;
	
	public WishRegister(Context context, String user_id, String wish_option,String title,String descript,String produc_code,String price,String event_date,
			String event_time,String visible_all,String visible_friend_ids,String picked,String filePath, WishRegistHttpRequest wr) {
		super(context);
		this.user_id=user_id;
		this.wish_option=wish_option;
		this.title=title;
		this.descript=descript;
		this.product_code=produc_code;
		this.price=price;
		this.event_date=event_date;
		this.event_time=event_time;
		this.visible_all=visible_all;
		this.visible_friend_ids=visible_friend_ids;
		this.picked=picked;
		this.img_name=filePath;
		this.wish_regist_HttpRequest=wr;
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
						Log.d("Change profile Request Response", "Success");
						if ( wish_regist_HttpRequest != null ){
							wish_regist_HttpRequest.wish_regist_requestSuccess();
						}						
					}					
					else {
						if ( wish_regist_HttpRequest != null )
							wish_regist_HttpRequest.wish_regist_requestFailure("");
						if ( !obj.isNull("error_description") ) {
						}						
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
            	if ( wish_regist_HttpRequest != null )
            		wish_regist_HttpRequest.wish_regist_requestFailure("");
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
		return HttpConstants.WISH_REGIST;
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

		params.put("user_id", user_id);
		params.put("wish_option", wish_option);
		params.put("title", title);
		params.put("event_date", event_date);
		params.put("event_time", event_time);
		params.put("descript", descript);
		params.put("product_code", product_code);
		params.put("price", price);
		params.put("visable_all", visible_all);
		params.put("picked", picked);
		params.put("visable_friend_ids", visible_friend_ids);
		
		if ( img_name.equals("") ) {
			params.put("img_name", "");
		}
		else{
			File photo_200 = new File(img_name);
		    try {
		        params.put("img_name", photo_200);
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
