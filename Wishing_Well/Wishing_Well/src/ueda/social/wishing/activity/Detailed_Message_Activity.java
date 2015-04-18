package ueda.social.wishing.activity;

import ueda.social.wishing.R;
import ueda.social.wishing.http.Delete_message;
import ueda.social.wishing.http.Send_message;
import ueda.social.wishing.http_request.Delete_message_Request;
import ueda.social.wishing.http_request.Send_message_Request;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Detailed_Message_Activity extends Activity implements Delete_message_Request,Send_message_Request{
	
	private TextView sender_name,send_date,send_time,msg_content;
//	private ImageView attach_file;
	private Button btn_send;
	private EditText msg_reply;
	private int current_user_id=0;
	private String str_sender_name,str_send_date,str_send_time,str_msg_content,str_msg_id,str_sender_id;
	private Delete_message_Request delete_msg=this;
	private Send_message_Request send_msg=this;
	Bundle bndanimation_to_child,bndanimation_to_parent;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_message_activity);
        bndanimation_to_child = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation,R.anim.animation2).toBundle();
		bndanimation_to_parent = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation3,R.anim.animation4).toBundle();
		
		// get the action bar
		ActionBar actionBar = getActionBar();
		// Enabling Back navigation on Action Bar icon
		actionBar.setDisplayHomeAsUpEnabled(true);
        Bundle bundle=getIntent().getExtras();
        str_sender_name=bundle.getString("sender_name");
        str_send_date=bundle.getString("send_date");
        str_send_time=bundle.getString("send_time");
        str_msg_content=bundle.getString("msg_content");
        str_msg_id=bundle.getString("msg_id");
        str_sender_id=bundle.getString("friend_id");
        
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		current_user_id=sharedPreferences.getInt("current_userid", -1);	
        
        sender_name=(TextView)this.findViewById(R.id.msg_sender_name);
        send_date=(TextView)this.findViewById(R.id.msg_date);
        send_time=(TextView)this.findViewById(R.id.msg_time);
        msg_content=(TextView)this.findViewById(R.id.msg_content);
        msg_reply=(EditText)this.findViewById(R.id.msg_reply_content);       
        btn_send=(Button)this.findViewById(R.id.btn_msg_reply);
        
        sender_name.setText(str_sender_name);
        send_date.setText(str_send_date);
        send_time.setText(str_send_time);
        msg_content.setText(str_msg_content);
//        attach_file.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
        btn_send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Send_message send_message=new Send_message(Detailed_Message_Activity.this, String.valueOf(current_user_id), str_sender_id, msg_reply.getText().toString(), "1", "", send_msg);
				send_message.onRun();
			}
		});
        
    }


    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
    	
    	switch (item.getItemId()) {
		case R.id.message_delete:
			Delete_message delete=new Delete_message(Detailed_Message_Activity.this, str_msg_id, delete_msg);
			delete.onRun();			
			break;
		default:
			break;
		}
    	
		return super.onOptionsItemSelected(item);
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.message, menu);
        return true;
    }


	@Override
	public void delete_message_requestFailure(String errMsg) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void delete_message_requestSuccess() {
		// TODO Auto-generated method stub
		Toast.makeText(Detailed_Message_Activity.this, "Message deleted!", Toast.LENGTH_SHORT).show();
		Intent intent=new Intent(Detailed_Message_Activity.this,InboxActivity.class);
		startActivity(intent,bndanimation_to_parent);
		finish();
	}


	@Override
	public void send_message_requestFailure(String errMsg) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void send_message_requestSuccess() {
		// TODO Auto-generated method stub
		Toast.makeText(Detailed_Message_Activity.this, "Message sent successfully!", Toast.LENGTH_SHORT).show();
		Intent intent=new Intent(Detailed_Message_Activity.this,InboxActivity.class);
		startActivity(intent,bndanimation_to_parent);
		finish();
	}
	public boolean onNavigateUp() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(Detailed_Message_Activity.this,InboxActivity.class);
		startActivity(intent,bndanimation_to_parent);
		finish();
		return true;
	}
	@Override
	public void onBackPressed(){
		
	}
}
