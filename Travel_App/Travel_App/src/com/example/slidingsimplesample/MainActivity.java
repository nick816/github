package com.example.slidingsimplesample;

import java.io.File;

import com.example.slidingsimplesample.activity.CountrySelectActivity;

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
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        fragmentReplace(0);
        ActionBar bar = getActionBar();
		//for color
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00C4CD")));
		
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
						dialog.dismiss();
					}
				});
				
				mButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						displayAlert(v);
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
	          Bitmap photo = extras.getParcelable("data");
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
	  
	public void displayAlert(View v){
		 DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener()
		    {
		      @Override
		      public void onClick(DialogInterface dialog, int which)
		      {
		        doTakePhotoAction();
		      }
		    };
		    
		    DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener()
		    {
		      @Override
		      public void onClick(DialogInterface dialog, int which)
		      {
		        doTakeAlbumAction();
		      }
		    };
		    		    
		    DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener()
		    {
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