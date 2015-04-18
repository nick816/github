package com.abma.texttimer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.abma.texttimer.schedule.Schedule;
import com.abma.texttimer.utility.AlarmReceiver;
import com.abma.texttimer.utility.FlowLayout;
import com.gmc.contact.Contact;


@SuppressLint("ValidFragment") public class FragmentCompose extends Fragment {

	private static final String TAG = FragmentCompose.class.toString();
	private static final String[] MONTH_STRINGS = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	private static final int PICK_CONTACT_SUBACTIVITY = 1;
	
	private boolean isEditCompose;
	private static ArrayList<Schedule> _mScheduleList;
	
	RelativeLayout layoutActions;
	Button btnTrash;
	Button btnEdit;
	
	RelativeLayout btnScheduleDate;
	RelativeLayout btnScheduleTime;
	RelativeLayout btnRecepients;
	
	TextView tvScheduleDate;
	TextView tvScheduleTime;
	FlowLayout layoutRecepientsContainer;
	
	RelativeLayout layoutScheduleDateIcon;
	RelativeLayout layoutScheduleTimeIcon;
	RelativeLayout layoutContactIcon;
	
	EditText etMessages;
	
	RelativeLayout btnSave;
	
	int mYear;
	int mMonth;
	int mDay;
	int mHour;
	int mMinute;
	
	ArrayList<Contact> _mContactList;
	
	boolean isSetDateTime = false;
	
	public FragmentCompose() {
		if ( _mScheduleList == null || _mScheduleList.size() <= 0) {
			this.isEditCompose = true;
			this._mContactList = null;
		}
		else {
			this.isEditCompose = false;
			if ( this._mContactList == null )
				this._mContactList = new ArrayList<Contact>();
			for ( int i = 0; i < _mScheduleList.size(); i++ )
				this._mContactList.add(_mScheduleList.get(i).getRecipient());
		}
	}
	
	public static void setState(Schedule schedule) {
		_mScheduleList = new ArrayList<Schedule>();
		if ( schedule == null )
			return;
		
		_mScheduleList.add(schedule);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
	}
	
