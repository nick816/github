package com.example.slidingsimplesample;
 
import com.example.slidingsimplesample.activity.MessageActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 
public class Fragment_Message extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        View v = inflater.inflate(R.layout.fragment_messages, container, false);
        
        Intent intent = new Intent(getActivity(), MessageActivity.class);
		getActivity().startActivity(intent);
		
        return v;
    }
}