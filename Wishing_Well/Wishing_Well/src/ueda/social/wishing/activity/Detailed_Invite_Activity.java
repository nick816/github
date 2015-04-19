package ueda.social.wishing.activity;

import java.util.ArrayList;

import ueda.social.wishing.R;
import ueda.social.wishing.http.Delete_message;
import ueda.social.wishing.http.Friend_accept;
import ueda.social.wishing.http.Friend_reject;
import ueda.social.wishing.http.Friend_search;
import ueda.social.wishing.http.Username_Email_search;
import ueda.social.wishing.http_request.Delete_message_Request;
import ueda.social.wishing.http_request.InviteAcceptHttpRequest;
import ueda.social.wishing.http_request.InviteRejectHttpRequest;
import ueda.social.wishing.http_request.Search_Result_Request;
import ueda.social.wishing.invite_activities.Username_Invite_Activity;
import ueda.social.wishing.model.User_Info;
import ueda.social.wishing.model.Userdata_DB_Helper;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class Detailed_Invite_Activity extends Activity implements OnClickListener,Search_Result_Request,Delete_message_Request,InviteAcceptHttpRequest,InviteRejectHttpRequest{
	
	private TextView send_time,msg_content;
	private Button btn_submit;
	private RadioGroup accept_reject,select_group;
	private int current_user_id=0;
	private String str_sender_name,str_send_date,str_send_time,str_msg_content,str_msg_id,str_sender_id,str_friend_category;
	private boolean accept_or_reject=true;
	private Search_Result_Request _search_result_resuest=this;
	private Delete_message_Request delete_msg=this;	
	private InviteAcceptHttpRequest i=this;
	private InviteRejectHttpRequest j=this;
	private Userdata_DB_Helper db;
	ArrayList<User_Info> friends=new ArrayList<User_Info>();
	Bundle bndanimation_to_child,bndanimation_to_parent;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_invite_activity);
        Bundle bundle=getIntent().getExtras();
        str_sender_name=bundle.getString("sender_name");
        str_send_date=bundle.getString("send_date");
        str_send_time=bundle.getString("send_time");
        str_msg_content=str_sender_name+" want to be a friend with you. You 'll...";
        str_msg_id=bundle.getString("msg_id");
        str_sender_id=bundle.getString("friend_id");  
        
        db=new Userdata_DB_Helper(this);
        
        bndanimation_to_child = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation,R.anim.animation2).toBundle();
		bndanimation_to_parent = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation3,R.anim.animation4).toBundle();
		
		// get the action bar
		ActionBar actionBar = getActionBar();
		// Enabling Back navigation on Action Bar icon
		actionBar.setDisplayHomeAsUpEnabled(true);       
        
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		current_user_id=sharedPreferences.getInt("current_userid", -1);	
        
        send_time=(TextView)this.findViewById(R.id.notify_date_time);
        msg_content=(TextView)this.findViewById(R.id.invite_content);
        btn_submit=(Button)this.findViewById(R.id.btn_invite_submit);
        accept_reject=(RadioGroup)this.findViewById(R.id.rad_accept_reject);
        select_group=(RadioGroup)this.findViewById(R.id.rad_select_group);
        
        send_time.setText(str_send_date+" "+str_send_time);
        msg_content.setText(str_msg_content);
        
        accept_reject.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId==R.id.rad_accept) {
					accept_or_reject=true;
					select_group.setVisibility(View.VISIBLE);
				}
				else{
					accept_or_reject=false;
					select_group.setVisibility(View.INVISIBLE);
				}
			}
		});
        str_friend_category="1";
        select_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.rad_group1:
					str_friend_category="1";
					break;
				case R.id.rad_group2:
					str_friend_category="2";
					break;
				case R.id.rad_group3:
					str_friend_category="3";
					break;
				case R.id.rad_group4:
					str_friend_category="4";
					break;
				case R.id.rad_group5:
					str_friend_category="5";
					break;
				case R.id.rad_group6:
					str_friend_category="6";
					break;
				default:
					str_friend_category="1";
					break;
				}
			}
		});        
        btn_submit.setOnClickListener(this);
    }  
	
	@Override
	public void delete_message_requestFailure(String errMsg) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void delete_message_requestSuccess() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(Detailed_Invite_Activity.this,InboxActivity.class);
		startActivity(intent,bndanimation_to_parent);
		finish();
	}
	@Override
	public void invite_reject_requestFailure(String errMsg) {
		// TODO Auto-generated method stub
		Toast.makeText(Detailed_Invite_Activity.this, "Invite reject failed!", Toast.LENGTH_SHORT).show();
	}
	@Override
	public void invite_reject_requestSuccess() {
		// TODO Auto-generated method stub
		Toast.makeText(Detailed_Invite_Activity.this, "Invite rejected!", Toast.LENGTH_SHORT).show();
		Delete_message delete_message=new Delete_message(Detailed_Invite_Activity.this, str_msg_id, delete_msg);
		delete_message.onRun();		
	}
	@Override
	public void invite_accept_requestFailure(String errMsg) {
		// TODO Auto-generated method stub
		Toast.makeText(Detailed_Invite_Activity.this, "Invite accept failed!", Toast.LENGTH_SHORT).show();
	}
	@Override
	public void invite_accept_requestSuccess() {
		// TODO Auto-generated method stub
		Username_Email_search search=new Username_Email_search(Detailed_Invite_Activity.this, str_sender_name, "", "", "", "",_search_result_resuest);
		search.onRun();
		Toast.makeText(Detailed_Invite_Activity.this, "Invite accepted!", Toast.LENGTH_SHORT).show();
		Delete_message delete_message=new Delete_message(Detailed_Invite_Activity.this, str_msg_id, delete_msg);
		delete_message.onRun();		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub	
		if (accept_or_reject) {
//			Toast.makeText(Detailed_Invite_Activity.this, String.valueOf(current_user_id)+"   "+str_sender_id+"   "+str_friend_category,Toast.LENGTH_SHORT).show();
			Friend_accept accept_friend=new Friend_accept(Detailed_Invite_Activity.this, String.valueOf(current_user_id), str_sender_id, str_friend_category, i);
			accept_friend.onRun();
		}
		else{
//			Toast.makeText(Detailed_Invite_Activity.this, String.valueOf(current_user_id)+"   "+str_sender_id,Toast.LENGTH_SHORT).show();
			Friend_reject reject_friend=new Friend_reject(Detailed_Invite_Activity.this, String.valueOf(current_user_id), str_sender_id,j);
			reject_friend.onRun();
		}
	}
	@Override
	public boolean onNavigateUp() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(Detailed_Invite_Activity.this,InboxActivity.class);
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
	public void requestSuccess(ArrayList<User_Info> user_infos) {
		// TODO Auto-generated method stub
		User_Info temp=new User_Info();
		temp=user_infos.get(0);
		temp.set_category(str_friend_category);		
		db.add_user(temp);
	}
}
