package com.abma.texttimer.adapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.abma.texttimer.R;

@SuppressWarnings("rawtypes")
public class ListRecepientPhoneNumberAdapter extends ArrayAdapter<Map> {
	
	protected LayoutInflater inflater = null;
	
	public ListRecepientPhoneNumberAdapter(Context c, int resourceid, ArrayList<Map> arrays) {
		super(c, resourceid, arrays);
		inflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return super.getCount();
	}

	public Map getItem(int position) {
		return (Map) super.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		return super.getItemId(position);
	}

	@Override
	public View getView(final int position, View convertview, ViewGroup parent) {
		System.out.println("LIST GetView position = " + position);
		View v = convertview;
		ViewHolder viewHolder = null;
		
		final Map phoneNumber = getItem(position);
		
		if (convertview == null) {
			viewHolder = new ViewHolder();
			v = inflater.inflate(R.layout.recepient_phonenumber_row, null);
			viewHolder.tvPhoneNumber = (TextView)v.findViewById(R.id.tvPhoneNumber);
			viewHolder.btnRemove = (Button)v.findViewById(R.id.btnTrash);
			
			viewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					AlertDialog.Builder alert = new AlertDialog.Builder(inflater.getContext());
			        alert.setTitle("Remove");
			        alert.setMessage("Are you sure remove?");
			        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			            @Override
			            public void onClick(DialogInterface dialog, int which) {
			                remove(phoneNumber);
			            }
			        });
			        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			            @Override
			            public void onClick(DialogInterface dialog, int which) {
			                dialog.dismiss();
			            }
			        });
			        alert.show();
				}
			});
			v.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) v.getTag();
		}
		
		@SuppressWarnings("unchecked")
		Iterator<String> iter = phoneNumber.keySet().iterator();
		while(iter.hasNext()) {
			String key = iter.next();
			String value = (String) phoneNumber.get(key);
			int type = Integer.parseInt(key);
			switch (type) {
			case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
			case ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE:
				value = "Mobile: " + value;
				break;

			case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
				value = "Home: " + value;
				break;
			case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
				value = "Work: " + value;
				break;
			default:
				break;
			}
			viewHolder.tvPhoneNumber.setText(value);
			break;
		}
		
		return v;
	}
	
	/*
	 * ViewHolder
	 */
	class ViewHolder{
		public TextView tvPhoneNumber  = null;
		public Button btnRemove = null;
	}

	@Override
	protected void finalize() throws Throwable {
		free();
		super.finalize();
	}
	
	protected void free(){
		inflater = null;
	}
}
