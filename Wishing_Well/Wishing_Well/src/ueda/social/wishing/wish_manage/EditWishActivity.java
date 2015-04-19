package ueda.social.wishing.wish_manage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import ueda.social.wishing.R;
import ueda.social.wishing.activity.WishListActivity;
import ueda.social.wishing.http.HttpConstants;
import ueda.social.wishing.http.WishUpdate;
import ueda.social.wishing.http_request.WishUpdateHttpRequest;
import ueda.social.wishing.image.SmartImageView;
import ueda.social.wishing.model.User_Info;
import ueda.social.wishing.model.Userdata_DB_Helper;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class EditWishActivity extends Activity implements WishUpdateHttpRequest{

	private static final int REQUEST_CAMERA=1;
	private static final int SELECT_FILE=2;
	private static final int CROP_IMAGE=3;
	private Bundle bndanimation_to_child,bndanimation_to_parent;
	private Spinner day,month,year,event;
	private EditText wish_title,wish_desc,wish_code,wish_price;
	private Button from_gallary,from_camera;
	private String[] days=new String[31];
	private String[] years=new String[85];
	private String sourceFile="";
	private String motherFile="";
	private SmartImageView wish_img;
	private Bitmap thePic;
	private String extStorageDirectory;
	private ArrayList<User_Info> friends=new ArrayList<User_Info>();
	private CharSequence[] friends_name;
	private ArrayList<String> friends_id=new ArrayList<String>();
	private boolean[] selected_friends; 
	private Userdata_DB_Helper db;
	private String wish_event,title,product_code,price,description,wish_date,visible_all,visible_friend_ids,wish_time,wish_day,wish_month,wish_year;
	private int current_user_id,wish_id;
	private String[] events={"Birthday","X mas","Wedding","Anniversary","Valentine day","House warming","Leaving present","New baby","Other"};
	private WishUpdateHttpRequest wr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_wish);
		
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		current_user_id=sharedPreferences.getInt("current_userid", -1);			

		wr=this;
		visible_friend_ids="";
		bndanimation_to_child = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation, R.anim.animation2).toBundle();
		bndanimation_to_parent = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation3, R.anim.animation4).toBundle();
		
		// get the action bar
		ActionBar actionBar = getActionBar();
		// Enabling Back navigation on Action Bar icon
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		db=new Userdata_DB_Helper(this);
		
		friends=db.getAll_users(); 
		friends_name=new CharSequence[friends.size()];
		selected_friends=new boolean[friends.size()];
		for (int i = 0; i < friends.size(); i++) {
			friends_name[i]=friends.get(i).get_username();
			friends_id.add(friends.get(i).get_user_id());
			selected_friends[i]=false;
		}
		event=(Spinner)this.findViewById(R.id.event_spinner_edit);
		day=(Spinner)this.findViewById(R.id.wish_day_spinner_edit);
		month=(Spinner)this.findViewById(R.id.wish_month_spinner_edit);
		year=(Spinner)this.findViewById(R.id.wish_year_spinner_edit);
		
		for (int i = 1; i <= 31; i++) {
			days[i-1]=String.valueOf(i);
		}
        for (int i = 0; i <85 ; i++) {
			years[i]=String.valueOf(i+2015);
		}
        ArrayAdapter<String> dayadapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,days);
        dayadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day.setAdapter(dayadapter);
        ArrayAdapter<String> yearadapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,years);
        yearadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(yearadapter);   
		
        wish_title=(EditText)this.findViewById(R.id.wish_title_edit);
        wish_desc=(EditText)this.findViewById(R.id.wish_desc_edit);
        wish_code=(EditText)this.findViewById(R.id.wish_code_edit);
        wish_price=(EditText)this.findViewById(R.id.wish_price_edit);
        from_camera=(Button)this.findViewById(R.id.btn_wish_camera_edit);
        from_gallary=(Button)this.findViewById(R.id.btn_wish_gallery_edit);
        wish_img=(SmartImageView)this.findViewById(R.id.img_wish_edit);
        
        extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        
