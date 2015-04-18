package ueda.social.wishing.invite_activities;

import java.util.ArrayList;

import ueda.social.wishing.R;
import ueda.social.wishing.activity.InviteSelectActivity;
import ueda.social.wishing.adapter.Detailed_Userinfo_ListAdapter;
import ueda.social.wishing.http.Friend_Invite;
import ueda.social.wishing.http.Username_Email_search;
import ueda.social.wishing.http_request.InviteHttpRequest;
import ueda.social.wishing.http_request.Search_Result_Request;
import ueda.social.wishing.model.User_Info;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Email_Invite_Activity extends Activity implements Search_Result_Request,InviteHttpRequest{

	private Search_Result_Request _search_result=this;
	private InviteHttpRequest _invite=this;
	Bundle bndanimation;
	private EditText search_email;
	private Button btn_search_email;
	private ListView search_result;
	private int current_user_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.email_contact_activity);
		
		bndanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation3,R.anim.animation4).toBundle();
		
		// get the action bar
		ActionBar actionBar = getActionBar();
		// Enabling Back navigation on Action Bar icon
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		search_email=(EditText)this.findViewById(R.id.email_search);
		btn_search_email=(Button)this.findViewById(R.id.search_email);
		search_result=(ListView)this.findViewById(R.id.emailsearchlist);
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		current_user_id = sharedPreferences.getInt("current_userid", -1);	
		
		btn_search_email.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String email=search_email.getText().toString();
				if (email.equals("")) {
					Toast.makeText(Email_Invite_Activity.this, "Enter E-mail Address!", Toast.LENGTH_SHORT).show();
				}
				else{
					Username_Email_search search=new Username_Email_search(Email_Invite_Activity.this, "", "", email, "", "",_search_result);
					search.onRun();
				}
			}
		});
	}
	@Override
	public void requestSuccess(final ArrayList<User_Info> user_infos) {
		// TODO Auto-generated method stub
		search_result.setAdapter(new Detailed_Userinfo_ListAdapter(Email_Invite_Activity.this, user_infos));
		search_result.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				final int index=position;
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Email_Invite_Activity.this);		 
					// set title
					alertDialogBuilder.setTitle("Confirm!");		 
					// set dialog message
					alertDialogBuilder
						.setMessage("Invite this user?")						
						.setPositiveButton("No",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, close
								// current activity
								dialog.cancel();
							}
						  })
						.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, just close
								// the dialog box and do nothing								
								Friend_Invite new_invite=new Friend_Invite(Email_Invite_Activity.this, String.valueOf(current_user_id), "", user_infos.get(index).get_email(),_invite);
								new_invite.onRun();
							}
						});		  
					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();		 
					// show it
					alertDialog.show();	
			}
		});
	}
	@Override
	public void requestFailure(String errMsg) {
		// TODO Auto-generated method stub
		
	}	
	@Override
	public void invite_requestFailure(String errMsg) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void invite_requestSuccess() {
		// TODO Auto-generated method stub
		Toast.makeText(Email_Invite_Activity.this, "Invite Success!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onNavigateUp() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(Email_Invite_Activity.this,InviteSelectActivity.class);
		startActivity(intent,bndanimation);
		finish();
		return true;
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	}

}
