package com.abma.texttimer;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.abma.texttimer.adapter.ListRecepientPhoneNumberAdapter;
import com.gmc.contact.Contact;

public class DialogFragmentRecepient extends DialogFragment {

	Contact _mContact;
	public DialogFragmentRecepient() {
	}
	
	public void setContact(Contact contact) {
		_mContact = contact;
	}
	
	 @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
	    View v = inflater.inflate(R.layout.fragment_recepient, container, false);
	    if ( _mContact != null ) {
	    	ImageView ivRecepientAvatar = (ImageView)v.findViewById(R.id.ivRecepientAvatar);
	    	TextView tvRecepientName = (TextView)v.findViewById(R.id.tvRecepientName);
	    	ListView listPhoneNumber = (ListView)v.findViewById(R.id.lvPhoneNumber);
	    	
	    	ivRecepientAvatar.setImageBitmap(_mContact.getBitmap());
	    	tvRecepientName.setText(_mContact.getContactName());
	    	
	    	ListRecepientPhoneNumberAdapter adapter = new ListRecepientPhoneNumberAdapter(getActivity(), R.layout.recepient_phonenumber_row, _mContact.getPhoneList());
	    	listPhoneNumber.setAdapter(adapter);
	    }
	    
	    getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
	
	    return v;
	}
}
