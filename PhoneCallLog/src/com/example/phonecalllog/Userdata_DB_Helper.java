package com.example.phonecalllog;

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
		        +"db_id INTEGER PRIMARY KEY AUTOINCREMENT, " +"user_id TEXT, "+"country TEXT, "+"email TEXT, "+"first_name TEXT, "
				+"last_name TEXT, "+"password TEXT, "+"phone_number TEXT, "+"user_name TEXT, "+"zip_code TEXT, "+"friend_group TEXT, "+"avarta TEXT, " +"gender INTEGER )";
		
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
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_BIRTHDAY = "user_birthday";
    private static final String KEY_COUNTRY = "country";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_FIRSTNAME = "first_name";
    private static final String KEY_LASTNAME = "last_name";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PHONENUMBER = "phone_number";
    private static final String KEY_USERNAME = "user_name";
    private static final String KEY_ZIP_CODE = "zip_code";
    private static final String KEY_GROUP = "friend_group";
    private static final String KEY_AVARTA= "avarta";
    private static final String KEY_GENDER = "gender";
    
    private static final String[] COLUMNS = {KEY_ID,KEY_USER_ID,KEY_USER_BIRTHDAY,KEY_COUNTRY,KEY_EMAIL,KEY_FIRSTNAME,KEY_LASTNAME,KEY_PASSWORD,KEY_PHONENUMBER,KEY_USERNAME,KEY_ZIP_CODE,KEY_GROUP,KEY_AVARTA,KEY_GENDER};
    
	public void add_user(User_Info user){
//		Log.d("addBook", value.toString());
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		 
		// 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID,user.get_user_id()); 
//        values.put(KEY_USER_BIRTHDAY,user.get_birthday()); 
//        values.put(KEY_COUNTRY, user.get_country());
        values.put(KEY_EMAIL, user.get_email());
//        values.put(KEY_FIRSTNAME, user.get_firstname());
//        values.put(KEY_LASTNAME, user.get_lastname());
        values.put(KEY_PASSWORD, user.get_passowrd());
        values.put(KEY_PHONENUMBER, user.get_phonenumber());
        values.put(KEY_USERNAME, user.get_username());
//        values.put(KEY_ZIP_CODE, user.get_zipcode());
        values.put(KEY_GROUP, user.get_category());
        values.put(KEY_AVARTA, user.get_avarta());
        values.put(KEY_GENDER, user.get_gender());
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
        temp.set_user_id(cursor.getString(1));
//        temp.set_birthday(cursor.getString(2));
//        temp.set_country(cursor.getString(3));
        temp.set_email(cursor.getString(4));
//        temp.set_firstname(cursor.getString(5));
//        temp.set_lastname(cursor.getString(6));
        temp.set_password(cursor.getString(7));
        temp.set_phonenumber(cursor.getString(8));
        temp.set_username(cursor.getString(9));
//        temp.set_zipcode(cursor.getString(10));
        temp.set_category(cursor.getString(11));
        temp.set_avarta(cursor.getString(12));
        temp.set_gender(cursor.getInt(13));        
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
            	temp_value.set_user_id(cursor.getString(1));
//            	temp_value.set_birthday(cursor.getString(2));
//            	temp_value.set_country(cursor.getString(3));
            	temp_value.set_email(cursor.getString(4));
//            	temp_value.set_firstname(cursor.getString(5));
//            	temp_value.set_lastname(cursor.getString(6));
            	temp_value.set_password(cursor.getString(7));
            	temp_value.set_phonenumber(cursor.getString(8));
            	temp_value.set_username(cursor.getString(9));
//            	temp_value.set_zipcode(cursor.getString(10));
            	temp_value.set_category(cursor.getString(11));
            	temp_value.set_avarta(cursor.getString(12));
            	temp_value.set_gender(cursor.getInt(13)); 
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
        values.put(KEY_USER_ID,user.get_user_id()); 
//        values.put(KEY_USER_BIRTHDAY,user.get_birthday()); 
//        values.put(KEY_COUNTRY, user.get_country());
        values.put(KEY_EMAIL, user.get_email());
//        values.put(KEY_FIRSTNAME, user.get_firstname());
//        values.put(KEY_LASTNAME, user.get_lastname());
        values.put(KEY_PASSWORD, user.get_passowrd());
        values.put(KEY_PHONENUMBER, user.get_phonenumber());
        values.put(KEY_USERNAME, user.get_username());
//        values.put(KEY_ZIP_CODE, user.get_zipcode());
        values.put(KEY_GROUP, user.get_category());
        values.put(KEY_AVARTA, user.get_avarta());
        values.put(KEY_GENDER, user.get_gender());
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
        db.delete(TABLE_VALUE,
        		KEY_ID+" = ?",
                new String[] { String.valueOf(user.get_db_id()) });
        
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
