package com.example.Model;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Userdata_DB_Helper extends SQLiteOpenHelper {
	
	
	// Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "User_database";
    private static final String TABLE_VALUE = "USERS_INFO";
   
	public Userdata_DB_Helper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);	
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// SQL statement to create book table
		String CREATE_USER_TABLE = "CREATE TABLE "+TABLE_VALUE+"( " 
		        +"db_id INTEGER PRIMARY KEY AUTOINCREMENT, " +"user_name TEXT, "+"user_content TEXT, "+"photo TEXT, "+"audio TEXT )";
		
		// create books table
		db.execSQL(CREATE_USER_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS books");
        
        // create fresh books table
        this.onCreate(db);
	}
	//---------------------------------------------------------------------
   
	/**
     * CRUD operations (create "add", read "get", update, delete) book + get all books + delete all books
     */
       
    // Books Table Columns names
    private static final String KEY_ID = "db_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_CONTENT = "user_content";
    private static final String KEY_PHOTO = "photo";
    private static final String KEY_AUDIO = "audio";    
    
    private static final String[] COLUMNS = {KEY_ID,KEY_USER_NAME,KEY_USER_CONTENT,KEY_PHOTO,KEY_AUDIO};
    
	public void add_user(User_Info user){
//		Log.d("addBook", value.toString());
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		 
		// 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME,user.get_name()); 
        values.put(KEY_USER_CONTENT,user.get_content()); 
        values.put(KEY_PHOTO, user.get_photo());
        values.put(KEY_AUDIO, user.get_audio());        
        // 3. insert
        db.insert(TABLE_VALUE, // table
        		null, //nullColumnHack
        		values); // key/value -> keys = column names/ values = column values
        
        // 4. close
        db.close(); 
	}
	
	public User_Info get_user(int user_id){

		// 1. get reference to readable DB
		SQLiteDatabase db = this.getReadableDatabase();
		 
		// 2. build query
        Cursor cursor = 
        		db.query(TABLE_VALUE, // a. table
        		COLUMNS, // b. column names
        		" user_id = ?", // c. selections 
                new String[] { String.valueOf(user_id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        
        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();
 
        // 4. build book object
        User_Info temp = new User_Info();
        temp.set_name(cursor.getString(1));
        temp.set_content(cursor.getString(2));
        temp.set_photo(cursor.getString(3));
        temp.set_audio(cursor.getString(4));        
		db.close();
        // 5. return book
        return temp;
	}
	
	// Get All Books
    public ArrayList<User_Info> getAll_users() {
        ArrayList<User_Info> users=new ArrayList<User_Info>();

        // 1. build the query
        String query = "SELECT  * FROM "+TABLE_VALUE;
 
    	// 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
 
        // 3. go over each row, build book and add it to list
        User_Info temp_value = null;
        if (cursor.moveToFirst()) {
            do {
            	temp_value = new User_Info();
            	temp_value.set_name(cursor.getString(1));
            	temp_value.set_content(cursor.getString(2));
            	temp_value.set_photo(cursor.getString(3));
            	temp_value.set_audio(cursor.getString(4));            	
                // Add book to books
                users.add(temp_value);
            } while (cursor.moveToNext());
        }
        db.close();
//		Log.d("getAllBooks()", values.toString());

        // return books
        return users;
    }   

    // Updating single book
    public int update_user(User_Info user) {

    	// 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
		// 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME,user.get_name()); 
        values.put(KEY_USER_CONTENT,user.get_content()); 
        values.put(KEY_PHOTO, user.get_photo());
        values.put(KEY_AUDIO, user.get_audio());        
        
        // 3. updating row
        int i = db.update(TABLE_VALUE, //table
        		values, // column/value
        		KEY_ID+" = ?", // selections
                new String[] { String.valueOf(user.get_db_id()) }); //selection args        
        // 4. close
        db.close();
        
        return i;
        
    }

    // Deleting single book
    public void delete_user(User_Info user) {
    	
    	// 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();        
        
        // 2. delete
        db.delete(TABLE_VALUE,	KEY_ID+" = ?",
                new String[] { 
        		String.valueOf(user.get_db_id()) }
        );
        
        // 3. close
        db.close();
    }
    public void delete_all(){
    	SQLiteDatabase db = this.getWritableDatabase();        
    
    	// 2. delete
        db.delete(TABLE_VALUE,null,null);        
        
        // 3. close
        db.close();
    }
}
