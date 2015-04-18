package ueda.social.wishing.activity;

import ueda.social.wishing.R;
import ueda.social.wishing.invite_activities.Email_Invite_Activity;
import ueda.social.wishing.invite_activities.Facebook_Invite_Activity;
import ueda.social.wishing.invite_activities.Phone_Invite_Activity;
import ueda.social.wishing.invite_activities.Username_Invite_Activity;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class InviteSelectActivity extends Activity {

	Bundle bndanimation_to_child,bndanimation_to_parent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invite);
		
		bndanimation_to_child = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation,R.anim.animation2).toBundle();
		bndanimation_to_parent = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation3,R.anim.animation4).toBundle();
		
		// get the action bar
		ActionBar actionBar = getActionBar();
		// Enabling Back navigation on Action Bar icon
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		LinearLayout user_contact=(LinearLayout)this.findViewById(R.id.username_contact);
        LinearLayout email_contact=(LinearLayout)this.findViewById(R.id.email_contact);
//        LinearLayout facebook_contact=(LinearLayout)this.findViewById(R.id.facebook_contact);
        LinearLayout phone_contact=(LinearLayout)this.findViewById(R.id.phone_contact);
        user_contact.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("linealayout", "username clicked");
				Intent intent=new Intent(InviteSelectActivity.this,Username_Invite_Activity.class);
				startActivity(intent,bndanimation_to_child);
				finish();
			}
		});
        email_contact.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("linealayout", "Email clicked");
				Intent intent=new Intent(InviteSelectActivity.this,Email_Invite_Activity.class);
				startActivity(intent,bndanimation_to_child);
				finish();
			}
		});
//        facebook_contact.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Log.d("linealayout", "Facebook clicked");
//				Intent intent=new Intent(InviteSelectActivity.this,Facebook_Invite_Activity.class);
//				startActivity(intent,bndanimation_to_child);	
//				finish();
//			}
//		});
        phone_contact.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("linealayout", "Linkedin clicked");
				Intent intent=new Intent(InviteSelectActivity.this,Phone_Invite_Activity.class);
				startActivity(intent,bndanimation_to_child);		
				finish();
			}
		});		
	}

	@Override
	public boolean onNavigateUp() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(InviteSelectActivity.this,SecondActivity.class);
		startActivity(intent,bndanimation_to_parent);
		finish();
		return true;
	}
	@Override
	public void onBackPressed(){
		
	}
}
