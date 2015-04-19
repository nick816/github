package com.example.slidingsimplesample;

import java.io.File;

import com.example.slidingsimplesample.activity.CountrySelectActivity;
import com.parse.ParseFile;
import com.parse.ParseObject;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends BaseActivity{

	private static final int PICK_FROM_CAMERA = 0;
	private static final int PICK_FROM_ALBUM = 1;
	private static final int CROP_FROM_CAMERA = 2;

	@SuppressWarnings("unused")
	private FrameLayout flContainer;

	private Spinner age, sex;
	@SuppressWarnings("unused")
	private String _age, _sex, _now;
	private String[] man_age = new String[100];

	private Uri mImageCaptureUri;
	final Context context = this;
	ImageView mButton, back, mPhotoImageView;

	private String countryName;
	private TextView txtCountry, number, share;
	private TextView tvAddress;
	private EditText about;
	private ImageView background;
	Bitmap photo ;

    @SuppressWarnings("unused")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        fragmentReplace(0);
        ActionBar bar = getActionBar();
		//for color
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00C4CD")));
		
		
		Intent country_get = getIntent();

		background = (ImageView)this.findViewById(R.id.background_set);

		countryName = country_get.getStringExtra("countryName");
		txtCountry = (TextView)this.findViewById(R.id.country_id);
		share = (TextView)this.findViewById(R.id.profile_share);
		share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Convert it to byte
//				ByteArrayOutputStream stream = new ByteArrayOutputStream();				
//				byte[] image = stream.toByteArray();
				
				byte[] image = "profile".getBytes();

				ParseFile file = new ParseFile("profile.png", image);
				file.saveInBackground();

				ParseObject data = new ParseObject("travel");				
				data.put("location", tvAddress.getText().toString());
				data.put("hometown", txtCountry.getText().toString());
				data.put("profile", file);
				data.saveInBackground();
			}
		});
		txtCountry.setText(countryName);

		age = (Spinner)this.findViewById(R.id.spinner_age);
		sex = (Spinner)this.findViewById(R.id.spinner_sex);

		for (int i = 1; i <= 100; i++){
			man_age[i-1]=String.valueOf(i);
		}
		ArrayAdapter<String> age_adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, man_age);
		age_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        age.setAdapter(age_adapter);

        age.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				_age = parent.getItemAtPosition(position).toString();	
