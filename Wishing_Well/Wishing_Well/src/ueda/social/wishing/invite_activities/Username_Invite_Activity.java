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

public class Username_Invite_Activity extends Activity implements Search_Result_Request,InviteHttpRequest {

	private Search_Result_Request _search_result=this;
	private InviteHttpRequest _invite=this;
	private EditText search_username;
	private Button btn_search_username;
	private ListView search_result;
	private int current_user_id=0;
	Bundle bndanimation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.username_contact_activity);
		
		bndanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation3,R.anim.animation4).toBundle();
		// get the action bar
		ActionBar actionBar = getActionBar();
		// Enabling Back navigation on Action Bar icon
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		search_username=(EditText)this.findViewById(R.id.username_search);
		btn_search_username=(Button)this.findViewById(R.id.search_username);
		search_result=(ListView)this.findViewById(R.id.result_username_list);
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		current_user_id = sharedPreferences.getInt("current_userid", -1);		
		btn_search_username.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String username=search_username.getText().toString();
				if (username.equals("")) {
					Toast.makeText(Username_Invite_Activity.this, "Enter username!", Toast.LENGTH_SHORT).show();
				}
				else{
					Username_Email_search search=new Username_Email_search(Username_Invite_Activity.this, username, "", "", "", "",_search_result);
					search.onRun();
				}
			}
		});
	}
	@Override
	public void requestFailure(String errMsg) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void requestSuccess(final ArrayList<User_Info> user_infos) {
		// TODO Auto-generated method stub		
		search_result.setAdapter(new Detailed_Userinfo_ListAdapter(Username_Invite_Activity.this, user_infos));	
		search_result.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				final int index=position;
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Username_Invite_Activity.this);		 
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
								Friend_Invite new_invite=new Friend_Invite(Username_Invite_Activity.this, String.valueOf(current_user_id), user_infos.get(index).get_username(), "",_invite);
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
	public void invite_requestFailure(String errMsg) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void invite_requestSuccess() {
		// TODO Auto-generated method stub
		Toast.makeText(Username_Invite_Activity.this, "Invite Success!", Toast.LENGTH_SHORT).show();
	}	
	@Override
	public boolean onNavigateUp() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(Username_Invite_Activity.this,InviteSelectActivity.class);
		startActivity(intent,bndanimation);
		return true;
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	}

}