	@SuppressWarnings("deprecation")
	@SuppressLint("SimpleDateFormat") 
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compose, container, false);
                
        layoutActions = (RelativeLayout)view.findViewById(R.id.layoutActions);
        btnTrash = (Button)view.findViewById(R.id.btnTrash);
        btnEdit = (Button)view.findViewById(R.id.btnEdit);

        btnScheduleDate = (RelativeLayout)view.findViewById(R.id.layoutScheduleDate);
        btnScheduleTime = (RelativeLayout)view.findViewById(R.id.layoutScheduleTime);
        btnRecepients = (RelativeLayout)view.findViewById(R.id.layoutButtonContacts);
        
        tvScheduleDate = (TextView)view.findViewById(R.id.tvScheduleDate);
        tvScheduleTime = (TextView)view.findViewById(R.id.tvScheduleTime);
        layoutRecepientsContainer = (FlowLayout)view.findViewById(R.id.recepientsContainer);
        
        layoutScheduleDateIcon = (RelativeLayout)view.findViewById(R.id.layoutCalendarIcon);
        layoutScheduleTimeIcon = (RelativeLayout)view.findViewById(R.id.layoutUpdownIcon);
        layoutContactIcon = (RelativeLayout)view.findViewById(R.id.layoutContactsIcon);
        
        etMessages = (EditText)view.findViewById(R.id.etEmailAddress);
        
        btnSave = (RelativeLayout)view.findViewById(R.id.layoutButtonSave);
        
        if ( _mScheduleList != null && _mScheduleList.size() > 0 ) {
        	Schedule schedule = _mScheduleList.get(0);
    		try {
    			DateFormat df = new SimpleDateFormat("MMM dd h:mm aa");
    			Date reservationDateTime = (Date)df.parse(schedule.getReservationDateTime());
    			mYear = schedule.getYear();
        		mMonth = reservationDateTime.getMonth();
        		mDay = reservationDateTime.getDate();
        		mHour = reservationDateTime.getHours();
        		mMinute = reservationDateTime.getMinutes();
        		
        		isSetDateTime = true;
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
    		
    		etMessages.setText(schedule.getMessage());
        }
        
        if ( mYear == 0 ) {
			Calendar cal = new GregorianCalendar();
			mYear = cal.get(Calendar.YEAR);
			mMonth = cal.get(Calendar.MONTH);
			mDay = cal.get(Calendar.DAY_OF_MONTH);
			mHour = cal.get(Calendar.HOUR_OF_DAY);
			mMinute = cal.get(Calendar.MINUTE);
		}
        
        updateDate();
        updateTime();
        
        if ( _mContactList == null )
        	_mContactList = new ArrayList<Contact>();
        
        for ( int i = 0; i < _mContactList.size(); i++ ) {
        	addRecepient(_mContactList.get(i), i);
        }

        displayLayout();
        
        btnScheduleDate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
					
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
							int dayOfMonth) {
						mYear = year;
						mMonth = monthOfYear;
						mDay = dayOfMonth;
						isSetDateTime = true;
						updateDate();
					}
				};
				
				
				System.out.println(mYear + ":" + mMonth + ":" + mDay);
				DatePickerDialog datePicker = new DatePickerDialog(FragmentCompose.this.getActivity(), mDateSetListener, mYear, mMonth, mDay);
				datePicker.getDatePicker().setMinDate(new Date().getTime() - 1000);
				datePicker.show();
			}
		});
        
        btnScheduleTime.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
					
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						mHour = hourOfDay;
						mMinute = minute;
						isSetDateTime = true;
						String date = tvScheduleDate.getText().toString();
						Date selDate = new Date(date);
						Date curDateTime = new Date();
						if ( selDate.getYear() == curDateTime.getYear() && selDate.getMonth() == curDateTime.getMonth() && selDate.getDate() == curDateTime.getDate() ) {
							if ( mHour == curDateTime.getHours() && mMinute <= curDateTime.getMinutes() ) {
								mMinute = curDateTime.getMinutes(); 
							}
							else if ( mHour < curDateTime.getHours() ) {
								mHour = curDateTime.getHours();
								mMinute = curDateTime.getMinutes();
							}
						}
						updateTime();
					}
				};
				new TimePickerDialog(FragmentCompose.this.getActivity(), mTimeSetListener, mHour, mMinute, false).show();
			}
		});
        btnRecepients.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pickContact();
			}
		});
        
        btnSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if ( _mContactList.size() == 0 ) {
					AlertDialog.Builder dlgAlert = new AlertDialog.Builder(FragmentCompose.this.getActivity());
					dlgAlert.setMessage("There are no selected recepients.").setCancelable(false).setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
					        // 'YES'
					    }
					});
					AlertDialog alert = dlgAlert.create();
					alert.show();
				}
				else if ( etMessages.getText().toString().isEmpty() ) {
					AlertDialog.Builder dlgAlert = new AlertDialog.Builder(FragmentCompose.this.getActivity());
					dlgAlert.setMessage("Message is empty.").setCancelable(false).setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
					        // 'YES'
					    }
					});
					AlertDialog alert = dlgAlert.create();
					alert.show();
				}
				else if ( isValidDateTime() == true ) {
					addSchedule();
					((MainActivity)getActivity()).onBackPressed();
				}
				else {
					AlertDialog.Builder dlgAlert = new AlertDialog.Builder(FragmentCompose.this.getActivity());
					dlgAlert.setMessage("Datetime is invalid.").setCancelable(false).setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
					        // 'YES'
					    }
					});
					AlertDialog alert = dlgAlert.create();
					alert.show();
				}
			}
		});
        
        btnEdit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				isEditCompose = true;
				displayLayout();
			}
		});
        
        btnTrash.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder alert_confirm = new AlertDialog.Builder(getActivity());
				alert_confirm.setMessage("Do you really want to delete?").setCancelable(false).setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				    	removeSchedule();
						((MainActivity)getActivity()).onBackPressed();
				    }
				}).setNegativeButton("No",
				new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				    	return;
				    }
				});
				AlertDialog alert = alert_confirm.create();
				alert.show();
			}
		});
        
        this.setRetainInstance(true);
        
        return view;
    }

	private void displayLayout() {
		if ( isEditCompose == false ) {
			layoutActions.setVisibility(View.VISIBLE);
			layoutScheduleDateIcon.setVisibility(View.GONE);
			layoutScheduleTimeIcon.setVisibility(View.GONE);
			layoutContactIcon.setVisibility(View.GONE);
			btnRecepients.setVisibility(View.GONE);
			btnScheduleDate.setClickable(false);
			btnScheduleTime.setClickable(false);
			etMessages.setEnabled(false);
			btnSave.setVisibility(View.GONE);
		}
		else {
			layoutActions.setVisibility(View.GONE);
			layoutScheduleDateIcon.setVisibility(View.VISIBLE);
			layoutScheduleTimeIcon.setVisibility(View.VISIBLE);
			layoutContactIcon.setVisibility(View.VISIBLE);
			btnRecepients.setVisibility(View.VISIBLE);
			btnScheduleDate.setClickable(true);
			btnScheduleTime.setClickable(true);
			etMessages.setEnabled(true);
			btnSave.setVisibility(View.VISIBLE);
		}
	}
	private void updateDate() {
		tvScheduleDate.setText(MONTH_STRINGS[mMonth] + " " + mDay + " " + mYear);
	}
	
	private void updateTime() {
		String time = "";
		String ampm = "AM";
		int hour = mHour;
		if ( hour >= 12 ) {
			hour -= 12;
			ampm = "PM";
		}
		if ( hour == 0 )
			hour = 12;
		if ( hour < 10 )
			time = "0" + hour;
		else
			time = "" + hour;
		
		time += ":";
		
		if ( mMinute < 10 ) 
			time += "0" + mMinute;
		else
			time += mMinute;
		time += " " + ampm;
		tvScheduleTime.setText(time);
	}
	
	@SuppressWarnings("deprecation")
	protected boolean isValidDateTime() {
		Date curDateTime = new Date();
		if ( isSetDateTime == false ) {
			Date datetime = new Date(curDateTime.getTime() + 60 * 1000);
			mYear = datetime.getYear() + 1900;
			mMonth = datetime.getMonth();
			mDay = datetime.getDate();
			mHour = datetime.getHours();
			mMinute = datetime.getMinutes();
			return true;
		}
		Date selDate = new Date(mYear - 1900, mMonth, mDay, mHour, mMinute, 0);
		System.out.println(curDateTime + ":" + selDate);
		if ( curDateTime.compareTo(selDate) > 0 )
			return false;
		return true;
	}
	
	private void addRecepient(final Contact contact, final int index) {
		int margin = getActivity().getResources().getDimensionPixelSize(R.dimen.recepient_item_padding) / 3;
		FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(margin, margin, margin, margin);
		final TextView recepientItem = new TextView(this.getActivity());
		recepientItem.setBackgroundResource(R.drawable.recepient_item);
		recepientItem.setClickable(true);
		recepientItem.setPadding(20, 10, 20, 10);
		recepientItem.setTextSize(12);
		recepientItem.setLayoutParams(params);
		if ( contact.getContactName() != null )
			recepientItem.setText(contact.getContactName() + "\n" + contact.getPhoneNumbers(this.getActivity()));
		recepientItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final CharSequence[] items = {
		                "Remove Recepient", "Cancel"
		        };

		        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		        builder.setTitle(contact.getContactName());
		        builder.setItems(items, 
	        		new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int item) {
			                if ( item == 0 ) {
			                	layoutRecepientsContainer.removeView(recepientItem);
			                	_mContactList.remove(index);
			                }
			            }
		        });
		        AlertDialog alert = builder.create();
		        alert.show();
			}
		});
        layoutRecepientsContainer.addView(recepientItem);
	}
	
	private void pickContact() {
		Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
	    intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
	    startActivityForResult(intent, PICK_CONTACT_SUBACTIVITY);
	}
	
	@Override  
	public void onActivityResult(int reqCode, int resultCode, Intent data) {  
	    super.onActivityResult(reqCode, resultCode, data);  
	    
	    if (reqCode == PICK_CONTACT_SUBACTIVITY && resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "Response: " + data.toString());
            Uri uriContact = data.getData();
            
            Contact contact = readContacts(uriContact);
            if ( contact != null ) {
            	addRecepient(contact, _mContactList.size());
            	_mContactList.add(contact);
            	Log.d(TAG, contact.toString());
            }
        }
	}
	
	public Contact readContacts(Uri uriContact) 
	{ 
		Contact contact = null;
		
		ContentResolver cr = this.getActivity().getContentResolver(); 
		Cursor cur = cr.query(uriContact, null, null, null, null); 

		if (cur != null && cur.moveToFirst()) { 
			if (Integer.parseInt(cur.getString(cur .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) { 
				String id = cur.getString(cur .getColumnIndex(ContactsContract.Contacts._ID));
				String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)); 
				String phoneType = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
				String phoneNumber = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				
				Contact prevContact = findContact(id);
            	if ( prevContact != null ) {
            		if ( hasPhoneNumber(prevContact, phoneNumber) == false ) {
            			prevContact.addPhoneNumber(phoneType, phoneNumber);
            		}
        			return null;
            	}
            	
				contact = new Contact();
				contact.setContactId(id); 
				contact.setContactName(name);
				contact.addPhoneNumber(phoneType, phoneNumber);
				
				String image_uri = cur .getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)); 
				if (image_uri != null) { 
					System.out.println(Uri.parse(image_uri)); 
					contact.setImageUri(image_uri);
					try { 
						contact.setBitmap(MediaStore.Images.Media .getBitmap(this.getActivity().getContentResolver(), Uri.parse(image_uri))); 
					} catch (FileNotFoundException e) { 
						e.printStackTrace(); 
					} catch (IOException e) {  
						e.printStackTrace(); 
					} 
				} 
			} 
		} 
		return contact;
	} 
	
	private Contact findContact(String contactid) {
		if ( _mContactList != null ) {
			for ( int i = 0; i < _mContactList.size(); i++ ) {
				Contact contact = _mContactList.get(i);
				if ( contact.getContactId().equals(contactid) == true )
					return contact;
			}
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean hasPhoneNumber(Contact contact, String phonenumber) {
		ArrayList<Map> phoneList = contact.getPhoneList();
		if ( phoneList != null ) {
			for ( int i = 0; i < phoneList.size(); i++ ) {
				Map map = phoneList.get(i);
				Iterator<String> iter = map.keySet().iterator();
				while(iter.hasNext()) {
					String value = (String) map.get(iter.next());
					if (value.equals(phonenumber))
						return true;
				}
			}
		}
		return false;
	}
	
	private void addSchedule() {
		FragmentSchedule frgSchedule = FragmentSchedule.getInstance();
		ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();
		for ( int i = 0; i < _mContactList.size(); i++ ) {
			Contact contact = _mContactList.get(i);
			Schedule schedule = null;
			for ( int j = 0; j < _mScheduleList.size(); j++ ) {
				Schedule prevSchedule = _mScheduleList.get(j);
				Contact prevContact = prevSchedule.getRecipient();
				if ( prevContact.isEqual(contact) ) {
					schedule = prevSchedule;
					schedule.setMessage(etMessages.getText().toString());
					schedule.setReservationDateTime(mYear, mMonth, mDay, mHour, mMinute);
					
					if ( schedule.isSent() == true ) {
						schedule.setState(false);
						frgSchedule.changeSchedule(schedule);
					}
					else {
						cancelAlarm(schedule);
						frgSchedule.updateSchedule(schedule, true);
					}
					_mScheduleList.remove(j);
					break;
				}
			}
			if ( schedule == null ) {
				schedule = new Schedule();
				schedule.setMessage(etMessages.getText().toString());
				schedule.setReservationDateTime(mYear, mMonth, mDay, mHour, mMinute);
				schedule.setRecepient(contact);
				schedule.setState(false);
				frgSchedule.addSchedule(schedule, true);
			}
			
			scheduleList.add(schedule);
		}
		cancelAlarms(_mScheduleList);
		for ( int i = _mScheduleList.size() - 1; i >= 0; i-- ) {
			frgSchedule.removeSchedule(_mScheduleList.get(i), true);
			_mScheduleList.remove(i);
		}
		_mScheduleList = scheduleList;
		setAlarms(_mScheduleList);
		frgSchedule.refreshSchedule();
	}
	private void removeSchedule() {
		FragmentSchedule frgSchedule = FragmentSchedule.getInstance();
		cancelAlarms(_mScheduleList);
		for ( int i = _mScheduleList.size() - 1; i >= 0; i-- ) {
			frgSchedule.removeSchedule(_mScheduleList.get(i), true);
			_mScheduleList.remove(i);
		}
		frgSchedule.refreshSchedule();
	}
	private void setAlarms(ArrayList<Schedule> scheduleList) {
		if ( scheduleList == null )
			return;
		for ( int i = 0; i < scheduleList.size(); i++ ) {
			setAlarm(scheduleList.get(i));
		}
	}
	private void cancelAlarms(ArrayList<Schedule> scheduleList) {
		if ( scheduleList == null )
			return;
		for ( int i = 0; i < scheduleList.size(); i++ ) {
			cancelAlarm(scheduleList.get(i));
		}
	}
	private void setAlarm(Schedule schedule) {
		Intent intent = new Intent(this.getActivity(), AlarmReceiver.class);
		intent.putExtra("phoneNumbers", schedule.getRecipient().getPhoneNumbers());
		intent.putExtra("message", schedule.getMessage());
		intent.putExtra("alarmid", schedule.getScheduleId());
		intent.putExtra("receiver", schedule.getRecipientName());
		PendingIntent pi = PendingIntent.getBroadcast(this.getActivity(), Integer.parseInt(schedule.getScheduleId()), intent, 0);
		AlarmManager alarmManager = (AlarmManager)this.getActivity().getSystemService(Activity.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, schedule.getReservationTime(),pi);
	}
	private void cancelAlarm(Schedule schedule) {
		Intent intent = new Intent(this.getActivity(), AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this.getActivity(), Integer.parseInt(schedule.getScheduleId()), intent, 0);
        
        AlarmManager alarmManager = (AlarmManager) this.getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pi);
	}
}
