package ueda.social.wishing.invite_activities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import ueda.social.wishing.R;
import ueda.social.wishing.activity.InviteSelectActivity;
import ueda.social.wishing.adapter.Phone_contact_ListAdapter;
import ueda.social.wishing.http.Friend_Invite;
import ueda.social.wishing.http.Phone_Invite;
import ueda.social.wishing.http_request.InviteHttpRequest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Phone_Invite_Activity extends Activity implements InviteHttpRequest{

	private ArrayList<String> phone_name = new ArrayList<String>();
	private ArrayList<String> phone_email = new ArrayList<String>();
	private ArrayList<String> phone_number = new ArrayList<String>();
	private ArrayList<String> phone_image = new ArrayList<String>();
	
	private ListView contact_list;
	private int current_user_id=0;
	Bundle bndanimation;
	
	private InviteHttpRequest _invite=this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phone_contact_activity);
		
		bndanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation3,R.anim.animation4).toBundle();
		
		// get the action bar
		ActionBar actionBar = getActionBar();
		// Enabling Back navigation on Action Bar icon
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		current_user_id = sharedPreferences.getInt("current_userid", -1);		
		contact_list=(ListView)this.findViewById(R.id.phone_contact_list);

		readContacts();
		contact_list.setAdapter(new Phone_contact_ListAdapter(Phone_Invite_Activity.this, phone_name, phone_email, phone_number, phone_image));
		contact_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				
				// TODO Auto-generated method stub
				final int index=position;
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Phone_Invite_Activity.this);		 
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
								Phone_Invite new_invite=new Phone_Invite(Phone_Invite_Activity.this, String.valueOf(current_user_id), phone_email.get(position),_invite);
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
	
	public void readContacts() { 
		
		ContentResolver cr = getContentResolver(); 
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null); 
		String phone = null; 
		String emailContact = null; 
		String image_uri = ""; 
		Bitmap bitmap = null; 
		if (cur.getCount() > 0) { 
			for (int i = 0; i < cur.getCount();i++) {
				phone_email.add("");
			}
			int index=0;
			while (cur.moveToNext()) { 
				String id = cur.getString(cur .getColumnIndex(ContactsContract.Contacts._ID)); 
				String name = cur .getString(cur .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)); 
				image_uri = cur .getString(cur .getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)); 
				if (Integer .parseInt(cur.getString(cur .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) { 						
					phone_name.add(name);
					Cursor pCur = cr.query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[] { id }, null); 
					
					while (pCur.moveToNext()) { 
						phone = pCur .getString(pCur .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						phone_number.add(phone);
					}
					pCur.close(); 
					Cursor emailCur = cr.query( ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[] { id }, null); 
					
					while (emailCur.moveToNext()) { 
						emailContact = emailCur .getString(emailCur .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)); 
						if (emailContact!=null&&!emailContact.equals("")) {
							phone_email.set(index, emailContact);
						}						
					} 
					emailCur.close(); 
				} 
				if (image_uri != null) {						
					phone_image.add(image_uri);
				}
				else{
					phone_image.add("");
				}
				index++;
			} 
		} 		
	}
	@Override
	public boolean onNavigateUp() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(Phone_Invite_Activity.this,InviteSelectActivity.class);
		startActivity(intent,bndanimation);
		finish();
		return true;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	}

	@Override
	public void invite_requestFailure(String errMsg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void invite_requestSuccess() {
		// TODO Auto-generated method stub
		Toast.makeText(Phone_Invite_Activity.this, "Invite Success!", Toast.LENGTH_SHORT).show();
	}
}
