package com.abma.texttimer;

import com.abma.texttimer.additional.ChangeFragmentListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class FragmentHome extends Fragment {

	LinearLayout btnHowTos;
	LinearLayout btnAboutUs;
	LinearLayout btnContactUs;
	LinearLayout btnLegalsTerms;
	LinearLayout btnShareThisApp;
	
	public FragmentHome() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        
        System.out.println("FragmentHome onCreateView");
        
        btnHowTos = (LinearLayout)view.findViewById(R.id.layoutButtonHowTos);
        btnAboutUs = (LinearLayout)view.findViewById(R.id.layoutButtonAboutUs);
        btnContactUs = (LinearLayout)view.findViewById(R.id.layoutButtonContactUs);
        btnShareThisApp = (LinearLayout)view.findViewById(R.id.layoutButtonShareThisApp);
        btnHowTos.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((ChangeFragmentListener)getActivity()).changeFragment(MainActivity.HOW_TO);
			}
		});
        btnAboutUs.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((ChangeFragmentListener)getActivity()).changeFragment(MainActivity.ABOUT_US);
			}
		});
        btnContactUs.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((ChangeFragmentListener)getActivity()).changeFragment(MainActivity.CONTACT_US);
			}
		});
        btnShareThisApp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent share = new Intent(android.content.Intent.ACTION_SEND);
			    share.setType("text/plain");
			    share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			 
			    // Add data to the intent, the receiving app will decide
			    // what to do with it.
			    share.putExtra(Intent.EXTRA_SUBJECT, MainActivity.APP_NAME);
			    share.putExtra(Intent.EXTRA_TEXT, "Download this great app at http://abmobileapps.com/apps/qrcodes/texttimer/index.html");
			 
			    startActivity(Intent.createChooser(share, "Share link!"));
			}
		});
        
        this.setRetainInstance(true);
        
        return view;
    }
}
