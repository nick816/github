package ueda.social.wishing.wish_manage;

import java.util.ArrayList;

import ueda.social.wishing.R;
import ueda.social.wishing.activity.InboxActivity;
import ueda.social.wishing.activity.WishListActivity;
import ueda.social.wishing.adapter.Detailed_Userinfo_ListAdapter;
import ueda.social.wishing.http.Delete_Wish;
import ueda.social.wishing.http.HttpConstants;
import ueda.social.wishing.http_request.Delete_wish_Request;
import ueda.social.wishing.image.SmartImageView;
import ueda.social.wishing.model.User_Info;
import ueda.social.wishing.model.Userdata_DB_Helper;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailedWishActivity extends Activity implements Delete_wish_Request{

	private Bundle bndanimation_to_child,bndanimation_to_parent;
	private TextView title,event,date,product_code,price,description;
	private SmartImageView wish_image;
	private ListView sharedFriends;
	private String image_Url,shared_all,shared;
	private String[] events={"Birthday","X mas","Wedding","Anniversary","Valentine day","House warming","Leaving present","New baby","Other"};
	private ArrayList<User_Info> friends=new ArrayList<User_Info>();
	private ArrayList<String> shared_ids=new ArrayList<String>();
	private Userdata_DB_Helper db;
	private Delete_wish_Request delete_wish=this;
	private String wish_id;
	private String from_where;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailed_wish);
		
		db=new Userdata_DB_Helper(this);
		friends=db.getAll_users();
		bndanimation_to_child = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation, R.anim.animation2).toBundle();
		bndanimation_to_parent = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation3, R.anim.animation4).toBundle();
		
		Bundle bundle=getIntent().getExtras();
		from_where=bundle.getString("to_detailed_wish");		
		
		// get the action bar
		ActionBar actionBar = getActionBar();
		// Enabling Back navigation on Action Bar icon
		actionBar.setDisplayHomeAsUpEnabled(true);		
		
		title=(TextView)this.findViewById(R.id.wish_title_detail);
		event=(TextView)this.findViewById(R.id.wish_event_detail);
		date=(TextView)this.findViewById(R.id.wish_date_detail);
		product_code=(TextView)this.findViewById(R.id.wish_product_detail);
		price=(TextView)this.findViewById(R.id.wish_price_detail);
		description=(TextView)this.findViewById(R.id.wish_desc_detail);
		wish_image=(SmartImageView)this.findViewById(R.id.wish_image_detail);
		sharedFriends=(ListView)this.findViewById(R.id.wish_share_friends);		
		
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DetailedWishActivity.this);
//		editor.putString("wish_id", my_wish.get(position).get_wish_id());
//		editor.putString("wish_title", my_wish.get(position).get_title());
//		editor.putString("wish_event", my_wish.get(position).get_event());
//		editor.putString("wish_date", my_wish.get(position).get_wish_date());
//		editor.putString("wish_product", my_wish.get(position).get_product_code());
//		editor.putString("wish_price", my_wish.get(position).get_price());
//		editor.putString("wish_description",my_wish.get(position).get_description());
//		editor.putString("wish_image", my_wish.get(position).get_img());
//		editor.putString("wish_shared_all", my_wish.get(position).get_visible_all());
//		editor.putString("wish_shared", my_wish.get(position).get_visible_friend_ids());
		if (from_where.equals("inbox")) {
			wish_id=bundle.getString("friend_wish_id");
		}
		else{
			wish_id=String.valueOf(sharedPreferences.getInt("wish_id", -1));
		}		
		title.setText(sharedPreferences.getString("wish_title",""));
		event.setText(events[Integer.parseInt(sharedPreferences.getString("wish_event",""))-1]);
		date.setText(sharedPreferences.getString("wish_date",""));
		product_code.setText(sharedPreferences.getString("wish_product",""));
		price.setText(sharedPreferences.getString("wish_price",""));
		description.setText(sharedPreferences.getString("wish_description",""));
		image_Url=sharedPreferences.getString("wish_image","");
		shared_all=sharedPreferences.getString("wish_shared_all","");
		shared=sharedPreferences.getString("wish_shared","");
		
		wish_image.setImageUrl(HttpConstants.PROFILE_IMAGE+image_Url);

		if (shared_all.equals("1")) {
			sharedFriends.setAdapter(new Detailed_Userinfo_ListAdapter(this, friends));
		}
		else{
			friends=new ArrayList<User_Info>();
			shared_ids=get_ids(shared.substring(1,shared.length()-1));
			for (int i = 0; i < shared_ids.size(); i++) {
				friends.add(db.get_user(Integer.parseInt(shared_ids.get(i))));
			}
			sharedFriends.setAdapter(new Detailed_Userinfo_ListAdapter(this, friends));
		}
		
	}
	public ArrayList<String> get_ids(String ids){
		int j=0;
		boolean flag=false;
		ArrayList<String> ids_temp=new ArrayList<String>();
		if (ids.length()==1) {
			ids_temp.add(ids);
			
		}else{
			for (int i = 0; i < ids.length(); i++) {
				String temp=ids.substring(i, i+1);
				if (temp.equals(",")) {
					flag=true;
				}
				else{
					j++;
					flag=false;
				}
				if (flag) {
					String temp_id=ids.substring(i-j,i);
					Log.d("gfjioghre", temp_id);
					j=0;
					flag=false;
					ids_temp.add(temp_id);
				}
			}
		}
		return ids_temp;
	}
	@Override
	public boolean onNavigateUp() {
		// TODO Auto-generated method stub
		if (from_where.equals("inbox")){
			Intent intent=new Intent(DetailedWishActivity.this,InboxActivity.class);
			startActivity(intent,bndanimation_to_parent);
			finish();
		}else{
			Intent intent=new Intent(DetailedWishActivity.this,WishListActivity.class);
			startActivity(intent,bndanimation_to_parent);
			finish();
		}
		
		return true;
	}
	@Override
	public void onBackPressed(){
		
	}
//}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detailed_wish, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.edit_wish) {
			Intent intent=new Intent(DetailedWishActivity.this, EditWishActivity.class);
			startActivity(intent, bndanimation_to_child);
			finish();
			return true;
		}
		else if(id==R.id.delete_wish){
			Delete_Wish delete=new Delete_Wish(DetailedWishActivity.this, wish_id, delete_wish);
			delete.onRun();
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void delete_wish_requestFailure(String errMsg) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void delete_wish_requestSuccess() {
		// TODO Auto-generated method stub
		Toast.makeText(DetailedWishActivity.this, "Present Deleted!", Toast.LENGTH_SHORT).show();
		Intent intent=new Intent(DetailedWishActivity.this, WishListActivity.class);
		startActivity(intent, bndanimation_to_parent);
		finish();
	}
}
