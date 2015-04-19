package ueda.social.wishing.activity;

import java.util.ArrayList;

import ueda.social.wishing.R;
import ueda.social.wishing.adapter.Message_ListAdapter;
import ueda.social.wishing.http.Delete_message;
import ueda.social.wishing.http.Message_Notifycation_Inbox;
import ueda.social.wishing.http_request.Delete_message_Request;
import ueda.social.wishing.http_request.Inbox_Result_Request;
import ueda.social.wishing.model.Message;
import ueda.social.wishing.model.User_Info;
import ueda.social.wishing.model.Userdata_DB_Helper;
import ueda.social.wishing.wish_manage.DetailedWishActivity;
import ueda.social.wishing.wish_manage.Detailed_Friend_WishActivity;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
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
import android.widget.ListView;

public class InboxActivity extends Activity implements Inbox_Result_Request,Delete_message_Request,OnItemClickListener{

	Bundle bndanimation_to_child,bndanimation_to_parent;
	private Context context;	
	private Inbox_Result_Request _search_result;	
	private int current_user_id=0;
//	private Button btn_new_message;
	private ListView message_list;
	private Message key_message=new Message();
	private Delete_message_Request  _delete_msg;	
	private ArrayList<Message> received_messages=new ArrayList<Message>();
	private ArrayList<String> sender_image_urls=new ArrayList<String>();
	ArrayList<User_Info> friends=new ArrayList<User_Info>();
	private Userdata_DB_Helper db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inbox);
		
		context=this;
		db=new Userdata_DB_Helper(this);
		friends=db.getAll_users();
		bndanimation_to_child = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation,R.anim.animation2).toBundle();
		bndanimation_to_parent = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation3,R.anim.animation4).toBundle();
		
		// get the action bar
		ActionBar actionBar = getActionBar();
		// Enabling Back navigation on Action Bar icon
		actionBar.setDisplayHomeAsUpEnabled(true);
		_search_result=this;
		_delete_msg=this;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		current_user_id = sharedPreferences.getInt("current_userid", -1);	
        
		key_message.set_user_id(String.valueOf(current_user_id));
//		key_message.set_msg_type("1");        
        message_list=(ListView)this.findViewById(R.id.message_list);
        Message_Notifycation_Inbox inbox=new Message_Notifycation_Inbox(context, key_message, _search_result);
        inbox.onRun();
        message_list.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inbox, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {		
		case R.id.new_message:
			Intent intent=new Intent(context, New_Message_Activity.class);
			startActivity(intent,bndanimation_to_child);				
			break;
		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onNavigateUp() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(InboxActivity.this,SecondActivity.class);
		startActivity(intent,bndanimation_to_parent);
		finish();
		return true;
	}
	@Override
	public void onBackPressed(){
		
	}

	@Override
	public void requestFailure(String errMsg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestSuccess(ArrayList<Message> messages) {
		// TODO Auto-generated method stub
		received_messages=messages;
		
		for (int i = 0; i < messages.size(); i++) {
			sender_image_urls.add("");		
		}
		message_list.setAdapter(new Message_ListAdapter(context, messages,sender_image_urls));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
		int notify_type,message_type;
		notify_type=Integer.parseInt(received_messages.get(position).get_notify_type());
		message_type=Integer.parseInt(received_messages.get(position).get_msg_type());
		if (message_type==1) {
			Intent intent= new Intent(context, Detailed_Message_Activity.class);
			intent.putExtra("sender_name", received_messages.get(position).get_username());
			intent.putExtra("msg_content", received_messages.get(position).get_content());
			intent.putExtra("friend_id", received_messages.get(position).get_friend_id());
			intent.putExtra("send_date", received_messages.get(position).get_date());
			intent.putExtra("send_time", received_messages.get(position).get_time());
			intent.putExtra("msg_id", received_messages.get(position).get_msg_id());
//			Toast.makeText(context, received_messages.get(position).get_friend_id(), Toast.LENGTH_SHORT).show();
			startActivity(intent,bndanimation_to_child);	
			finish();
		}
		else{
			switch (notify_type) {
			case 1:
				Intent intent= new Intent(context, Detailed_Invite_Activity.class);
				intent.putExtra("sender_name", received_messages.get(position).get_username());
				intent.putExtra("friend_id", received_messages.get(position).get_friend_id());
				intent.putExtra("send_date", received_messages.get(position).get_date());
				intent.putExtra("send_time", received_messages.get(position).get_time());
				intent.putExtra("msg_id", received_messages.get(position).get_msg_id());
				intent.putExtra("notify_type", received_messages.get(position).get_notify_type());
				startActivity(intent,bndanimation_to_child);
				finish();
				break;
			case 2:
				//receive invite accept
				Intent intent2=new Intent(context,Modify_Friend_Category_Activity.class);
				intent2.putExtra("modify_type", 1);
				intent2.putExtra("sender_name", received_messages.get(position).get_username());
				intent2.putExtra("friend_id", received_messages.get(position).get_friend_id());
				intent2.putExtra("msg_id", received_messages.get(position).get_msg_id());
				startActivity(intent2,bndanimation_to_child);		
				finish();
				break;
			case 3:
				//receive invite reject
				Delete_message delete_message=new Delete_message(context, received_messages.get(position).get_msg_id(), _delete_msg);
				delete_message.onRun();		
				break;
			case 4:
				//shared Wish
//				Intent intent3=new Intent(context,Detailed_Friend_WishActivity.class);
//				intent3.putExtra("to_detailed_friend_wish", "inbox");
//				intent3.putExtra("friend_wish_id", received_messages.get(position).get_friend_wish_id());
//				startActivity(intent3, bndanimation_to_child);
//				finish();
			default:
				break;
			}		
		}	
	}

	@Override
	public void delete_message_requestFailure(String errMsg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete_message_requestSuccess() {
		// TODO Auto-generated method stub		
		Intent intent=new Intent(InboxActivity.this,SecondActivity.class);
		startActivity(intent,bndanimation_to_parent);
		finish();
	}
}
