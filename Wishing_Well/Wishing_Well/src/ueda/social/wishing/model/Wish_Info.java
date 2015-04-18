package ueda.social.wishing.model;

public class Wish_Info {
	
	private int db_id;
	private String user_id="";
	private String wish_id="";
	private String wish_title="";
	private String wish_description="";	
	private String wish_event="";
	private String wish_date="";
	private String wish_time="";
	private String wish_product_code="";
	private String wish_price="";
	private String wish_img="";
	private String picked="";
	private String deleted="";
	private String visible_all="";
	private String visible_friend_ids="";
	private int    like_count=0;
	private int    is_like=0;
	
	public Wish_Info(){	
		
	}
	
	public Wish_Info(String user_id,String wish_id,String title,String description,String event,
			String date,String time,String product_code,String price,
			String img,String picked,String visible_all,String visible_friends,String deleted,int like,int is_like){
		super();
		this.user_id=user_id;
		this.wish_id=wish_id;
		this.picked=picked;
		this.visible_all=visible_all;
		this.visible_friend_ids=visible_friends;
		this.wish_date=date;
		this.wish_description=description;
		this.wish_event=event;
		this.wish_price=price;
		this.wish_img=img;
		this.wish_product_code=product_code;
		this.wish_time=time;
		this.wish_title=title;
		this.deleted=deleted;
		this.like_count=like;
		this.is_like=is_like;
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
	public String get_picked(){
		return this.picked;
	}
	public void set_picked(String picked){
		this.picked=picked;
	}
	public String get_deleted(){
		return this.deleted;
	}
	public void set_deleted(String deleted){
		this.deleted=deleted;
	}
	public String get_visible_all() {
		return this.visible_all;
	}
	public void set_visible_all(String visible_all){
		this.visible_all=visible_all;
	}
	public String get_visible_friend_ids() {
		return this.visible_friend_ids;
	}	
	public void set_visible_friend_ids(String visible_friend_ids){
		this.visible_friend_ids=visible_friend_ids;
	}
	public void set_wish_date(String date){
		this.wish_date=date;
	}
	public String get_wish_date() {
		return this.wish_date;
	}
	public void set_time(String time){
		this.wish_time=time;
	}
	public String get_time() {
		return this.wish_time;
	}
	public void set_title(String title){
		this.wish_title=title;
	}
	public String get_title() {
		return this.wish_title;
	}
	public void set_description(String description){
		this.wish_description=description;
	}
	public String get_description() {
		return this.wish_description;
	}
	public void set_product_code(String product_code){
		this.wish_product_code=product_code;
	}
	public String get_product_code() {
		return this.wish_product_code;
	}
	public void set_price(String price){
		this.wish_price=price;
	}
	public String get_price() {
		return this.wish_price;
	}
	public void set_img(String img){
		this.wish_img=img;
	}
	public String get_img() {
		return this.wish_img;
	}
	public void set_event(String event){
		this.wish_event=event;
	}
	public String get_event() {
		return this.wish_event;
	}

	public String get_wish_id() {
		// TODO Auto-generated method stub
		return this.wish_id;
	}
	public void set_wish_id(String wish_id){
		this.wish_id=wish_id;
	}
	public int get_like_count(){
		return this.like_count;
	}
	public void set_like_count(int like){
		this.like_count=like;
	}
	public int get_is_like(){
		return this.is_like;
	}
	public void set_is_like(int like){
		this.is_like=like;
	}
}
