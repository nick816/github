package ueda.social.wishing.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import ueda.social.wishing.R;
import ueda.social.wishing.http.Change_profile;
import ueda.social.wishing.http.HttpConstants;
import ueda.social.wishing.http_request.Change_profileHttpRequest;
import ueda.social.wishing.image.SmartImageView;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.IpPrefix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Profile_Activity extends Activity implements Change_profileHttpRequest{

	private static final int REQUEST_CAMERA=1;
	private static final int SELECT_FILE=2;
	private static final int CROP_IMAGE=3;
	
	private Button from_gallary,from_camera,btn_save;
	private SmartImageView profile;
	private RadioGroup gender;
	private RadioButton gender_male,gender_female;
	private Spinner birth_day,birth_month,birth_year;
	private EditText old_pass,new_pass,confirm_pass,first_name,last_name,country,post_code,email,mobile_number,username,tick;
	
	private String[] days=new String[31];
	private String[] years=new String[200];
	private String old_password,oldpass,newpass,confirmpass,str_first,str_last,str_country,str_post,str_email,str_mobile,str_username,str_birthday,str_birth_day,str_birth_month,str_birth_year;
	private int current_user_id=0,user_gender;
	private String extStorageDirectory;
	private Change_profileHttpRequest c=this;
	int serverResponseCode = 0;
	private String sourceFile="";
	private String motherFile="";
	private String avatar_file="";
	private Bitmap thePic;
	Bundle bndanimation_to_child,bndanimation_to_parent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_activity);
		
		bndanimation_to_child = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation,R.anim.animation2).toBundle();
		bndanimation_to_parent = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation3,R.anim.animation4).toBundle();
		// get the action bar
		ActionBar actionBar = getActionBar();
		// Enabling Back navigation on Action Bar icon
		actionBar.setDisplayHomeAsUpEnabled(true);		
		
		btn_save=(Button)this.findViewById(R.id.btn_change_profile);
		profile=(SmartImageView)this.findViewById(R.id.img_profile);
		from_camera=(Button)this.findViewById(R.id.btn_profile_camera);
		from_gallary=(Button)this.findViewById(R.id.btn_profile_gallery);
		tick=(EditText)this.findViewById(R.id.my_tick);
		old_pass=(EditText)this.findViewById(R.id.profile_old_password);
		new_pass=(EditText)this.findViewById(R.id.profile_sign_password);
		confirm_pass=(EditText)this.findViewById(R.id.profile_confirm_pass);
		first_name=(EditText)this.findViewById(R.id.profile_firstname);
		last_name=(EditText)this.findViewById(R.id.profile_lastname);
		country=(EditText)this.findViewById(R.id.profile_country);
		post_code=(EditText)this.findViewById(R.id.profile_zipcode);
		email=(EditText)this.findViewById(R.id.profile_email);
		mobile_number=(EditText)this.findViewById(R.id.profile_phone_number);
		username=(EditText)this.findViewById(R.id.profile_sign_username);
		gender=(RadioGroup)this.findViewById(R.id.profile_gender);
		gender_male=(RadioButton)this.findViewById(R.id.profile_male);
		gender_female=(RadioButton)this.findViewById(R.id.profile_female);
		birth_day=(Spinner)this.findViewById(R.id.profile_birth_day);
		birth_month=(Spinner)this.findViewById(R.id.profile_birth_month);
		birth_year=(Spinner)this.findViewById(R.id.profile_birth_year);
				
		for (int i = 1; i <= 31; i++) {
			days[i-1]=String.valueOf(i);
		}
        for (int i = 0; i <200 ; i++) {
			years[i]=String.valueOf(i+1900);
		}
        
        ArrayAdapter<String> dayadapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,days);
        dayadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        birth_day.setAdapter(dayadapter);
        ArrayAdapter<String> yearadapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,years);
        yearadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        birth_year.setAdapter(yearadapter);   
        
		extStorageDirectory = Environment.getExternalStorageDirectory().toString();
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		current_user_id=sharedPreferences.getInt("current_userid", -1);	
		old_password=sharedPreferences.getString("password", "");
		avatar_file=sharedPreferences.getString("avatar_image", "");
		username.setText(sharedPreferences.getString("username", ""));
		first_name.setText(sharedPreferences.getString("first_name", ""));
		last_name.setText(sharedPreferences.getString("last_name", ""));
		country.setText(sharedPreferences.getString("country", ""));
		post_code.setText(sharedPreferences.getString("zipcode", ""));
		email.setText(sharedPreferences.getString("email", ""));
		mobile_number.setText(sharedPreferences.getString("phonenumber", ""));
		tick.setText(sharedPreferences.getString("tick", ""));
		
		String day_of_birth,month_of_birth,year_of_birth,temp_birth;
		temp_birth=sharedPreferences.getString("birthday", "");
		day_of_birth=temp_birth.substring(0, 2);
		month_of_birth=temp_birth.substring(3,5);
		year_of_birth=temp_birth.substring(6);
		
		birth_day.setSelection(Integer.parseInt(day_of_birth)-1);
		birth_month.setSelection(Integer.parseInt(month_of_birth)-1);
		birth_year.setSelection(Integer.parseInt(year_of_birth)-1900);
		
		if (sharedPreferences.getInt("gender", -1)==0) {
			gender_male.setSelected(true);
			gender_female.setSelected(false);
		}
		else{
			gender_male.setSelected(false);
			gender_female.setSelected(true);
		}
		
		if (avatar_file.equals("")) {
			profile.setImageResource(R.drawable.empty_user);
		}
		else{
			profile.setImageUrl(HttpConstants.PROFILE_IMAGE+avatar_file);
		}
		
		birth_day.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				str_birth_day=parent.getItemAtPosition(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
				str_birth_day="1";
			}
		});
        birth_month.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
				str_birth_month=String.valueOf(position+1);	