///////////////////////Init Window/////////////////////////////////
//		editor.putString("wish_id", my_wish.get(position).get_wish_id());
//		editor.putString("wish_title", my_wish.get(position).get_title());
//		editor.putString("wish_event", my_wish.get(position).get_event());
//		editor.putString("wish_date", my_wish.get(position).get_wish_date());
//		editor.putString("wish_product", my_wish.get(position).get_product_code());
//		editor.putString("wish_price", my_wish.get(position).get_price());
//		editor.putString("wish_description",my_wish.get(position).get_description());
//		editor.putString("wish_image", my_wish.get(position).get_img());
//		editor.putString("wish_shared_all", my_wish.get(position).get_visible_all());
//		editor.putString("wish_shared", my_wish.get(position).get_visible_friend_ids());
        wish_id=sharedPreferences.getInt("wish_id", -1);
        wish_title.setText(sharedPreferences.getString("wish_title", ""));
        wish_desc.setText(sharedPreferences.getString("wish_description", ""));
        wish_code.setText(sharedPreferences.getString("wish_product", ""));
        wish_price.setText(sharedPreferences.getString("wish_price", ""));        
        wish_img.setImageUrl(HttpConstants.PROFILE_IMAGE+sharedPreferences.getString("wish_image", ""));
        wish_date=sharedPreferences.getString("wish_date", "");
        
        String day_of_birth,month_of_birth,year_of_birth;		
		day_of_birth=wish_date.substring(0, 2);
		month_of_birth=wish_date.substring(3,5);
		year_of_birth=wish_date.substring(6);
		Log.d("fhuiahuaghrua", month_of_birth+""+day_of_birth+""+year_of_birth);
		event.setSelection(Integer.parseInt(sharedPreferences.getString("wish_event", ""))-1);

		day.setSelection(Integer.parseInt(day_of_birth)-1);
		month.setSelection(Integer.parseInt(month_of_birth)-1);
		year.setSelection(Integer.parseInt(year_of_birth)-2015);
        
        from_camera.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				/*create instance of File with name img.jpg*/
				File file = new File(Environment.getExternalStorageDirectory()+File.separator + "temp.jpg");
				motherFile=file.getPath();
				/*put uri as extra in intent object*/
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
				/*start activity for result pass intent as argument and request code */
				startActivityForResult(intent, REQUEST_CAMERA);
			}
		});
		from_gallary.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(
                        Intent.createChooser(intent, "Select File"),
                        SELECT_FILE);
			}
		});
        event.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//				String temp=event.getSelectedItem().toString();
