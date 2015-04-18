package com.example.starnote;

import java.io.File;
import java.io.IOException;

import com.example.Model.User_Info;
import com.example.Model.Userdata_DB_Helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddNewActivity extends Activity implements OnClickListener {

	private static final int REQUEST_CAMERA=1;
	private static final int SELECT_FILE=2;
	private static final int RQS_OPEN_AUDIO_MP3=3;
	
	private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp3";
	private static final String AUDIO_RECORDER_FOLDER = "Star Note";
	
	private int output_formats = MediaRecorder.OutputFormat.MPEG_4;
	private int btn_count=0;
	private String file_exts =  AUDIO_RECORDER_FILE_EXT_MP4;
	private String audiofile;
	private String imagefile;
	
	private MediaRecorder recorder = null;
	private MediaPlayer mPlayer;
	
	private long startTime = 0L;
	long timeInMilliseconds = 0L;
	long timeSwapBuff = 0L;
	long updatedTime = 0L;
	
	private Uri audio,image;
	
	private Handler customHandler = new Handler();
	
	private ImageView photoImage,audioImage;
	private EditText name,content;
	
	private Userdata_DB_Helper db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_note);
		
		photoImage=(ImageView)this.findViewById(R.id.photo_image);
		audioImage=(ImageView)this.findViewById(R.id.audio_image);
		name=(EditText)this.findViewById(R.id.edit_headline);
		content=(EditText)this.findViewById(R.id.edit_content);
		
		audioImage.setOnClickListener(this);
		photoImage.setOnClickListener(this);
		
		audioImage.setEnabled(false);
		photoImage.setEnabled(false);
		
		db=new Userdata_DB_Helper(AddNewActivity.this);
		
		audio=Uri.parse("");
		image=Uri.parse("");
		imagefile="";
		audiofile="";
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {				
		getMenuInflater().inflate(R.menu.menu_setting, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
			
		int id = item.getItemId();
		switch (id) {			
			case R.id.save:	
				User_Info temp=new User_Info();
				temp.set_name(name.getText().toString());
				temp.set_content(content.getText().toString());
				temp.set_audio(audiofile);
				temp.set_photo(imagefile);
				db.add_user(temp);
				Intent intent =  new Intent(AddNewActivity.this, MainActivity.class);
				startActivity(intent);
				break;
			case R.id.add_img:	
				selectImage();
				break;			
			case R.id.add_record:
				selectAudio();
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("image.jpg")) {
                        f = temp;
                        break;
                    }
                }
	            if (!image.toString().equals("")) {
					photoImage.setImageURI(image);
				}
            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                image=selectedImageUri;
                String tempPath = getPath(selectedImageUri, AddNewActivity.this);
                imagefile=tempPath;
                if (!image.toString().equals("")) {
					photoImage.setImageURI(image);
				}
            }else if(requestCode==RQS_OPEN_AUDIO_MP3){
            	Uri audioFileUri = data.getData();
            	audio=audioFileUri;
            	String audiotempPath=getPath(audioFileUri, AddNewActivity.this);
                audiofile=audiotempPath;
                if (!audio.toString().equals("")) {	
                	audioImage.setImageResource(R.drawable.audio1);	
					audioImage.setEnabled(true);
                }
            }
            Toast.makeText(AddNewActivity.this, imagefile, Toast.LENGTH_SHORT).show();
            Toast.makeText(AddNewActivity.this, audiofile, Toast.LENGTH_SHORT).show();
        }
    }
	
	public String getPath(Uri uri, Activity activity) {
        String[] projection = { MediaColumns.DATA };
        @SuppressWarnings("deprecation")
		Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
	
	private void selectImage() {
		final CharSequence[] items = { "Take Photo", "Choose from Library",
		          "Cancel" };
		
		AlertDialog.Builder builder = new AlertDialog.Builder(AddNewActivity.this);
		builder.setTitle("Add Photo");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
		    public void onClick(DialogInterface dialog, int item) {
		        if (items[item].equals("Take Photo")) {
		            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		            File f = new File(android.os.Environment
		                    .getExternalStorageDirectory()+"/Star Note/", "image.jpg");
		            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		            image=Uri.fromFile(f);
		            imagefile=f.getPath();
		            startActivityForResult(intent, REQUEST_CAMERA);
		        } else if (items[item].equals("Choose from Library")) {
		            Intent intent = new Intent(
		                    Intent.ACTION_PICK,
		                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		            intent.setType("image/*");
		            startActivityForResult(
		                    Intent.createChooser(intent, "Select File"),
		                    SELECT_FILE);
		        } else if (items[item].equals("Cancel")) {
		            dialog.dismiss();
		        }
			}
		});
		builder.show();
	}
	
	public void selectAudio() {
		// TODO Auto-generated method stub
		final CharSequence[] items = { "Record Audio", "Choose from Library",
        "Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(AddNewActivity.this);
		builder.setTitle("Add Audio");
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int item) {
		        if (items[item].equals("Record Audio")) {
		        	final Dialog dialog_record = new Dialog(AddNewActivity.this);
		        	dialog_record.setContentView(R.layout.dialog_recording);
		        	dialog_record.setTitle("Record Audio");
		        	
		        	startTime = 0L;
		        	timeInMilliseconds = 0L;
		        	timeSwapBuff = 0L;
		        	updatedTime = 0L;
		        	
					final TextView file_name=(TextView)dialog_record.findViewById(R.id.recording_audio_name);
					final TextView timer_value=(TextView)dialog_record.findViewById(R.id.timerValue);
					final ImageView mic_image=(ImageView)dialog_record.findViewById(R.id.mic_image);
					final Button start = (Button) dialog_record.findViewById(R.id.btn_record_start);
					final Button save=(Button)dialog_record.findViewById(R.id.btn_audio_save);
					
					file_name.setText("audio.mp3");
					// if button is clicked, close the custom dialog
					final Runnable updateTimerThread = new Runnable() {

						public void run() {
							
							timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
							
							updatedTime = timeSwapBuff + timeInMilliseconds;

							int secs = (int) (updatedTime / 1000);
							int mins = secs / 60;
							secs = secs % 60;
							int milliseconds = (int) (updatedTime % 1000);
							timer_value.setText("" + mins + ":"
									+ String.format("%02d", secs) + ":"
									+ String.format("%03d", milliseconds));
							customHandler.postDelayed(this, 0);
						}

					};
					
					start.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {							
							startTime = SystemClock.uptimeMillis();							
							customHandler.postDelayed(updateTimerThread, 0);
							start.setEnabled(false);
							mic_image.setImageResource(R.drawable.microphone_recording);
							startRecording();
						}	
					});	
					
					save.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							stopRecording();
							timeSwapBuff += timeInMilliseconds;
							customHandler.removeCallbacks(updateTimerThread);
							dialog_record.dismiss();
							File f = new File(android.os.Environment
		                            .getExternalStorageDirectory()+"/GPS Locater/", "audio.mp3");
							audio=Uri.fromFile(f);
							if (!audio.toString().equals("")) {
								audioImage.setImageResource(R.drawable.audio1);	
								audioImage.setEnabled(true);
							}														
						}
					});
					dialog_record.show();
		        } else if (items[item].equals("Choose from Library")) {
		        	Intent intent = new Intent();
		     	   	intent.setType("audio/mp3");
		     	   	intent.setAction(Intent.ACTION_GET_CONTENT);
		     	   	startActivityForResult(Intent.createChooser(
		     	   				intent, "Open Audio (mp3) file"), RQS_OPEN_AUDIO_MP3);
		        } else if (items[item].equals("Cancel")) {
		            dialog.dismiss();
		        }
		    }
		});
		builder.show();
	}
	
	private void startRecording() {
		recorder = new MediaRecorder();

		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(output_formats);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(getFilename());
		audiofile=getFilename();
		audio=Uri.parse(audiofile);

		recorder.setOnErrorListener(errorListener);
		recorder.setOnInfoListener(infoListener);

		try {
			recorder.prepare();
			recorder.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void stopRecording() {
		if (null != recorder) {
			recorder.stop();
			recorder.reset();
			recorder.release();
			recorder = null;
		}
	}
	
	private String getFilename() {
		String filepath = Environment.getExternalStorageDirectory().getPath();
		File file = new File(filepath, AUDIO_RECORDER_FOLDER);

		if (!file.exists()) {
			file.mkdirs();
		}
		return (file.getAbsolutePath() + "/" + "audio" + file_exts);
	}
	
	private MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
		@Override
		public void onError(MediaRecorder mr, int what, int extra) {
			Toast.makeText(AddNewActivity.this,
					"Error: " + what + ", " + extra, Toast.LENGTH_SHORT).show();
		}
	};

	private MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
		@Override
		public void onInfo(MediaRecorder mr, int what, int extra) {
			Toast.makeText(AddNewActivity.this,
					"Warning: " + what + ", " + extra, Toast.LENGTH_SHORT)
					.show();
		}
	};
	protected void onDestroy() {
		super.onDestroy();
		// TODO Auto-generated method stub
		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int item_id=v.getId();
		switch (item_id) {			
			case R.id.photo_image:
				break;
			case R.id.audio_image:
				btn_count++;
				switch (btn_count%2) {
					case 0:	
						if (mPlayer.isPlaying()) {
							mPlayer.stop();
							audioImage.setImageResource(R.drawable.audio1);
						}
						break;
					case 1:
						audioImage.setImageResource(R.drawable.audio);
						mPlayer = new MediaPlayer();
						mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
						try {
							mPlayer.setDataSource(getApplicationContext(), audio);
						} catch (IllegalArgumentException e) {
							Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
						} catch (SecurityException e) {
							Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
						} catch (IllegalStateException e) {
							Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
						} catch (IOException e) {
							e.printStackTrace();
						}
						try {
							mPlayer.prepare();
						} catch (IllegalStateException e) {
							Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
						} catch (IOException e) {
							Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
						}
						mPlayer.start();
						break;
					default:
						break;
				}				
				break;
			default:
				break;
		}
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(AddNewActivity.this, MainActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left);
		finish();
	}
}
