package ueda.social.wishing.activity;

import java.util.ArrayList;

import ueda.social.wishing.R;
import ueda.social.wishing.http.Send_message;
import ueda.social.wishing.http_request.Send_message_Request;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class New_Message_Activity extends Activity implements Send_message_Request,OnItemSelectedListener{

	private int current_user_id=0,friend_id;
	private String content;
	private String receiver_id;
	private ArrayList<User_Info> friends=new ArrayList<User_Info>();
	private ArrayList<String> friend_names=new ArrayList<String>();
	private Button btn_send;
	private Spinner receiver;
	private EditText message_content;
	Bundle bndanimation_to_child,bndanimation_to_parent;
	private Send_message_Request _send;
	private Userdata_DB_Helper db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_message_activity);
		
		bndanimation_to_child = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation,R.anim.animation2).toBundle();
		bndanimation_to_parent = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation3,R.anim.animation4).toBundle();
		
		// get the action bar
		ActionBar actionBar = getActionBar();
		// Enabling Back navigation on Action Bar icon
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		db=new Userdata_DB_Helper(this);
		_send=this;
		friends=db.getAll_users();
		
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		current_user_id=sharedPreferences.getInt("current_userid", -1);
		friend_id=sharedPreferences.getInt("friend_db_id", -1);
//		Toast.makeText(New_Message_Activity.this, String.valueOf(friend_id), Toast.LENGTH_SHORT).show();
		for (int i = 0; i < friends.size(); i++) {
			friend_names.add(friends.get(i).get_username());
		}
		
		btn_send=(Button)this.findViewById(R.id.btn_message_send);
		receiver=(Spinner)this.findViewById(R.id.send_to);
		message_content=(EditText)this.findViewById(R.id.message_content);		
		ArrayAdapter<String> name_adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,friend_names);
        name_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        receiver.setAdapter(name_adapter);	
        if (friend_id!=-1) {
        	receiver.setSelection(friend_id);
		}
        else{
        	receiver.setSelection(0);
        }
        
		SharedPreferences.Editor editor = sharedPreferences.edit();		
		editor.putInt("friend_db_id", 0);
		editor.commit();
		
		receiver.setOnItemSelectedListener(this);		
		btn_send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				content=message_content.getText().toString();
				if (content.equals("")) {
					Toast.makeText(New_Message_Activity.this, "Enter message", Toast.LENGTH_SHORT).show();
				}
				else{
					Send_message send_message=new Send_message(New_Message_Activity.this, String.valueOf(current_user_id), receiver_id, content, "1", "", _send);
					send_message.onRun();
				}
			}
		});
		for (int i = 0; i < friends.size(); i++) {
			Log.d("Friends_ID", friends.get(i).get_username()+friends.get(i).get_user_id());
		}
	}
	@Override
	public void send_message_requestFailure(String errMsg) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void send_message_requestSuccess() {
		// TODO Auto-generated method stub
		Toast.makeText(New_Message_Activity.this, "Message send successfully!", Toast.LENGTH_SHORT).show();
		Intent intent=new Intent(New_Message_Activity.this,InboxActivity.class);
		startActivity(intent,bndanimation_to_parent);
		finish();
	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		receiver_id=friends.get(position).get_user_id();		
	}
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		receiver_id=friends.get(0).get_user_id();
	}

	@Override
	public boolean onNavigateUp() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(New_Message_Activity.this,InboxActivity.class);
		startActivity(intent,bndanimation_to_parent);
		finish();
		return true;
	}
	@Override
	public void onBackPressed(){
		
	}
}