//				_age=String.valueOf(position);

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				_age = "1";
			}

		});

        sex.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				_sex = parent.getItemAtPosition(position).toString();

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				_sex = "";

			}
		});

		TextView edit = (TextView)this.findViewById(R.id.edit_profile);
		edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final Dialog dialog = new Dialog(context);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.edit_photo);

				Window dialogWindow = dialog.getWindow();
				dialogWindow.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

				dialog.setCanceledOnTouchOutside(false);

				mButton = (ImageView)dialog.findViewById(R.id.take_image);
				mPhotoImageView = (ImageView)dialog.findViewById(R.id.preview);

				back = (ImageView)dialog.findViewById(R.id.back);
				back.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if(photo == null){
							dialog.dismiss();
						}else{
							displayAlert1(v);
							dialog.dismiss();
						}

					}
					
				});

				mButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						displayAlert2(v);						
					}
				});

				dialog.show();
			}
		});

		TextView country = (TextView)this.findViewById(R.id.country_select);
		country.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent coun = new Intent(MainActivity.this, CountrySelectActivity.class);
				startActivity(coun);
			}
		});

		//Current Location by GPS
		tvAddress = (TextView)findViewById(R.id.tvAddress);
		String address = "";
		GPSService mGPSService = new GPSService(context);
		mGPSService.getLocation();

		if (mGPSService.isLocationAvailable == false) {

			// Here you can ask the user to try again, using return; for that
//			Toast.makeText(getActivity(), "Your location is not available, please try again.", Toast.LENGTH_SHORT).show();
			return;

			// Or you can continue without getting the location, remove the return; above and uncomment the line given below
			// address = "Location not available";
		} else {

			// Getting location co-ordinates
			double latitude = mGPSService.getLatitude();
			double longitude = mGPSService.getLongitude();
			address = mGPSService.getLocationAddress();
			tvAddress.setText("" + address);
		}

		// make sure you close the gps after using it. Save user's battery power
		mGPSService.closeGPS();

		about = (EditText)this.findViewById(R.id.about_text);
		number = (TextView)this.findViewById(R.id.number);
		
		about.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				number.setText(s.length()+"/200");
				if(s.length() == 250){
					Toast.makeText(MainActivity.this, "Sorry, you can't enter more characters!", Toast.LENGTH_SHORT).show();					
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}
			
			@Override
			public void afterTextChanged(Editable s) {

			}
		});
    }

    private void doTakePhotoAction()
	  {
	    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    
	    // Create a route to the temporary used file
	    String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
	    mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));
	    
	    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
	    //intent.putExtra("return-data", true);
	    startActivityForResult(intent, PICK_FROM_CAMERA);
	  }

	  /**
	   * Take image to Album
	   */
	  private void doTakeAlbumAction()
	  {
	    // Call image
	    Intent intent = new Intent(Intent.ACTION_PICK);
	    intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
	    startActivityForResult(intent, PICK_FROM_ALBUM);
	  }

	  @Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent data)
	  {
	    if(resultCode != RESULT_OK)
	    {
	      return;
	    }

	    switch(requestCode)
	    {
	      case CROP_FROM_CAMERA:
	      {

	        final Bundle extras = data.getExtras();

	        if(extras != null)
	        {
	          photo= extras.getParcelable("data");
	          mPhotoImageView.setImageBitmap(photo);
	        }

	     // Delete temporary.
	        File f = new File(mImageCaptureUri.getPath());
	        if(f.exists())
	        {
	          f.delete();
	        }

	        break;
	      }

	      case PICK_FROM_ALBUM:
	      {	        
	    	  mImageCaptureUri = data.getData();
	      }

	      case PICK_FROM_CAMERA:
	      {

	        Intent intent = new Intent("com.android.camera.action.CROP");
	        intent.setDataAndType(mImageCaptureUri, "image/*");

	        intent.putExtra("outputX", 90);
	        intent.putExtra("outputY", 90);
	        intent.putExtra("aspectX", 1);
	        intent.putExtra("aspectY", 1);
	        intent.putExtra("scale", true);
	        intent.putExtra("return-data", true);
	        startActivityForResult(intent, CROP_FROM_CAMERA);

	        break;
	      }
	    }
	  }

		private void displayAlert1(View v) {
			DialogInterface.OnClickListener saveListener = new DialogInterface.OnClickListener(){
				 
			      @Override
			      public void onClick(DialogInterface dialog, int which)
			      {
				    	  background.setImageBitmap(photo);
				    	  dialog.dismiss();
			      }
			 };
			 
			DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener(){

			      @Override
			      public void onClick(DialogInterface dialog, int which)
			      {
			    	  dialog.dismiss();
			      }
		    };

		    new AlertDialog.Builder(this)
		      .setTitle("Set as profile picture?")
		      .setNeutralButton("Save", saveListener)
		      .setNegativeButton("Cancel", cancelListener)
		      .show();
		}
		public void displayAlert2(View v){

			 DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener(){

			      @Override
			      public void onClick(DialogInterface dialog, int which)
			      {
			        doTakePhotoAction();
			      }
			 };

			 DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener(){

			      @Override
			      public void onClick(DialogInterface dialog, int which)
			      {
			        doTakeAlbumAction();
			      }
			 };

			 DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener(){

			      @Override
			      public void onClick(DialogInterface dialog, int which)
			      {
			        dialog.dismiss();
			      }
			 };

			 new AlertDialog.Builder(this)
			      .setTitle("Select image to upload")
			      .setPositiveButton("Photo shoot", cameraListener)
			      .setNeutralButton("Select album", albumListener)
			      .setNegativeButton("Cancel", cancelListener)
			      .show();
		}	
}