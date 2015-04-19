package ueda.social.wishing.activity;

import java.util.ArrayList;

import ueda.social.wishing.R;
import ueda.social.wishing.adapter.Detailed_Friend_WishInfo_ListAdapter;
import ueda.social.wishing.http.Change_profile;
import ueda.social.wishing.http.Friend_search;
import ueda.social.wishing.http.Get_Friend_Wishes;
import ueda.social.wishing.http.HttpConstants;
import ueda.social.wishing.http_request.Change_profileHttpRequest;
import ueda.social.wishing.http_request.Get_Friend_Wish_Result_Request;
import ueda.social.wishing.http_request.Search_Result_Request;
import ueda.social.wishing.image.SmartImageView;
import ueda.social.wishing.model.User_Info;
import ueda.social.wishing.model.Userdata_DB_Helper;
import ueda.social.wishing.model.Wish_Info;
import ueda.social.wishing.wish_manage.Detailed_Friend_WishActivity;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class SecondActivity extends Activity implements Search_Result_Request,Get_Friend_Wish_Result_Request,Change_profileHttpRequest,OnItemClickListener, OnItemSelectedListener{

	private Search_Result_Request _search_result_request=this;
	private Get_Friend_Wish_Result_Request _get_friend_wish=this;
	private Change_profileHttpRequest change_profile_HttpRequest=this;	
	
	private TextView username,tick;
	private ListView friend_wish_list;
	private SmartImageView profile_image;
	private Button btn_tick_edit;
	private EditText tick_edit;
	private Spinner event_selector;
	
	private Context context;
	private Userdata_DB_Helper db;
	
	private int current_user_id,current_gender,is_like;
	private String current_username,current_tick,current_avarta;
	private String[] events={"Birthday","X mas","Wedding","Anniversary","Valentine day","House warming","Leaving present","New baby","Other"};
	private ArrayList<Wish_Info> friend_wishes=new ArrayList<Wish_Info>();
	private ArrayList<Wish_Info> selected_wish=new ArrayList<Wish_Info>();
	
	private ProgressDialog progress_dg;
	
	private Bundle bndanimation_to_child,bndanimation_to_parent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		
		context=this;
		
		bndanimation_to_child = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation, R.anim.animation2).toBundle();
		bndanimation_to_parent = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation3, R.anim.animation4).toBundle();
		
		db=new Userdata_DB_Helper(context);
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		current_user_id=sharedPreferences.getInt("current_userid", -1);	
		current_username=sharedPreferences.getString("username", "");
		current_tick=sharedPreferences.getString("tick", "");
		current_avarta=sharedPreferences.getString("avatar_image", "");
		current_gender=sharedPreferences.getInt("gender", -1);
		is_like=sharedPreferences.getInt("is_like", -1);
		
		db.delete_all();
		
		profile_image=(SmartImageView)this.findViewById(R.id.first_profile_image);
		tick=(TextView)this.findViewById(R.id.first_tick);
		username=(TextView)this.findViewById(R.id.first_username);
		friend_wish_list=(ListView)this.findViewById(R.id.first_wish_list);
		btn_tick_edit=(Button)this.findViewById(R.id.button1);
		tick_edit=(EditText)this.findViewById(R.id.edit_tick);		
		event_selector=(Spinner)this.findViewById(R.id.friend_event_spinner);
		
		username.setText(current_username);
		tick.setText(current_tick);
		if (!current_avarta.equals("")) {
			profile_image.setNewImageUrl(HttpConstants.PROFILE_IMAGE+current_avarta);
		}
		else{
			profile_image.setImageResource(R.drawable.empty_user);
		}
		
		progress_dg=new ProgressDialog(context);
		progress_dg.setMessage("Loading data...");
		progress_dg.show();
		
		Friend_search get_friends=new Friend_search(context, String.valueOf(current_user_id), "", "1", _search_result_request);
		get_friends.onRun();	
		
		tick_edit.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					tick.setText(tick_edit.getText().toString());
					tick_edit.setVisibility(View.INVISIBLE);
					tick.setVisibility(View.VISIBLE);
		            InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SecondActivity.this);
					SharedPreferences.Editor editor = sharedPreferences.edit();		
					editor.putString("tick", tick.getText().toString());
					editor.commit();
		            Change_profile change_tick=new Change_profile(context, String.valueOf(current_user_id), "", "", current_gender, "", "", "", "", "", "", "", "",tick_edit.getText().toString(), change_profile_HttpRequest);
		            change_tick.onRun();
		            return true;
		        }
				return false;
			}
		});
		friend_wish_list.setOnItemClickListener(this);
		btn_tick_edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tick.setVisibility(View.INVISIBLE);
				tick_edit.setVisibility(View.VISIBLE);
				tick_edit.setText(tick.getText());
			}
		});
		event_selector.setOnItemSelectedListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		switch (id) {
		
		case R.id.invite_friend:	
			Intent invite_select=new Intent(SecondActivity.this,InviteSelectActivity.class);
			startActivity(invite_select, bndanimation_to_child);	
			finish();
			break;
		case R.id.inbox:
			Intent inbox=new Intent(SecondActivity.this,InboxActivity.class);
			startActivity(inbox,bndanimation_to_child);
			finish();
			break;
		case R.id.address:
			Intent address=new Intent(SecondActivity.this, AddressActivity.class);
			startActivity(address, bndanimation_to_child);
			finish();
			break;
		case R.id.help:
			Intent help=new Intent(SecondActivity.this,HelpActivity.class);
			startActivity(help, bndanimation_to_child);
			finish();
			break;
		case R.id.wishlist:
			Intent wish=new Intent(SecondActivity.this,WishListActivity.class);
			startActivity(wish, bndanimation_to_child);
			finish();
			break;
		case R.id.profile:
			Intent profile=new Intent(SecondActivity.this,Profile_Activity.class);
			startActivity(profile, bndanimation_to_child);
			finish();
			break;
		case R.id.logout:
			Intent log_out=new Intent(SecondActivity.this, LoginActivity.class);
			startActivity(log_out);
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void requestFailure(String errMsg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestSuccess(ArrayList<User_Info> user_infos) {
		// TODO Auto-generated method stub		
		for (int i = 0; i < user_infos.size(); i++) {
			db.add_user(user_infos.get(i));
		}
		Get_Friend_Wishes get_friend_wish=new Get_Friend_Wishes(context, String.valueOf(current_user_id), _get_friend_wish);
		get_friend_wish.onRun();
	}

	@Override
	public void get_wish_requestFailure(String errMsg) {
		// TODO Auto-generated method stub
		progress_dg.dismiss();
	}

	@Override
	public void get_wish_requestSuccess(ArrayList<Wish_Info> wish_infos) {
		// TODO Auto-generated method stub
		progress_dg.dismiss();
		if (wish_infos.size()==0) {
			Toast.makeText(context, "No Wishes", Toast.LENGTH_SHORT).show();
		}
		else{
			friend_wishes=wish_infos;			
		}
		friend_wish_list.setAdapter(new Detailed_Friend_WishInfo_ListAdapter(context, friend_wishes));		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, final int position,
			long id) {
		// TODO Auto-generated method stub
		final int user_id=Integer.parseInt(friend_wishes.get(position).get_user_id());
        User_Info temp=new User_Info();
        temp=db.get_user(user_id);				
		Intent intent=new Intent(SecondActivity.this,Detailed_Friend_WishActivity.class);
		intent.putExtra("image_url", temp.get_avarta());
		intent.putExtra("wish_id", friend_wishes.get(position).get_wish_id());
		intent.putExtra("name", temp.get_username());
		intent.putExtra("title", friend_wishes.get(position).get_title());
		intent.putExtra("event", events[Integer.parseInt(friend_wishes.get(position).get_event())-1]);
		intent.putExtra("date", friend_wishes.get(position).get_wish_date());
		intent.putExtra("code", friend_wishes.get(position).get_product_code());
		intent.putExtra("price", friend_wishes.get(position).get_price());
		intent.putExtra("description", friend_wishes.get(position).get_description());
		intent.putExtra("like_count", String.valueOf(friend_wishes.get(position).get_like_count()));
		intent.putExtra("is_like", friend_wishes.get(position).get_is_like());
		intent.putExtra("wish_image", friend_wishes.get(position).get_img());
		startActivity(intent,bndanimation_to_child);
		finish();
	}

	@Override
	public void change_profile_requestFailure(String errMsg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void change_profile_requestSuccess() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if (position==0) {
			friend_wish_list.setAdapter(new Detailed_Friend_WishInfo_ListAdapter(context, friend_wishes));
		}
		else{
			selected_wish=new ArrayList<Wish_Info>();
			for (int i = 0; i < friend_wishes.size(); i++) {
				if (Integer.parseInt(friend_wishes.get(i).get_event())==position) {
					selected_wish.add(friend_wishes.get(i));
				}
			}
			friend_wish_list.setAdapter(new Detailed_Friend_WishInfo_ListAdapter(context, selected_wish));
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}
