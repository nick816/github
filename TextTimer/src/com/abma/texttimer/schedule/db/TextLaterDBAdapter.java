package com.abma.texttimer.schedule.db;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.abma.texttimer.FragmentSchedule;
import com.abma.texttimer.schedule.Schedule;
import com.gmc.contact.Contact;

public class TextLaterDBAdapter {

	static final String dbName = "textlater.db"; // name of Database;
    static final String[] colNames = {"id", "contactID", "contactName", "contactNumber", "contactPhotoUri", "reservationDateTime", "year", "message"};
    static final int dbMode = SQLiteDatabase.CREATE_IF_NECESSARY;

    String tableName = "scheduleTable"; // name of Table;
    boolean isSent = false;
    Context _mContext = null;
     
    // Database
    SQLiteDatabase db;
    
    static ScheduledDBAdapter _mAdapter = null;

    public TextLaterDBAdapter(Context context) {
    	this._mContext = context;
    }
    
    public void setTableName(String tableName, boolean sent) {
    	this.tableName = tableName;
    	this.isSent = sent;
    	createDatabase(dbName, dbMode);
    	createTable();
    }
    // Create and Open Database
    public void createDatabase(String dbName, int dbMode){
        db = _mContext.openOrCreateDatabase(dbName, dbMode,null);
    }
    // Create Table
    public void createTable(){
        String sql = "create table if not exists " + tableName + "(id integer primary key autoincrement, contactID text not null, contactName text not null, contactNumber text not null, contactPhotoUri text not null, reservationDateTime DATETIME DEFAULT CURRENT_TIMESTAMP, message text not null)";
        db.execSQL(sql);
    }
     
    // Delete Table
    public void removeTable(){
        String sql = "drop table " + tableName;
        db.execSQL(sql);
    }
     
    // Insert Data
    public String insertData(Schedule schedule){
    	Contact contact = schedule.getRecipient();
        String sql = "insert into " + tableName + " values(NULL, ?, ?, ?, ?, ?, ?);";
        db.execSQL(sql, new Object[]{contact.getContactId(), contact.getContactName(), contact.getPhoneNumbers(), contact.getImageUri(), schedule.getReservationDateTime1(), schedule.getMessage()});
        
        sql = "select last_insert_rowid()";
        Cursor c = db.rawQuery(sql, null);
        String scheduleId = null;
        if (c != null && c.moveToFirst()) 
        {
            scheduleId = c.getString(0);
        //The 0 is the column index, we only have 1 column, so the index is 0
        }
        return scheduleId;
    }
     
    // Update Data
    public void updateData(Schedule schedule){
    	Contact contact = schedule.getRecipient();
    	String sql = "update " + tableName + " set contactID = ?, contactName = ?, contactNumber = ?, contactPhotoUri = ?, reservationDateTime = ?, message = ? where id = ?;";
        db.execSQL(sql, new Object[]{contact.getContactId(), contact.getContactName(), contact.getPhoneNumbers(), contact.getImageUri(), schedule.getReservationDateTime1(), schedule.getMessage(), schedule.getScheduleId()});
    }
     
    // Delete Data
    public void removeData(int index){
        String sql = "delete from " + tableName + " where id = "+index+";";
        db.execSQL(sql);
    }
     
    // Read Data
    public Schedule selectData(int index){
    	Schedule schedule = new Schedule();
        String sql = "select * from " +tableName+ " where id = "+index+";";
        Cursor result = db.rawQuery(sql, null);
         
        if(result.moveToFirst()){
        	String id = result.getString(0);
            String contactID = result.getString(1);
            String contactName = result.getString(2);
            String contactNumbers = result.getString(3);
            String contactPhotoUri = result.getString(4);
            String reservationDateTime = result.getString(5);
//            long reservationTime = Long.parseLong(result.getString(5));
            String message = result.getString(6);
            
            schedule.setScheduleId(id);
            schedule.setReservationDateTime1(reservationDateTime);
            schedule.setMessage(message);
            schedule.setState(isSent);
            
            Contact contact = new Contact();
            contact.setContactId(contactID);
            contact.setContactName(contactName);
            contact.setImageUri(contactPhotoUri);
            contact.addPhoneNumbers(contactNumbers);
            schedule.setRecepient(contact);
        }
        result.close();
        
        return schedule;
    }
     
     
    // Read All Data
    public ArrayList<Schedule> selectAll(){
    	
    	ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();
//    	String query = "select reservationDateTime, datetime('now', 'localtime') from " + tableName + ";";
//    	Cursor results1 = db.rawQuery(query, null);
//        
//        results1.moveToFirst();
////        static final String[] colNames = {"id", "contactID", "contactName", "contactNumber", "contactPhotoUri", "reservationDateTime", "message"};
//        while(!results1.isAfterLast()){
//        	System.out.println("HELLO : " + results1.getString(0) + ":" + results1.getString(1));
//        	results1.moveToNext();
//        }
//        results1.close();
        String sql = "select * from " + tableName;
        if ( isSent == true ) {
        	sql += " where reservationDateTime<=datetime('now', 'localtime') order by reservationDateTime DESC, contactName ASC;";
        }
        else {
        	sql += " where reservationDateTime>datetime('now', 'localtime') order by reservationDateTime ASC, contactName ASC;";
        }
        Cursor results = db.rawQuery(sql, null);
         
        results.moveToFirst();
//        static final String[] colNames = {"id", "contactID", "contactName", "contactNumber", "contactPhotoUri", "reservationDateTime", "message"};
        while(!results.isAfterLast()){
            String id = results.getString(0);
            String contactID = results.getString(1);
            String contactName = results.getString(2);
            String contactNumbers = results.getString(3);
            String contactPhotoUri = results.getString(4);
            String reservationDateTime = results.getString(5);
//            long reservationTime = Long.parseLong(results.getString(5));
            String message = results.getString(6);
            
            Schedule schedule = new Schedule();
            schedule.setScheduleId(id);
            schedule.setReservationDateTime1(reservationDateTime);
            schedule.setMessage(message);            
            schedule.setState(isSent);
            
            Contact contact = new Contact();
            contact.setContactId(contactID);
            contact.setContactName(contactName);
            contact.setImageUri(contactPhotoUri);
            contact.addPhoneNumbers(contactNumbers);
            schedule.setRecepient(contact);
            
            scheduleList.add(schedule);
            
            results.moveToNext();
        }
        results.close();
        
        return scheduleList;
    }

}
