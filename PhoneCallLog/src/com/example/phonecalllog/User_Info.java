package com.example.phonecalllog;

public class User_Info {
	
	private int db_id;
	private String user_id="";
//	private String firstname="";
//	private String lastname="";
	private int gender=0;
//	private String birthday="";
//	private String country="";
//	private String zip_code="";
	private String email="";
	private String phonenumber="";
	private String username="";
	private String password="";
	private String category="";
	private String avarta="";
	private String tick="";
	
	public User_Info(){	
		
	}
	
	public User_Info(String user_id,String first,String last,int gender,String birthday,
			String country,String zip_code,String email,String phonenumber,
			String username,String password,String category,String avarta,String tick){
		super();
		this.user_id=user_id;
//		this.firstname=first;
//		this.lastname=last;
		this.gender=gender;
//		this.birthday=birthday;
//		this.country=country;
//		this.zip_code=zip_code;
		this.email=email;
		this.phonenumber=phonenumber;
		this.username=username;
		this.password=password;
		this.category=category;
		this.avarta=avarta;
		this.tick=tick;
	}
	public int get_db_id(){
		return this.db_id;
	}
	public void set_db_id(int db_id){
		this.db_id=db_id;
	}
	public String get_user_id(){
		return this.user_id;
	}
	public void set_user_id(String user_id){
		this.user_id=user_id;
	}
//	public void set_firstname(String firstname){
//		this.firstname=firstname;
//	}
//	public String get_firstname() {
//		return this.firstname;
//	}
//	public void set_lastname(String lastname){
//		this.lastname=lastname;
//	}
//	public String get_lastname() {
//		return this.lastname;
//	}
	public void set_gender(int gender){
		this.gender=gender;
	}
	public int get_gender() {
		return this.gender;
	}
//	public void set_birthday(String birthday){
//		this.birthday=birthday;
//	}
//	public String get_birthday() {
//		return this.birthday;
//	}
//	public void set_country(String country){
//		this.country=country;
//	}
//	public String get_country() {
//		return this.country;
//	}
//	public void set_zipcode(String zipcode){
//		this.zip_code=zipcode;
//	}
//	public String get_zipcode() {
//		return this.zip_code;
//	}
	public void set_email(String email){
		this.email=email;
	}
	public String get_email() {
		return this.email;
	}
	public void set_phonenumber(String phonenumber){
		this.phonenumber=phonenumber;
	}
	public String get_phonenumber() {
		return this.phonenumber;
	}
	public void set_username(String username){
		this.username=username;
	}
	public String get_username() {
		return this.username;
	}
	public void set_password(String password){
		this.password=password;
	}
	public String get_passowrd() {
		return this.password;
	}
	public void set_category(String category){
		this.category=category;
	}
	public String get_category() {
		return this.category;
	}
	public void set_avarta(String avarta){
		this.avarta=avarta;
	}
	public String get_avarta() {
		return this.avarta;
	}
	public void  set_tick(String tick){
		this.tick=tick;
	}
	public String get_tick(){
		return this.tick;
	}
}
