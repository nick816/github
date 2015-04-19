package com.example.Model;

public class User_Info {
	
	private int db_id;	
	private String name="";
	private String content="";
	private String photo="";
	private String audio="";
	
	public User_Info(){	
		
	}
	
	public User_Info(String user_id,String name,String content,String photo,String audio){
		super();
		this.name=name;
		this.content=content;
		this.photo=photo;
		this.audio=audio;
	}
	public int get_db_id(){
		return this.db_id;
	}
	public void set_db_id(int db_id){
		this.db_id=db_id;
	}
	public String get_name(){
		return this.name;
	}
	public void set_name(String name){
		this.name=name;
	}
	public void set_content(String content){
		this.content=content;
	}
	public String get_content() {
		return this.content;
	}
	public void set_photo(String photo){
		this.photo=photo;
	}
	public String get_photo() {
		return this.photo;
	}
	public void set_audio(String audio){
		this.audio=audio;
	}
	public String get_audio() {
		return this.audio;
	}
}
