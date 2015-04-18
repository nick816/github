package com.gmc.contact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.ContactsContract.CommonDataKinds.Phone;

@SuppressWarnings("rawtypes")
public class Contact {
	String id = null;
	String name = null;
	// type, Phonenumber
	ArrayList<Map> phoneList = null;
	// type, emailaddress
	ArrayList<Map> emailList = null;
	String image_uri = ""; 
	Bitmap bitmap = null; 
	
	public void setContactId(String id) {
		this.id = id;
	}
	public void setContactName(String name) {
		this.name = name;
	}
	public void addPhoneNumber(String type, String phonenumber) {
//		ContactsContract.CommonDataKinds.Phone.TYPE_HOME = 1
//		ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE = 2
//		ContactsContract.CommonDataKinds.Phone.TYPE_WORK = 3
		if ( this.phoneList == null ) {
			this.phoneList = new ArrayList<Map>();
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(type, phonenumber);
		this.phoneList.add(map);
	}
	public void addPhoneNumbers(String phoneNumbers) {
		String[] arr = phoneNumbers.split("\n");
		if ( arr == null )
			return;
		
		for ( int i = 0; i < arr.length; i++ ) {
			String[] pNum = arr[i].split(":");
			if ( pNum.length == 2 )
				addPhoneNumber(pNum[0],pNum[1]);
		}
	}
	public void addEmailContact(String type, String emailContact) {
		if ( this.emailList == null ) {
			this.emailList = new ArrayList<Map>();
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(type, emailContact);
		this.emailList.add(map);
	}
	
	public void setImageUri(String imageuri) {
		this.image_uri = imageuri;
	}
	
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	public String getContactId() {
		return this.id;
	}
	
	public String getContactName() {
		return this.name;
	}
	
	public ArrayList<Map> getPhoneList() {
		return this.phoneList;
	}
	
	@SuppressWarnings("unchecked")
	public String getPhoneNumbers() {
		String phoneNumbers = "";
		if ( this.phoneList != null ) {
			for ( int i = 0; i < this.phoneList.size(); i++ ) {
				Map map = this.phoneList.get(i);
				Iterator<String> iter = map.keySet().iterator();
				while(iter.hasNext()) {
					String key = iter.next();
					String value = (String) map.get(key);
					if ( phoneNumbers != "" )
						phoneNumbers += ",";
					phoneNumbers += key + ":" + value; 
				}
			}
		}
		return phoneNumbers;
	}
	
	@SuppressWarnings("unchecked")
	public String getPhoneNumbers(Context context) {
		String phoneNumbers = "";
		if ( this.phoneList != null ) {
			for ( int i = 0; i < this.phoneList.size(); i++ ) {
				Map map = this.phoneList.get(i);
				Iterator<String> iter = map.keySet().iterator();
				while(iter.hasNext()) {
					String key = iter.next();
					String value = (String) map.get(key);
					if ( phoneNumbers != "" )
						phoneNumbers += "\n";
					phoneNumbers += Phone.getTypeLabel(context.getResources(), Integer.parseInt(key), "") + ":" + value; 
				}
			}
		}
		return phoneNumbers;
	}
	
	public ArrayList<Map> getEmailList() {
		return this.emailList;
	}
	
	public String getImageUri() {
		return this.image_uri;
	}
	
	public Bitmap getBitmap() {
		return this.bitmap;
	}
	
	public boolean isEqual(Contact other) {
		if ( this.getContactId().equals(other.getContactId()) )
			return true;
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public String toString() {
		String result = ""
				+ "ID : " + this.id + "\n"
				+ "Name : " + this.name + "\n";
		if ( this.phoneList != null ) {
			result += "Phone List\n";
			for ( int i = 0; i < this.phoneList.size(); i++ ) {
				Map map = this.phoneList.get(i);
				Iterator<String> iter = map.keySet().iterator();
				while(iter.hasNext()) {
					String key = iter.next();
					String value = (String) map.get(key);
					result += key + " : " + value + "\n"; 
				}
			}
		}
		if ( this.emailList != null ) {
			result += "Email List\n";
			for ( int i = 0; i < this.emailList.size(); i++ ) {
				Map map = this.emailList.get(i);
				Iterator<String> iter = map.keySet().iterator();
				while(iter.hasNext()) {
					String key = iter.next();
					String value = (String) map.get(key);
					result += key + " : " + value + "\n"; 
				}
			}
		}
		if ( this.image_uri != null )
			result += this.image_uri + "\n";
		
		return result;
	}
}
