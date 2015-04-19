package com.abma.texttimer;

import com.abma.texttimer.additional.ReadFileFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentHowTos extends ReadFileFragment {

	TextView tvHowtosContent;
	public FragmentHowTos() {
		_filename = "howtos.txt";
	}
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_howtos, container, false);

        System.out.println("FragmentHowTos onCreateView");
        
        tvHowtosContent = (TextView)view.findViewById(R.id.tvAboutusContent);
     
        tvHowtosContent.setText(readFromAssetFile());
        
        this.setRetainInstance(true);
        
        return view;
	}
}
