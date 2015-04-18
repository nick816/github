package com.abma.texttimer;

import com.abma.texttimer.additional.ReadFileFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentAboutUS extends ReadFileFragment {

	TextView tvAboutUSContent;
	public FragmentAboutUS() {
		_filename = "aboutus.txt";
	}
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);

        System.out.println("FragmentAboutUS onCreateView");
        
        tvAboutUSContent = (TextView)view.findViewById(R.id.tvAboutusContent);
     
        tvAboutUSContent.setText(readFromAssetFile());
        
        this.setRetainInstance(true);
        
        return view;
	}

}
