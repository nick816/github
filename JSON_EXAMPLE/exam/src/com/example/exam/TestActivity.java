package com.example.exam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.exam.smart.SmartImageView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TestActivity extends Activity {

	TextView desView, audioView, titleView;
	SmartImageView imgView;
	ListView listView = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
		listView = (ListView)findViewById(R.id.listView);
		
		new HttpAsyncTask().execute("http://192.168.0.192/zuumbli_api/index.php/api/getJsonMedias");
	}
	
	public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {
 
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
 
            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
 
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
 
            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
 
        } catch (Exception e) {
            Log.e("InputStream", e.getLocalizedMessage());
        }
 
        return result;
    }
 
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
 
        inputStream.close();
        return result;
 
    }
 
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) 
                return true;
            else
                return false;   
    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
 
            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
            
            try {
				JSONObject items = new JSONObject(result);
				JSONArray array = items.optJSONArray("itemid");
				List list = new ArrayList();
				for ( int i = 0; i < array.length(); i++ ) {
					JSONObject obj = array.getJSONObject(i);
					Item item = new Item();
					item.title = obj.getString("title");
					item.photo_url = obj.getString("photo_url");
					item.audio_url = obj.getString("audio_url");
					item.description = obj.getString("description");
					list.add(item);
				}
				listView.setAdapter(new ImageAdapter(TestActivity.this, list));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
       }
    }
    
//    @SuppressLint("NewApi")
	public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        List items;

        public ImageAdapter(Context c, List list) {
            mContext = c;
            items = list;
        }

        public int getCount() {
            return items.size();
        }

        public Object getItem(int position) {
            return items.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
//        @SuppressLint("NewApi")
		public View getView(int position, View convertView, ViewGroup parent) {
        	View view = convertView;
        	if (view == null) {
        		   LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        		   view = inflater.inflate(R.layout.item, parent, false);
        	}
        	Button button = (Button)view.findViewById(R.id.button);
        	audioView = (TextView)view.findViewById(R.id.textView1);
        	titleView = (TextView)view.findViewById(R.id.title);
        	desView = (TextView)view.findViewById(R.id.description);
        	imgView = (SmartImageView)view.findViewById(R.id.imageView1);
        	Item item = (Item)getItem(position);
        	titleView.setText(item.title);
        	desView.setText(item.description);
        	imgView.setImageUrl(item.photo_url);
        	new ImageAsyncTask(imgView).execute(item.photo_url);
        	return view;
        }
        
        private class ImageAsyncTask extends AsyncTask<String, Void, String> {
        	Bitmap bmp = null;
        	ImageView img = null;
        	public ImageAsyncTask(ImageView img) {
				// TODO Auto-generated constructor stub
        		this.img = img;
			}
            @Override
            protected String doInBackground(String... urls) {
     
            	try {
	                InputStream in = new URL(urls[0]).openStream();
	                bmp = BitmapFactory.decodeStream(in);
	            } catch (Exception e) {
	               // log error
	            }
	            return null;
            }
            // onPostExecute displays the results of the AsyncTask.
            @Override
            protected void onPostExecute(String result) {
                Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
                
                img.setImageBitmap(bmp);
           }
        }
    }
}
