package ueda.social.wishing.activity;

import java.util.ArrayList;

import ueda.social.wishing.R;
import ueda.social.wishing.adapter.Detailed_WishInfo_ListAdapter;
import ueda.social.wishing.http.Friend_search;
import ueda.social.wishing.http.Get_Wishes;
import ueda.social.wishing.http_request.Get_Wish_Result_Request;
import ueda.social.wishing.http_request.Search_Result_Request;
import ueda.social.wishing.model.User_Info;
import ueda.social.wishing.model.Userdata_DB_Helper;
import ueda.social.wishing.model.Wish_Info;
import ueda.social.wishing.wish_manage.DetailedWishActivity;
import ueda.social.wishing.wish_manage.NewWishActivity;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class WishListActivity extends Activity implements Get_Wish_Result_Request, OnItemClickListener, OnItemLongClickListener{

	private Context context;		
	private Get_Wish_Result_Request _get_wish_result=this;
	private int current_user_id=0;
	private ListView wish_list;
	private ArrayList<Wish_Info> my_wish=new ArrayList<Wish_Info>();
	private Bundle bndanimation_to_child,bndanimation_to_parent;
	
	private ProgressDialog progress_dg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wish_list);
		
		bndanimation_to_child = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation, R.anim.animation2).toBundle();
		bndanimation_to_parent = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation3, R.anim.animation4).toBundle();
		
		// get the action bar
		ActionBar actionBar = getActionBar();
		// Enabling Back navigation on Action Bar icon
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		context=this;		
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		current_user_id=sharedPreferences.getInt("current_userid", -1);	
		
		progress_dg=new ProgressDialog(context);
		progress_dg.setMessage("Loading data...");
		progress_dg.show();
		Get_Wishes get_wished=new Get_Wishes(context, String.valueOf(current_user_id), "", "", _get_wish_result);
		get_wished.onRun();	
		wish_list=(ListView)this.findViewById(R.id.wish_list_view);
		wish_list.setOnItemLongClickListener(this);
		wish_list.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wish_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.new_wish:
			Intent new_wish=new Intent(WishListActivity.this,NewWishActivity.class);
			startActivity(new_wish, bndanimation_to_child);
			finish();
			break;		
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}	

	@Override
	public void get_wish_requestFailure(String errMsg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void get_wish_requestSuccess(ArrayList<Wish_Info> wish_infos) {
		// TODO Auto-generated method stub
		progress_dg.dismiss();
		for (int i = 0; i < wish_infos.size(); i++) {
			if (!wish_infos.get(i).get_deleted().equals("1")) {
				my_wish.add(wish_infos.get(i));
			}
		}		
		wish_list.setAdapter(new Detailed_WishInfo_ListAdapter(WishListActivity.this, my_wish));			
	}
	@Override
	public boolean onNavigateUp() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(WishListActivity.this,SecondActivity.class);
		startActivity(intent,bndanimation_to_parent);
		finish();
		return true;
	}
	@Override
	public void onBackPressed(){
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(WishListActivity.this);
		SharedPreferences.Editor editor = sharedPreferences.edit();		
		
		editor.putInt("wish_id",Integer.parseInt( my_wish.get(position).get_wish_id()));
		editor.putString("wish_title", my_wish.get(position).get_title());
		editor.putString("wish_event", my_wish.get(position).get_event());
		editor.putString("wish_date", my_wish.get(position).get_wish_date());
		editor.putString("wish_product", my_wish.get(position).get_product_code());
		editor.putString("wish_price", my_wish.get(position).get_price());
		editor.putString("wish_description",my_wish.get(position).get_description());
		editor.putString("wish_image", my_wish.get(position).get_img());
		editor.putString("wish_shared_all", my_wish.get(position).get_visible_all());
		editor.putString("wish_shared", my_wish.get(position).get_visible_friend_ids());
		editor.commit();
		
		Intent intent=new Intent(WishListActivity.this,DetailedWishActivity.class);	
		intent.putExtra("to_detailed_wish", "wish_list");
		startActivity(intent, bndanimation_to_child);
		finish();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		
		return false;
	}
}