//				Log.d("Birth_month", birth_month);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				str_birth_month="January";
			}
		});
        birth_year.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				str_birth_year=parent.getItemAtPosition(position).toString();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				str_birth_year="1900";
			}
		});
		
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
		btn_save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int selectedId = gender.getCheckedRadioButtonId(); 
			    if (selectedId==R.id.profile_male) {
					user_gender=0;
				}
			    else{
			    	user_gender=1;
			    }
			    str_first=first_name.getText().toString();
		    	str_last=last_name.getText().toString();
		    	str_country=country.getText().toString();
		    	str_post=post_code.getText().toString();
		    	str_email=email.getText().toString();
		    	str_mobile=mobile_number.getText().toString();
		    	str_username=username.getText().toString();		    	
		    	str_birthday=padding(Integer.parseInt(str_birth_day))+"-"+padding(Integer.parseInt(str_birth_month))+"-"+str_birth_year;
				oldpass=old_pass.getText().toString();
				newpass=new_pass.getText().toString();
				confirmpass=confirm_pass.getText().toString();
				String str_tick=tick.getText().toString();
				
				if (!old_password.equals(oldpass)) {
					Toast.makeText(Profile_Activity.this, "Enter correct old password.", Toast.LENGTH_SHORT).show();
				}
				else if (new_pass.getText().toString().equals("")||new_pass.getText().toString().length()<8) {
					Toast.makeText(Profile_Activity.this, "Enter new password at least 8 characters", Toast.LENGTH_SHORT).show();
				}		
				else if (confirm_pass.getText().toString().equals("")){
					Toast.makeText(Profile_Activity.this, "Enter confirm password", Toast.LENGTH_SHORT).show();
				}
				else if(!newpass.equals(confirmpass)){
					Toast.makeText(Profile_Activity.this, "Enter correct confirm password.", Toast.LENGTH_SHORT).show();
				}
				else{					
					Change_profile change_profile=new Change_profile(Profile_Activity.this, String.valueOf(current_user_id), str_first, str_last, user_gender, str_birthday, str_country, str_post, str_email, str_mobile, str_username, newpass, sourceFile,str_tick, c);
					change_profile.onRun();
				}
			}
		});
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
    			profile.setImageBitmap(thePic);
    			OutputStream outStream = null;
	        	   File file = new File(extStorageDirectory, "avatar.png");	        	   
	        	   try {
	        	    outStream = new FileOutputStream(file);
	        	    thePic.compress(Bitmap.CompressFormat.PNG, 100, outStream);
	        	    outStream.flush();
	        	    outStream.close();	        	   
	        	    Toast.makeText(Profile_Activity.this, "Saved", Toast.LENGTH_SHORT).show();	  
	        	    sourceFile=file.getPath();
	        	   } catch (FileNotFoundException e) {
	        	    // TODO Auto-generated catch block
	        	    e.printStackTrace();
	        	    Toast.makeText(Profile_Activity.this, e.toString(), Toast.LENGTH_LONG).show();
	        	   } catch (IOException e) {
	        	    // TODO Auto-generated catch block
	        	    e.printStackTrace();
	        	    Toast.makeText(Profile_Activity.this, e.toString(), Toast.LENGTH_LONG).show(); 
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
		cropIntent.putExtra("outputX", 160);
		cropIntent.putExtra("outputY", 160);
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

	public String padding(int temp){
    	if (temp<10) {
			return "0"+String.valueOf(temp);
		}
    	else{
    		return String.valueOf(temp);    
    	}
    }
	@Override
	public void change_profile_requestFailure(String errMsg) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void change_profile_requestSuccess() {
		// TODO Auto-generated method stub
		if (!motherFile.equals("")) {
			File file1=new File(motherFile);
			file1.delete();
		}
		if (!sourceFile.equals("")) {
			File file2=new File(sourceFile);
			file2.delete();
		}
		Toast.makeText(Profile_Activity.this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
		Intent intent=new Intent(Profile_Activity.this,SecondActivity.class);
		startActivity(intent,bndanimation_to_parent);
		finish();
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
		Intent intent=new Intent(Profile_Activity.this,SecondActivity.class);
		startActivity(intent,bndanimation_to_parent);
		finish();
		return true;
	}
	@Override
	public void onBackPressed(){
		
	}
}