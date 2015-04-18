package ueda.social.wishing.activity;

import ueda.social.wishing.R;
import ueda.social.wishing.http.Delete_message;
import ueda.social.wishing.http.Friend_Grouping;
import ueda.social.wishing.http_request.Delete_message_Request;
import ueda.social.wishing.http_request.Friend_GroupingHttpRequest;
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

public class Modify_Friend_Category_Activity extends Activity implements OnClickListener,Delete_message_Request,Friend_GroupingHttpRequest{
	
	private TextView msg_content;
	private Button btn_submit;
	private RadioGroup select_group;
	private int current_user_id=0;
	private String str_sender_name,str_msg_content,str_msg_id,str_sender_id,str_friend_category;
	private int modify_type=0;
	private Delete_message_Request delete_msg=this;	
	private Friend_GroupingHttpRequest fg=this;
	Bundle bndanimation_to_child,bndanimation_to_parent;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.modify_friend_category_activity);
        Bundle bundle=getIntent().getExtras();
        str_sender_name=bundle.getString("sender_name");
        str_msg_content=str_sender_name+" is friend with you!He 'll be in ...";
        str_msg_id=bundle.getString("msg_id");
        str_sender_id=bundle.getString("friend_id");  
        modify_type=bundle.getInt("modify_type");
        
        bndanimation_to_child = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation,R.anim.animation2).toBundle();
		bndanimation_to_parent = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation3,R.anim.animation4).toBundle();
		
		// get the action bar
		ActionBar actionBar = getActionBar();
		// Enabling Back navigation on Action Bar icon
		actionBar.setDisplayHomeAsUpEnabled(true);       
        
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		current_user_id=sharedPreferences.getInt("current_userid", -1);	        

        msg_content=(TextView)this.findViewById(R.id.category_content);
        btn_submit=(Button)this.findViewById(R.id.btn_modify_group_submit);
        select_group=(RadioGroup)this.findViewById(R.id.rad_modify_group);
        
        msg_content.setText(str_msg_content);
        
        str_friend_category="1";
        select_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.rad_mod_group1:
					str_friend_category="1";
					break;
				case R.id.rad_mod_group2:
					str_friend_category="2";
					break;
				case R.id.rad_mod_group3:
					str_friend_category="3";
					break;
				case R.id.rad_mod_group4:
					str_friend_category="4";
					break;
				case R.id.rad_mod_group5:
					str_friend_category="5";
					break;
				case R.id.rad_mod_group6:
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
		Intent intent=new Intent(Modify_Friend_Category_Activity.this,AddressActivity.class);
		startActivity(intent,bndanimation_to_parent);		
		finish();
	}	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub	
		Friend_Grouping friend_grouping=new Friend_Grouping(Modify_Friend_Category_Activity.this, String.valueOf(current_user_id), str_sender_id, str_friend_category, fg);
		friend_grouping.onRun();		
	}

	@Override
	public void friend_grouping_requestFailure(String errMsg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void friend_grouping_requestSuccess() {
		// TODO Auto-generated method stub
		
		if (modify_type==1) {
			Delete_message delete_message=new Delete_message(Modify_Friend_Category_Activity.this, str_msg_id, delete_msg);
			delete_message.onRun();		
		}
		else{
			Intent intent=new Intent(Modify_Friend_Category_Activity.this,AddressActivity.class);
			startActivity(intent,bndanimation_to_parent);		
			finish();
		}
	}
	public boolean onNavigateUp() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(Modify_Friend_Category_Activity.this,AddressActivity.class);
		startActivity(intent,bndanimation_to_parent);
		finish();
		return true;
	}
	@Override
	public void onBackPressed(){
		
	}
}
