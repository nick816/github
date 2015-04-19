package com.example.phonecalllog;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;

import com.example.phonecalllog.ConnectionDetector;

import com.example.phonecalllog.InterfaceAsyncHttpRequest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.ResponseHandlerInterface;

public abstract class AsyncHttpRequestSample implements InterfaceAsyncHttpRequest{

	public AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
	private final List<RequestHandle> requestHandles = new LinkedList<RequestHandle>();
	private String _headers, _body;
	
	protected Context _context;
	
	protected boolean isRunning = false; 
	
	protected boolean isDebuggable = true;
	
	public AsyncHttpRequestSample(Context context) {
		_context = context;
	}
	
	public List<RequestHandle> getRequestHandles() {
        return requestHandles;
    }

	@Override
    public void addRequestHandle(RequestHandle handle) {
        if (null != handle) {
            requestHandles.add(handle);
        }
    }

    public void onRun() {
    	ConnectionDetector cd = new ConnectionDetector(_context);
		if ( cd.isConnectingToInternet() ) {
			isRunning = true;
			RequestHandle handle = executeSample(getAsyncHttpClient(),
	                getDefaultURL(),
	                getRequestHeaders(),
	                getRequestEntity(),
	                getResponseHandler());
			if ( handle != null ) {
				addRequestHandle(handle);
			}
		}
    }

    public void onCancel() {
        asyncHttpClient.cancelRequests(_context, true);
    }

    public List<Header> getRequestHeadersList() {
        List<Header> headers = new ArrayList<Header>();
        String headersRaw = _headers == null ? null : _headers;

        if (headersRaw != null && headersRaw.length() > 3) {
            String[] lines = headersRaw.split("\\r?\\n");
            for (String line : lines) {
                try {
                    int equalSignPos = line.indexOf('=');
                    if (1 > equalSignPos) {
                        throw new IllegalArgumentException("Wrong header format, may be 'Key=Value' only");
                    }

                    String headerName = line.substring(0, equalSignPos).trim();
                    String headerValue = line.substring(1 + equalSignPos).trim();

                    headers.add(new BasicHeader(headerName, headerValue));
                } catch (Throwable t) {
                    Log.e("SampleParentActivity", "Not a valid header line: " + line, t);
                }
            }
        }
        return headers;
    }

    public Header[] getRequestHeaders() {
        List<Header> headers = getRequestHeadersList();
        return headers.toArray(new Header[headers.size()]);
    }

    public HttpEntity getRequestEntity() {
        if (isRequestBodyAllowed() && _body != null) {
            try {
                return new StringEntity(_body);
            } catch (UnsupportedEncodingException e) {
                Log.e("SampleParentActivity", "cannot create String entity", e);
            }
        }
        return null;
    }

    protected final void debugHeaders(String TAG, Header[] headers) {
        if (headers != null) {
            Log.d(TAG, "Return Headers:");
            StringBuilder builder = new StringBuilder();
            for (Header h : headers) {
                String _h = String.format(Locale.US, "%s : %s", h.getName(), h.getValue());
                Log.d(TAG, _h);
                builder.append(_h);
                builder.append("\n");
            }
        }
    }

    protected static String throwableToString(Throwable t) {
        if (t == null)
            return null;

        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    protected final void debugThrowable(String TAG, Throwable t) {
        if (t != null) {
            Log.e(TAG, "AsyncHttpClient returned error", t);
            Log.e(TAG, throwableToString(t));
        }
    }

    protected final void debugResponse(String TAG, String response) {
        if (response != null) {
            Log.d(TAG, "Response data:");
            Log.d(TAG, response);
        }
    }

    protected final void debugStatusCode(String TAG, int statusCode) {
        String msg = String.format(Locale.US, "Return Status Code: %d", statusCode);
        Log.d(TAG, msg);
    }

    public static int getContrastColor(int color) {
        double y = (299 * Color.red(color) + 587 * Color.green(color) + 114 * Color.blue(color)) / 1000;
        return y >= 128 ? Color.BLACK : Color.WHITE;
    }

    public boolean isCancelButtonAllowed() {
        return false;
    }

    public AsyncHttpClient getAsyncHttpClient() {
        return this.asyncHttpClient;
    }

    public void setAsyncHttpClient(AsyncHttpClient client) {
    	client.setTimeout(30000);
//    	client.setMaxConnections(10);
        this.asyncHttpClient = client;
    }
    
    public Drawable getLocalImage(String filename) {
		if ( filename != "" ) {
			File f = new File(filename);
		    if(f.exists())
		    { 
		    	return Drawable.createFromPath(filename);
		    }
		}
		return null;
	}

	@Override
	public ResponseHandlerInterface getResponseHandler() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDefaultURL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getDefaultValue() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isRequestHeadersAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRequestBodyAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RequestHandle executeSample(AsyncHttpClient client, String URL,
			Header[] headers, HttpEntity entity,
			ResponseHandlerInterface responseHandler) {
		// TODO Auto-generated method stub
		return null;
	}
}
