package com.example.phonecalllog.search;

public class Call_Info {
	
	private String number;
	private String data;
	private String type;
	private String duration;

	public Call_Info(){
		
	}
	
	public Call_Info(String number, String data, String type, String duration){
		super();
		this.number = number;
		this.data = data;
		this.type = type;
		this.duration = duration;
	}
	
	public void set_number(String number){
		this.number = number;
	}
	public String get_number(){
		return this.number;
	}
	
	public void set_data(String data){
		this.data = data;
	}
	public String get_data(){
		return this.data;
	}
	
	public void set_type(String type){
		this.type = type;
	}
	public String get_type(){
		return this.type;
	}
	
	public void set_duration(String duration){
		this.duration = duration;
	}
	public String get_duration(){
		return this.duration;
	}
}
