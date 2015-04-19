package com.androidhive.androidsqlite;

public class Contact {
	
	//private variables
	int _id;
	String _user_number;
	String _phone_number;
	String _type;
	String _date;
	String _duration;
	
	// Empty constructor
	public Contact(){
		
	}
	// constructor
	public Contact(int id, String name, String phone_number, String type, String date, String duration){
		this._id = id;
		this._user_number = name;
		this._phone_number = phone_number;
		this._type = type;
		this._date = date;
		this._duration = duration;
	}
	
	// constructor
	public Contact(String name, String phone_number, String type, String date, String duration){
		this._user_number = name;
		this._phone_number = phone_number;
		this._type = type;
		this._date = date;
		this._duration = duration;
	}
	// getting ID
	public int getID(){
		return this._id;
	}
	
	// setting id
	public void setID(int id){
		this._id = id;
	}
	
	// getting name
	public String getName(){
		return this._user_number;
	}
	
	// setting name
	public void setName(String user_name){
		this._user_number = user_name;
	}
	
	// getting phone number
	public String getPhoneNumber(){
		return this._phone_number;
	}
	
	// setting phone number
	public void setPhoneNumber(String phone_number){
		this._phone_number = phone_number;
	}
	
	public String getType(){
		return this._type;
	}
	
	// setting phone number
	public void setType(String type){
		this._type = type;
	}
	
	public String getDate(){
		return this._date;
	}
	
	// setting phone number
	public void setDate(String date){
		this._date = date;
	}
	
	public String getDuration(){
		return this._duration;
	}
	
	// setting phone number
	public void setDuration(String duration){
		this._duration = duration;
	}
}
