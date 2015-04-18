package com.abma.texttimer.schedule;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;

import com.gmc.contact.Contact;

@SuppressLint("SimpleDateFormat") 
public class Schedule {
	
	private String id = null;
	private Contact recepient = null;
	private String message = null;
	private String reservationDateTime = null;
	private int year;
	private boolean isSent = false;
	
	private long reservationTime; 

	ArrayList<Contact> contactsList = null;
	
	public Schedule() {

	}
	
	public void setScheduleId(String id) {
		this.id = id;
	}
	
	public void setRecepient(Contact contact) {
		this.recepient = contact;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setYear(int year) {
		this.year = year;
	}
	@SuppressWarnings("deprecation")
	public void setReservationDateTime(long time) {
		this.reservationTime = time;
		DateFormat df = new SimpleDateFormat("MMM dd h:mm aa");
		this.reservationDateTime = df.format(time);
		this.year = (new Date(time)).getYear() + 1900;
	}
	public void setReservationDateTime(int year, int month, int day, int hour, int minute) {
		Calendar c = Calendar.getInstance();
        c.set(year, month, day, hour, minute, 0);
        this.year = year;
		setReservationDateTime(c.getTimeInMillis());
	}
	@SuppressWarnings("deprecation")
	public void setReservationDateTime(String datetime) {
		DateFormat df = new SimpleDateFormat("MMM dd h:mm aa");
		try {
			Date date = (Date)df.parse(datetime);
			if ( this.year > 0 )
				date.setYear(this.year);
			else {
				date.setYear((new Date()).getYear());
			}
			this.reservationTime = date.getTime();
			setReservationDateTime(this.reservationTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void setReservationDateTime1(String datetime) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
		try {
			Date date = (Date)df.parse(datetime);
			if ( this.year > 0 )
				date.setYear(this.year);
			else {
				date.setYear((new Date()).getYear());
			}
			this.reservationTime = date.getTime();
			setReservationDateTime(this.reservationTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public void setState(boolean sent) {
		this.isSent = sent;
	}
	public String getScheduleId() {
		return this.id;
	}
	public Contact getRecipient() {
		return this.recepient;
	}
	public String getRecipientName() {
		return this.recepient.getContactName();
	}
	public String getMessage() {
		return this.message;
	}
	@SuppressWarnings("deprecation")
	public int getYear() {
		if ( this.year <= 0 )
			this.year = (new Date()).getYear() + 1900;
		return this.year;
	}
	public String getReservationDateTime() {
		return this.reservationDateTime;
	}
	public String getReservationDateTime1() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");  
		return dateFormat.format(new Date(this.reservationTime));
	}
	public long getReservationTime() {
		return this.reservationTime;
	}
	public String getRemainingDateTime() {
		if ( reservationTime > 0 ) {
			String remainingTime = "";
			Date curDateTime = new Date();
			long diff = ((long)(reservationTime / 60 / 1000)) - ((long)(curDateTime.getTime() / 60 / 1000));
			if ( diff < 0 )
				return "";
			
			long minutes = diff;
			long hours = minutes / 60;
			long days = hours / 24;
			hours = hours % 24;
			minutes = minutes % 60;
			remainingTime = "";
			if ( days > 0 )
				remainingTime +=  days;
			if ( days == 1 )
				remainingTime += "day ";
			else if ( days > 1 )
				remainingTime += "days ";
			remainingTime += hours + ":";
			if ( minutes < 10 )
				remainingTime += "0";
			remainingTime += minutes + " hours until sent";
			return remainingTime;
		}
		else {
			return "";
		}
	}
	public boolean isSent() {
		return this.isSent;
	}
	
	public String getReservationTimeForCompare() {
		long res = (long)(this.reservationTime / (60 * 1000));
		return "" + res;
	}
}
