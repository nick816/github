package ueda.social.wishing.model;

public class Message {
	private String msg_id;
	private String user_ID="";
	private String friend_id="";
	private String date="";
	private String time="";
	private String msg_type="";
	private String notify_type="";
	private String state="";
	private String username="";
	private String content="";
	private String friend_wish_id;

	public Message(){
		this.msg_id="";
		this.user_ID="";
		this.friend_id="";
		this.date="";
		this.time="";
		this.time="";
		this.msg_type="";
		this.notify_type="";
		this.state="";
		this.username="";
		this.content="";
		this.friend_wish_id="";
	}
	
	public Message(String msg_id,String user_id,String friend_id,String date,String time,String msg_type,String notify_type,String state,String username,String content,String friend_wish_id){
		this.msg_id=msg_id;
		this.user_ID=user_id;
		this.friend_id=friend_id;		
		this.date=date;
		this.time=time;
		this.msg_type=msg_type;
		this.notify_type=notify_type;
		this.state=state;
		this.username=username;
		this.content=content;
		this.friend_wish_id=friend_wish_id;
	}
	public void set_msg_id(String msg_id){
		this.msg_id=msg_id;
	}
	public String get_msg_id() {
		return this.msg_id;
	}
	public void set_user_id(String user_id){
		this.user_ID=user_id;
	}
	public String get_user_id() {
		return this.user_ID;
	}
	public void set_friend_id(String friend_id){
		this.friend_id=friend_id;
	}
	public String get_friend_id() {
		return this.friend_id;
	}	
	public void set_date(String date){
		this.date=date;
	}
	public String get_date() {
		return this.date;
	}
	public void set_time(String time){
		this.time=time;
	}
	public String get_time() {
		return this.time;
	}
	public void set_msg_type(String msg_type){
		this.msg_type=msg_type;
	}
	public String get_msg_type() {
		return this.msg_type;
	}
	public void set_notify_type(String notify_type){
		this.notify_type=notify_type;
	}
	public String get_notify_type() {
		return this.notify_type;
	}
	public void set_state(String state){
		this.state=state;
	}
	public String get_state() {
		return this.state;
	}
	public void set_username(String username){
		this.username=username;
	}
	public String get_username() {
		return this.username;
	}	
	public void set_content(String content){
		this.content=content;
	}
	public String get_content() {
		return this.content;
	}	
	public void set_friend_wish_id(String id){
		this.friend_wish_id=id;
	}
	public String get_friend_wish_id() {
		return this.friend_wish_id;
	}	
}
