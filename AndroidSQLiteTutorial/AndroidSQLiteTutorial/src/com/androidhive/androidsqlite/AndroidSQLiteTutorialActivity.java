package com.androidhive.androidsqlite;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class AndroidSQLiteTutorialActivity extends Activity {
    /** Called when the activity is first created. */
	
	DatabaseHandler db ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        db = new DatabaseHandler(this);
        
        final EditText id=(EditText)this.findViewById(R.id.editText3);
        final EditText name=(EditText)this.findViewById(R.id.editText1);
        final EditText phone_number=(EditText)this.findViewById(R.id.editText2);
        final EditText type=(EditText)this.findViewById(R.id.editText4);
        final EditText date=(EditText)this.findViewById(R.id.editText5);
        final EditText duration=(EditText)this.findViewById(R.id.editText6);
        Button add=(Button)this.findViewById(R.id.button1);
        Button delete=(Button)this.findViewById(R.id.button2);
        Button update=(Button)this.findViewById(R.id.button3);
        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
//        db.addContact(new Contact(1,"Ravi", "9100000555555555555555555555555555555555555555555000", "afwfw", " fwfewfwe", " wfwfwaf" ));
 
        delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				db.deleteContact(Integer.parseInt(id.getText().toString()));
				
				// Reading all contacts
		        Log.d("Reading: ", "Reading all contacts..");
				List<Contact> contacts = db.getAllContacts();       
				 
		        for (Contact cn : contacts) {
		            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber() + " ,Type: " + cn.getType() + " ,Date: " + cn.getDate() + " ,Duration: " + cn.getDuration();
		                // Writing Contacts to log
		        Log.d("Name: ", log);
		        
		        }
			}
		});
        
		add.setOnClickListener(new OnClickListener() {
			

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stubCon
				Contact contact=new Contact();
				contact.setName("gfegea");
				contact.setType("gfegea");
				contact.setDuration("gfegea");
				contact.setPhoneNumber("gfegea");
				contact.setDate("gfegea");
				db.addContact(contact);
				
				Log.d("Reading: ", "Reading all contacts..");
				List<Contact> contacts = db.getAllContacts();       
				 
		        for (Contact cn : contacts) {
		            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber() + " ,Type: " + cn.getType() + " ,Date: " + cn.getDate() + " ,Duration: " + cn.getDuration();
		                // Writing Contacts to log
		        Log.d("Name: ", log);
		        
		        }
			}
		});
        
        update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Contact temp=new Contact();
				temp.setID(Integer.parseInt(id.getText().toString()));
				temp.setName(name.getText().toString());
				temp.setPhoneNumber(phone_number.getText().toString());
				temp.setType(type.getText().toString());
				temp.setDate(date.getText().toString());
				temp.setDuration(duration.getText().toString());
				db.updateContact(temp);
				
				// Reading all contacts
		        Log.d("Reading: ", "Reading all contacts..");
				List<Contact> contacts = db.getAllContacts();       
				 
		        for (Contact cn : contacts) {
		            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber() + " ,Type: " + cn.getType() + " ,Date: " + cn.getDate() + " ,Duration: " + cn.getDuration();
		                // Writing Contacts to log
		        Log.d("Name: ", log);
		        
		        }
			}
		});
        
    }
}