//				Toast.makeText(NewWishActivity.this, temp, Toast.LENGTH_SHORT).show();
				switch (position) {
				case 1:		
					day.setSelection(24);
					month.setSelection(11);
					break;
				case 4:
					day.setSelection(13);
					month.setSelection(1);
					break;				
				}
				wish_event=String.valueOf(position+1);
				Log.d("Wish_event", wish_event);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				wish_event="1";
			}
		});
        day.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				wish_day=parent.getItemAtPosition(position).toString();				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				wish_day="1";
			}
		});
        month.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				wish_month=String.valueOf(position+1).toString();		
				Log.d("Wish Month", wish_month);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				wish_month="January";
				Log.d("Wish Month", wish_month);
			}
		});
        year.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				wish_year=parent.getItemAtPosition(position).toString();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				wish_year="2015";
			}
		});        
	}

	public String padding(int temp){
    	if (temp<10) {
			return "0"+String.valueOf(temp);
		}
    	else{
    		return String.valueOf(temp);    
    	}
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
            	File file = new File(Environment.getExternalStorageDirectory()+File.separator + "temp.jpg");
    			//Crop the captured image using an other intent
    			try {
    				/*the user's device may not support cropping*/
    				cropCapturedImage(Uri.fromFile(file));
    			}
    			catch(ActivityNotFoundException aNFE){
    				//display an error message if user device doesn't support
    				String errorMessage = "Sorry - your device doesn't support the crop action!";
    				Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
    				toast.show();
    			}

            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData(); 
                try {
    				/*the user's device may not support cropping*/
    				cropCapturedImage(selectedImageUri);
    			}
    			catch(ActivityNotFoundException aNFE){
    				//display an error message if user device doesn't support
    				String errorMessage = "Sorry - your device doesn't support the crop action!";
    				Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
    				toast.show();
    			}
            }
            else if(requestCode==CROP_IMAGE){
    			//Create an instance of bundle and get the returned data
    			Bundle extras = data.getExtras();
    			//get the cropped bitmap from extras
    			thePic = extras.getParcelable("data");
    			//set image bitmap to image view
    			wish_img.setImageBitmap(thePic);
    			OutputStream outStream = null;
	        	   File file = new File(extStorageDirectory, "wish.png");	        	   
	        	   try {
	        	    outStream = new FileOutputStream(file);
	        	    thePic.compress(Bitmap.CompressFormat.PNG, 100, outStream);
	        	    outStream.flush();
	        	    outStream.close();	        	   
	        	    Toast.makeText(EditWishActivity.this, "Saved", Toast.LENGTH_SHORT).show();	  
	        	    sourceFile=file.getPath();
	        	   } catch (FileNotFoundException e) {
	        	    // TODO Auto-generated catch block
	        	    e.printStackTrace();
	        	    Toast.makeText(EditWishActivity.this, e.toString(), Toast.LENGTH_LONG).show();
	        	   } catch (IOException e) {
	        	    // TODO Auto-generated catch block
	        	    e.printStackTrace();
	        	    Toast.makeText(EditWishActivity.this, e.toString(), Toast.LENGTH_LONG).show(); 
	        	  }
    		}
        }
    }
	
	public void cropCapturedImage(Uri picUri){
		//call the standard crop action intent 
		Intent cropIntent = new Intent("com.android.camera.action.CROP");
		//indicate image type and Uri of image
		cropIntent.setDataAndType(picUri, "image/*");
		//set crop properties
		cropIntent.putExtra("crop", "true");
		//indicate aspect of desired crop
		cropIntent.putExtra("aspectX", 1);
		cropIntent.putExtra("aspectY", 1);
		//indicate output X and Y
		cropIntent.putExtra("outputX", 512);
		cropIntent.putExtra("outputY", 512);
		cropIntent.putExtra("scale", true);
		//retrieve data on return
		cropIntent.putExtra("return-data", true);
		//start the activity - we handle returning in onActivityResult
		startActivityForResult(cropIntent,CROP_IMAGE);
	}
	public String getPath(Uri uri, Activity activity) {
        String[] projection = { MediaColumns.DATA };
        Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_wish, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.share_all:
			visible_all="1";
			break;
		case R.id.select:
			visible_all="0";
			showDialog(0);
			break;
		case R.id.done:			
			break;
		case R.id.save_wish:
			onSave();
			break;
		case R.id.pick_wish:
			onPick();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected Dialog onCreateDialog( int id ) 
	{
		return 
		new AlertDialog.Builder( this)
        	.setTitle( "Friends" )
        	.setMultiChoiceItems( friends_name, selected_friends, new DialogSelectionClickHandler() )
        	.setPositiveButton( "OK", new DialogButtonClickHandler() )        	
        	.create();
	}
	
	
	public class DialogSelectionClickHandler implements DialogInterface.OnMultiChoiceClickListener
	{
		public void onClick( DialogInterface dialog, int clicked, boolean selected )
		{
			Log.i( "ME", friends_name[ clicked ] + " selected: " + selected );
		}
	}
	

	public class DialogButtonClickHandler implements DialogInterface.OnClickListener
	{
		public void onClick(DialogInterface dialog, int clicked)
		{
			switch(clicked)
			{
				case DialogInterface.BUTTON_POSITIVE:
					printSelectedPlanets();
					break;
			}
		}
	}
	
	protected void printSelectedPlanets(){
		visible_friend_ids="";
		for( int i = 0; i < friends_name.length; i++ ){
			if (selected_friends[i]) {
				visible_friend_ids+=","+friends_id.get(i);
			}				
		}
		visible_friend_ids+=",";
	}
	
	@Override
	public boolean onNavigateUp() {
		// TODO Auto-generated method stub
		if (!motherFile.equals("")) {
			File file1=new File(motherFile);
			file1.delete();
		}
		if (!sourceFile.equals("")) {
			File file2=new File(sourceFile);
			file2.delete();
		}		
		Intent intent=new Intent(EditWishActivity.this,WishListActivity.class);
		startActivity(intent,bndanimation_to_parent);
		finish();
		return true;
	}
	@Override
	public void onBackPressed(){
		
	}
	public void onSave(){
		wish_time="";
		wish_date=padding(Integer.parseInt(wish_day))+"-"+padding(Integer.parseInt(wish_month))+"-"+wish_year;
		title=wish_title.getText().toString();
		description=wish_desc.getText().toString();
				
		if(wish_code.getText().toString().equals("")){
			product_code="";
		}
		else{
			product_code=wish_code.getText().toString();
		}
		if(wish_price.getText().toString().equals("")){
			price="";
		}
		else{
			price=wish_price.getText().toString();
		}
		if (!visible_all.equals("1")&&visible_friend_ids.length()<4) {
			Toast.makeText(EditWishActivity.this, "Select share friends!", Toast.LENGTH_SHORT).show();
		}else{
			if (!wish_date.equals("")&&!title.equals("")&&!description.equals("")&&!wish_event.equals("")) {
				WishUpdate update=new WishUpdate(EditWishActivity.this,String.valueOf(current_user_id), String.valueOf(wish_id)
						, wish_event,title, description, product_code, price, wish_date, wish_time, visible_all, visible_friend_ids,"0", sourceFile, wr);
				update.onRun();
			}else{
				Toast.makeText(EditWishActivity.this, "Enter wish details!", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	public void onPick(){
		wish_time="";
		wish_date=padding(Integer.parseInt(wish_day))+"-"+padding(Integer.parseInt(wish_month))+"-"+wish_year;
		title=wish_title.getText().toString();
		description=wish_desc.getText().toString();
		if(wish_code.getText().toString().equals("")){
			product_code="";
		}
		else{
			product_code=wish_code.getText().toString();
		}
		if(wish_price.getText().toString().equals("")){
			price="";
		}
		else{
			price=wish_price.getText().toString();
		}
		if (!visible_all.equals("1")&&visible_friend_ids.length()<4) {
			Toast.makeText(EditWishActivity.this, "Select share friends!", Toast.LENGTH_SHORT).show();
		}else{
			if (!wish_date.equals("")&&!title.equals("")&&!description.equals("")&&!wish_event.equals("")) {
				WishUpdate update=new WishUpdate(EditWishActivity.this,String.valueOf(current_user_id), String.valueOf(wish_id)
						, wish_event,title, description, product_code, price, wish_date, wish_time, visible_all, visible_friend_ids,"1", sourceFile, wr);
				update.onRun();
			}else{
				Toast.makeText(EditWishActivity.this, "Enter wish details!", Toast.LENGTH_SHORT).show();
			}
		}
	}
	@Override
	public void wish_update_requestFailure(String errMsg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void wish_update_requestSuccess() {
		// TODO Auto-generated method stub
		Toast.makeText(EditWishActivity.this, "Present updated!", Toast.LENGTH_SHORT).show();
		if (!motherFile.equals("")) {
			File file1=new File(motherFile);
			file1.delete();
		}
		if (!sourceFile.equals("")) {
			File file2=new File(sourceFile);
			file2.delete();
		}		
		Intent intent=new Intent(EditWishActivity.this,WishListActivity.class);
		startActivity(intent,bndanimation_to_parent);
		finish();
	}
}
